package compiler;

import org.antlr.v4.runtime.*;
import grammar.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TestAllPython {
    public static void main(String[] args) {
        String[] testFiles = {
            "test_indentation.py",
            "test_python.py",
            "examples/sample_python.txt",
            "examples/app.py"
        };

        int passCount = 0;
        int failCount = 0;

        System.out.println("===========================================");
        System.out.println("  COMPREHENSIVE PYTHON PARSER TEST");
        System.out.println("===========================================\n");

        for (String filename : testFiles) {
            System.out.println("Testing: " + filename);
            System.out.print("  ");

            try {
                String pythonCode = new String(Files.readAllBytes(Paths.get(filename)));

                CharStream input = CharStreams.fromString(pythonCode);
                PythonLexer lexer = new PythonLexer(input);

                // Suppress error output for summary
                lexer.removeErrorListeners();

                CommonTokenStream tokens = new CommonTokenStream(lexer);
                int tokenCount = tokens.getNumberOfOnChannelTokens();

                PythonParser parser = new PythonParser(tokens);
                parser.removeErrorListeners();

                PythonParser.File_inputContext tree = parser.file_input();

                if (parser.getNumberOfSyntaxErrors() == 0) {
                    System.out.println("✓ PASSED (" + tokenCount + " tokens)");
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

        System.out.println("\n===========================================");
        System.out.println("  RESULTS");
        System.out.println("===========================================");
        System.out.println("  Passed: " + passCount + "/" + testFiles.length);
        System.out.println("  Failed: " + failCount + "/" + testFiles.length);

        if (failCount == 0) {
            System.out.println("\n  ✓ ALL TESTS PASSED!");
        }
        System.out.println("===========================================");
    }
}
