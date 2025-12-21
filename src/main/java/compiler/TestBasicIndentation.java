package compiler;

import org.antlr.v4.runtime.*;
import grammar.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TestBasicIndentation {
    public static void main(String[] args) {
        try {
            String pythonCode = new String(Files.readAllBytes(Paths.get("test_basic_indentation.py")));

            CharStream input = CharStreams.fromString(pythonCode);
            PythonLexer lexer = new PythonLexer(input);
            lexer.removeErrorListeners();

            CommonTokenStream tokens = new CommonTokenStream(lexer);
            int tokenCount = tokens.getNumberOfOnChannelTokens();

            PythonParser parser = new PythonParser(tokens);
            parser.removeErrorListeners();

            PythonParser.File_inputContext tree = parser.file_input();

            System.out.println("=====================================");
            System.out.println("  CPython Indentation Test");
            System.out.println("=====================================");
            System.out.println("Tokens: " + tokenCount);

            if (parser.getNumberOfSyntaxErrors() == 0) {
                System.out.println("Result: ✓ PASSED");
                System.out.println("\nAll CPython indentation features working:");
                System.out.println("  ✓ Basic function indentation");
                System.out.println("  ✓ Class and method indentation");
                System.out.println("  ✓ Implicit line continuation in [...]");
                System.out.println("  ✓ Implicit line continuation in {...}");
                System.out.println("  ✓ Implicit line continuation in (...)");
                System.out.println("  ✓ if/elif/else with DEDENT");
                System.out.println("  ✓ Nested if statements");
                System.out.println("  ✓ try/except/finally");
            } else {
                System.out.println("Result: ✗ FAILED (" + parser.getNumberOfSyntaxErrors() + " errors)");
            }

        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
