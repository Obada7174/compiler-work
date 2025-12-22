package compiler;

import compiler.ast.*;
import compiler.ast.ASTPrinter;
import compiler.visitors.SimplePythonASTBuilder;
import grammar.PythonLexer;
import grammar.PythonParser;
import org.antlr.v4.runtime.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Test class for building and displaying Python Abstract Syntax Tree
 */
public class TestPythonAST {

    public static void main(String[] args) {
        // Only test files with SUPPORTED grammar features
        String[] testFiles = {
            "test_python.py"
            // Note: test_python_full.py contains UNSUPPORTED features:
            // - Type hints (x: int, -> List[str])
            // - Default parameters with *args/**kwargs
            // - Lambda expressions
            // - Decorators (@decorator)
            // - Comprehensions [x for x in ...]
            // - Match statements
            // - With statements
            // - Async/await
            // - Unpacking (*rest)
            // - Walrus operator (:=)
            // - Global/nonlocal keywords
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

    private static void testPythonFile(String filename) throws IOException {
        // Read the Python file
        String pythonCode = new String(Files.readAllBytes(Paths.get(filename)));

        System.out.println("\n─────────────────────────────────────────────────────────────────");
        System.out.println("  SOURCE CODE");
        System.out.println("─────────────────────────────────────────────────────────────────");
        System.out.println(pythonCode);
        System.out.println("─────────────────────────────────────────────────────────────────\n");

        // Create lexer
        CharStream input = CharStreams.fromString(pythonCode);
        PythonLexer lexer = new PythonLexer(input);

        // Error listener
        lexer.removeErrorListeners();
        lexer.addErrorListener(new BaseErrorListener() {
            @Override
            public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
                                  int line, int charPositionInLine, String msg,
                                  RecognitionException e) {
                System.err.println("LEXER ERROR at line " + line + ":" + charPositionInLine + " - " + msg);
            }
        });

        // Create token stream
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        // Create parser
        PythonParser parser = new PythonParser(tokens);

        parser.removeErrorListeners();
        parser.addErrorListener(new BaseErrorListener() {
            @Override
            public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
                                  int line, int charPositionInLine, String msg,
                                  RecognitionException e) {
                System.err.println("PARSER ERROR at line " + line + ":" + charPositionInLine + " - " + msg);
            }
        });

        // Parse
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
