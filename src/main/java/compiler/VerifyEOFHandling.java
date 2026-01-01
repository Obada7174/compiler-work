package compiler;

import org.antlr.v4.runtime.*;
import grammar.*;
import java.util.*;

/*
  VERIFICATION 3: EOF Indentation Flush
  Proves all DEDENT tokens are emitted at EOF
 */
public class VerifyEOFHandling {
    public static void main(String[] args) {
        System.out.println("VERIFICATION 3: EOF INDENTATION FLUSH");

        // Test 1: Single level indentation
        testEOF("Test 1: Single indent level",
            "def func():\n" +
            "    x = 1");

        // Test 2: Multiple nested levels
        testEOF("Test 2: Multiple nested levels",
            "class A:\n" +
            "    def method():\n" +
            "        if True:\n" +
            "            x = 1");

        // Test 3: Multiple functions
        testEOF("Test 3: Multiple functions at same level",
            "def func1():\n" +
            "    x = 1\n" +
            "\n" +
            "def func2():\n" +
            "    y = 2");
    }

    private static void testEOF(String testName, String code) {
        System.out.println("\n" + testName);
        System.out.println("─────────────────────────────────────────────────────────────");
        System.out.println("Code:");
        for (String line : code.split("\n")) {
            System.out.println("  " + line);
        }

        CharStream input = CharStreams.fromString(code);
        PythonLexer lexer = new PythonLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        tokens.fill();

        List<Token> allTokens = tokens.getTokens();

        System.out.println("\nTokens before EOF:");
        int eofIndex = -1;
        for (int i = allTokens.size() - 1; i >= 0; i--) {
            Token t = allTokens.get(i);
            if (t.getType() == Token.EOF) {
                eofIndex = i;
                break;
            }
        }

        if (eofIndex == -1) {
            System.err.println("No EOF token found!");
            return;
        }

        // Show last 10 tokens
        int start = Math.max(0, eofIndex - 9);
        List<String> dedentsBeforeEOF = new ArrayList<>();

        for (int i = start; i <= eofIndex; i++) {
            Token t = allTokens.get(i);
            String tokenName = lexer.getVocabulary().getSymbolicName(t.getType());
            if (tokenName == null) tokenName = "EOF";

            String text = t.getText().replace("\n", "\\n");
            if (text.isEmpty()) text = "<empty>";

            String marker = "";
            if ("DEDENT".equals(tokenName)) {
                dedentsBeforeEOF.add(tokenName);
                marker = " ← DEDENT before EOF";
            } else if ("EOF".equals(tokenName)) {
                marker = " ← EOF";
            }

            System.out.printf("  [%2d] %-12s '%s'%s%n", i, tokenName, text, marker);
        }

        // Verify DEDENT count
        System.out.println("\nVerification:");
        System.out.println("  DEDENT tokens before EOF: " + dedentsBeforeEOF.size());

        // Count INDENT tokens in entire stream
        int totalIndents = 0;
        int totalDedents = 0;
        for (Token t : allTokens) {
            String name = lexer.getVocabulary().getSymbolicName(t.getType());
            if ("INDENT".equals(name)) totalIndents++;
            if ("DEDENT".equals(name)) totalDedents++;
        }

        System.out.println("  Total INDENT:  " + totalIndents);
        System.out.println("  Total DEDENT:  " + totalDedents);

        if (totalIndents == totalDedents) {
            System.out.println("PASS: All indentation levels closed at EOF");
        } else {
            System.err.println("FAIL: Unbalanced INDENT/DEDENT");
        }
    }
}
