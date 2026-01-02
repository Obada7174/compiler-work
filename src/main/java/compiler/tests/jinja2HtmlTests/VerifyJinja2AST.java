package compiler.tests.jinja2HtmlTests;

import compiler.ast.core.ASTNode;
import compiler.ast.core.BlockNode;
import compiler.ast.core.expressions.IdentifierNode;
import compiler.ast.core.expressions.MemberAccessNode;
import compiler.ast.core.expressions.NumberLiteralNode;
import compiler.ast.core.expressions.StringLiteralNode;
import compiler.ast.jinjaHtml.HTMLElementNode;
import compiler.ast.jinjaHtml.HTMLTextNode;
import compiler.ast.jinjaHtml.Jinja2VarNode;
import compiler.ast.python.*;
import compiler.visitors.SimpleJinja2ASTBuilder;
import grammar.*;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class VerifyJinja2AST {
    public static void main(String[] args) {
        String templateSource = """
            <div class="container">
                <h1>{{ title }}</h1>
                {% for item in items %}
                    <p>{{ item.name }} - {{ item.price }}</p>
                {% endfor %}
            </div>
        """;


        System.out.println("Template Source:");
             System.out.println(templateSource);

        // Lexer & Parser
        Jinja2Lexer lexer = new Jinja2Lexer(CharStreams.fromString(templateSource));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        Jinja2Parser parser = new Jinja2Parser(tokens);
        parser.removeErrorListeners();

        // Parse
        ParseTree tree = parser.template();

        // Build AST
        SimpleJinja2ASTBuilder builder = new SimpleJinja2ASTBuilder();
        ASTNode ast = builder.visit(tree);

        System.out.println("AST TREE:");
        System.out.println("─────────────────────────────────────────────────────────────");
        printAST(ast, 0);
        System.out.println("─────────────────────────────────────────────────────────────");
    }

    private static void printAST(ASTNode node, int indent) {
        if (node == null) return;

        for (int i = 0; i < indent; i++) System.out.print("  ");

        String content = "";
        if (node instanceof HTMLTextNode htmlText) content = "Text: " + htmlText.getText().trim();
        else if (node instanceof Jinja2VarNode varNode) content = "VarExpr -> " + varNode.getExpression();
//        else if (node instanceof TemplateNode) content = "TemplateNode";
        else if (node instanceof BlockNode blockNode) content = "Block: " + blockNode.getName();
        else if (node instanceof HTMLElementNode elemNode) content = "HTML Element: " + elemNode.getTagName();
        else if (node instanceof IdentifierNode idNode) content = "Identifier: " + idNode.getName();
        else if (node instanceof StringLiteralNode strNode) content = "String: " + strNode.getValue();
        else if (node instanceof NumberLiteralNode numNode) content = "Number: " + numNode.getValue();
        else if (node instanceof BooleanLiteralNode boolNode) content = "Boolean: " + boolNode.getValue();
        else if (node instanceof BinaryOpNode binNode) content = "BinaryOp: " + binNode.getOperator();
        else if (node instanceof MemberAccessNode memNode) content = "MemberAccess: " + memNode.getMemberName();
        else if (node instanceof IndexAccessNode) content = "IndexAccess";

        System.out.println(node.getClass().getSimpleName() + (content.isEmpty() ? "" : " -> " + content));

        for (ASTNode child : node.getChildren()) {
            printAST(child, indent + 1);
        }
    }
}
