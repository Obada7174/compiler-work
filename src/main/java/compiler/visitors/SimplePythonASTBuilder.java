package compiler.visitors;

import compiler.ast.*;
import grammar.PythonParser;
import grammar.PythonParserBaseVisitor;

import java.util.*;

/**
 * Python AST Builder compatible with labeled-alternative grammar
 */
public class SimplePythonASTBuilder extends PythonParserBaseVisitor<ASTNode> {

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
        return new AssignmentNode(List.of(target), value, line);
    }

    @Override
    public ASTNode visitExprStmt(PythonParser.ExprStmtContext ctx) {
        return visit(ctx.expr_stmt().expr());
    }


    @Override
    public ASTNode visitReturnStmt(PythonParser.ReturnStmtContext ctx) {
        ExpressionNode value = ctx.return_stmt().expr() != null
                ? (ExpressionNode) visit(ctx.return_stmt().expr())
                : null;
        return new ReturnStatementNode(value, ctx.start.getLine());
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


    @Override
    public ASTNode visitSingleArithExpr(PythonParser.SingleArithExprContext ctx) {
        return visit(ctx.arith_expr());
    }

    @Override
    public ASTNode visitBinaryComparison(PythonParser.BinaryComparisonContext ctx) {
        ExpressionNode left = (ExpressionNode) visit(ctx.arith_expr(0));
        ExpressionNode right = (ExpressionNode) visit(ctx.arith_expr(1));
        String op = ctx.getChild(1).getText();
        return new ComparisonNode(op, left, right, ctx.start.getLine());
    }

    @Override
    public ASTNode visitArith_expr(PythonParser.Arith_exprContext ctx) {
        ASTNode left = visit(ctx.atom_expr(0));
        for (int i = 1; i < ctx.atom_expr().size(); i++) {
            String op = ctx.getChild(2 * i - 1).getText();
            ASTNode right = visit(ctx.atom_expr(i));
            left = new BinaryOpNode(op, (ExpressionNode) left, (ExpressionNode) right, ctx.start.getLine());
        }
        return left;
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
            if (call.call().arglist() != null) {
                for (var a : call.call().arglist().argument()) {
                    args.add((ExpressionNode) visit(a));
                }
            }
            return new FunctionCallNode((ExpressionNode) base, args, line);
        }

        if (ctx instanceof PythonParser.MemberAccessTrailerContext mem) {
            return new MemberAccessNode((ExpressionNode) base, mem.NAME().getText(), line);
        }

        if (ctx instanceof PythonParser.IndexAccessTrailerContext idx) {
            return new IndexAccessNode((ExpressionNode) base,
                    (ExpressionNode) visit(idx.expr()), line);
        }

        return base;
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
        String t = ctx.STRING().getText();
        return new StringLiteralNode(t.substring(1, t.length() - 1), ctx.start.getLine());
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
        return ctx.expr() != null ? visit(ctx.expr()) : null;
    }

    @Override
    public ASTNode visitListAtom(PythonParser.ListAtomContext ctx) {
        return visit(ctx.listLit());
    }

    @Override
    public ASTNode visitListLit(PythonParser.ListLitContext ctx) {
        List<ExpressionNode> elements = new ArrayList<>();
        for (var e : ctx.expr()) {
            elements.add((ExpressionNode) visit(e));
        }
        return new ListLiteralNode(elements, ctx.start.getLine());
    }

    @Override
    public ASTNode visitDictAtom(PythonParser.DictAtomContext ctx) {
        return visit(ctx.dictLit());
    }

    @Override
    public ASTNode visitDictLit(PythonParser.DictLitContext ctx) {
        Map<ExpressionNode, ExpressionNode> map = new LinkedHashMap<>();
        for (var item : ctx.dictItem()) {
            ExpressionNode key = item.STRING() != null
                    ? new StringLiteralNode(item.STRING().getText().replace("\"", ""), ctx.start.getLine())
                    : new StringLiteralNode(item.NAME().getText(), ctx.start.getLine());

            ExpressionNode value = (ExpressionNode) visit(item.expr());
            map.put(key, value);
        }
        return new DictionaryLiteralNode(map, ctx.start.getLine());
    }
}
