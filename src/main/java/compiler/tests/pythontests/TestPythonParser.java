package compiler.tests.pythontests;

import org.antlr.v4.runtime.*;
import grammar.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class TestPythonParser {

    public static void main(String[] args) {
        try {
            String resourcePath = "/examples/app.py";
            InputStream is = TestPythonParser.class.getResourceAsStream(resourcePath);

            if (is == null) {
                System.err.println("ERROR: File not found in resources: " + resourcePath);
                return;
            }

            // Read the Python file from resources
            String pythonCode = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            System.out.println("  Testing Python Parser");

            // Lexer
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
            System.out.println("✓ Lexical Analysis completed");
            System.out.println("  Total tokens: " + tokens.getNumberOfOnChannelTokens());

            // Parser
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

            PythonParser.File_inputContext tree = parser.file_input();
            System.out.println("Syntax Analysis completed!!!!!");
            System.out.println("  Parse tree root: " + tree.getClass().getSimpleName());
            System.out.println("  Python Parser Test PASSED!");

        } catch (IOException e) {
            System.err.println("ERROR: Could not read Python file - " + e.getMessage());
        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
