package compiler.visitors;

import compiler.ast.*;
import grammar.*;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Builds AST nodes for Jinja2 templates.
 */
public  class Jinja2ASTBuilder extends Jinja2ParserBaseVisitor<ASTNode> {

    // ═══════════════════════════════════════════════════════════════════════
    // MAIN ENTRY POINT
    // ═══════════════════════════════════════════════════════════════════════

    @Override
    public ASTNode visitContent(Jinja2Parser.ContentContext ctx) {
        ASTNode result = null;

        if (ctx instanceof Jinja2Parser.HtmlElementContentContext htmlCtx) {
            result = visit(htmlCtx.htmlElement());
        } else if (ctx instanceof Jinja2Parser.JinjaVarContentContext varCtx) {
            result = visit(varCtx.jinjaVar());
        } else if (ctx instanceof Jinja2Parser.JinjaControlContentContext ctrlCtx) {
            result = visit(ctrlCtx.jinjaControl());
        } else if (ctx instanceof Jinja2Parser.HtmlTextContentContext textCtx) {
            String text = textCtx.HTML_TEXT().getText();
            if (!text.trim().isEmpty()) {
                result = new HTMLTextNode(text, textCtx.start.getLine());
            }
        }

        if (result == null) {
            // debug: لمعرفة ما يتم تجاهله
            String trimmed = ctx.getText().replace("\n", "").trim();
            if (!trimmed.isEmpty()) {
                System.out.println("Ignored content: " + trimmed);
            }
        }

        return result;
    }

    // ═══════════════════════════════════════════════════════════════════════
    // HTML ELEMENTS
    // ═══════════════════════════════════════════════════════════════════════


    @Override
    public ASTNode visitHtmlElement(Jinja2Parser.HtmlElementContext ctx) {
        int lineNumber = ctx.start.getLine();
        String tagName = ctx.HTML_TAG_NAME().getText();

        Map<String, String> attributes = new HashMap<>();
        for (Jinja2Parser.HtmlAttributeContext attrCtx : ctx.htmlAttribute()) {
            String attrName = attrCtx.HTML_TAG_NAME().getText();
            String attrValue = "";
            if (attrCtx.attrValue() != null) {
                attrValue = attrCtx.attrValue().getText();
                if ((attrValue.startsWith("\"") && attrValue.endsWith("\"")) ||
                        (attrValue.startsWith("'") && attrValue.endsWith("'"))) {
                    attrValue = attrValue.substring(1, attrValue.length() - 1);
                }
            }
            attributes.put(attrName, attrValue);
        }

        boolean selfClosing = ctx.HTML_TAG_SLASH_CLOSE() != null;
        HTMLElementNode element = new HTMLElementNode(tagName, attributes, selfClosing, lineNumber);

        if (!selfClosing && ctx.htmlContent() != null) {
            for (Jinja2Parser.HtmlContentContext contentCtx : ctx.htmlContent()) {
                ASTNode child = visit(contentCtx);
                if (child != null) {
                    element.addChild(child);
                }
            }
        }

        return element;
    }

    @Override
    public ASTNode visitHtmlContent(Jinja2Parser.HtmlContentContext ctx) {
        if (ctx.htmlElement() != null) return visit(ctx.htmlElement());
        if (ctx.jinjaVar() != null) return visit(ctx.jinjaVar());
        if (ctx.jinjaControl() != null) return visit(ctx.jinjaControl());

        if (ctx.HTML_TEXT() != null) {
            String text = ctx.HTML_TEXT().getText();
            if (!text.trim().isEmpty()) {
                return new HTMLTextNode(text, ctx.start.getLine());
            }
        }

        return null;
    }
    // ═══════════════════════════════════════════════════════════════════════
    // JINJA2 VARIABLES {{ }}
    // ═══════════════════════════════════════════════════════════════════════

    @Override
    public ASTNode visitJinjaVar(Jinja2Parser.JinjaVarContext ctx) {
        int lineNumber = ctx.start.getLine();
        ExpressionNode expr = (ExpressionNode) visit(ctx.jinjaExpression());

        List<String> filters = new ArrayList<>();
        for (Jinja2Parser.JinjaFilterContext filterCtx : ctx.jinjaFilter()) {
            filters.add(filterCtx.JINJA_VAR_IDENTIFIER().getText());
        }

        return new Jinja2VarNode(expr, filters, lineNumber);
    }

    // ═══════════════════════════════════════════════════════════════════════
    // JINJA2 EXPRESSIONS
    // ═══════════════════════════════════════════════════════════════════════

    @Override
    public ASTNode visitJinjaIdentifierExpr(Jinja2Parser.JinjaIdentifierExprContext ctx) {
        return new IdentifierNode(ctx.JINJA_VAR_IDENTIFIER().getText(), ctx.start.getLine());
    }

    @Override
    public ASTNode visitJinjaNumberExpr(Jinja2Parser.JinjaNumberExprContext ctx) {
        return new NumberLiteralNode(Double.parseDouble(ctx.JINJA_VAR_NUMBER().getText()), ctx.start.getLine());
    }

