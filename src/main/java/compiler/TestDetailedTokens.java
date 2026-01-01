package compiler;

import org.antlr.v4.runtime.*;
import grammar.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TestDetailedTokens {
    public static void main(String[] args) {
        try {
            String code = new String(Files.readAllBytes(Paths.get("examples/sample_python.txt")));

            System.out.println("Testing: examples/sample_python.txt");
            System.out.println();
            CharStream input = CharStreams.fromString(code);
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
            tokens.fill();

            System.out.println("TOKEN STREAM:");
            System.out.println("=============");
            for (Token t : tokens.getTokens()) {
                String name = lexer.getVocabulary().getSymbolicName(t.getType());
                if (name == null) name = "EOF";

                String text = t.getText()
                    .replace("\n", "\\n")
                    .replace("\r", "\\r")
                    .replace("\t", "\\t");

                if (text.length() > 30) {
                    text = text.substring(0, 27) + "...";
                }

                System.out.printf("Line %3d:%-3d %-15s '%s'%n",
                    t.getLine(),
                    t.getCharPositionInLine(),
                    name,
                    text);
            }

            System.out.println("\n\nPARSING:");
            System.out.println("========");

            // Reset for parsing
            tokens.seek(0);
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
                System.out.println("PARSE SUCCESS");
            } else {
                System.out.println("PARSE FAILED (" + parser.getNumberOfSyntaxErrors() + " errors)");
            }

        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
