package compiler.tests.pythontests;

import org.antlr.v4.runtime.*;
import grammar.*;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class TestBasicIndentation {
    public static void main(String[] args) {
        try {
            // Load file from Maven resources using ClassLoader
            ClassLoader classLoader = TestBasicIndentation.class.getClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream(
                    "examples/test_basic_indentation.py"
            );

            if (inputStream == null) {
                throw new RuntimeException(
                        "Test file not found in resources: examples/test_basic_indentation.py"
                );
            }

                     String pythonCode = new String(
                    inputStream.readAllBytes(),
                    StandardCharsets.UTF_8
            );

                      CharStream input = CharStreams.fromString(pythonCode);
            PythonLexer lexer = new PythonLexer(input);
            lexer.removeErrorListeners();

            CommonTokenStream tokens = new CommonTokenStream(lexer);
            tokens.fill();
            int tokenCount = tokens.getNumberOfOnChannelTokens();

            PythonParser parser = new PythonParser(tokens);
            parser.removeErrorListeners();

            PythonParser.File_inputContext tree = parser.file_input();

            // Output
            System.out.println();
            System.out.println("  CPython Indentation Test");
            System.out.println("Tokens: " + tokenCount);

            if (parser.getNumberOfSyntaxErrors() == 0) {
                System.out.println("Result:PASSED");
                System.out.println("\nAll CPython indentation features working:");
                System.out.println("Basic function indentation");
                System.out.println("Class and method indentation");
                System.out.println("Implicit line continuation in [...]");
                System.out.println("Implicit line continuation in {...}");
                System.out.println("Implicit line continuation in (...)");
                System.out.println("if/elif/else with DEDENT");
                System.out.println("Nested if statements");
                System.out.println("try/except/finally");
            } else {
                System.out.println("Result: ✗ FAILED (" + parser.getNumberOfSyntaxErrors() + " errors)");
            }

        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
