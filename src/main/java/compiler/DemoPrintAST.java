package compiler;

import compiler.ast.ASTNode;
import compiler.utils.ASTClassNamePrinter;
import compiler.visitors.SimplePythonASTBuilder;
import grammar.PythonLexer;
import grammar.PythonParser;
import org.antlr.v4.runtime.*;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;


public class DemoPrintAST {

    public static void main(String[] args) {
        String resourcePath = args.length > 0 ? args[0] : "examples/app.py";

        System.out.println("DEMO: Print AST with Class Names");

        try {
            // Load source file
            System.out.println("Loading: " + resourcePath);
            String sourceCode = loadSourceFile(resourcePath);
            System.out.println("Source loaded: " + sourceCode.length() + " characters\n");

            // Compile to AST
            System.out.println("Compiling to AST...");
            ASTNode ast = compileToAST(sourceCode);
            System.out.println("AST construction complete\n");

            // EXAMPLE 1: Print entire AST
            System.out.println("\n" + "=".repeat(60));
            System.out.println("EXAMPLE 1: Print Entire AST");
            System.out.println("=".repeat(60) + "\n");
            ASTClassNamePrinter.printAST(ast);

            // EXAMPLE 2: Print AST with statistics
            System.out.println("\n" + "=".repeat(60));
            System.out.println("EXAMPLE 2: Print AST with Statistics");
            System.out.println("=".repeat(60) + "\n");
            ASTClassNamePrinter.printASTWithStats(ast);

            // EXAMPLE 3: Print first child (if exists)
            if (ast.getChildren().size() > 0) {
                System.out.println("\n" + "=".repeat(60));
                System.out.println("EXAMPLE 3: Print First Child Node Only");
                System.out.println("=".repeat(60) + "\n");

                ASTNode firstChild = ast.getChildren().get(0);
                ASTClassNamePrinter.printSubtree(firstChild, "First Child Subtree");
            }

            // EXAMPLE 4: Print specific node with custom indentation
            System.out.println("\n" + "=".repeat(60));
            System.out.println("EXAMPLE 4: Print Node with Custom Indentation");
            System.out.println("=".repeat(60) + "\n");

            System.out.println("Root node with 4-space indent:\n");
            ASTClassNamePrinter.printNode(ast, 4);

            // EXAMPLE 5: Iterate and print each top-level statement
            System.out.println("\n" + "=".repeat(60));
            System.out.println("EXAMPLE 5: Print Each Top-Level Statement Separately");
            System.out.println("=".repeat(60) + "\n");

            int statementNum = 1;
            for (ASTNode child : ast.getChildren()) {
                ASTClassNamePrinter.printSubtree(child,
                    "Statement " + statementNum + ": " + child.getClass().getSimpleName());
                statementNum++;
            }

            System.out.println("✓ Demo completed successfully!");

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static ASTNode compileToAST(String sourceCode) throws Exception {
        // Lexical analysis
        CharStream input = CharStreams.fromString(sourceCode);
        PythonLexer lexer = new PythonLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        // Syntactic analysis
        PythonParser parser = new PythonParser(tokens);
        parser.removeErrorListeners(); // Suppress error messages for demo
        PythonParser.File_inputContext parseTree = parser.file_input();

        // AST construction
        SimplePythonASTBuilder astBuilder = new SimplePythonASTBuilder();
        return astBuilder.visit(parseTree);
    }

    private static String loadSourceFile(String resourcePath) throws Exception {
        // Try to load from resources first
        InputStream is = DemoPrintAST.class.getClassLoader()
                .getResourceAsStream(resourcePath);

        if (is != null) {
            Scanner scanner = new Scanner(is, StandardCharsets.UTF_8);
            StringBuilder sb = new StringBuilder();
            while (scanner.hasNextLine()) {
                sb.append(scanner.nextLine()).append("\n");
            }
            scanner.close();
            return sb.toString();
        }

        // If not in resources, try as file path
        return new String(java.nio.file.Files.readAllBytes(
            java.nio.file.Paths.get(resourcePath)), StandardCharsets.UTF_8);
    }
}
