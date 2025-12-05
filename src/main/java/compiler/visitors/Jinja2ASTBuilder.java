package compiler.visitors;

import compiler.ast.*;
import grammar.Jinja2Parser;
import grammar.Jinja2ParserBaseVisitor;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Visitor that builds the AST from the ANTLR parse tree for Jinja2 templates
 * Demonstrates the Visitor design pattern
 */
public class Jinja2ASTBuilder extends Jinja2ParserBaseVisitor<ASTNode> {

    // ═══════════════════════════════════════════════════════════════════════
    // MAIN ENTRY POINT
    // ═══════════════════════════════════════════════════════════════════════

    @Override
    public ASTNode visitTemplate(Jinja2Parser.TemplateContext ctx) {
        int lineNumber = ctx.start != null ? ctx.start.getLine() : 1;
        ProgramNode program = new ProgramNode(lineNumber);

        for (Jinja2Parser.ContentContext contentCtx : ctx.content()) {
            ASTNode node = visit(contentCtx);
            if (node != null) {
                program.addChild(node);
            }
        }

        return program;
    }

    @Override
    public ASTNode visitContent(Jinja2Parser.ContentContext ctx) {
        if (ctx.htmlElement() != null) {
            return visit(ctx.htmlElement());
        } else if (ctx.jinjaVar() != null) {
            return visit(ctx.jinjaVar());
        } else if (ctx.jinjaControl() != null) {
            return visit(ctx.jinjaControl());
        } else if (ctx.HTML_TEXT() != null) {
            int lineNumber = ctx.start.getLine();
            String text = ctx.HTML_TEXT().getText();
            // Skip empty or whitespace-only text nodes
            if (!text.trim().isEmpty()) {
                return new HTMLTextNode(text, lineNumber);
            }
        }
        return null;
    }

    // ═══════════════════════════════════════════════════════════════════════
    // HTML ELEMENTS
    // ═══════════════════════════════════════════════════════════════════════

