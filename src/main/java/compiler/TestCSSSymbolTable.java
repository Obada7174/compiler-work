package compiler;

import compiler.ast.css.CSSASTNode;
import compiler.ast.css.CSSStylesheetNode;
import compiler.symboltable.css.CSSSymbolTable;
import compiler.symboltable.css.CSSSymbolTableBuilder;
import compiler.visitors.CSSASTBuilder;
import grammar.CSSLexer;
import grammar.CSSParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

public class TestCSSSymbolTable {

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║              CSS SYMBOL TABLE GENERATOR                        ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝\n");

        // Example 1: User's provided example
        testExample1();

        // Example 2: More comprehensive CSS
        testExample2();
    }

     private static void testExample1() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("EXAMPLE 1: Basic CSS with Variables, Selector, and Keyframes");
        System.out.println("=".repeat(80) + "\n");

        String css = """
                :root {
                  --main-color: red;
                }
                .button {
                  color: var(--main-color);
                  animation: fadeIn 2s;
                }
                @keyframes fadeIn {
                  from { opacity: 0; }
                  to { opacity: 1; }
                }
                """;

        processCSS(css, "example1.css");
    }

     private static void testExample2() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("EXAMPLE 2: Comprehensive CSS with Media Queries and Nested Rules");
        System.out.println("=".repeat(80) + "\n");

        String css = """
                :root {
                  --primary-color: #3498db;
                  --secondary-color: #2ecc71;
                  --spacing: 1rem;
                }

                body {
                  font-family: Arial, sans-serif;
                  color: var(--primary-color);
                }

                .container {
                  max-width: 1200px;
                  padding: var(--spacing);
                }

                #header {
                  background: var(--primary-color);
                  color: white;
                }

                .btn:hover {
                  transform: scale(1.1);
                }

                a[href^="https://"] {
                  color: blue;
                }

                @keyframes slideIn {
                  from {
                    transform: translateX(-100%);
                  }
                  to {
                    transform: translateX(0);
                  }
                }

                @media screen and (max-width: 768px) {
                  .container {
                    padding: 0.5rem;
                  }
                  #header {
                    font-size: 1.5rem;
                  }
                }
                """;

        processCSS(css, "example2.css");
    }

    private static void processCSS(String cssCode, String filename) {
        try {

            CSSLexer lexer = new CSSLexer(CharStreams.fromString(cssCode));
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            CSSParser parser = new CSSParser(tokens);

            CSSParser.StylesheetContext parseTree = parser.stylesheet();

            CSSASTBuilder astBuilder = new CSSASTBuilder();
            CSSASTNode ast = astBuilder.visit(parseTree);

            if (!(ast instanceof CSSStylesheetNode)) {
                System.err.println("Error: AST root is not CSSStylesheetNode");
                return;
            }

            CSSStylesheetNode stylesheet = (CSSStylesheetNode) ast;

            CSSSymbolTableBuilder symbolTableBuilder = new CSSSymbolTableBuilder();
            CSSSymbolTable symbolTable = symbolTableBuilder.build(stylesheet);
            symbolTable.setSourceFile(filename);

            symbolTable.print();

            System.out.println("\n════════════════════════════════════════════════════════════════");
            System.out.println("                    SYMBOL TABLE (JSON)");
            System.out.println("════════════════════════════════════════════════════════════════\n");
            System.out.println(symbolTable.toJSON());

            System.out.println("\n════════════════════════════════════════════════════════════════");
            System.out.println("                       STATISTICS");
            System.out.println("════════════════════════════════════════════════════════════════");
            symbolTable.getStatistics().forEach((key, value) ->
                    System.out.println(String.format("  %-20s: %d", key, value))
            );
            System.out.println("════════════════════════════════════════════════════════════════");

            System.out.println("\n✓✓✓ Symbol Table Generated Successfully! ✓✓✓\n");

        } catch (Exception e) {
            System.err.println("Error processing CSS: " + e.getMessage());
            e.printStackTrace();
        }
    }


    @SuppressWarnings("unused")
    private static void demonstrateLookup(CSSSymbolTable symbolTable) {
        System.out.println("\n════════════════════════════════════════════════════════════════");
        System.out.println("                    SYMBOL LOOKUP DEMO");
        System.out.println("════════════════════════════════════════════════════════════════\n");

        // Look up a CSS variable
        var mainColorVars = symbolTable.lookup("--main-color");
        if (!mainColorVars.isEmpty()) {
            System.out.println("Looking up '--main-color':");
            mainColorVars.forEach(entry -> System.out.println("  " + entry));
        }

        // Look up a class selector
        var buttonClasses = symbolTable.lookup("button");
        if (!buttonClasses.isEmpty()) {
            System.out.println("\nLooking up '.button':");
            buttonClasses.forEach(entry -> System.out.println("  " + entry));
        }

        // Get all keyframes
        var keyframes = symbolTable.getKeyframes();
        if (!keyframes.isEmpty()) {
            System.out.println("\nAll Keyframes:");
            keyframes.forEach(entry -> System.out.println("  " + entry));
        }
    }
}
