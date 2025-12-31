package compiler.visitors;

import compiler.ast.*;
import compiler.ast.flask.*;
import grammar.PythonParser;
import grammar.PythonParserBaseVisitor;

import java.util.*;


public class SimplePythonASTBuilder extends PythonParserBaseVisitor<ASTNode> {

    // Track Flask imports for semantic analysis
    private final Set<String> flaskImports = new HashSet<>();
    private final Set<String> flaskAppNames = new HashSet<>();
    private boolean isInMainGuard = false;

    @Override
    public ASTNode visitFile_input(PythonParser.File_inputContext ctx) {
        ProgramNode program = new ProgramNode(ctx.start != null ? ctx.start.getLine() : 1);
        for (PythonParser.StmtContext stmt : ctx.stmt()) {
            ASTNode node = visit(stmt);
            if (node != null) program.addChild(node);
        }
        return program;
    }

    @Override
    public ASTNode visitAssignmentStmt(PythonParser.AssignmentStmtContext ctx) {
        int line = ctx.start.getLine();
        ExpressionNode target = (ExpressionNode) visit(ctx.assignment().atom_expr());
        ExpressionNode value = (ExpressionNode) visit(ctx.assignment().expr());

        // Check for Flask app instantiation: app = Flask(__name__)
        if (target instanceof IdentifierNode && isFlaskAppInstantiation(value)) {
            String appName = ((IdentifierNode) target).getName();
            flaskAppNames.add(appName);

            String moduleName = extractFlaskModuleName(value);
            return new FlaskAppNode(appName, moduleName, null, line);
        }

        return new AssignmentNode(List.of(target), value, line);
    }

    private boolean isFlaskAppInstantiation(ExpressionNode expr) {
        if (!(expr instanceof FunctionCallNode call)) return false;

        ExpressionNode func = call.getFunction();
        if (func instanceof IdentifierNode id) {
            return "Flask".equals(id.getName()) && flaskImports.contains("Flask");
        }
        return false;
    }

    private String extractFlaskModuleName(ExpressionNode expr) {
        if (!(expr instanceof FunctionCallNode call)) return "__name__";

        List<ExpressionNode> args = call.getArguments();
        if (!args.isEmpty()) {
            ExpressionNode firstArg = args.get(0);
            if (firstArg instanceof IdentifierNode id) {
                return id.getName();
            }
            if (firstArg instanceof StringLiteralNode str) {
                return str.getValue();
            }
        }
        return "__name__";
    }

    @Override
    public ASTNode visitExprStmt(PythonParser.ExprStmtContext ctx) {
        return visit(ctx.expr_stmt().expr());
    }

    @Override
    public ASTNode visitGlobalStmt(PythonParser.GlobalStmtContext ctx) {
        // Grammar: global_stmt : GLOBAL NAME (COMMA NAME)*
        var globalStmt = ctx.global_stmt();
        List<String> names = new ArrayList<>();
        for (var nameToken : globalStmt.NAME()) {
            names.add(nameToken.getText());
        }
        return new GlobalStatementNode(names, ctx.start.getLine());
    }

    @Override
    public ASTNode visitReturnStmt(PythonParser.ReturnStmtContext ctx) {
        // Grammar: RETURN (expr (COMMA expr)*)?
        var returnStmt = ctx.return_stmt();
        List<PythonParser.ExprContext> exprs = returnStmt.expr();

        if (exprs == null || exprs.isEmpty()) {
            return new ReturnStatementNode(null, ctx.start.getLine());
        }

        // If single expression, return it directly
        if (exprs.size() == 1) {
            ExpressionNode value = (ExpressionNode) visit(exprs.get(0));
            return new ReturnStatementNode(value, ctx.start.getLine());
        }

        // Multiple expressions create a tuple
        List<ExpressionNode> elements = new ArrayList<>();
        for (var e : exprs) {
            elements.add((ExpressionNode) visit(e));
        }
        TupleNode tuple = new TupleNode(elements, ctx.start.getLine());
        return new ReturnStatementNode(tuple, ctx.start.getLine());
    }


    @Override
    public ASTNode visitFunctionDefStmt(PythonParser.FunctionDefStmtContext ctx) {
        return visit(ctx.funcdef());
    }

