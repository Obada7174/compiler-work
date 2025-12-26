package compiler;

import org.antlr.v4.runtime.*;
import grammar.*;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class TestIndentation {
    public static void main(String[] args) {
        try {
            // قراءة الملف من resources مباشرة كـ InputStream
            InputStream is = TestIndentation.class.getClassLoader()
                    .getResourceAsStream("examples/test_indentation.py");

            if (is == null) {
                throw new RuntimeException("File not found in resources: examples/test_indentation.py");
            }

            // تحويل InputStream إلى String
            String pythonCode;
            try (Scanner scanner = new Scanner(is, StandardCharsets.UTF_8)) {
                scanner.useDelimiter("\\A"); // قراءة كل المحتوى
                pythonCode = scanner.hasNext() ? scanner.next() : "";
            }

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
            tokens.fill();
            System.out.println("Lexical Analysis:");
            System.out.println("  Total tokens: " + tokens.size());

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
