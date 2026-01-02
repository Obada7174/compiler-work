package compiler.tests.pythontests;

import org.antlr.v4.runtime.*;
import grammar.*;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class TestAllPython {

    public static void main(String[] args) {

        String[] testFiles = {
                "examples/app.py"
        };

        int passCount = 0;
        int failCount = 0;

        System.out.println("COMPREHENSIVE PYTHON PARSER TEST");

        for (String resourcePath : testFiles) {
            System.out.println("Testing: " + resourcePath);
            System.out.print("  ");

            try {
                InputStream inputStream = TestAllPython.class
                        .getClassLoader()
                        .getResourceAsStream(resourcePath);

                if (inputStream == null) {
                    throw new RuntimeException("Resource not found: " + resourcePath);
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

                PythonParser parser = new PythonParser(tokens);
                parser.removeErrorListeners();
                if (parser.getNumberOfSyntaxErrors() == 0) {
                    System.out.println("✓ PASSED (" + tokens.size() + " tokens)");
                    passCount++;
                } else {
                    System.out.println("✗ FAILED (" + parser.getNumberOfSyntaxErrors() + " errors)");
                    failCount++;
                }

            } catch (Exception e) {
                System.out.println("✗ ERROR: " + e.getMessage());
                failCount++;
            }
        }

        System.out.println();
        System.out.println("  RESULTS");
        System.out.println("  Passed: " + passCount + "/" + testFiles.length);
        System.out.println("  Failed: " + failCount + "/" + testFiles.length);
    }
}
