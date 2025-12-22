package compiler;

import compiler.ast.*;
import compiler.ast.ASTPrinter;
import compiler.visitors.SimpleJinja2ASTBuilder;
import grammar.Jinja2Lexer;
import grammar.Jinja2Parser;
import org.antlr.v4.runtime.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Test class for building and displaying Jinja2 Abstract Syntax Tree
 */
public class TestJinja2AST {

    public static void main(String[] args) {
        String[] testFiles = {
            "test_jinja2_simple.html",
            "test_jinja2_advanced.html"
        };

        for (String filename : testFiles) {
            System.out.println("\n\n");
            System.out.println("╔════════════════════════════════════════════════════════════════╗");
            System.out.println("║          TESTING JINJA2 AST BUILDER: " + String.format("%-19s", filename) + "  ║");
            System.out.println("╚════════════════════════════════════════════════════════════════╝");

            try {
                testJinja2File(filename);
            } catch (Exception e) {
                System.err.println("✗ Error processing " + filename + ": " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private static void testJinja2File(String filename) throws IOException {
        // Read the Jinja2 file
        String jinja2Code = new String(Files.readAllBytes(Paths.get(filename)));

        System.out.println("\n─────────────────────────────────────────────────────────────────");
        System.out.println("  SOURCE CODE");
        System.out.println("─────────────────────────────────────────────────────────────────");
        System.out.println(jinja2Code);
        System.out.println("─────────────────────────────────────────────────────────────────\n");

        // Create lexer
        CharStream input = CharStreams.fromString(jinja2Code);
        Jinja2Lexer lexer = new Jinja2Lexer(input);

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
        Jinja2Parser parser = new Jinja2Parser(tokens);

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
        Jinja2Parser.TemplateContext tree = parser.template();

        System.out.println("✓ Parsing completed successfully");
        System.out.println("  Parse tree root: " + tree.getClass().getSimpleName());
        System.out.println();

        // Build AST
        SimpleJinja2ASTBuilder astBuilder = new SimpleJinja2ASTBuilder();
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