    @Override
    public ASTNode visitFuncdef(PythonParser.FuncdefContext ctx) {
        int line = ctx.start.getLine();
        String name = ctx.NAME().getText();

        List<ParameterNode> params = new ArrayList<>();
        if (ctx.parameters().NAME() != null) {
            for (var n : ctx.parameters().NAME()) {
                params.add(new ParameterNode(n.getText(), null, null, line));
            }
        }

        ASTNode suite = visit(ctx.suite());
        return new FunctionDefNode(name, params, suite.getChildren(), new ArrayList<>(), null, line);
    }

    @Override
    public ASTNode visitClassDefStmt(PythonParser.ClassDefStmtContext ctx) {
        return visit(ctx.classdef());
    }

    @Override
    public ASTNode visitClassdef(PythonParser.ClassdefContext ctx) {
        int line = ctx.start.getLine();
        String name = ctx.NAME().getText();
        ASTNode suite = visit(ctx.suite());
        return new ClassDefNode(name, new ArrayList<>(), suite.getChildren(), new ArrayList<>(), line);
    }


    @Override
    public ASTNode visitDecorated(PythonParser.DecoratedContext ctx) {
        int line = ctx.start.getLine();

        // Build list of decorators
        List<DecoratorNode> decorators = new ArrayList<>();
        List<RouteDecoratorNode> routeDecorators = new ArrayList<>();

        for (PythonParser.DecoratorContext decoratorCtx : ctx.decorator()) {
            DecoratorNode decorator = buildDecorator(decoratorCtx);
            decorators.add(decorator);

            // Track route decorators for Flask
            if (decorator instanceof RouteDecoratorNode routeDecorator) {
                routeDecorators.add(routeDecorator);
            }
        }

        // Build the decorated definition (function or class)
        if (ctx.funcdef() != null) {
            FunctionDefNode funcDef = (FunctionDefNode) visit(ctx.funcdef());
            funcDef.setDecorators(decorators);

            // If this has route decorators, create FlaskRouteFunction
            if (!routeDecorators.isEmpty()) {
                return new FlaskRouteFunction(funcDef, routeDecorators, line);
            }

            return funcDef;
        }

        if (ctx.classdef() != null) {
            ClassDefNode classDef = (ClassDefNode) visit(ctx.classdef());
            classDef.setDecorators(decorators);
            return classDef;
        }

        return null;
    }

    private DecoratorNode buildDecorator(PythonParser.DecoratorContext ctx) {
        int line = ctx.start.getLine();

        // Build decorator expression (e.g., "app.route" or "staticmethod")
        ExpressionNode decoratorExpr = (ExpressionNode) visit(ctx.atom_expr());

        // Build arguments list if call exists
        List<ExpressionNode> args = new ArrayList<>();
        if (ctx.call() != null && ctx.call().arglist() != null) {
            var arglist = ctx.call().arglist();
            if (arglist instanceof PythonParser.NormalArgListContext normalArgList) {
                for (var argCtx : normalArgList.argument()) {
                    args.add((ExpressionNode) visit(argCtx));
                }
            } else if (arglist instanceof PythonParser.GeneratorArgListContext genArgList) {
                ExpressionNode element = (ExpressionNode) visit(genArgList.expr());
                ComprehensionClause clause = buildComprehensionClause(genArgList.comp_for());
                args.add(new GeneratorExpressionNode(element, clause, genArgList.start.getLine()));
            }
        }

        // Check if this is a Flask route decorator
        if (isFlaskRouteDecorator(decoratorExpr)) {
            return buildRouteDecorator(decoratorExpr, args, line);
        }

        return new DecoratorNode(decoratorExpr, args, line);
    }

    private boolean isFlaskRouteDecorator(ExpressionNode expr) {
        if (!(expr instanceof MemberAccessNode member)) return false;

        String memberName = member.getMemberName();
        if (!"route".equals(memberName)) return false;

        // Check if object is a registered Flask app
        ExpressionNode obj = member.getObject();
        if (obj instanceof IdentifierNode id) {
            return flaskAppNames.contains(id.getName());
        }

        return false;
    }

