package compiler.tests.pythontests;

import org.antlr.v4.runtime.*;
import grammar.*;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class TestDetailedPython {
    public static void main(String[] args) {
        try {
            InputStream is = TestDetailedPython.class.getClassLoader()
                    .getResourceAsStream("examples/test_python.py");

            if (is == null) {
                System.err.println("ERROR: File not found in resources/examples/test_python.py");
                return;
            }

            Scanner scanner = new Scanner(is, StandardCharsets.UTF_8);
            StringBuilder sb = new StringBuilder();
            while (scanner.hasNextLine()) {
                sb.append(scanner.nextLine()).append("\n");
            }
            String pythonCode = sb.toString();
            scanner.close();

            System.out.println("Testing: test_python.py");
            System.out.println();
            CharStream input = CharStreams.fromString(pythonCode);
            PythonLexer lexer = new PythonLexer(input);
            lexer.removeErrorListeners();

            CommonTokenStream tokens = new CommonTokenStream(lexer);
            int tokenCount = tokens.getNumberOfOnChannelTokens();

            PythonParser parser = new PythonParser(tokens);
            parser.removeErrorListeners();

            PythonParser.File_inputContext tree = parser.file_input();

            System.out.println("Tokens: " + tokenCount);
            if (parser.getNumberOfSyntaxErrors() == 0) {
                System.out.println("PASSED");
            } else {
                System.out.println("FAILED (" + parser.getNumberOfSyntaxErrors() + " errors)");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
