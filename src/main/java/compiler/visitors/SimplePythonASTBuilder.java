package compiler.visitors;

import compiler.ast.*;

import grammar.PythonParser;
import grammar.PythonParserBaseVisitor;
import grammar.PythonParser.TrailerContext;

import java.util.ArrayList;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Enhanced Python AST Builder with support for more Python constructs.
 */
public class SimplePythonASTBuilder extends PythonParserBaseVisitor<ASTNode> {

    @Override
    public ASTNode visitFile_input(PythonParser.File_inputContext ctx) {
        int lineNumber = ctx.start != null ? ctx.start.getLine() : 1;
        ProgramNode program = new ProgramNode(lineNumber);

        if (ctx.stmt() != null) {
            for (PythonParser.StmtContext stmtCtx : ctx.stmt()) {
                ASTNode node = visit(stmtCtx);
                if (node != null) {
                    program.addChild(node);
                }
            }
        }

        return program;
    }

    @Override
    public ASTNode visitStmt(PythonParser.StmtContext ctx) {
        if (ctx.simple_stmt() != null) {
            return visit(ctx.simple_stmt());
        } else if (ctx.compound_stmt() != null) {
            return visit(ctx.compound_stmt());
        } else if (ctx.decorated() != null) {
            return visit(ctx.decorated());
        }
        return null;
    }

    @Override
    public ASTNode visitSimple_stmt(PythonParser.Simple_stmtContext ctx) {
        if (ctx.small_stmt() != null && !ctx.small_stmt().isEmpty()) {
            return visit(ctx.small_stmt(0));
        }
        return null;
    }

    @Override
    public ASTNode visitAssignment(PythonParser.AssignmentContext ctx) {
        int lineNumber = ctx.start.getLine();
        List<ExpressionNode> targets = new ArrayList<>();
        if (ctx.atom_expr() != null) {
            ASTNode targetNode = visit(ctx.atom_expr());
            if (targetNode instanceof ExpressionNode) {
                targets.add((ExpressionNode) targetNode);
            }
        }

        ExpressionNode value = null;
        if (ctx.expr() != null) {
            ASTNode valueNode = visit(ctx.expr());
            if (valueNode instanceof ExpressionNode) {
                value = (ExpressionNode) valueNode;
            }
        }

        return new AssignmentNode(targets, value, lineNumber);
    }

    @Override
    public ASTNode visitExpr_stmt(PythonParser.Expr_stmtContext ctx) {
        if (ctx.expr() != null) {
            return visit(ctx.expr());
        }
        return null;
    }

    @Override
    public ASTNode visitReturn_stmt(PythonParser.Return_stmtContext ctx) {
        int lineNumber = ctx.start.getLine();
        ExpressionNode value = null;
        if (ctx.expr() != null) {
            ASTNode valueNode = visit(ctx.expr());
            if (valueNode instanceof ExpressionNode) {
                value = (ExpressionNode) valueNode;
            }
        }
        return new ReturnStatementNode(value, lineNumber);
    }

    @Override
    public ASTNode visitDecorated(PythonParser.DecoratedContext ctx) {
        int lineNumber = ctx.start.getLine();
        List<DecoratorNode> decorators = new ArrayList<>();
        if (ctx.decorator() != null) {
            for (PythonParser.DecoratorContext decoratorCtx : ctx.decorator()) {
                ASTNode decoratorNode = visit(decoratorCtx);
                if (decoratorNode instanceof DecoratorNode) {
                    decorators.add((DecoratorNode) decoratorNode);
                }
            }
        }

        ASTNode decorated = null;
        if (ctx.funcdef() != null) {
            decorated = visit(ctx.funcdef());
            if (decorated instanceof FunctionDefNode) {
                ((FunctionDefNode) decorated).setDecorators(decorators);
            }
        } else if (ctx.classdef() != null) {
            decorated = visit(ctx.classdef());
            if (decorated instanceof ClassDefNode) {
                ((ClassDefNode) decorated).setDecorators(decorators);
            }
        }

        return decorated;
    }

