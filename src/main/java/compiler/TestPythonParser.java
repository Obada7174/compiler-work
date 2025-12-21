package compiler;

import org.antlr.v4.runtime.*;
// Generated parser classes
import grammar.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class TestPythonParser {
    public static void main(String[] args) {
        try {
            // Read the test Python file
            String pythonCode = new String(Files.readAllBytes(Paths.get("C:\\Users\\Lenovo\\Desktop\\flask-compiler2\\compiler-work\\examples\\app.py")));

            System.out.println("═══════════════════════════════════════════════════════════");
            System.out.println("  Testing Python Parser");
            System.out.println("═══════════════════════════════════════════════════════════");
            System.out.println("\nInput Python Code:");
            System.out.println("-----------------------------------------------------------");
            System.out.println(pythonCode);
            System.out.println("-----------------------------------------------------------\n");

            // Create lexer
            CharStream input = CharStreams.fromString(pythonCode);
            PythonLexer lexer = new PythonLexer(input);

            // Add error listener
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

            // Create parser
            PythonParser parser = new PythonParser(tokens);

            // Add error listener
            parser.removeErrorListeners();
            parser.addErrorListener(new BaseErrorListener() {
                @Override
                public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
                                        int line, int charPositionInLine, String msg,
                                        RecognitionException e) {
                    System.err.println("PARSER ERROR at line " + line + ":" + charPositionInLine + " - " + msg);
                }
            });


            System.out.println("✓ Syntax Analysis completed");
            System.out.println("\n═══════════════════════════════════════════════════════════");
            System.out.println("  Python Parser Test PASSED!");
            System.out.println("═══════════════════════════════════════════════════════════\n");

        } catch (IOException e) {
            System.err.println("ERROR: Could not read test_python.py - " + e.getMessage());
        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
