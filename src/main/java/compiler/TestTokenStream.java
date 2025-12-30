package compiler;

import grammar.PythonLexer;
import org.antlr.v4.runtime.*;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.net.URISyntaxException;
import java.net.URL;

public class TestTokenStream {
    public static void main(String[] args) {
        try {
            // الحصول على الملف من resources
            URL resource = TestTokenStream.class
                    .getClassLoader()
                    .getResource("examples/test_indentation.py");

            if (resource == null) {
                System.err.println("ERROR: File not found in resources!");
                return;
            }

            String filePath = Paths.get(resource.toURI()).toString();
            String pythonCode = new String(Files.readAllBytes(Paths.get(filePath)));

            // طباعة محتوى الملف
            System.out.println("Python Code:");
            System.out.println("====================================");
            System.out.println(pythonCode);
            System.out.println("====================================\n");

            // إنشاء Lexer و Token Stream
            CharStream input = CharStreams.fromString(pythonCode);
            PythonLexer lexer = new PythonLexer(input);

            // التعامل مع أخطاء Lexer
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

            // طباعة كل الـ tokens
            System.out.println("Token Stream:");
            System.out.println("====================================");
            for (Token token : tokens.getTokens()) {
                String tokenName = lexer.getVocabulary().getSymbolicName(token.getType());
                if (tokenName == null) tokenName = "EOF";

                String text = token.getText()
                        .replace("\n", "\\n")
                        .replace("\r", "\\r")
                        .replace("\t", "\\t");
                if (text.isEmpty()) text = "<empty>";

                System.out.printf("Line %2d:%2d  %-15s  '%s'%n",
                        token.getLine(),
                        token.getCharPositionInLine(),
                        tokenName,
                        text);
            }
            System.out.println("====================================");
            System.out.println("Total tokens: " + tokens.size());

        } catch (URISyntaxException e) {
            System.err.println("File path error: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