    private RouteDecoratorNode buildRouteDecorator(ExpressionNode decoratorExpr,
                                                   List<ExpressionNode> args,
                                                   int line) {
        MemberAccessNode member = (MemberAccessNode) decoratorExpr;
        String appRef = ((IdentifierNode) member.getObject()).getName();

        // Extract route path (first positional argument)
        String routePath = "/";
        if (!args.isEmpty()) {
            ExpressionNode firstArg = args.get(0);
            if (firstArg instanceof StringLiteralNode str) {
                routePath = str.getValue();
            } else if (firstArg instanceof KeywordArgNode kwarg && "rule".equals(kwarg.getKey())) {
                if (kwarg.getValue() instanceof StringLiteralNode str) {
                    routePath = str.getValue();
                }
            }
        }

        // Extract HTTP methods
        List<String> methods = new ArrayList<>();
        for (ExpressionNode arg : args) {
            if (arg instanceof KeywordArgNode kwarg && "methods".equals(kwarg.getKey())) {
                methods = extractMethodsList(kwarg.getValue());
                break;
            }
        }
        if (methods.isEmpty()) {
            methods.add("GET");  // Default HTTP method
        }

        return new RouteDecoratorNode(appRef, routePath, methods, args, line);
    }

    private List<String> extractMethodsList(ExpressionNode expr) {
        List<String> methods = new ArrayList<>();

        if (expr instanceof ListLiteralNode list) {
            for (ExpressionNode elem : list.getElements()) {
                if (elem instanceof StringLiteralNode str) {
                    methods.add(str.getValue().toUpperCase());
                }
            }
        }

        return methods;
    }

     @Override
    public ASTNode visitIfStmt(PythonParser.IfStmtContext ctx) {
        int line = ctx.start.getLine();
        ExpressionNode condition = (ExpressionNode) visit(ctx.if_stmt().expr(0));

        ASTNode thenSuite = visit(ctx.if_stmt().suite(0));
        List<ASTNode> elseBlock = new ArrayList<>();

        if (ctx.if_stmt().suite().size() > 1) {
            ASTNode elseSuite = visit(ctx.if_stmt().suite(1));
            elseBlock = elseSuite.getChildren();
        }

        return new IfStatementNode(condition, thenSuite.getChildren(), elseBlock, line);
    }

    @Override
    public ASTNode visitForStmt(PythonParser.ForStmtContext ctx) {
        int line = ctx.start.getLine();
        IdentifierNode target = new IdentifierNode(ctx.for_stmt().NAME().getText(), line);
        ExpressionNode iterable = (ExpressionNode) visit(ctx.for_stmt().expr());
        ASTNode body = visit(ctx.for_stmt().suite(0));
        return new ForStatementNode(target, iterable, body.getChildren(), new ArrayList<>(), line);
    }

    @Override
    public ASTNode visitWhileStmt(PythonParser.WhileStmtContext ctx) {
        int line = ctx.start.getLine();
        ExpressionNode cond = (ExpressionNode) visit(ctx.while_stmt().expr());
        ASTNode body = visit(ctx.while_stmt().suite(0));
        return new WhileStatementNode(cond, body.getChildren(), new ArrayList<>(), line);
    }

    @Override
    public ASTNode visitSuite(PythonParser.SuiteContext ctx) {
        ProgramNode block = new ProgramNode(ctx.start.getLine());
        for (PythonParser.StmtContext stmt : ctx.stmt()) {
            ASTNode node = visit(stmt);
            if (node != null) block.addChild(node);
        }
        return block;
    }


    // ═══════════════════════════════════════════════════════════════════════════
    // EXPRESSION HANDLING - Updated for full Python expression grammar
    // ═══════════════════════════════════════════════════════════════════════════

    @Override
    public ASTNode visitConditionalExpr(PythonParser.ConditionalExprContext ctx) {
        // Grammar: or_test (IF or_test ELSE expr)?
        ExpressionNode value = (ExpressionNode) visit(ctx.or_test(0));

        if (ctx.IF() != null) {
            // Ternary conditional expression: value if condition else alternative
            ExpressionNode condition = (ExpressionNode) visit(ctx.or_test(1));
            ExpressionNode alternative = (ExpressionNode) visit(ctx.expr());
            return new TernaryNode(condition, value, alternative, ctx.start.getLine());
        }

        return value;
    }

    @Override
    public ASTNode visitOrExpr(PythonParser.OrExprContext ctx) {
        // Grammar: and_test (OR and_test)*
        ASTNode result = visit(ctx.and_test(0));
        for (int i = 1; i < ctx.and_test().size(); i++) {
            ASTNode right = visit(ctx.and_test(i));
            result = new BinaryOpNode("or", (ExpressionNode) result, (ExpressionNode) right, ctx.start.getLine());
        }
        return result;
    }