    @Override
    public ASTNode visitDecorator(PythonParser.DecoratorContext ctx) {
        int lineNumber = ctx.start.getLine();
        ExpressionNode decoratorExpr = null;
        if (ctx.atom_expr() != null) {
            ASTNode exprNode = visit(ctx.atom_expr());
            if (exprNode instanceof ExpressionNode) {
                decoratorExpr = (ExpressionNode) exprNode;
            }
        }

        List<ExpressionNode> arguments = new ArrayList<>();
        if (ctx.call() != null && ctx.call().arglist() != null) {
            PythonParser.ArglistContext arglist = ctx.call().arglist();
            if (arglist.argument() != null) {
                for (PythonParser.ArgumentContext argCtx : arglist.argument()) {
                    ASTNode argNode = visit(argCtx);
                    if (argNode instanceof ExpressionNode) {
                        arguments.add((ExpressionNode) argNode);
                    }
                }
            }
        }

        return new DecoratorNode(decoratorExpr, arguments, lineNumber);
    }

    @Override
    public ASTNode visitIf_stmt(PythonParser.If_stmtContext ctx) {
        int lineNumber = ctx.start.getLine();
        ExpressionNode condition = null;
        if (ctx.expr() != null && !ctx.expr().isEmpty()) {
            ASTNode condNode = visit(ctx.expr(0));
            if (condNode instanceof ExpressionNode) condition = (ExpressionNode) condNode;
        }

        List<ASTNode> thenBlock = new ArrayList<>();
        List<ASTNode> elseBlock = new ArrayList<>();
        if (ctx.suite() != null && !ctx.suite().isEmpty()) {
            ASTNode suiteNode = visit(ctx.suite(0));
            if (suiteNode != null) thenBlock.addAll(suiteNode.getChildren());
            if (ctx.suite().size() > 1) {
                ASTNode elseNode = visit(ctx.suite(1));
                if (elseNode != null) elseBlock.addAll(elseNode.getChildren());
            }
        }

        return new IfStatementNode(condition, thenBlock, elseBlock, lineNumber);
    }

    @Override
    public ASTNode visitFuncdef(PythonParser.FuncdefContext ctx) {
        int lineNumber = ctx.start.getLine();
        String functionName = ctx.NAME() != null ? ctx.NAME().getText() : "unknown";

        List<ParameterNode> parameters = new ArrayList<>();
        if (ctx.parameters() != null) {
            String paramsText = ctx.parameters().getText();
            if (!paramsText.equals("()") && paramsText.length() > 2) {
                String[] paramNames = paramsText.substring(1, paramsText.length() - 1).split(",");
                for (String paramName : paramNames) {
                    paramName = paramName.trim();
                    if (!paramName.isEmpty()) {
                        parameters.add(new ParameterNode(paramName, null, null, lineNumber));
                    }
                }
            }
        }

        List<ASTNode> body = new ArrayList<>();
        if (ctx.suite() != null) {
            ASTNode suiteNode = visit(ctx.suite());
            if (suiteNode != null) body.addAll(suiteNode.getChildren());
        }

        return new FunctionDefNode(functionName, parameters, body, new ArrayList<>(), null, lineNumber);
    }

    @Override
    public ASTNode visitClassdef(PythonParser.ClassdefContext ctx) {
        int lineNumber = ctx.start.getLine();
        String className = ctx.NAME() != null ? ctx.NAME().getText() : "unknown";
        List<ExpressionNode> baseClasses = new ArrayList<>();
        List<ASTNode> body = new ArrayList<>();
        if (ctx.suite() != null) {
            ASTNode suiteNode = visit(ctx.suite());
            if (suiteNode != null) body.addAll(suiteNode.getChildren());
        }
        return new ClassDefNode(className, baseClasses, body, new ArrayList<>(), lineNumber);
    }

    @Override
    public ASTNode visitFor_stmt(PythonParser.For_stmtContext ctx) {
        int lineNumber = ctx.start.getLine();
        String target = ctx.NAME() != null ? ctx.NAME().getText() : "";
        ExpressionNode iterable = null;
        if (ctx.expr() != null) {
            ASTNode iterNode = visit(ctx.expr());
            if (iterNode instanceof ExpressionNode) iterable = (ExpressionNode) iterNode;
        }

        List<ASTNode> body = new ArrayList<>();
        if (ctx.suite() != null && !ctx.suite().isEmpty()) {
            ASTNode suiteNode = visit(ctx.suite(0));
            if (suiteNode != null) body.addAll(suiteNode.getChildren());
        }

        return new ForStatementNode(new IdentifierNode(target, lineNumber), iterable, body, new ArrayList<>(), lineNumber);
    }

