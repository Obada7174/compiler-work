
import grammar.*;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class TestJinja2Contexts {

    public static void main(String[] args) {
        String templateSource = """
            <div class="container">
                <h1>{{ title }}</h1>
                {% for item in items %}
                    <p>{{ item.name }} - {{ item.price }}</p>
                {% endfor %}
            </div>
        """;

        // 1. Lexer + Tokens
        Jinja2Lexer lexer = new Jinja2Lexer(CharStreams.fromString(templateSource));
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        // 2. Parser
        Jinja2Parser parser = new Jinja2Parser(tokens);
        parser.removeErrorListeners();

        // 3. ParseTree
        ParseTree tree = parser.template();

        // 4. Print all contexts recursively
        System.out.println("=== ParseTree Contexts ===");
        printParseTreeContexts(tree, 0);
    }

    private static void printParseTreeContexts(ParseTree node, int indent) {
        if (node == null) return;

        // Indentation
        for (int i = 0; i < indent; i++) System.out.print("  ");

        // Print node info
        String className = node.getClass().getSimpleName();
        String text = node.getText().replace("\n", "\\n").replace("\r", "\\r");
        System.out.println(className + " -> '" + text + "'");

        // Recurse
        for (int i = 0; i < node.getChildCount(); i++) {
            printParseTreeContexts(node.getChild(i), indent + 1);
        }
    }
}
