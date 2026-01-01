package compiler;

import org.antlr.v4.runtime.*;
import grammar.*;

public class TestSimple {
    public static void main(String[] args) {
        String code = "def func():\n    x = 1\n";

        System.out.println("Testing simple code:");

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

        System.out.println("\nTokens:");
        for (Token t : tokens.getTokens()) {
            String name = lexer.getVocabulary().getSymbolicName(t.getType());
            if (name == null) name = "EOF";
            System.out.printf("%3d: %-15s '%s'%n", t.getLine(), name, t.getText().replace("\n", "\\n"));
        }

        PythonParser parser = new PythonParser(tokens);
        parser.addErrorListener(new BaseErrorListener() {
            @Override
            public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
                                    int line, int charPositionInLine, String msg,
                                    RecognitionException e) {
                System.err.println("\nPARSER ERROR at line " + line + ":" + charPositionInLine + " - " + msg);
            }
        });

        PythonParser.File_inputContext tree = parser.file_input();

        if (parser.getNumberOfSyntaxErrors() == 0) {
            System.out.println("\n✓ SUCCESS");
        } else {
            System.out.println("\n✗ FAILED");
        }
    }
}
