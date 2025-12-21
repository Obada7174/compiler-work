package compiler.visitors;

import compiler.ast.*;
import grammar.Jinja2Parser;
import grammar.Jinja2ParserBaseVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * Builds AST for Jinja2 template structure (template-level nodes only).
 * Does NOT handle expressions directly.
 */
public class Jinja2ASTBuilder
        extends Jinja2ParserBaseVisitor<ASTNode> {

    private final Jinja2ExpressionBuilder expressionBuilder =
            new Jinja2ExpressionBuilder();

    @Override
    public ASTNode visitTemplate(Jinja2Parser.TemplateContext ctx) {
        TemplateNode root = new TemplateNode(ctx.getStart().getLine());

        for (var child : ctx.children) {
            ASTNode node = visit(child);
            if (node != null) {
                root.addChild(node);
            }
        }

        return root;
    }

    @Override
    public ASTNode visitJinjaVar(Jinja2Parser.JinjaVarContext ctx) {
        int line = ctx.getStart().getLine();

        ExpressionNode expression =
                expressionBuilder.visit(ctx.jinjaExpression());

        if (expression == null) {
            throw new IllegalStateException(
                    "Failed to build Jinja expression at line " + line
            );
        }

        List<String> filters = new ArrayList<>();
        for (Jinja2Parser.JinjaFilterContext filterCtx : ctx.jinjaFilter()) {
            filters.add(filterCtx.JINJA_VAR_IDENTIFIER().getText());
        }

        return new Jinja2VarNode(expression, filters, line);
    }
}