    @Override
    public ASTNode visitHtmlElement(Jinja2Parser.HtmlElementContext ctx) {
        int lineNumber = ctx.start.getLine();
        String tagName = ctx.HTML_TAG_NAME().getText();

        // Extract attributes
        Map<String, String> attributes = new HashMap<>();
        for (Jinja2Parser.HtmlAttributeContext attrCtx : ctx.htmlAttribute()) {
            String attrName = attrCtx.HTML_TAG_NAME().getText();
            String attrValue = "";

            if (attrCtx.attrValue() != null) {
                // For simplicity, concatenate all text from attribute value
                attrValue = attrCtx.attrValue().getText();
                // Remove quotes if present
                if ((attrValue.startsWith("\"") && attrValue.endsWith("\"")) ||
                    (attrValue.startsWith("'") && attrValue.endsWith("'"))) {
                    attrValue = attrValue.substring(1, attrValue.length() - 1);
                }
            }

            attributes.put(attrName, attrValue);
        }

        // Check if self-closing
        boolean selfClosing = ctx.HTML_TAG_SLASH_CLOSE() != null;

        HTMLElementNode element = new HTMLElementNode(tagName, attributes, selfClosing, lineNumber);

        // Add content children (if not self-closing)
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
        if (ctx.htmlElement() != null) {
            return visit(ctx.htmlElement());
        } else if (ctx.jinjaVar() != null) {
            return visit(ctx.jinjaVar());
        } else if (ctx.jinjaControl() != null) {
            return visit(ctx.jinjaControl());
        } else if (ctx.HTML_TEXT() != null) {
            int lineNumber = ctx.start.getLine();
            String text = ctx.HTML_TEXT().getText();
            if (!text.trim().isEmpty()) {
                return new HTMLTextNode(text, lineNumber);
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

        // Extract filters
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
        int lineNumber = ctx.start.getLine();
        String name = ctx.JINJA_VAR_IDENTIFIER().getText();
        return new IdentifierNode(name, lineNumber);
    }

    @Override
    public ASTNode visitJinjaNumberExpr(Jinja2Parser.JinjaNumberExprContext ctx) {
        int lineNumber = ctx.start.getLine();
        double value = Double.parseDouble(ctx.JINJA_VAR_NUMBER().getText());
        return new NumberLiteralNode(value, lineNumber);
    }

    @Override
    public ASTNode visitJinjaStringExpr(Jinja2Parser.JinjaStringExprContext ctx) {
        int lineNumber = ctx.start.getLine();
        String text = ctx.JINJA_VAR_STRING().getText();
        // Remove quotes
        String value = text.substring(1, text.length() - 1);
        return new StringLiteralNode(value, lineNumber);
    }

    @Override
    public ASTNode visitJinjaTrueExpr(Jinja2Parser.JinjaTrueExprContext ctx) {
        int lineNumber = ctx.start.getLine();
        return new BooleanLiteralNode(true, lineNumber);
    }

    @Override
    public ASTNode visitJinjaFalseExpr(Jinja2Parser.JinjaFalseExprContext ctx) {
        int lineNumber = ctx.start.getLine();
        return new BooleanLiteralNode(false, lineNumber);
    }

    @Override
    public ASTNode visitJinjaMemberAccessExpr(Jinja2Parser.JinjaMemberAccessExprContext ctx) {
        int lineNumber = ctx.start.getLine();
        ExpressionNode object = (ExpressionNode) visit(ctx.jinjaExpression());
        String memberName = ctx.JINJA_VAR_IDENTIFIER().getText();
        return new MemberAccessNode(object, memberName, lineNumber);
    }

    @Override
    public ASTNode visitJinjaIndexAccessExpr(Jinja2Parser.JinjaIndexAccessExprContext ctx) {
        int lineNumber = ctx.start.getLine();
        ExpressionNode object = (ExpressionNode) visit(ctx.jinjaExpression(0));
        ExpressionNode index = (ExpressionNode) visit(ctx.jinjaExpression(1));
        return new IndexAccessNode(object, index, lineNumber);
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
    public ASTNode visitJinjaDivExpr(Jinja2Parser.JinjaDivExprContext ctx) {
        return createBinaryOp(ctx.jinjaExpression(0), ctx.jinjaExpression(1), "/", ctx.start.getLine());
    }

    @Override
    public ASTNode visitJinjaComparisonExpr(Jinja2Parser.JinjaComparisonExprContext ctx) {
        int lineNumber = ctx.start.getLine();
        ExpressionNode left = (ExpressionNode) visit(ctx.jinjaExpression(0));
        ExpressionNode right = (ExpressionNode) visit(ctx.jinjaExpression(1));
        String operator = ctx.comparisonOp().getText();
        return new BinaryOpNode(operator, left, right, lineNumber);
    }

    @Override
    public ASTNode visitJinjaListExpr(Jinja2Parser.JinjaListExprContext ctx) {
        int lineNumber = ctx.start.getLine();
        List<ExpressionNode> elements = new ArrayList<>();

        if (ctx.jinjaArgList() != null) {
            for (Jinja2Parser.JinjaExpressionContext exprCtx : ctx.jinjaArgList().jinjaExpression()) {
                ExpressionNode elem = (ExpressionNode) visit(exprCtx);
                elements.add(elem);
            }
        }

        return new ListLiteralNode(elements, lineNumber);
    }

    // ═══════════════════════════════════════════════════════════════════════
    // JINJA2 CONTROL STRUCTURES
    // ═══════════════════════════════════════════════════════════════════════

    @Override
    public ASTNode visitJinjaControl(Jinja2Parser.JinjaControlContext ctx) {
        if (ctx.jinjaIf() != null) {
            return visit(ctx.jinjaIf());
        } else if (ctx.jinjaFor() != null) {
            return visit(ctx.jinjaFor());
        } else if (ctx.jinjaBlock() != null) {
            return visit(ctx.jinjaBlock());
        }
        // Handle other control structures if needed
        return null;
    }

    @Override
    public ASTNode visitJinjaIf(Jinja2Parser.JinjaIfContext ctx) {
        int lineNumber = ctx.start.getLine();
        ExpressionNode condition = (ExpressionNode) visit(ctx.jinjaStmtExpression(0));

        // Collect 'then' block content
        List<ASTNode> thenBlock = new ArrayList<>();
        int contentStart = 0;
        int contentEnd = ctx.content().size();

        // Find where elif or else starts
        int elifCount = ctx.JINJA_STMT_ELIF().size();
        if (elifCount > 0 || ctx.JINJA_STMT_ELSE() != null) {
            // For simplicity, collect all content before first elif/else
            // In a full implementation, we would handle elif chains properly
            for (int i = contentStart; i < contentEnd; i++) {
                ASTNode node = visit(ctx.content(i));
                if (node != null) {
                    thenBlock.add(node);
                    contentStart = i + 1;
                    break;
                }
            }
        } else {
            // No elif/else, all content goes to then block
            for (Jinja2Parser.ContentContext contentCtx : ctx.content()) {
                ASTNode node = visit(contentCtx);
                if (node != null) {
                    thenBlock.add(node);
                }
            }
        }

        // For simplicity, collect remaining content as else block
        List<ASTNode> elseBlock = new ArrayList<>();
        for (int i = contentStart; i < contentEnd; i++) {
            ASTNode node = visit(ctx.content(i));
            if (node != null) {
                elseBlock.add(node);
            }
        }

        return new JinjaIfNode(condition, thenBlock, elseBlock, lineNumber);
    }

    @Override
    public ASTNode visitJinjaFor(Jinja2Parser.JinjaForContext ctx) {
        int lineNumber = ctx.start.getLine();

        // Get variable name
        String variable;
        if (ctx.jinjaStmtTarget().JINJA_STMT_IDENTIFIER().size() > 0) {
            variable = ctx.jinjaStmtTarget().JINJA_STMT_IDENTIFIER(0).getText();
        } else {
            variable = "item";
        }

        // Get iterable expression
        ExpressionNode iterable = (ExpressionNode) visit(ctx.jinjaStmtExpression());

        // Collect loop body
        List<ASTNode> body = new ArrayList<>();
        for (Jinja2Parser.ContentContext contentCtx : ctx.content()) {
            ASTNode node = visit(contentCtx);
            if (node != null) {
                body.add(node);
            }
        }

        return new JinjaForNode(variable, iterable, body, lineNumber);
    }

    @Override
    public ASTNode visitJinjaBlock(Jinja2Parser.JinjaBlockContext ctx) {
        int lineNumber = ctx.start.getLine();
        String blockName = ctx.JINJA_STMT_IDENTIFIER(0).getText();

        List<ASTNode> content = new ArrayList<>();
        for (Jinja2Parser.ContentContext contentCtx : ctx.content()) {
            ASTNode node = visit(contentCtx);
            if (node != null) {
                content.add(node);
            }
        }

        return new JinjaBlockNode(blockName, content, lineNumber);
    }

    // ═══════════════════════════════════════════════════════════════════════
    // JINJA2 STATEMENT EXPRESSIONS
    // ═══════════════════════════════════════════════════════════════════════

    @Override
    public ASTNode visitJinjaStmtIdentifierExpr(Jinja2Parser.JinjaStmtIdentifierExprContext ctx) {
        int lineNumber = ctx.start.getLine();
        String name = ctx.JINJA_STMT_IDENTIFIER().getText();
        return new IdentifierNode(name, lineNumber);
    }

    @Override
    public ASTNode visitJinjaStmtNumberExpr(Jinja2Parser.JinjaStmtNumberExprContext ctx) {
        int lineNumber = ctx.start.getLine();
        double value = Double.parseDouble(ctx.JINJA_STMT_NUMBER().getText());
        return new NumberLiteralNode(value, lineNumber);
    }

    @Override
    public ASTNode visitJinjaStmtStringExpr(Jinja2Parser.JinjaStmtStringExprContext ctx) {
        int lineNumber = ctx.start.getLine();
        String text = ctx.JINJA_STMT_STRING().getText();
        String value = text.substring(1, text.length() - 1);
        return new StringLiteralNode(value, lineNumber);
    }

    @Override
    public ASTNode visitJinjaStmtMemberAccessExpr(Jinja2Parser.JinjaStmtMemberAccessExprContext ctx) {
        int lineNumber = ctx.start.getLine();
        ExpressionNode object = (ExpressionNode) visit(ctx.jinjaStmtExpression());
        String memberName = ctx.JINJA_STMT_IDENTIFIER().getText();
        return new MemberAccessNode(object, memberName, lineNumber);
    }

    @Override
    public ASTNode visitJinjaStmtListExpr(Jinja2Parser.JinjaStmtListExprContext ctx) {
        int lineNumber = ctx.start.getLine();
        List<ExpressionNode> elements = new ArrayList<>();

        if (ctx.jinjaStmtArgList() != null) {
            for (Jinja2Parser.JinjaStmtExpressionContext exprCtx : ctx.jinjaStmtArgList().jinjaStmtExpression()) {
                ExpressionNode elem = (ExpressionNode) visit(exprCtx);
                elements.add(elem);
            }
        }

        return new ListLiteralNode(elements, lineNumber);
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
