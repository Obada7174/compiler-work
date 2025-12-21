package compiler.visitors;

import compiler.ast.*;
import grammar.PythonParser;
import grammar.PythonParserBaseVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * Visitor to build AST from PythonParser.
 */
public class PythonASTBuilder extends PythonParserBaseVisitor<ASTNode> {

    @Override
    public ASTNode visitFile_input(PythonParser.File_inputContext ctx) {
        ProgramNode program = new ProgramNode(ctx.getStart().getLine());
        for (PythonParser.StmtContext stmtCtx : ctx.stmt()) {
            ASTNode stmtNode = visit(stmtCtx);
            if (stmtNode != null) program.addChild(stmtNode);
        }
        return program;
    }

    @Override
    public ASTNode visitStmt(PythonParser.StmtContext ctx) {
        if (ctx.simple_stmt() != null) return visit(ctx.simple_stmt());
        if (ctx.compound_stmt() != null) return visit(ctx.compound_stmt());
        if (ctx.decorated() != null) return visit(ctx.decorated());
        return null;
    }

    @Override
    public ASTNode visitExpr_stmt(PythonParser.Expr_stmtContext ctx) {
        return visit(ctx.expr());
    }

    @Override
    public ASTNode visitAssignment(PythonParser.AssignmentContext ctx) {
        ExpressionNode target = (ExpressionNode) visit(ctx.atom_expr());
        ExpressionNode value = (ExpressionNode) visit(ctx.expr());
        return new AssignmentNode(target, value, ctx.getStart().getLine());
    }

    @Override
    public ASTNode visitIf_stmt(PythonParser.If_stmtContext ctx) {
        ExpressionNode condition = (ExpressionNode) visit(ctx.expr(0));
        List<ASTNode> thenBlock = collectSuite(ctx.suite(0));
        List<ASTNode> elseBlock = null;
        if (ctx.ELSE() != null && ctx.suite().size() > 1) {
            elseBlock = collectSuite(ctx.suite(ctx.suite().size() - 1));
        }
        return new IfNode(condition, thenBlock, elseBlock, ctx.getStart().getLine());
    }

    @Override
    public ASTNode visitFor_stmt(PythonParser.For_stmtContext ctx) {
        String varName = ctx.NAME().getText();
        ExpressionNode iterable = (ExpressionNode) visit(ctx.expr());
        List<ASTNode> body = collectSuite(ctx.suite());
        return new ForNode(varName, iterable, body, ctx.getStart().getLine());
    }

    @Override
    public ASTNode visitWhile_stmt(PythonParser.While_stmtContext ctx) {
        ExpressionNode condition = (ExpressionNode) visit(ctx.expr());
        List<ASTNode> body = collectSuite(ctx.suite());
        return new WhileNode(condition, body, ctx.getStart().getLine());
    }


    @Override
    public ASTNode visitFuncdef(PythonParser.FuncdefContext ctx) {
        String funcName = ctx.NAME().getText();
        FunctionNode funcNode = new FunctionNode(funcName, ctx.getStart().getLine());
        funcNode.setBody(collectSuite(ctx.suite()));
        return funcNode;
    }

    @Override
    public ASTNode visitClassdef(PythonParser.ClassdefContext ctx) {
        String className = ctx.NAME().getText();
        ClassNode classNode = new ClassNode(className, ctx.getStart().getLine());
        classNode.setBody(collectSuite(ctx.suite()));
        return classNode;
    }


    @Override
    public ASTNode visitAtom(PythonParser.AtomContext ctx) {
        if (ctx.NUMBER() != null)
            return new NumberLiteralNode(Double.parseDouble(ctx.NUMBER().getText()), ctx.getStart().getLine());

        if (ctx.STRING() != null)
            return new StringLiteralNode(ctx.STRING().getText(), ctx.getStart().getLine());

        if (ctx.NAME() != null) {
            String text = ctx.NAME().getText();
            if (text.equals("True") || text.equals("False"))
                return new BooleanLiteralNode(Boolean.parseBoolean(text), ctx.getStart().getLine());
            if (text.equals("None"))
                return new NoneLiteralNode(ctx.getStart().getLine());
            return new IdentifierNode(text, ctx.getStart().getLine());
        }

        if (ctx.listLit() != null)
            return visitListLit(ctx.listLit());

        if (ctx.dictLit() != null)
            return visitDictLit(ctx.dictLit());

        return super.visitAtom(ctx);
    }