    @Override
    public ASTNode visitAndExpr(PythonParser.AndExprContext ctx) {
        // Grammar: not_test (AND not_test)*
        ASTNode result = visit(ctx.not_test(0));
        for (int i = 1; i < ctx.not_test().size(); i++) {
            ASTNode right = visit(ctx.not_test(i));
            result = new BinaryOpNode("and", (ExpressionNode) result, (ExpressionNode) right, ctx.start.getLine());
        }
        return result;
    }

    @Override
    public ASTNode visitNotExpr(PythonParser.NotExprContext ctx) {
        // Grammar: NOT not_test
        ExpressionNode operand = (ExpressionNode) visit(ctx.not_test());
        return new UnaryOpNode("not", operand, ctx.start.getLine());
    }

    @Override
    public ASTNode visitComparisonExpr(PythonParser.ComparisonExprContext ctx) {
        // Just delegate to comparison
        return visit(ctx.comparison());
    }

    @Override
    public ASTNode visitComparison(PythonParser.ComparisonContext ctx) {
        // Grammar: arith_expr (comp_op arith_expr)*
        ASTNode result = visit(ctx.arith_expr(0));

        List<PythonParser.Comp_opContext> ops = ctx.comp_op();
        for (int i = 0; i < ops.size(); i++) {
            String op = ops.get(i).getText();
            ASTNode right = visit(ctx.arith_expr(i + 1));
            result = new ComparisonNode(op, (ExpressionNode) result, (ExpressionNode) right, ctx.start.getLine());
        }

        return result;
    }

    @Override
    public ASTNode visitArith_expr(PythonParser.Arith_exprContext ctx) {
        // Grammar: term ((PLUS | MINUS) term)*
        ASTNode left = visit(ctx.term(0));
        for (int i = 1; i < ctx.term().size(); i++) {
            String op = ctx.getChild(2 * i - 1).getText();
            ASTNode right = visit(ctx.term(i));
            left = new BinaryOpNode(op, (ExpressionNode) left, (ExpressionNode) right, ctx.start.getLine());
        }
        return left;
    }

    @Override
    public ASTNode visitTerm(PythonParser.TermContext ctx) {
        // Grammar: factor ((STAR | DIV | MOD | FLOORDIV) factor)*
        ASTNode left = visit(ctx.factor(0));
        for (int i = 1; i < ctx.factor().size(); i++) {
            String op = ctx.getChild(2 * i - 1).getText();
            ASTNode right = visit(ctx.factor(i));
            left = new BinaryOpNode(op, (ExpressionNode) left, (ExpressionNode) right, ctx.start.getLine());
        }
        return left;
    }

    @Override
    public ASTNode visitFactor(PythonParser.FactorContext ctx) {
        // Grammar: (PLUS | MINUS)? atom_expr
        ASTNode expr = visit(ctx.atom_expr());

        if (ctx.PLUS() != null) {
            return new UnaryOpNode("+", (ExpressionNode) expr, ctx.start.getLine());
        }
        if (ctx.MINUS() != null) {
            return new UnaryOpNode("-", (ExpressionNode) expr, ctx.start.getLine());
        }

        return expr;
    }

    @Override
    public ASTNode visitAtom_expr(PythonParser.Atom_exprContext ctx) {
        ASTNode base = visit(ctx.atom());
        for (var trailer : ctx.trailer()) {
            base = visitTrailerWithBase(trailer, base);
        }
        return base;
    }

    private ASTNode visitTrailerWithBase(PythonParser.TrailerContext ctx, ASTNode base) {
        int line = ctx.start.getLine();

        if (ctx instanceof PythonParser.CallTrailerContext call) {
            List<ExpressionNode> args = new ArrayList<>();
            var arglist = call.call().arglist();
            if (arglist != null) {
                // Handle different arglist types
                if (arglist instanceof PythonParser.GeneratorArgListContext genArgList) {
                    // Generator expression as sole argument
                    ExpressionNode element = (ExpressionNode) visit(genArgList.expr());
                    ComprehensionClause clause = buildComprehensionClause(genArgList.comp_for());
                    args.add(new GeneratorExpressionNode(element, clause, genArgList.start.getLine()));
                } else if (arglist instanceof PythonParser.NormalArgListContext normalArgList) {
                    // Normal argument list
                    for (var a : normalArgList.argument()) {
                        args.add((ExpressionNode) visit(a));
                    }
                }
            }
            return new FunctionCallNode((ExpressionNode) base, args, line);
        }

        if (ctx instanceof PythonParser.MemberAccessTrailerContext mem) {
            return new MemberAccessNode((ExpressionNode) base, mem.NAME().getText(), line);
        }

        if (ctx instanceof PythonParser.SubscriptTrailerContext sub) {
            return handleSubscript((ExpressionNode) base, sub.subscript(), line);
        }

        return base;
    }

