package compiler;

import org.antlr.v4.runtime.*;
import grammar.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TestIndentation {
    public static void main(String[] args) {
        try {
            String pythonCode = new String(Files.readAllBytes(Paths.get("test_indentation.py")));

            System.out.println("Testing Python Indentation Handling");
            System.out.println("====================================\n");
            System.out.println(pythonCode);
            System.out.println("\n====================================\n");

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
            System.out.println("Lexical Analysis:");
            System.out.println("  Total tokens: " + tokens.getNumberOfOnChannelTokens());

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
                System.out.println("\nSyntax Analysis: SUCCESS");
                System.out.println("  Parse tree: " + tree.getClass().getSimpleName());
                System.out.println("\n✓ Indentation handling works correctly!");
            } else {
                System.out.println("\nSyntax Analysis: FAILED");
                System.out.println("  Errors: " + parser.getNumberOfSyntaxErrors());
            }

        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