    // --- Visit List Literal ---
    public ASTNode visitListLit(PythonParser.ListLitContext ctx) {
        List<ExpressionNode> elements = new ArrayList<>();
        if (ctx.expr() != null) {
            for (PythonParser.ExprContext exprCtx : ctx.expr()) {
                ExpressionNode e = (ExpressionNode) visit(exprCtx);
                if (e != null) elements.add(e);
            }
        }
        return new ListLiteralNode(elements, ctx.getStart().getLine());
    }

    // --- Visit Dict Literal ---
    public ASTNode visitDictLit(PythonParser.DictLitContext ctx) {
        List<DictItemNode> items = new ArrayList<>();
        if (ctx.dictItem() != null) {
            for (PythonParser.DictItemContext itemCtx : ctx.dictItem()) {
                ExpressionNode key = (ExpressionNode) visit(itemCtx.getChild(0));
                ExpressionNode value = (ExpressionNode) visit(itemCtx.expr());
                items.add(new DictItemNode(key, value, itemCtx.getStart().getLine()));
            }
        }
        return new DictLiteralNode(items, ctx.getStart().getLine());
    }

    @Override
    public ASTNode visitExpr(PythonParser.ExprContext ctx) {
        return visit(ctx.comparison());
    }

    @Override
    public ASTNode visitComparison(PythonParser.ComparisonContext ctx) {
        if (ctx.arith_expr().size() == 1) return visit(ctx.arith_expr(0));
        ASTNode left = visit(ctx.arith_expr(0));
        ASTNode right = visit(ctx.arith_expr(1));
        String op = ctx.getChild(1).getText();
        return new BinaryOpNode(op, (ExpressionNode) left, (ExpressionNode) right, ctx.getStart().getLine());
    }

    @Override
    public ASTNode visitArith_expr(PythonParser.Arith_exprContext ctx) {
        if (ctx.atom_expr().size() == 1) return visit(ctx.atom_expr(0));
        ASTNode left = visit(ctx.atom_expr(0));
        for (int i = 1; i < ctx.atom_expr().size(); i++) {
            String op = ctx.getChild(i * 2 - 1).getText();
            ASTNode right = visit(ctx.atom_expr(i));
            left = new BinaryOpNode(op, (ExpressionNode) left, (ExpressionNode) right, ctx.getStart().getLine());
        }
        return left;
    }

    @Override
    public ASTNode visitAtom_expr(PythonParser.Atom_exprContext ctx) {
        ASTNode node = visit(ctx.atom());
        for (PythonParser.TrailerContext t : ctx.trailer()) {
            if (t.call() != null) {
                List<ExpressionNode> args = visitArgList(t.call().arglist());
                node = new FunctionCallNode((ExpressionNode) node, args, ctx.getStart().getLine());
            } else if (t.DOT() != null) {
                node = new MemberAccessNode((ExpressionNode) node, t.NAME().getText(), ctx.getStart().getLine());
            } else if (t.LBRACK() != null) {
                node = new IndexAccessNode((ExpressionNode) node, (ExpressionNode) visit(t.expr()), ctx.getStart().getLine());
            }
        }
        return node;
    }

    // --- Private helpers ---
    private List<ASTNode> collectSuite(PythonParser.SuiteContext ctx) {
        List<ASTNode> nodes = new ArrayList<>();
        if (ctx != null) {
            for (PythonParser.StmtContext stmt : ctx.stmt()) {
                ASTNode n = visit(stmt);
                if (n != null) nodes.add(n);
            }
        }
        return nodes;
    }

    private List<ExpressionNode> visitArgList(PythonParser.ArglistContext ctx) {
        List<ExpressionNode> args = new ArrayList<>();
        if (ctx != null) {
            for (PythonParser.ArgumentContext arg : ctx.argument()) {
                if (arg instanceof PythonParser.PositionalArgContext) {
                    args.add((ExpressionNode) visit(((PythonParser.PositionalArgContext) arg).expr()));
                } else if (arg instanceof PythonParser.KeywordArgContext) {
                    args.add((ExpressionNode) visit(((PythonParser.KeywordArgContext) arg).expr()));
                }
            }
        }
        return args;
    }
}