    private ASTNode handleSubscript(ExpressionNode base, PythonParser.SubscriptContext ctx, int line) {
        if (ctx instanceof PythonParser.IndexSubscriptContext idx) {
            return new IndexAccessNode(base, (ExpressionNode) visit(idx.expr()), line);
        }

        if (ctx instanceof PythonParser.SliceSubscriptContext slice) {
            // Handle slice: [start:stop:step] where any part can be omitted
            ExpressionNode start = null;
            ExpressionNode stop = null;
            ExpressionNode step = null;

            List<PythonParser.Slice_argContext> args = slice.slice_arg();
            int argIndex = 0;

            // Parse according to colon positions
            // slice_arg? COLON slice_arg? (COLON slice_arg?)?
            String text = ctx.getText();
            int firstColon = text.indexOf(':');
            int secondColon = text.indexOf(':', firstColon + 1);

            // Get start if present (before first colon)
            if (args.size() > argIndex && firstColon > 0) {
                start = (ExpressionNode) visit(args.get(argIndex++));
            }

            // Get stop if present (between first and second colon)
            if (args.size() > argIndex && secondColon != firstColon + 1 && secondColon != -1) {
                stop = (ExpressionNode) visit(args.get(argIndex++));
            } else if (args.size() > argIndex && secondColon == -1 && args.size() - argIndex >= 1) {
                // No second colon, so this is the stop
                stop = (ExpressionNode) visit(args.get(argIndex++));
            }

            // Get step if present (after second colon)
            if (args.size() > argIndex && secondColon != -1) {
                step = (ExpressionNode) visit(args.get(argIndex));
            }

            return new SliceNode(base, start, stop, step, line);
        }

        // Fallback - treat as index access
        return new IndexAccessNode(base, (ExpressionNode) visit(ctx), line);
    }

    @Override
    public ASTNode visitNameAtom(PythonParser.NameAtomContext ctx) {
        return new IdentifierNode(ctx.NAME().getText(), ctx.start.getLine());
    }

    @Override
    public ASTNode visitNumberAtom(PythonParser.NumberAtomContext ctx) {
        return new NumberLiteralNode(Double.parseDouble(ctx.NUMBER().getText()), ctx.start.getLine());
    }

    @Override
    public ASTNode visitStringAtom(PythonParser.StringAtomContext ctx) {
        // Grammar: STRING+ (allows implicit string concatenation)
        StringBuilder sb = new StringBuilder();
        for (var strToken : ctx.STRING()) {
            String t = strToken.getText();
            // Remove quotes (could be single, double, or triple-quoted)
            String content;
            if (t.startsWith("'''") || t.startsWith("\"\"\"")) {
                content = t.substring(3, t.length() - 3);
            } else {
                content = t.substring(1, t.length() - 1);
            }
            sb.append(content);
        }
        return new StringLiteralNode(sb.toString(), ctx.start.getLine());
    }

    @Override
    public ASTNode visitTrueAtom(PythonParser.TrueAtomContext ctx) {
        return new BooleanLiteralNode(true, ctx.start.getLine());
    }

    @Override
    public ASTNode visitFalseAtom(PythonParser.FalseAtomContext ctx) {
        return new BooleanLiteralNode(false, ctx.start.getLine());
    }

    @Override
    public ASTNode visitNoneAtom(PythonParser.NoneAtomContext ctx) {
        return new NoneLiteralNode(ctx.start.getLine());
    }

    @Override
    public ASTNode visitParenAtom(PythonParser.ParenAtomContext ctx) {
        // Grammar: LPAREN tupleOrGenExp RPAREN
        return visit(ctx.tupleOrGenExp());
    }


    @Override
    public ASTNode visitEmptyParens(PythonParser.EmptyParensContext ctx) {
        // Empty tuple
        return new TupleNode(new ArrayList<>(), ctx.start.getLine());
    }

    @Override
    public ASTNode visitSingleExpr(PythonParser.SingleExprContext ctx) {
        // Single expression in parens - not a tuple, just grouping
        return visit(ctx.expr());
    }

