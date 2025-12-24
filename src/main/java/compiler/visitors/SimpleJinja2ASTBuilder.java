package compiler.visitors;

import compiler.ast.*;
import grammar.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Simplified Jinja2 AST Builder - compiles successfully with basic functionality
 * This is a working foundation that can be enhanced based on actual usage
 */
public class SimpleJinja2ASTBuilder extends Jinja2ParserBaseVisitor<ASTNode> {

    @Override
    public ASTNode visitTemplateNode(Jinja2Parser.TemplateNodeContext ctx) {
        int lineNumber = ctx.start != null ? ctx.start.getLine() : 1;
        ProgramNode program = new ProgramNode(lineNumber);

        // Process all content nodes
        if (ctx.content() != null) {
            for (Jinja2Parser.ContentContext contentCtx : ctx.content()) {
                ASTNode child = visit(contentCtx);
                if (child != null) {
                    program.addChild(child);
                }
            }
        }

        return program;
    }

    @Override
    public ASTNode visitHtmlTextContent(Jinja2Parser.HtmlTextContentContext ctx) {
        int lineNumber = ctx.start != null ? ctx.start.getLine() : 1;
        String text = ctx.HTML_TEXT() != null ? ctx.HTML_TEXT().getText() : "";

        // Skip whitespace-only text
        if (!text.trim().isEmpty()) {
            return new HTMLTextNode(text, lineNumber);
        }
        return null;
    }

    @Override
    public ASTNode visitHtmlElementContent(Jinja2Parser.HtmlElementContentContext ctx) {
        if (ctx.htmlElement() != null) {
            return visit(ctx.htmlElement());
        }
        return null;
    }

    @Override
    public ASTNode visitHtmlElement(Jinja2Parser.HtmlElementContext ctx) {
        int lineNumber = ctx.start != null ? ctx.start.getLine() : 1;

        // Get tag name
        String tagName = "";
        org.antlr.v4.runtime.tree.TerminalNode tagNameNode = ctx.HTML_TAG_NAME();
        if (tagNameNode != null) {
            tagName = tagNameNode.getText();
        }

        // Get attributes
        java.util.Map<String, String> attributes = new java.util.LinkedHashMap<>();
        if (ctx.htmlAttribute() != null) {
            for (Jinja2Parser.HtmlAttributeContext attrCtx : ctx.htmlAttribute()) {
                org.antlr.v4.runtime.tree.TerminalNode attrNameNode = attrCtx.HTML_TAG_NAME();
                String attrName = attrNameNode != null ? attrNameNode.getText() : "";
                String attrValue = "";
                if (attrCtx.attrValue() != null) {
                    attrValue = attrCtx.attrValue().getText();
                }
                if (!attrName.isEmpty()) {
                    attributes.put(attrName, attrValue);
                }
            }
        }

        // Check if self-closing
        boolean selfClosing = ctx.HTML_TAG_SLASH_CLOSE() != null;

        // Create the HTML element node
        HTMLElementNode htmlElement = new HTMLElementNode(tagName, attributes, selfClosing, lineNumber);

        // Process all nested content
        if (ctx.htmlContent() != null) {
            for (Jinja2Parser.HtmlContentContext contentCtx : ctx.htmlContent()) {
                ASTNode child = visit(contentCtx);
                if (child != null) {
                    htmlElement.addChild(child);
                }
            }
        }

        return htmlElement;
    }

    @Override
    public ASTNode visitHtmlContent(Jinja2Parser.HtmlContentContext ctx) {
        // HtmlContent is a wrapper around different content types, just process the child
        return visitChildren(ctx);
    }

    @Override
    public ASTNode visitJinjaVarContent(Jinja2Parser.JinjaVarContentContext ctx) {
        if (ctx.jinjaVar() != null) {
            return visit(ctx.jinjaVar());
        }
        return null;
    }

    @Override
    public ASTNode visitJinjaControlContent(Jinja2Parser.JinjaControlContentContext ctx) {
        if (ctx.jinjaControl() != null) {
            return visit(ctx.jinjaControl());
        }
        return null;
    }

