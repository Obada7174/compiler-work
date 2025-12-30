package compiler;

import compiler.ast.ASTNode;
import compiler.visitors.ASTPrinter;
import compiler.visitors.SimpleJinja2ASTBuilder;
import grammar.Jinja2Lexer;
import grammar.Jinja2Parser;
import org.antlr.v4.runtime.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;


public class TestJinja2AST {

    public static void main(String[] args) {
        String[] testFiles = {
                "examples/test1_display_products.html",
                "examples/test2_add_product.html",
                "examples/test3_product_details.html",

        };

        for (String filename : testFiles) {
            System.out.println("\n\n");
            System.out.println("╔════════════════════════════════════════════════════════════════╗");
            System.out.println("║          TESTING JINJA2 AST BUILDER: "
                    + String.format("%-28s", filename) + "║");
            System.out.println("╚════════════════════════════════════════════════════════════════╝");

            try {
                testJinja2File(filename);
            } catch (Exception e) {
                System.err.println("✗ Error processing " + filename);
                e.printStackTrace();
            }
        }
    }


    private static void testJinja2File(String resourcePath) throws IOException {

        // ---------------------------------------------------------------------
        // 1) Load file from resources (CLASSPATH)
        // ---------------------------------------------------------------------
        InputStream is = TestJinja2AST.class
                .getClassLoader()
                .getResourceAsStream(resourcePath);

        if (is == null) {
            throw new IllegalStateException(
                    "Resource not found on classpath: " + resourcePath +
                            "\nExpected location: src/main/resources/" + resourcePath
            );
        }

        String jinja2Code = new String(is.readAllBytes(), StandardCharsets.UTF_8);

        // ---------------------------------------------------------------------
        // 3) Lexer
        // ---------------------------------------------------------------------
        CharStream input = CharStreams.fromString(jinja2Code);
        Jinja2Lexer lexer = new Jinja2Lexer(input);

        lexer.removeErrorListeners();
        lexer.addErrorListener(new BaseErrorListener() {
            @Override
            public void syntaxError(
                    Recognizer<?, ?> recognizer,
                    Object offendingSymbol,
                    int line,
                    int charPositionInLine,
                    String msg,
                    RecognitionException e) {

                System.err.printf(
                        "LEXER ERROR at line %d:%d - %s%n",
                        line, charPositionInLine, msg
                );
            }
        });

        // ---------------------------------------------------------------------
        // 4) Parser
        // ---------------------------------------------------------------------
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        Jinja2Parser parser = new Jinja2Parser(tokens);

        parser.removeErrorListeners();
        parser.addErrorListener(new BaseErrorListener() {
            @Override
            public void syntaxError(
                    Recognizer<?, ?> recognizer,
                    Object offendingSymbol,
                    int line,
                    int charPositionInLine,
                    String msg,
                    RecognitionException e) {

                System.err.printf(
                        "PARSER ERROR at line %d:%d - %s%n",
                        line, charPositionInLine, msg
                );
            }
        });

        // ---------------------------------------------------------------------
        // 5) Parse
        // ---------------------------------------------------------------------
        Jinja2Parser.TemplateContext tree = parser.template();

        System.out.println("✓ Parsing completed successfully");
        System.out.println("  Parse tree root: " + tree.getClass().getSimpleName());
        System.out.println();

        // ---------------------------------------------------------------------
        // 6) Build AST
        // ---------------------------------------------------------------------
        SimpleJinja2ASTBuilder astBuilder = new SimpleJinja2ASTBuilder();
        ASTNode ast = astBuilder.visit(tree);

        if (ast == null) {
            throw new IllegalStateException("AST Builder returned null");
        }

        System.out.println("✓ AST built successfully");
        System.out.println("  AST root type: " + ast.getNodeType());
        System.out.println();

        // ---------------------------------------------------------------------
        // 7) Print AST
        // ---------------------------------------------------------------------
        ASTPrinter.printTreeBoxed(ast);

        // ---------------------------------------------------------------------
        // 8) Summary
        // ---------------------------------------------------------------------
        System.out.println("\n" + ASTPrinter.summarize(ast));
        System.out.println("\n✓✓✓ Test completed successfully! ✓✓✓\n");
    }
}