    @Override
    public ASTNode visitWhile_stmt(PythonParser.While_stmtContext ctx) {
        int lineNumber = ctx.start.getLine();
        ExpressionNode condition = null;
        if (ctx.expr() != null) {
            ASTNode condNode = visit(ctx.expr());
            if (condNode instanceof ExpressionNode) condition = (ExpressionNode) condNode;
        }

        List<ASTNode> body = new ArrayList<>();
        if (ctx.suite() != null && !ctx.suite().isEmpty()) {
            ASTNode suiteNode = visit(ctx.suite(0));
            if (suiteNode != null) body.addAll(suiteNode.getChildren());
        }

        return new WhileStatementNode(condition, body, new ArrayList<>(), lineNumber);
    }

    @Override
    public ASTNode visitTry_stmt(PythonParser.Try_stmtContext ctx) {
        int lineNumber = ctx.start.getLine();
        List<ASTNode> tryBlock = new ArrayList<>();
        if (ctx.suite() != null && !ctx.suite().isEmpty()) {
            ASTNode suiteNode = visit(ctx.suite(0));
            if (suiteNode != null) tryBlock.addAll(suiteNode.getChildren());
        }
        return new TryStatementNode(tryBlock, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), lineNumber);
    }

    @Override
    public ASTNode visitSuite(PythonParser.SuiteContext ctx) {
        ProgramNode container = new ProgramNode(ctx.start != null ? ctx.start.getLine() : 1);
        if (ctx.stmt() != null) {
            for (PythonParser.StmtContext stmtCtx : ctx.stmt()) {
                ASTNode node = visit(stmtCtx);
                if (node != null) container.addChild(node);
            }
        }
        return container;
    }

    @Override
    public ASTNode visitExpr(PythonParser.ExprContext ctx) {
        if (ctx.comparison() != null) return visit(ctx.comparison());
        return null;
    }

    @Override
    public ASTNode visitSingleArithExpr(PythonParser.SingleArithExprContext ctx) {
        return visit(ctx.arith_expr());
    }

    @Override
    public ASTNode visitBinaryComparison(PythonParser.BinaryComparisonContext ctx) {
        ExpressionNode left = (ExpressionNode) visit(ctx.arith_expr(0));
        ExpressionNode right = (ExpressionNode) visit(ctx.arith_expr(1));
        String operator = ctx.getChild(1).getText(); // EQ, NE, LT, ...
        return new ComparisonNode(operator, left, right, ctx.start.getLine());
    }



    @Override
    public ASTNode visitArith_expr(PythonParser.Arith_exprContext ctx) {
        if (ctx.atom_expr() != null && !ctx.atom_expr().isEmpty()) {
            ASTNode left = visit(ctx.atom_expr(0));
            if (ctx.atom_expr().size() > 1) {
                int lineNumber = ctx.start.getLine();
                for (int i = 1; i < ctx.atom_expr().size(); i++) {
                    String operator = "";
                    if (ctx.PLUS() != null && !ctx.PLUS().isEmpty() && ctx.PLUS().size() >= i) operator = "+";
                    else if (ctx.MINUS() != null && !ctx.MINUS().isEmpty() && ctx.MINUS().size() >= i) operator = "-";
                    else if (ctx.STAR() != null && !ctx.STAR().isEmpty() && ctx.STAR().size() >= i) operator = "*";
                    else if (ctx.DIV() != null && !ctx.DIV().isEmpty() && ctx.DIV().size() >= i) operator = "/";
                    else if (ctx.MOD() != null && !ctx.MOD().isEmpty() && ctx.MOD().size() >= i) operator = "%";

                    ASTNode right = visit(ctx.atom_expr(i));
                    if (left instanceof ExpressionNode && right instanceof ExpressionNode && !operator.isEmpty()) {
                        left = new BinaryOpNode(operator, (ExpressionNode) left, (ExpressionNode) right, lineNumber);
                    }
                }
            }
            return left;
        }
        return null;
    }

    @Override
    public ASTNode visitAtom_expr(PythonParser.Atom_exprContext ctx) {
        int lineNumber = ctx.start.getLine();
        ASTNode base = ctx.atom() != null ? visit(ctx.atom()) : null;
        if (base == null) return null;
        if (ctx.trailer() != null && !ctx.trailer().isEmpty()) {
            for (PythonParser.TrailerContext trailerCtx : ctx.trailer()) {
                base = visitTrailerWithBase(trailerCtx, base);
            }
        }
        return base;
    }

    private ASTNode visitTrailerWithBase(PythonParser.TrailerContext ctx, ASTNode base) {
        int lineNumber = ctx.start.getLine();
        if (ctx.call() != null) {
            List<ExpressionNode> arguments = new ArrayList<>();
            if (ctx.call().arglist() != null && ctx.call().arglist().argument() != null) {
                for (PythonParser.ArgumentContext argCtx : ctx.call().arglist().argument()) {
                    ASTNode argNode = visit(argCtx);
                    if (argNode instanceof ExpressionNode) arguments.add((ExpressionNode) argNode);
                }
            }
            if (base instanceof ExpressionNode) return new FunctionCallNode((ExpressionNode) base, arguments, lineNumber);
        }

        if (ctx.DOT() != null && ctx.NAME() != null && base instanceof ExpressionNode) {
            return new MemberAccessNode((ExpressionNode) base, ctx.NAME().getText(), lineNumber);
        }

        return base;
    }

    @Override
    public ASTNode visitPositionalArg(PythonParser.PositionalArgContext ctx) {
        return ctx.expr() != null ? visit(ctx.expr()) : null;
    }

    @Override
    public ASTNode visitKeywordArg(PythonParser.KeywordArgContext ctx) {
        return ctx.expr() != null ? visit(ctx.expr()) : null;
    }

    @Override
    public ASTNode visitAtom(PythonParser.AtomContext ctx) {
        int lineNumber = ctx.start.getLine();
        if (ctx.NAME() != null) return new IdentifierNode(ctx.NAME().getText(), lineNumber);
        if (ctx.NUMBER() != null) return new NumberLiteralNode(Double.parseDouble(ctx.NUMBER().getText()), lineNumber);
        if (ctx.STRING() != null) {
            String text = ctx.STRING().getText();
            String value = text.length() > 2 ? text.substring(1, text.length() - 1) : text;
            return new StringLiteralNode(value, lineNumber);
        }
        if (ctx.FSTRING() != null) {
            String text = ctx.FSTRING().getText();
            String value = text.length() > 3 ? text.substring(2, text.length() - 1) : text;
            return new StringLiteralNode(value, lineNumber);
        }
        if (ctx.TRUE() != null) return new BooleanLiteralNode(true, lineNumber);
        if (ctx.FALSE() != null) return new BooleanLiteralNode(false, lineNumber);
        if (ctx.NONE() != null) return new NoneLiteralNode(lineNumber);
        if (ctx.listLit() != null) return visit(ctx.listLit());
        if (ctx.dictLit() != null) return visit(ctx.dictLit());
        if (ctx.expr() != null) return visit(ctx.expr());
        return null;
    }

    @Override
    public ASTNode visitListLit(PythonParser.ListLitContext ctx) {
        int lineNumber = ctx.start.getLine();
        List<ExpressionNode> elements = new ArrayList<>();
        if (ctx.expr() != null) {
            for (PythonParser.ExprContext exprCtx : ctx.expr()) {
                ASTNode element = visit(exprCtx);
                if (element instanceof ExpressionNode) elements.add((ExpressionNode) element);
            }
        }
        return new ListLiteralNode(elements, lineNumber);
    }

    @Override
    public ASTNode visitDictLit(PythonParser.DictLitContext ctx) {
        int lineNumber = ctx.start.getLine();
        Map<ExpressionNode, ExpressionNode> entries = new LinkedHashMap<>();
        if (ctx.dictItem() != null) {
            for (PythonParser.DictItemContext itemCtx : ctx.dictItem()) {
                ExpressionNode key = null;
                if (itemCtx.STRING() != null) {
                    String keyText = itemCtx.STRING().getText();
                    String keyValue = keyText.length() > 2 ? keyText.substring(1, keyText.length() - 1) : keyText;
                    key = new StringLiteralNode(keyValue, lineNumber);
                } else if (itemCtx.NAME() != null) {
                    key = new StringLiteralNode(itemCtx.NAME().getText(), lineNumber);
                }

                ExpressionNode value = null;
                if (itemCtx.expr() != null) {
                    ASTNode valueNode = visit(itemCtx.expr());
                    if (valueNode instanceof ExpressionNode) value = (ExpressionNode) valueNode;
                }

                if (key != null && value != null) entries.put(key, value);
            }
        }
        return new DictionaryLiteralNode(entries, lineNumber);
    }
}
