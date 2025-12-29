package compiler.examples;

import compiler.ast.css.CSSASTNode;
import compiler.visitors.CSSASTBuilder;
import grammar.CSSLexer;
import grammar.CSSParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

/**
 * Example demonstrating CSS AST Builder usage.
 * This example parses CSS code and prints the resulting Abstract Syntax Tree.
 */
public class CSSASTExample {

    public static void main(String[] args) {
        // Example CSS code
        String cssCode = """
                /* Example Stylesheet */
                .container {
                    color: #fff;
                    background-color: rgb(255, 0, 0);
                    margin: 10px 20px;
                    padding: calc(100% - 20px);
                    font-size: 16px;
                }

                #header, .nav-item:hover {
                    display: flex;
                    width: 100%;
                }

                div > p.text {
                    line-height: 1.5;
                }

                @media screen and (max-width: 768px) {
                    .container {
                        width: 100%;
                    }
                }

                a[href="https://example.com"] {
                    text-decoration: none;
                }
                """;

        System.out.println("=".repeat(80));
        System.out.println("CSS AST BUILDER EXAMPLE");
        System.out.println("=".repeat(80));
        System.out.println();
        System.out.println("Input CSS:");
        System.out.println("-".repeat(80));
        System.out.println(cssCode);
        System.out.println("-".repeat(80));
        System.out.println();

        // Build AST
        CSSASTNode ast = parseCSS(cssCode);

        // Print AST
        System.out.println("Generated AST:");
        System.out.println("-".repeat(80));
        if (ast != null) {
            ast.print("");
        } else {
            System.out.println("Failed to build AST");
        }
        System.out.println("-".repeat(80));
    }

    /**
     * Parses CSS code and returns the AST root node.
     */
    public static CSSASTNode parseCSS(String cssCode) {
        try {
            // Create lexer
            CSSLexer lexer = new CSSLexer(CharStreams.fromString(cssCode));
            CommonTokenStream tokens = new CommonTokenStream(lexer);

            // Create parser
            CSSParser parser = new CSSParser(tokens);

            // Parse stylesheet (root rule)
            CSSParser.StylesheetContext parseTree = parser.stylesheet();

            // Build AST using visitor
            CSSASTBuilder builder = new CSSASTBuilder();
            return builder.visit(parseTree);

        } catch (Exception e) {
            System.err.println("Error parsing CSS: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
