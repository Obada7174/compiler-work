//package compiler.visitors;
//
//import compiler.ast.*;
//import grammar.PythonParser;
//import grammar.PythonParserBaseVisitor;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Visitor to build AST from PythonParser (labeled alternatives).
// *
// * Assumptions (change constructors if your concrete classes differ slightly):
// *  - AssignmentNode(int line, ExpressionNode target, ExpressionNode value)
// *  - FunctionNode(String name, int line) + setBody(BlockNode)
// *  - ClassNode(String name, int line) + setBody(BlockNode)
// *  - ForNode(int line, IdentifierNode target, ASTNode iterable, BlockNode body, BlockNode elseBlock)
// *  - WhileNode(int line, ExpressionNode cond, BlockNode body, BlockNode elseBlock)
// *  - TryNode(int line, BlockNode tryBlock, List<ExceptNode> excepts, FinallyNode finallyNode)
// *  - BlockNode(String name, List<ASTNode> statements, int line)
// */
//public class PythonASTBuilder extends PythonParserBaseVisitor<ASTNode> {
//
//    // ---------------- File Input ----------------
//    @Override
//    public ASTNode visitFile_input(PythonParser.File_inputContext ctx) {
//        ProgramNode program = new ProgramNode(ctx.getStart().getLine());
//        // file_input : (stmt | NEWLINE)* EOF
//        for (PythonParser.StmtContext stmtCtx : ctx.stmt()) {
//            ASTNode stmtNode = visit(stmtCtx);
//            if (stmtNode != null) program.addChild(stmtNode);
//        }
//        return program;
//    }
//
//    // ---------------- Generic stmt dispatcher ----------------
//    @Override
//    public ASTNode visitStmt(PythonParser.StmtContext ctx) {
//        if (ctx.simple_stmt() != null) return visit(ctx.simple_stmt());
//        if (ctx.compound_stmt() != null) return visit(ctx.compound_stmt());
//        if (ctx.decorated() != null) return visit(ctx.decorated());
//        return null;
//    }
//
//    // ---------------- Simple Statements ----------------
//    @Override
//    public ASTNode visitExprStmt(PythonParser.ExprStmtContext ctx) {
//        // expr_stmt : expr
//        ExpressionNode expr = (ExpressionNode) visit(ctx.expr());
//        // It's fine to return the expression node directly for expression statements,
//        // or you can wrap it in a StatementNode/BlockNode if you prefer.
//        return expr;
//    }
//
//    @Override
//    public ASTNode visitAssignmentStmt(PythonParser.AssignmentStmtContext ctx) {
//        // grammar: assignment : atom_expr ASSIGN expr
//        ExpressionNode left = (ExpressionNode) visit(ctx.atom_expr());
//        ExpressionNode right = (ExpressionNode) visit(ctx.expr());
//        // Using assumed constructor: AssignmentNode(int line, ExpressionNode target, ExpressionNode value)
//        return new AssignmentNode(ctx.getStart().getLine(), left, right);
//    }
//
//    @Override
//    public ASTNode visitReturnStmt(PythonParser.ReturnStmtContext ctx) {
//        ExpressionNode value = ctx.expr() != null ? (ExpressionNode) visit(ctx.expr()) : null;
//        return new ReturnNode(value, ctx.getStart().getLine());
//    }
//
//    @Override
//    public ASTNode visitPassStmt(PythonParser.PassStmtContext ctx) {
//        return new PassNode(ctx.getStart().getLine());
//    }
//
//    // ---------------- Compound Statements ----------------
//
//    @Override
//    public ASTNode visitFunctionDefStmt(PythonParser.FunctionDefStmtContext ctx) {
//        // funcdef : DEF NAME parameters COLON suite
//        String name = ctx.NAME().getText();
//        FunctionNode fn = new FunctionNode(name, ctx.getStart().getLine());
//
//        // parameters -> can collect names (we create IdentifierNode for each param)
//        if (ctx.parameters() != null) {
//            List<IdentifierNode> params = new ArrayList<>();
//            for (int i = 0; i < ctx.parameters().NAME().size(); ++i) {
//                String paramName = ctx.parameters().NAME(i).getText();
//                params.add(new IdentifierNode(paramName, ctx.parameters().NAME(i).getSymbol().getLine()));
//            }
//            // if FunctionNode has a setter for params, set them; else add as children:
//            for (IdentifierNode p : params) fn.addChild(p);
//        }
//
//        // body (suite)
//        BlockNode bodyBlock = new BlockNode("function_body", collectSuite(ctx.suite()), ctx.suite().getStart().getLine());
//        // set body -- using the setBody pattern you used earlier
//        fn.setBody(bodyBlock);
//
//        return fn;
//    }
//
//    @Override
//    public ASTNode visitClassDefStmt(PythonParser.ClassDefStmtContext ctx) {
//        // classdef : CLASS NAME (LPAREN (dotted_name (COMMA dotted_name)*)? RPAREN)? COLON suite
//        String className = ctx.NAME().getText();
//        ClassNode cls = new ClassNode(className, ctx.getStart().getLine());
//
//        // base classes (dotted_name) - optional
//        if (ctx.LPAREN() != null && ctx.dotted_name() != null) {
//            for (PythonParser.Dotted_nameContext dn : ctx.dotted_name()) {
//                // add base class name as IdentifierNode child or as metadata
//                IdentifierNode base = new IdentifierNode(dn.getText(), dn.getStart().getLine());
//                cls.addChild(base);
//            }
//        }
//
//        BlockNode bodyBlock = new BlockNode("class_body", collectSuite(ctx.suite()), ctx.suite().getStart().getLine());
//        cls.setBody(bodyBlock);
//        return cls;
//    }
//
//    @Override
//    public ASTNode visitIfStmt(PythonParser.IfStmtContext ctx) {
//        // IF expr COLON suite (ELIF expr COLON suite)* (ELSE COLON suite)?
//        ExpressionNode cond0 = (ExpressionNode) visit(ctx.expr(0));
//        BlockNode thenBlock = new BlockNode("if_body", collectSuite(ctx.suite(0)), ctx.suite(0).getStart().getLine());
//
//        BlockNode elseBlock = null;
//        // else (if present) is the last suite when ELSE present
//        if (ctx.ELSE() != null) {
//            int lastSuiteIndex = ctx.suite().size() - 1;
//            elseBlock = new BlockNode("else_body", collectSuite(ctx.suite(lastSuiteIndex)), ctx.suite(lastSuiteIndex).getStart().getLine());
//        }
//
//        // collect ELIF branches if any
//        List<IfNode> elifNodes = new ArrayList<>();
//        int elifCount = ctx.ELIF().size(); // number of ELIF tokens
//        // ANTLR arranges expr and suite lists interleaved: expr(1), suite(1) ... for elifs
//        for (int i = 1; i <= ctx.ELIF().size(); ++i) {
//            ExpressionNode elifCond = (ExpressionNode) visit(ctx.expr(i));
//            BlockNode elifBody = new BlockNode("elif_body", collectSuite(ctx.suite(i)), ctx.suite(i).getStart().getLine());
//            IfNode elifNode = new IfNode(ctx.getStart().getLine(), elifCond, elifBody, null, new ArrayList<>());
//            elifNodes.add(elifNode);
//        }
//
//        // create top-level IfNode. Constructor assumed: IfNode(int line, ExpressionNode cond, BlockNode thenBlock, BlockNode elseBlock, List<IfNode> elifs)
//        return new IfNode(ctx.getStart().getLine(), cond0, thenBlock, elseBlock, elifNodes);
//    }
//
//    @Override
//    public ASTNode visitForStmt(PythonParser.ForStmtContext ctx) {
//        // FOR NAME IN expr COLON suite (ELSE COLON suite)?
//        IdentifierNode target = new IdentifierNode(ctx.NAME().getText(), ctx.NAME().getSymbol().getLine());
//        ExpressionNode iterable = (ExpressionNode) visit(ctx.expr());
//        BlockNode body = new BlockNode("for_body", collectSuite(ctx.suite(0)), ctx.suite(0).getStart().getLine());
//        BlockNode elseBlock = null;
//        if (ctx.ELSE() != null && ctx.suite().size() > 1) {
//            elseBlock = new BlockNode("for_else", collectSuite(ctx.suite(1)), ctx.suite(1).getStart().getLine());
//        }
//        // Constructor assumed: ForNode(int line, IdentifierNode target, ASTNode iterable, BlockNode body, BlockNode elseBlock)
//        return new ForNode(ctx.getStart().getLine(), target, iterable, body, elseBlock);
//    }
//
//    @Override
//    public ASTNode visitWhileStmt(PythonParser.WhileStmtContext ctx) {
//        ExpressionNode cond = (ExpressionNode) visit(ctx.expr());
//        BlockNode body = new BlockNode("while_body", collectSuite(ctx.suite(0)), ctx.suite(0).getStart().getLine());
//        BlockNode elseBlock = null;
//        if (ctx.ELSE() != null && ctx.suite().size() > 1) {
//            elseBlock = new BlockNode("while_else", collectSuite(ctx.suite(1)), ctx.suite(1).getStart().getLine());
//        }
//        return new WhileNode(ctx.getStart().getLine(), cond, body, elseBlock);
//    }
//
//    @Override
//    public ASTNode visitTryStmt(PythonParser.TryStmtContext ctx) {
//        // TRY COLON suite (EXCEPT ... COLON suite)+ (FINALLY COLON suite)?
//        BlockNode tryBlock = new BlockNode("try_block", collectSuite(ctx.suite(0)), ctx.suite(0).getStart().getLine());
//
//        List<ExceptNode> excepts = new ArrayList<>();
//        // excepts: repeat for each EXCEPT ... COLON suite pair
//        // ANTLR gives multiple EXCEPT occurrences; iterate contexts of EXCEPT using ctx.EXCEPT() size and scheme:
//        // Here we iterate over the subcontexts by counting how many EXCEPT... suites exist.
//        // We can scan children to find EXCEPT contexts — but simpler: use the parser structure: after try suite, there are EXCEPT groups followed by optional FINALLY.
//        int suiteIndex = 1;
//        for (int i = 1; i < ctx.suite().size(); ++i) {
//            // We cannot trivially map which suite belongs to except vs finally without using token positions,
//            // but typical grammar has at least one EXCEPT, so iterate EXCEPT occurrences by tokens:
//        }
//        // For now, create empty excepts (you can extend to parse exception names/aliases similarly)
//        FinallyNode finallyNode = null;
//        if (ctx.FINALLY() != null) {
//            // FINALLY present; last suite is finally body
//            int last = ctx.suite().size() - 1;
//            finallyNode = new FinallyNode(new BlockNode("finally_body", collectSuite(ctx.suite(last)), ctx.suite(last).getStart().getLine()), ctx.getStart().getLine());
//        }
//
//        return new TryNode(ctx.getStart().getLine(), tryBlock, excepts, finallyNode);
//    }
//
//    // ---------------- Expressions ----------------
//
//    @Override
//    public ASTNode visitComparison(PythonParser.ComparisonContext ctx) {
//        // comparison : arith_expr ((EQ|NE|LT|GT|LE|GE) arith_expr)?
//        ExpressionNode left = (ExpressionNode) visit(ctx.arith_expr(0));
//        if (ctx.getChildCount() > 1 && ctx.arith_expr().size() > 1) {
//            String op = ctx.getChild(1).getText(); // operator token text
//            ExpressionNode right = (ExpressionNode) visit(ctx.arith_expr(1));
//            return new BinaryOpNode(op, left, right, ctx.getStart().getLine());
//        }
//        return left;
//    }
//
//    @Override
//    public ASTNode visitArith_expr(PythonParser.Arith_exprContext ctx) {
//        // arith_expr : atom_expr ((PLUS|MINUS|STAR|DIV|MOD) atom_expr)*
//        ExpressionNode left = (ExpressionNode) visit(ctx.atom_expr(0));
//        if (ctx.atom_expr().size() > 1) {
//            ExpressionNode current = left;
//            // fold left-to-right
//            int idx = 1;
//            for (int i = 1; i < ctx.getChildCount(); ++i) {
//                // children pattern: atom_expr op atom_expr op atom_expr ...
//                if (ctx.atom_expr().size() > idx) {
//                    String op = ctx.getChild(2*idx - 1).getText();
//                    ExpressionNode right = (ExpressionNode) visit(ctx.atom_expr(idx));
//                    current = new BinaryOpNode(op, current, right, ctx.getStart().getLine());
//                    idx++;
//                } else break;
//            }
//            return current;
//        }
//        return left;
//    }
//
//    @Override
//    public ASTNode visitAtom_expr(PythonParser.Atom_exprContext ctx) {
//        // atom_expr : atom trailer*
//        ExpressionNode base = (ExpressionNode) visit(ctx.atom());
//        // apply trailers
//        for (PythonParser.TrailerContext trailer : ctx.trailer()) {
//            if (trailer.call() != null) {
//                // call -> LPAREN arglist? RPAREN
//                List<ExpressionNode> args = new ArrayList<>();
//                if (trailer.call().arglist() != null) {
//                    for (PythonParser.ArgumentContext a : trailer.call().arglist().argument()) {
//                        ASTNode argNode = visit(a);
//                        if (argNode instanceof ExpressionNode) args.add((ExpressionNode)argNode);
//                        else if (argNode instanceof PositionalArgNode) args.add(((PositionalArgNode)argNode).getValue());
//                        else if (argNode instanceof KeywordArgNode) args.add(((KeywordArgNode)argNode).getValue());
//                    }
//                }
//                // if you have CallNode class: new CallNode(base, args, ctx.getStart().getLine())
//                CallNode call = new CallNode(base, args, ctx.getStart().getLine());
//                base = call;
//            } else if (trailer.DOT() != null) {
//                // .NAME
//                IdentifierNode member = new IdentifierNode(trailer.NAME().getText(), trailer.NAME().getSymbol().getLine());
//                MemberAccessNode mac = new MemberAccessNode(ctx.getStart().getLine());
//                mac.addChild(base);
//                mac.addChild(member);
//                base = mac;
//            } else if (trailer.LBRACK() != null) {
//                // [ expr ]
//                ExpressionNode indexExpr = (ExpressionNode) visit(trailer.expr());
//                IndexAccessNode ian = new IndexAccessNode(ctx.getStart().getLine());
//                ian.addChild(base);
//                ian.addChild(indexExpr);
//                base = ian;
//            }
//        }
//        return base;
//    }
//
//    // ---------------- Atom terminals ----------------
//    @Override
//    public ASTNode visitNameAtom(PythonParser.NameAtomContext ctx) {
//        return new IdentifierNode(ctx.NAME().getText(), ctx.start.getLine());
//    }
//
//    @Override
//    public ASTNode visitNumberAtom(PythonParser.NumberAtomContext ctx) {
//        try {
//            double v = Double.parseDouble(ctx.NUMBER().getText());
//            return new NumberLiteralNode(v, ctx.start.getLine());
//        } catch (NumberFormatException e) {
//            return new NumberLiteralNode(0, ctx.start.getLine());
//        }
//    }
//
//    @Override
//    public ASTNode visitStringAtom(PythonParser.StringAtomContext ctx) {
//        // strip quotes? keep raw for now
//        return new StringLiteralNode(ctx.STRING().getText(), ctx.start.getLine());
//    }
//
//    @Override
//    public ASTNode visitTrueAtom(PythonParser.TrueAtomContext ctx) {
//        return new BooleanLiteralNode(true, ctx.start.getLine());
//    }
//
//    @Override
//    public ASTNode visitFalseAtom(PythonParser.FalseAtomContext ctx) {
//        return new BooleanLiteralNode(false, ctx.start.getLine());
//    }
//
//    @Override
//    public ASTNode visitNoneAtom(PythonParser.NoneAtomContext ctx) {
//        return new NoneLiteralNode(ctx.start.getLine());
//    }
//
//    @Override
//    public ASTNode visitListAtom(PythonParser.ListAtomContext ctx) {
//        ListLiteralNode list = new ListLiteralNode(ctx.start.getLine());
//        if (ctx.listLit() != null && ctx.listLit().expr() != null) {
//            for (PythonParser.ExprContext e : ctx.listLit().expr()) {
//                list.addChild((ASTNode) visit(e));
//            }
//        }
//        return list;
//    }
//
//    @Override
//    public ASTNode visitDictAtom(PythonParser.DictAtomContext ctx) {
//        DictLiteralNode dict = new DictLiteralNode(ctx.start.getLine());
//        if (ctx.dictLit() != null && ctx.dictLit().dictItem() != null) {
//            for (PythonParser.DictItemContext di : ctx.dictLit().dictItem()) {
//                ASTNode key = visit(di.getChild(0)); // STRING or NAME
//                ExpressionNode val = (ExpressionNode) visit(di.expr());
//                DictItemNode item = new DictItemNode((ExpressionNode) key, val, di.start.getLine());
//                dict.addChild(item);
//            }
//        }
//        return dict;
//    }
//
//    // ---------------- Arguments ----------------
//    @Override
//    public ASTNode visitPositionalArg(PythonParser.PositionalArgContext ctx) {
//        ExpressionNode e = (ExpressionNode) visit(ctx.expr());
//        return new PositionalArgNode(e, ctx.getStart().getLine());
//    }
//
//    @Override
//    public ASTNode visitKeywordArg(PythonParser.KeywordArgContext ctx) {
//        String key = ctx.NAME().getText();
//        ExpressionNode val = (ExpressionNode) visit(ctx.expr());
//        return new KeywordArgNode(key, val, ctx.getStart().getLine());
//    }
//
//    // ---------------- Suite helper ----------------
//    /**
//     * Collect statements from a suite context into a list of ASTNode.
//     * Note: suite : simple_stmt | NEWLINE INDENT stmt+ DEDENT
//     */
//    private List<ASTNode> collectSuite(PythonParser.SuiteContext ctx) {
//        List<ASTNode> nodes = new ArrayList<>();
//        if (ctx.simple_stmt() != null) {
//            ASTNode n = visit(ctx.simple_stmt());
//            if (n != null) nodes.add(n);
//        } else {
//            for (PythonParser.StmtContext s : ctx.stmt()) {
//                ASTNode n = visit(s);
//                if (n != null) nodes.add(n);
//            }
//        }
//        return nodes;
//    }
//}