    @Override
    public ASTNode visitHtmlCommentContent(Jinja2Parser.HtmlCommentContentContext ctx) {
        // Skip HTML comments for now
        return null;
    }

    @Override
    public ASTNode visitHtmlCloseContent(Jinja2Parser.HtmlCloseContentContext ctx) {
        // HTML close tags are handled by their opening tags
        return null;
    }

    @Override
    public ASTNode visitScriptContent(Jinja2Parser.ScriptContentContext ctx) {
        if (ctx.htmlScriptTag() != null) {
            return visit(ctx.htmlScriptTag());
        }
        return null;
    }

    @Override
    public ASTNode visitStyleContent(Jinja2Parser.StyleContentContext ctx) {
        if (ctx.htmlStyleTag() != null) {
            return visit(ctx.htmlStyleTag());
        }
        return null;
    }

    @Override
    public ASTNode visitJinjaVar(Jinja2Parser.JinjaVarContext ctx) {
        int lineNumber = ctx.start != null ? ctx.start.getLine() : 1;

        // Get the expression
        ExpressionNode expr = null;
        if (ctx.jinjaExpression() != null) {
            ASTNode exprNode = visit(ctx.jinjaExpression());
            if (exprNode instanceof ExpressionNode) {
                expr = (ExpressionNode) exprNode;
            }
        }

        // Get filters
        List<String> filters = new ArrayList<>();
        if (ctx.jinjaFilter() != null) {
            for (Jinja2Parser.JinjaFilterContext filterCtx : ctx.jinjaFilter()) {
                if (filterCtx.JINJA_VAR_IDENTIFIER() != null) {
                    filters.add(filterCtx.JINJA_VAR_IDENTIFIER().getText());
                }
            }
        }

        return new Jinja2VarNode(expr, filters, lineNumber);
    }

    @Override
    public ASTNode visitJinjaIdentifierExpr(Jinja2Parser.JinjaIdentifierExprContext ctx) {
        int lineNumber = ctx.start != null ? ctx.start.getLine() : 1;
        String name = ctx.JINJA_VAR_IDENTIFIER() != null ? ctx.JINJA_VAR_IDENTIFIER().getText() : "";
        return new IdentifierNode(name, lineNumber);
    }

    @Override
    public ASTNode visitJinjaNumberExpr(Jinja2Parser.JinjaNumberExprContext ctx) {
        int lineNumber = ctx.start != null ? ctx.start.getLine() : 1;
        double value = ctx.JINJA_VAR_NUMBER() != null ? Double.parseDouble(ctx.JINJA_VAR_NUMBER().getText()) : 0.0;
        return new NumberLiteralNode(value, lineNumber);
    }

    @Override
    public ASTNode visitJinjaStringExpr(Jinja2Parser.JinjaStringExprContext ctx) {
        int lineNumber = ctx.start != null ? ctx.start.getLine() : 1;
        String text = ctx.JINJA_VAR_STRING() != null ? ctx.JINJA_VAR_STRING().getText() : "";
        // Remove quotes
        String value = text.length() > 2 ? text.substring(1, text.length() - 1) : text;
        return new StringLiteralNode(value, lineNumber);
    }

    @Override
    public ASTNode visitJinjaMemberAccessExpr(Jinja2Parser.JinjaMemberAccessExprContext ctx) {
        int lineNumber = ctx.start != null ? ctx.start.getLine() : 1;

        ExpressionNode object = null;
        if (ctx.jinjaExpression() != null) {
            ASTNode objNode = visit(ctx.jinjaExpression());
            if (objNode instanceof ExpressionNode) {
                object = (ExpressionNode) objNode;
            }
        }

        String memberName = ctx.JINJA_VAR_IDENTIFIER() != null ? ctx.JINJA_VAR_IDENTIFIER().getText() : "";
        return new MemberAccessNode(object, memberName, lineNumber);
    }