    @Override
    public ASTNode visitJinjaStringExpr(Jinja2Parser.JinjaStringExprContext ctx) {
        String text = ctx.JINJA_VAR_STRING().getText();
        String value = text.substring(1, text.length() - 1);
        return new StringLiteralNode(value, ctx.start.getLine());
    }

    @Override
    public ASTNode visitJinjaTrueExpr(Jinja2Parser.JinjaTrueExprContext ctx) {
        return new BooleanLiteralNode(true, ctx.start.getLine());
    }

    @Override
    public ASTNode visitJinjaFalseExpr(Jinja2Parser.JinjaFalseExprContext ctx) {
        return new BooleanLiteralNode(false, ctx.start.getLine());
    }

    @Override
    public ASTNode visitJinjaMemberAccessExpr(Jinja2Parser.JinjaMemberAccessExprContext ctx) {
        ExpressionNode object = (ExpressionNode) visit(ctx.jinjaExpression());
        String memberName = ctx.JINJA_VAR_IDENTIFIER().getText();
        return new MemberAccessNode(object, memberName, ctx.start.getLine());
    }

    @Override
    public ASTNode visitJinjaIndexAccessExpr(Jinja2Parser.JinjaIndexAccessExprContext ctx) {
        ExpressionNode object = (ExpressionNode) visit(ctx.jinjaExpression(0));
        ExpressionNode index = (ExpressionNode) visit(ctx.jinjaExpression(1));
        return new IndexAccessNode(object, index, ctx.start.getLine());
    }

    @Override
    public ASTNode visitJinjaAddExpr(Jinja2Parser.JinjaAddExprContext ctx) {
        return createBinaryOp(ctx.jinjaExpression(0), ctx.jinjaExpression(1), "+", ctx.start.getLine());
    }

    @Override
    public ASTNode visitJinjaSubExpr(Jinja2Parser.JinjaSubExprContext ctx) {
        return createBinaryOp(ctx.jinjaExpression(0), ctx.jinjaExpression(1), "-", ctx.start.getLine());
    }

    @Override
    public ASTNode visitJinjaMulExpr(Jinja2Parser.JinjaMulExprContext ctx) {
        return createBinaryOp(ctx.jinjaExpression(0), ctx.jinjaExpression(1), "*", ctx.start.getLine());
    }
    @Override
    public ASTNode visitTemplate(Jinja2Parser.TemplateContext ctx) {
        ProgramNode program = new ProgramNode(ctx.getStart().getLine());
        for (ParseTree child : ctx.children) {
            ASTNode node = visit(child);
            if (node != null) program.addChild(node);
            else System.out.println("Skipped node: " + child.getText().trim());
        }
        return program;
    }


    @Override
    public ASTNode visitTerminal(TerminalNode node) {
        String text = node.getText().trim();
        if (text.isEmpty()) return null;

        // HTML نصي
        if (node.getSymbol().getType() == Jinja2Lexer.HTML_TEXT) {
            return new HTMLTextNode(text, node.getSymbol().getLine());
        }

        return null; // تجاهل باقي الرموز
    }

    @Override
    public ASTNode visitJinjaDivExpr(Jinja2Parser.JinjaDivExprContext ctx) {
        return createBinaryOp(ctx.jinjaExpression(0), ctx.jinjaExpression(1), "/", ctx.start.getLine());
    }

    @Override
    public ASTNode visitJinjaComparisonExpr(Jinja2Parser.JinjaComparisonExprContext ctx) {
        ExpressionNode left = (ExpressionNode) visit(ctx.jinjaExpression(0));
        ExpressionNode right = (ExpressionNode) visit(ctx.jinjaExpression(1));
        return new BinaryOpNode(ctx.comparisonOp().getText(), left, right, ctx.start.getLine());
    }

    // ═══════════════════════════════════════════════════════════════════════
    // JINJA2 BLOCKS
    // ═══════════════════════════════════════════════════════════════════════

    @Override
    public ASTNode visitJinjaBlock(Jinja2Parser.JinjaBlockContext ctx) {
        String blockName = ctx.JINJA_STMT_IDENTIFIER(0).getText();
        List<ASTNode> content = new ArrayList<>();
        for (Jinja2Parser.ContentContext contentCtx : ctx.content()) {
            ASTNode node = visit(contentCtx);
            if (node != null) content.add(node);
        }
        return new BlockNode(blockName, content, ctx.start.getLine());
    }

    // ═══════════════════════════════════════════════════════════════════════
    // HELPER METHODS
    // ═══════════════════════════════════════════════════════════════════════

    private BinaryOpNode createBinaryOp(Jinja2Parser.JinjaExpressionContext leftCtx,
                                        Jinja2Parser.JinjaExpressionContext rightCtx,
                                        String operator, int lineNumber) {
        ExpressionNode left = (ExpressionNode) visit(leftCtx);
        ExpressionNode right = (ExpressionNode) visit(rightCtx);
        return new BinaryOpNode(operator, left, right, lineNumber);
    }
}
