package compiler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

// ANTLR Runtime imports
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

// Generated parser/lexer imports
import grammar.Jinja2Lexer;
import grammar.Jinja2Parser;

// Compiler internal imports
import compiler.visitors.Jinja2ASTBuilder;
import compiler.ast.ASTNode;
import compiler.utils.ASTPrinter;
import compiler.symboltable.SymbolTable;

/**
 * Main entry point for the Flask/Jinja2/HTML/CSS Compiler
 * Demonstrates all academic requirements:
 * 1. Lexer and Parser (ANTLR4)
 * 2. AST with OOP (inheritance, polymorphism)
 * 3. Visitor pattern for AST construction
 * 4. Symbol Table with operations
 * 5. AST Printer for visualization
 */
public class Main {

    public static void main(String[] args) {
        printHeader();

        // Determine input source
        String input;
        String inputSource;

        if (args.length > 0) {
            // Read from file specified in arguments
            String filePath = args[0];
            inputSource = "File: " + filePath;
            try {
                input = Files.readString(Paths.get(filePath));
            } catch (IOException e) {
                System.err.println("Error reading file: " + filePath);
                e.printStackTrace();
                return;
            }
        } else {
            // Use default test program
            inputSource = "Built-in Example";
            input = getDefaultExample();
        }

        System.out.println("╔═══════════════════════════════════════════════╗");
        System.out.println("║  INPUT SOURCE: " + String.format("%-29s", inputSource) + "║");
        System.out.println("╚═══════════════════════════════════════════════╝");
        System.out.println("\n" + input + "\n");
        System.out.println("═══════════════════════════════════════════════════\n");

        try {
            // ═══════════════════════════════════════════════════════════════
            // STAGE 1: LEXICAL ANALYSIS
            // ═══════════════════════════════════════════════════════════════
            System.out.println("┌─ STAGE 1: Lexical Analysis ─────────────────┐");
            CharStream charStream = CharStreams.fromString(input);
            Jinja2Lexer lexer = new Jinja2Lexer(charStream);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            tokens.fill(); // Force lexing
            System.out.println("│  ✓ Tokenization completed");
            System.out.println("│  → Total tokens: " + tokens.size());
            System.out.println("└──────────────────────────────────────────────┘\n");

            // ═══════════════════════════════════════════════════════════════
            // STAGE 2: SYNTAX ANALYSIS
            // ═══════════════════════════════════════════════════════════════
            System.out.println("┌─ STAGE 2: Syntax Analysis ──────────────────┐");
            Jinja2Parser parser = new Jinja2Parser(tokens);

            // Add custom error listener
            parser.removeErrorListeners();
            parser.addErrorListener(new BaseErrorListener() {
                @Override
                public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
                                      int line, int charPositionInLine, String msg,
                                      RecognitionException e) {
                    System.err.println("│  ✗ Syntax Error at line " + line + ":" + charPositionInLine);
                    System.err.println("│    " + msg);
                }
            });

            Jinja2Parser.TemplateContext parseTree = parser.template();

            if (parser.getNumberOfSyntaxErrors() > 0) {
                System.out.println("│  ✗ Parsing failed with " + parser.getNumberOfSyntaxErrors() + " error(s)");
                System.out.println("└──────────────────────────────────────────────┘\n");
                return;
            }

            System.out.println("│  ✓ Parsing completed successfully");
            System.out.println("│  → Parse tree root: " + parseTree.getClass().getSimpleName());
            System.out.println("└──────────────────────────────────────────────┘\n");

            // ═══════════════════════════════════════════════════════════════
            // STAGE 3: AST CONSTRUCTION (Using Visitor Pattern)
            // ═══════════════════════════════════════════════════════════════
            System.out.println("┌─ STAGE 3: AST Construction ─────────────────┐");
            System.out.println("│  Using Visitor Pattern...");

            Jinja2ASTBuilder astBuilder = new Jinja2ASTBuilder();
            ASTNode ast = astBuilder.visit(parseTree);

            System.out.println("│  ✓ AST construction completed");
            System.out.println("│  → Root node type: " + ast.getNodeType());
            System.out.println("│  → Child count: " + ast.getChildren().size());
            System.out.println("└──────────────────────────────────────────────┘\n");

            // ═══════════════════════════════════════════════════════════════
            // STAGE 4: AST VISUALIZATION
            // ═══════════════════════════════════════════════════════════════
            System.out.println("┌─ STAGE 4: AST Visualization ────────────────┐");
            System.out.println("│  Printing AST with details...");
            System.out.println("└──────────────────────────────────────────────┘");

            ASTPrinter.printWithStats(ast);

            // ═══════════════════════════════════════════════════════════════
            // STAGE 5: SYMBOL TABLE DEMONSTRATION
            // ═══════════════════════════════════════════════════════════════
            System.out.println("\n┌─ STAGE 5: Symbol Table Operations ──────────┐");
            System.out.println("│  Demonstrating symbol table functionality");
            System.out.println("└──────────────────────────────────────────────┘");

            demonstrateSymbolTable();

            // ═══════════════════════════════════════════════════════════════
            // COMPLETION
            // ═══════════════════════════════════════════════════════════════
            printFooter();

        } catch (Exception e) {
            System.err.println("\n✗ Error during compilation:");
            e.printStackTrace();
        }
    }

    /**
     * Demonstrate Symbol Table operations for academic purposes
     */
    private static void demonstrateSymbolTable() {
        SymbolTable symbolTable = new SymbolTable();

        System.out.println("\n→ Inserting symbols into global scope:");
        symbolTable.insert("page_title", new SymbolTable.Symbol("page_title", "string", "Product Catalog"));
        symbolTable.insert("user", new SymbolTable.Symbol("user", "object", "{name: 'John', logged_in: true}"));
        symbolTable.insert("products", new SymbolTable.Symbol("products", "list", "[...]"));

        System.out.println("\n→ Entering new scope (function/block):");
        symbolTable.enterScope();

        symbolTable.insert("product", new SymbolTable.Symbol("product", "object", "{id: 1, name: 'Laptop'}"));
        symbolTable.insert("price", new SymbolTable.Symbol("price", "number", 999.99));

        System.out.println("\n→ Looking up symbols:");
        System.out.println("  Lookup 'products': " + symbolTable.lookup("products"));
        System.out.println("  Lookup 'product': " + symbolTable.lookup("product"));
        System.out.println("  Lookup 'price': " + symbolTable.lookup("price"));

        System.out.println("\n→ Updating symbol value:");
        symbolTable.update("price", 899.99);

        System.out.println("\n→ Current symbol table contents:");
        symbolTable.printScopes();

        System.out.println("→ Exiting scope:");
        symbolTable.exitScope();

        System.out.println("\n→ After exiting scope:");
        symbolTable.printScopes();
    }

    /**
     * Print header
     */
    private static void printHeader() {
        System.out.println("\n╔═══════════════════════════════════════════════╗");
        System.out.println("║   Flask/Jinja2/HTML/CSS Compiler             ║");
        System.out.println("║   ANTLR4-Based Compiler Project              ║");
        System.out.println("╠═══════════════════════════════════════════════╣");
        System.out.println("║  Academic Requirements:                       ║");
        System.out.println("║  ✓ Language Definitions (Jinja2/HTML/CSS)    ║");
        System.out.println("║  ✓ Lexer and Parser (ANTLR4)                 ║");
        System.out.println("║  ✓ AST with OOP Principles                   ║");
        System.out.println("║  ✓ Visitor Pattern for AST Construction      ║");
        System.out.println("║  ✓ Symbol Table with Operations              ║");
        System.out.println("║  ✓ AST Printer for Visualization             ║");
        System.out.println("╚═══════════════════════════════════════════════╝\n");
    }

    /**
     * Print footer
     */
    private static void printFooter() {
        System.out.println("\n╔═══════════════════════════════════════════════╗");
        System.out.println("║          COMPILATION COMPLETED                ║");
        System.out.println("╠═══════════════════════════════════════════════╣");
        System.out.println("║  All stages executed successfully:            ║");
        System.out.println("║  1. Lexical Analysis                    ✓    ║");
        System.out.println("║  2. Syntax Analysis                     ✓    ║");
        System.out.println("║  3. AST Construction (Visitor)          ✓    ║");
        System.out.println("║  4. AST Visualization                   ✓    ║");
        System.out.println("║  5. Symbol Table Operations             ✓    ║");
        System.out.println("╚═══════════════════════════════════════════════╝\n");
    }

    /**
     * Default example template
     */
    private static String getDefaultExample() {
        return """
            <!DOCTYPE html>
            <html>
            <head>
                <title>{{ page_title }}</title>
            </head>
            <body>
                <h1>Welcome to {{ site_name }}</h1>

                {% if user.is_logged_in %}
                    <p>Hello, {{ user.name }}!</p>
                {% else %}
                    <p>Please log in</p>
                {% endif %}

                <div class="products">
                    {% for product in products %}
                        <div class="product-card">
                            <h3>{{ product.name }}</h3>
                            <p>Price: ${{ product.price }}</p>
                        </div>
                    {% endfor %}
                </div>
            </body>
            </html>
            """;
    }
}
