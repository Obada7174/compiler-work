package compiler.visitors;

import compiler.ast.*;
import grammar.Jinja2Parser;
import grammar.Jinja2ParserBaseVisitor;

/**
 * Builds AST nodes for Jinja2 expressions only.
 */
public class Jinja2ExpressionBuilder
        extends Jinja2ParserBaseVisitor<ExpressionNode> {

    @Override
    public ExpressionNode visitTemplate(
            Jinja2Parser.TemplateContext ctx) {
        return null; // not handled here
    }

    @Override
    public ExpressionNode visitJinjaIdentifierExpr(
            Jinja2Parser.JinjaIdentifierExprContext ctx) {

        return new IdentifierNode(
                ctx.JINJA_VAR_IDENTIFIER().getText(),
                ctx.getStart().getLine()
        );
    }

    @Override
    public ExpressionNode visitJinjaNumberExpr(
            Jinja2Parser.JinjaNumberExprContext ctx) {

        return new NumberLiteralNode(
                Double.parseDouble(ctx.JINJA_VAR_NUMBER().getText()),
                ctx.getStart().getLine()
        );
    }

    /**
     * Handles member access: product.name
     */

    @Override
    public ExpressionNode visitJinjaOrExpr(
            Jinja2Parser.JinjaOrExprContext ctx) {
        return visit(ctx.getChild(0));
    }

    @Override
    public ASTNode visitContent(Jinja2Parser.ContentContext ctx) {
        return null;
    }

    @Override
    public ExpressionNode visitJinjaAndExpr(
            Jinja2Parser.JinjaAndExprContext ctx) {
        return visit(ctx.getChild(0));
    }

    @Override
    public ExpressionNode visitJinjaComparisonExpr(
            Jinja2Parser.JinjaComparisonExprContext ctx) {
        return visit(ctx.getChild(0));
    }


    @Override
    public ExpressionNode visitJinjaAddExpr(
            Jinja2Parser.JinjaAddExprContext ctx) {

        ExpressionNode left =
                visit(ctx.jinjaExpression(0));
        ExpressionNode right =
                visit(ctx.jinjaExpression(1));

        if (left == null || right == null) {
            throw new IllegalStateException(
                    "Invalid add expression at line "
                            + ctx.getStart().getLine()
            );
        }

        return new BinaryOpNode(
                "+",
                left,
                right,
                ctx.getStart().getLine()
        );
    }
}
