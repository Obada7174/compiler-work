package compiler;

import compiler.ast.ASTNode;
import compiler.visitors.Jinja2ASTBuilder;
import grammar.Jinja2Lexer;
import grammar.Jinja2Parser;
import org.antlr.v4.runtime.*;

public class Main {

    public static void main(String[] args) {
        String templateSource = """
            <html>
            <body>

                <h1>{{ page_title }}</h1>

                {% if user.logged_in %}
                    <p>Welcome {{ user.name }}</p>
                {% else %}
                    <p>Please login</p>
                {% endif %}

                {% for product in products %}
                    <div>{{ product.name }} - {{ product.price }}</div>
                {% endfor %}

            </body>
            </html>
            """;

        try {
            // 1. Input
            CharStream input = CharStreams.fromString(templateSource);

            // 2. Lexer
            Jinja2Lexer lexer = new Jinja2Lexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);

            // 3. Parser
            Jinja2Parser parser = new Jinja2Parser(tokens);
            parser.removeErrorListeners();
            parser.addErrorListener(new DiagnosticErrorListener());

            // 4. Parse
            Jinja2Parser.TemplateContext tree = parser.template();

            // 5. Build AST
            Jinja2ASTBuilder builder = new Jinja2ASTBuilder();
            ASTNode ast = builder.visit(tree);

            if (ast == null) {
                System.err.println("AST is null – check Jinja visitor.");
                return;
            }

            // 6. Print AST
            System.out.println("====== JINJA2 AST ======");
            ast.print("");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