    // Jinja Statement Expression Visitors (for control structures)
    @Override
    public ASTNode visitJinjaStmtIdentifierExpr(Jinja2Parser.JinjaStmtIdentifierExprContext ctx) {
        int lineNumber = ctx.start != null ? ctx.start.getLine() : 1;
        String name = ctx.JINJA_STMT_IDENTIFIER() != null ? ctx.JINJA_STMT_IDENTIFIER().getText() : "";
        return new IdentifierNode(name, lineNumber);
    }

    @Override
    public ASTNode visitJinjaStmtStringExpr(Jinja2Parser.JinjaStmtStringExprContext ctx) {
        int lineNumber = ctx.start != null ? ctx.start.getLine() : 1;
        String text = ctx.JINJA_STMT_STRING() != null ? ctx.JINJA_STMT_STRING().getText() : "";
        // Remove quotes
        String value = text.length() > 2 ? text.substring(1, text.length() - 1) : text;
        return new StringLiteralNode(value, lineNumber);
    }

    @Override
    public ASTNode visitJinjaStmtMemberAccessExpr(Jinja2Parser.JinjaStmtMemberAccessExprContext ctx) {
        int lineNumber = ctx.start != null ? ctx.start.getLine() : 1;

        ExpressionNode object = null;
        if (ctx.jinjaStmtExpression() != null) {
            ASTNode objNode = visit(ctx.jinjaStmtExpression());
            if (objNode instanceof ExpressionNode) {
                object = (ExpressionNode) objNode;
            }
        }

        String memberName = ctx.JINJA_STMT_IDENTIFIER() != null ? ctx.JINJA_STMT_IDENTIFIER().getText() : "";
        return new MemberAccessNode(object, memberName, lineNumber);
    }

    // Jinja If/For/Block Implementations
    @Override
    public ASTNode visitJinjaIfStmt(Jinja2Parser.JinjaIfStmtContext ctx) {
        int lineNumber = ctx.start != null ? ctx.start.getLine() : 1;

        // Get condition
        ExpressionNode condition = null;
        if (ctx.jinjaStmtExpression() != null && !ctx.jinjaStmtExpression().isEmpty()) {
            ASTNode condNode = visit(ctx.jinjaStmtExpression(0));
            if (condNode instanceof ExpressionNode) {
                condition = (ExpressionNode) condNode;
            }
        }

        // Collect body content
        List<ASTNode> thenBlock = new ArrayList<>();
        if (ctx.content() != null) {
            for (Jinja2Parser.ContentContext contentCtx : ctx.content()) {
                ASTNode child = visit(contentCtx);
                if (child != null) {
                    thenBlock.add(child);
                }
            }
        }

        // Create if node with empty else block for now
        return new JinjaIfNode(condition, thenBlock, new ArrayList<>(), lineNumber);
    }

    @Override
    public ASTNode visitJinjaForStmt(Jinja2Parser.JinjaForStmtContext ctx) {
        int lineNumber = ctx.start != null ? ctx.start.getLine() : 1;

        // Get target variable name
        String target = "";
        if (ctx.jinjaStmtTarget() != null && ctx.jinjaStmtTarget().JINJA_STMT_IDENTIFIER(0) != null) {
            target = ctx.jinjaStmtTarget().JINJA_STMT_IDENTIFIER(0).getText();
        }

        // Get iterable expression
        ExpressionNode iterable = null;
        if (ctx.jinjaStmtExpression() != null) {
            ASTNode iterNode = visit(ctx.jinjaStmtExpression());
            if (iterNode instanceof ExpressionNode) {
                iterable = (ExpressionNode) iterNode;
            }
        }

        // Collect body content
        List<ASTNode> body = new ArrayList<>();
        if (ctx.content() != null) {
            for (Jinja2Parser.ContentContext contentCtx : ctx.content()) {
                ASTNode child = visit(contentCtx);
                if (child != null) {
                    body.add(child);
                }
            }
        }

        // Create for node with collected body
        return new JinjaForNode(target, iterable, body, lineNumber);
    }

