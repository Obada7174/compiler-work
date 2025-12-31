package compiler;

import compiler.ast.*;
import compiler.visitors.ASTPrinter;
import compiler.visitors.SimplePythonASTBuilder;
import grammar.PythonLexer;
import grammar.PythonParser;
import org.antlr.v4.runtime.*;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * Test class for building and displaying Python Abstract Syntax Tree
 */
public class TestPythonAST {

    public static void main(String[] args) {
        // Test files inside resources/examples
        String[] testFiles = {
                "examples/test_python.py",
                "examples/app.py"
        };

        for (String filename : testFiles) {
            System.out.println("\n\n");
            System.out.println("╔════════════════════════════════════════════════════════════════╗");
            System.out.println("║          TESTING PYTHON AST BUILDER: " + String.format("%-20s", filename) + "  ║");
            System.out.println("╚════════════════════════════════════════════════════════════════╝");

            try {
                testPythonFile(filename);
            } catch (Exception e) {
                System.err.println("✗ Error processing " + filename + ": " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private static void testPythonFile(String filename) throws Exception {
        // Load file from resources using ClassLoader
        InputStream is = TestPythonAST.class.getClassLoader().getResourceAsStream(filename);
        if (is == null) {
            System.err.println("ERROR: File not found in resources: " + filename);
            return;
        }

        // Convert InputStream to String
        Scanner scanner = new Scanner(is, StandardCharsets.UTF_8);
        StringBuilder sb = new StringBuilder();
        while (scanner.hasNextLine()) {
            sb.append(scanner.nextLine()).append("\n");
        }
        String pythonCode = sb.toString();
        scanner.close();
        // Create lexer
        CharStream input = CharStreams.fromString(pythonCode);
        PythonLexer lexer = new PythonLexer(input);

        lexer.removeErrorListeners();
        lexer.addErrorListener(new BaseErrorListener() {
            @Override
            public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
                                    int line, int charPositionInLine, String msg,
                                    RecognitionException e) {
                System.err.println("LEXER ERROR at line " + line + ":" + charPositionInLine + " - " + msg);
            }
        });

        CommonTokenStream tokens = new CommonTokenStream(lexer);

        PythonParser parser = new PythonParser(tokens);
        parser.removeErrorListeners();
        parser .addErrorListener(new BaseErrorListener() {
            @Override
            public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
                                    int line, int charPositionInLine, String msg,
                                    RecognitionException e) {
                System.err.println("PARSER ERROR at line " + line + ":" + charPositionInLine + " - " + msg);
            }
        });

        PythonParser.File_inputContext tree = parser.file_input();

        System.out.println("✓ Parsing completed successfully");
        System.out.println("  Parse tree root: " + tree.getClass().getSimpleName());
        System.out.println();

        // Build AST
        SimplePythonASTBuilder astBuilder = new SimplePythonASTBuilder();
        ASTNode ast = astBuilder.visit(tree);

        if (ast == null) {
            System.err.println("✗ Failed to build AST - returned null");
            return;
        }

        System.out.println("✓ AST built successfully");
        System.out.println("  AST root type: " + ast.getNodeType());
        System.out.println();

        // Print the AST
        ASTPrinter.printTreeBoxed(ast);

        // Print summary
        System.out.println("\n" + ASTPrinter.summarize(ast));

        System.out.println("\n✓✓✓ Test completed successfully! ✓✓✓\n");
    }
}
