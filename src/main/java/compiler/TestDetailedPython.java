package compiler;

import org.antlr.v4.runtime.*;
import grammar.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TestDetailedPython {
    public static void main(String[] args) {
        String filename = "test_python.py";

        System.out.println("Testing: " + filename);
        System.out.println("===========================================\n");

        try {
            String pythonCode = new String(Files.readAllBytes(Paths.get(filename)));

            CharStream input = CharStreams.fromString(pythonCode);
            PythonLexer lexer = new PythonLexer(input);

            lexer.addErrorListener(new BaseErrorListener() {
                @Override
                public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
                                        int line, int charPositionInLine, String msg,
                                        RecognitionException e) {
                    System.err.println("LEXER ERROR at line " + line + ":" + charPositionInLine + " - " + msg);
                }
            });

            CommonTokenStream tokens = new CommonTokenStream(lexer);
            System.out.println("Tokens: " + tokens.getNumberOfOnChannelTokens());

            PythonParser parser = new PythonParser(tokens);
            parser.addErrorListener(new BaseErrorListener() {
                @Override
                public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
                                        int line, int charPositionInLine, String msg,
                                        RecognitionException e) {
                    System.err.println("PARSER ERROR at line " + line + ":" + charPositionInLine + " - " + msg);
                }
            });

            PythonParser.File_inputContext tree = parser.file_input();

            if (parser.getNumberOfSyntaxErrors() == 0) {
                System.out.println("\n✓ SUCCESS");
            } else {
                System.out.println("\n✗ FAILED with " + parser.getNumberOfSyntaxErrors() + " errors");
            }

        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