    @Override
    public ASTNode visitJinjaBlock(Jinja2Parser.JinjaBlockContext ctx) {
        int lineNumber = ctx.start != null ? ctx.start.getLine() : 1;

        // Get block name
        String blockName = "";
        if (ctx.JINJA_STMT_IDENTIFIER() != null && !ctx.JINJA_STMT_IDENTIFIER().isEmpty()) {
            blockName = ctx.JINJA_STMT_IDENTIFIER(0).getText();
        }

        // Collect block content
        List<ASTNode> content = new ArrayList<>();
        if (ctx.content() != null) {
            for (Jinja2Parser.ContentContext contentCtx : ctx.content()) {
                ASTNode child = visit(contentCtx);
                if (child != null) {
                    content.add(child);
                }
            }
        }

        // Create block node with collected content
        return new JinjaBlockNode(blockName, content, lineNumber);
    }

    // Jinja Control Structure Visitors
    @Override
    public ASTNode visitJinjaIfControl(Jinja2Parser.JinjaIfControlContext ctx) {
        if (ctx.jinjaIf() != null) {
            return visit(ctx.jinjaIf());
        }
        return null;
    }

    @Override
    public ASTNode visitJinjaForControl(Jinja2Parser.JinjaForControlContext ctx) {
        if (ctx.jinjaFor() != null) {
            return visit(ctx.jinjaFor());
        }
        return null;
    }

    @Override
    public ASTNode visitJinjaBlockControl(Jinja2Parser.JinjaBlockControlContext ctx) {
        if (ctx.jinjaBlock() != null) {
            return visit(ctx.jinjaBlock());
        }
        return null;
    }

    @Override
    public ASTNode visitJinjaExtends(Jinja2Parser.JinjaExtendsContext ctx) {
        int lineNumber = ctx.start != null ? ctx.start.getLine() : 1;

        // Get template name
        String templateName = "";
        if (ctx.JINJA_STMT_STRING() != null) {
            String text = ctx.JINJA_STMT_STRING().getText();
            // Remove quotes
            templateName = text.length() > 2 ? text.substring(1, text.length() - 1) : text;
        }

        // Use StringLiteralNode to represent extends template name
        // Prefix with "extends:" to distinguish it
        return new StringLiteralNode("extends:" + templateName, lineNumber);
    }

    // Other Jinja control structures (stub implementations for now)
    @Override
    public ASTNode visitJinjaExtendsControl(Jinja2Parser.JinjaExtendsControlContext ctx) {
        if (ctx.jinjaExtends() != null) {
            return visit(ctx.jinjaExtends());
        }
        return null;
    }

    @Override
    public ASTNode visitJinjaIncludeControl(Jinja2Parser.JinjaIncludeControlContext ctx) {
        // TODO: Implement include
        return null;
    }

    @Override
    public ASTNode visitJinjaSetControl(Jinja2Parser.JinjaSetControlContext ctx) {
        // TODO: Implement set
        return null;
    }

    @Override
    public ASTNode visitJinjaMacroControl(Jinja2Parser.JinjaMacroControlContext ctx) {
        // TODO: Implement macro
        return null;
    }

    @Override
    public ASTNode visitJinjaCallControl(Jinja2Parser.JinjaCallControlContext ctx) {
        // TODO: Implement call
        return null;
    }

    @Override
    public ASTNode visitJinjaFilterBlockControl(Jinja2Parser.JinjaFilterBlockControlContext ctx) {
        // TODO: Implement filter block
        return null;
    }

    @Override
    public ASTNode visitJinjaWithControl(Jinja2Parser.JinjaWithControlContext ctx) {
        // TODO: Implement with
        return null;
    }

    @Override
    public ASTNode visitJinjaAutoescapeControl(Jinja2Parser.JinjaAutoescapeControlContext ctx) {
        // TODO: Implement autoescape
        return null;
    }

    @Override
    public ASTNode visitJinjaImportControl(Jinja2Parser.JinjaImportControlContext ctx) {
        // TODO: Implement import
        return null;
    }

    @Override
    protected ASTNode defaultResult() {
        // Return null as default - will be filtered out
        return null;
    }
}
