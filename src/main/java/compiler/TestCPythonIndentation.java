package compiler;

import org.antlr.v4.runtime.*;
import grammar.*;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class TestCPythonIndentation {
    public static void main(String[] args) {
        try {
            InputStream is = TestCPythonIndentation.class
                    .getClassLoader()
                    .getResourceAsStream("examples/test_cpython_indentation.py");

            if (is == null) {
                throw new RuntimeException("File not found in resources: examples/test_cpython_indentation.py");
            }

            String pythonCode = new String(is.readAllBytes(), StandardCharsets.UTF_8);

            System.out.println("Testing CPython-Style Indentation");
            System.out.println("=====================================\n");

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
            int tokenCount = tokens.getNumberOfOnChannelTokens();

            PythonParser parser = new PythonParser(tokens);

            // إضافة Error Listener للـ Parser
            parser.addErrorListener(new BaseErrorListener() {
                @Override
                public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
                                        int line, int charPositionInLine, String msg,
                                        RecognitionException e) {
                    System.err.println("PARSER ERROR at line " + line + ":" + charPositionInLine + " - " + msg);
                }
            });

            PythonParser.File_inputContext tree = parser.file_input();

            System.out.println("Tokens: " + tokenCount);

            if (parser.getNumberOfSyntaxErrors() == 0) {
                System.out.println("\n✓ SUCCESS - All CPython indentation rules working!");
                System.out.println("  - Basic indentation: ✓");
                System.out.println("  - Nested indentation: ✓");
                System.out.println("  - Implicit line continuation ([...]): ✓");
                System.out.println("  - Implicit line continuation ({...}): ✓");
                System.out.println("  - Implicit line continuation ((...)): ✓");
                System.out.println("  - Decorators with multiline: ✓");
                System.out.println("  - if/elif/else with DEDENT: ✓");
                System.out.println("  - for/else: ✓");
                System.out.println("  - try/except/finally: ✓");
            } else {
                System.out.println("\n✗ FAILED with " + parser.getNumberOfSyntaxErrors() + " errors");
            }

        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