    @Override
    public ASTNode visitTupleExpr(PythonParser.TupleExprContext ctx) {
        // Tuple: expr COMMA (expr (COMMA expr)*)?
        List<ExpressionNode> elements = new ArrayList<>();
        for (var e : ctx.expr()) {
            elements.add((ExpressionNode) visit(e));
        }
        return new TupleNode(elements, ctx.start.getLine());
    }

    @Override
    public ASTNode visitGeneratorExpr(PythonParser.GeneratorExprContext ctx) {
        // Generator expression: expr comp_for
        ExpressionNode elementExpr = (ExpressionNode) visit(ctx.expr());
        ComprehensionClause clause = buildComprehensionClause(ctx.comp_for());
        return new GeneratorExpressionNode(elementExpr, clause, ctx.start.getLine());
    }


    @Override
    public ASTNode visitListAtom(PythonParser.ListAtomContext ctx) {
        return visit(ctx.listDisplay());
    }

    @Override
    public ASTNode visitEmptyList(PythonParser.EmptyListContext ctx) {
        return new ListLiteralNode(new ArrayList<>(), ctx.start.getLine());
    }

    @Override
    public ASTNode visitListLiteral(PythonParser.ListLiteralContext ctx) {
        List<ExpressionNode> elements = new ArrayList<>();
        for (var e : ctx.expr()) {
            elements.add((ExpressionNode) visit(e));
        }
        return new ListLiteralNode(elements, ctx.start.getLine());
    }

    @Override
    public ASTNode visitListComprehension(PythonParser.ListComprehensionContext ctx) {
        ExpressionNode elementExpr = (ExpressionNode) visit(ctx.expr());
        ComprehensionClause clause = buildComprehensionClause(ctx.comp_for());
        return new ListComprehensionNode(elementExpr, clause, ctx.start.getLine());
    }

    private ComprehensionClause buildComprehensionClause(PythonParser.Comp_forContext ctx) {
        String target = ctx.NAME().getText();
        ExpressionNode iterable = (ExpressionNode) visit(ctx.or_test());

        List<ExpressionNode> conditions = new ArrayList<>();
        ComprehensionClause nestedFor = null;

        if (ctx.comp_iter() != null) {
            processCompIter(ctx.comp_iter(), conditions);
        }

        return new ComprehensionClause(target, iterable, conditions, nestedFor, ctx.start.getLine());
    }

    private void processCompIter(PythonParser.Comp_iterContext ctx, List<ExpressionNode> conditions) {
        if (ctx.comp_if() != null) {
            conditions.add((ExpressionNode) visit(ctx.comp_if().or_test()));
            if (ctx.comp_if().comp_iter() != null) {
                processCompIter(ctx.comp_if().comp_iter(), conditions);
            }
        }
        // comp_for inside comp_iter is for nested comprehensions - simplified for now
    }


    @Override
    public ASTNode visitDictAtom(PythonParser.DictAtomContext ctx) {
        return visit(ctx.dictLit());
    }

    @Override
    public ASTNode visitDictLit(PythonParser.DictLitContext ctx) {
        Map<ExpressionNode, ExpressionNode> map = new LinkedHashMap<>();
        for (var item : ctx.dictItem()) {
            // Grammar: dictItem : expr COLON expr
            ExpressionNode key = (ExpressionNode) visit(item.expr(0));
            ExpressionNode value = (ExpressionNode) visit(item.expr(1));
            map.put(key, value);
        }
        return new DictionaryLiteralNode(map, ctx.start.getLine());
    }

    @Override
    public ASTNode visitKeywordArg(PythonParser.KeywordArgContext ctx) {
        String key = ctx.NAME().getText();
        ExpressionNode value = (ExpressionNode) visit(ctx.expr());
        return new KeywordArgNode(key, value, ctx.start.getLine());
    }

    @Override
    public ASTNode visitPositionalArg(PythonParser.PositionalArgContext ctx) {
        return visit(ctx.expr());
    }

    @Override
    public ASTNode visitImportNames(PythonParser.ImportNamesContext ctx) {
        int line = ctx.start.getLine();
        List<String> moduleNames = new ArrayList<>();

        for (var dottedName : ctx.dotted_name()) {
            StringBuilder moduleName = new StringBuilder();
            for (var name : dottedName.NAME()) {
                if (moduleName.length() > 0) moduleName.append(".");
                moduleName.append(name.getText());
            }
            moduleNames.add(moduleName.toString());
        }

        // Simple import: import flask
        String firstModule = moduleNames.isEmpty() ? "" : moduleNames.get(0);
        return new ImportStatementNode(firstModule, moduleNames, new ArrayList<>(), false, line);
    }
    @Override
    public ASTNode visitImportFrom(PythonParser.ImportFromContext ctx) {
        int line = ctx.start.getLine();

        // Build module name
        StringBuilder moduleName = new StringBuilder();
        for (var name : ctx.dotted_name().NAME()) {
            if (moduleName.length() > 0) moduleName.append(".");
            moduleName.append(name.getText());
        }

        // Get imported names
        List<String> importedNames = new ArrayList<>();
        if (ctx.NAME() != null) {
            for (var name : ctx.NAME()) {
                importedNames.add(name.getText());
            }
        }

        String module = moduleName.toString();

        // Track Flask imports for semantic analysis
        if ("flask".equals(module)) {
            flaskImports.addAll(importedNames);

            // Return specialized FlaskImportNode
            return new FlaskImportNode(module, importedNames, line);
        }

        return new ImportStatementNode(module, importedNames, new ArrayList<>(), true, line);
    }

    @Override
    public ASTNode visitTryStmt(PythonParser.TryStmtContext ctx) {
        int line = ctx.start.getLine();
        var tryStmt = ctx.try_stmt();

        // Try body
        ASTNode tryBody = visit(tryStmt.suite(0));

        // Except clauses
        List<ExceptClauseNode> exceptClauses = new ArrayList<>();
        int suiteIndex = 1;

        // Count EXCEPT tokens to know how many except clauses we have
        int exceptCount = 0;
        for (int i = 0; i < tryStmt.getChildCount(); i++) {
            if (tryStmt.getChild(i).getText().equals("except")) {
                exceptCount++;
            }
        }

        // Build except clauses
        int nameIndex = 0;
        for (int i = 0; i < exceptCount && suiteIndex < tryStmt.suite().size(); i++) {
            ExpressionNode exceptionType = null;
            String alias = null;

            // Check for exception type
            if (tryStmt.NAME() != null && nameIndex < tryStmt.NAME().size()) {
                String typeName = tryStmt.NAME(nameIndex).getText();
                exceptionType = new IdentifierNode(typeName, line);
                nameIndex++;

                // Check for alias (AS NAME)
                if (nameIndex < tryStmt.NAME().size()) {
                    // Look for AS keyword before this name
                    boolean foundAs = false;
                    for (int j = 0; j < tryStmt.getChildCount(); j++) {
                        if (tryStmt.getChild(j).getText().equals("as")) {
                            foundAs = true;
                            break;
                        }
                    }
                    if (foundAs) {
                        alias = tryStmt.NAME(nameIndex).getText();
                        nameIndex++;
                    }
                }
            }

            ASTNode exceptBody = visit(tryStmt.suite(suiteIndex));
            suiteIndex++;

            exceptClauses.add(new ExceptClauseNode(exceptionType, alias,
                    exceptBody.getChildren(), line));
        }

        // Finally clause
        List<ASTNode> finallyBody = new ArrayList<>();
        if (suiteIndex < tryStmt.suite().size()) {
            ASTNode finallySuite = visit(tryStmt.suite(suiteIndex));
            finallyBody = finallySuite.getChildren();
        }

        // TryStatementNode expects: tryBlock, exceptClauses, elseBlock, finallyBlock
        return new TryStatementNode(tryBody.getChildren(), exceptClauses,
                new ArrayList<>(), finallyBody, line);
    }


    @Override
    public ASTNode visitPassStmt(PythonParser.PassStmtContext ctx) {
        return new PassNode(ctx.start.getLine());
    }


    @Override
    public ASTNode visitFstringAtom(PythonParser.FstringAtomContext ctx) {
        String text = ctx.FSTRING().getText();
        // Remove f" and " from start and end
        String content = text.substring(2, text.length() - 1);
        return new StringLiteralNode(content, ctx.start.getLine());
    }

    public Set<String> getFlaskAppNames() {
        return new HashSet<>(flaskAppNames);
    }


    public Set<String> getFlaskImports() {
        return new HashSet<>(flaskImports);
    }


    public boolean hasFlaskImport() {
        return !flaskImports.isEmpty();
    }
}
