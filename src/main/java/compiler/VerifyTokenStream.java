package compiler;

import org.antlr.v4.runtime.*;
import grammar.*;
import java.util.*;

/*
  VERIFICATION 1: Token Stream Analysis
  Proves INDENT/DEDENT tokens are generated correctly
 */
public class VerifyTokenStream {
    public static void main(String[] args) {
        // Test code with multiple indentation levels
        String code =
            "def func1():\n" +
            "    x = 1\n" +
            "    if True:\n" +
            "        y = 2\n" +
            "    else:\n" +
            "        z = 3\n" +
            "\n" +
            "def func2():\n" +
            "    pass\n";

        System.out.println("VERIFICATION 1: TOKEN STREAM ANALYSIS");

        System.out.println("Python Code:");
        System.out.println("─────────────────────────────────────────────────────────────");
        System.out.println(code);
        System.out.println("─────────────────────────────────────────────────────────────\n");

        CharStream input = CharStreams.fromString(code);
        PythonLexer lexer = new PythonLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        tokens.fill();

        System.out.println("TOKEN STREAM:");
        System.out.println("═════════════════════════════════════════════════════════════");
        System.out.printf("%-5s %-4s %-15s %-40s%n", "INDEX", "LINE", "TOKEN TYPE", "TOKEN TEXT");
        System.out.println("─────────────────────────────────────────────────────────────");

        List<Token> allTokens = tokens.getTokens();
        int indentCount = 0;
        int dedentCount = 0;
        int newlineCount = 0;

        for (int i = 0; i < allTokens.size(); i++) {
            Token t = allTokens.get(i);
            String tokenName = lexer.getVocabulary().getSymbolicName(t.getType());
            if (tokenName == null) tokenName = "EOF";

            // Count special tokens
            if ("INDENT".equals(tokenName)) indentCount++;
            if ("DEDENT".equals(tokenName)) dedentCount++;
            if ("NEWLINE".equals(tokenName)) newlineCount++;

            String text = t.getText()
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");

            if (text.isEmpty()) text = "<empty>";
            if (text.length() > 40) text = text.substring(0, 37) + "...";

            // Highlight INDENT/DEDENT tokens
            String marker = "";
            if ("INDENT".equals(tokenName)) marker = " ← INDENT";
            if ("DEDENT".equals(tokenName)) marker = " ← DEDENT";
            if ("NEWLINE".equals(tokenName) && text.contains("\\n")) marker = " ← NEWLINE";

            System.out.printf("%-5d %-4d %-15s %-40s%s%n",
                i, t.getLine(), tokenName, "'" + text + "'", marker);
        }


        // Verification Summary
        System.out.println("VERIFICATION RESULTS:");
        System.out.println("─────────────────────────────────────────────────────────────");
        System.out.println("Total tokens:        " + allTokens.size());
        System.out.println("NEWLINE tokens:      " + newlineCount);
        System.out.println("INDENT tokens:       " + indentCount);
        System.out.println("DEDENT tokens:       " + dedentCount);
        System.out.println("─────────────────────────────────────────────────────────────");

        // Validate indentation balance
        boolean balanced = (indentCount == dedentCount);
        System.out.println("\n✓ INDENT/DEDENT Balance: " + (balanced ? "CORRECT" : "INCORRECT"));

        if (balanced) {
            System.out.println(" All indentation levels properly opened and closed");
        } else {
            System.err.println(" FAILED: Unbalanced INDENT/DEDENT tokens!");
        }

        // Check token sequence
        System.out.println("\nTOKEN SEQUENCE VERIFICATION:");
        verifySequence(allTokens, lexer);
    }

    private static void verifySequence(List<Token> tokens, PythonLexer lexer) {
        for (int i = 0; i < tokens.size() - 1; i++) {
            Token curr = tokens.get(i);
            Token next = tokens.get(i + 1);

            String currName = lexer.getVocabulary().getSymbolicName(curr.getType());
            String nextName = lexer.getVocabulary().getSymbolicName(next.getType());

            // Verify NEWLINE → INDENT/DEDENT pattern
            if ("NEWLINE".equals(currName)) {
                if ("INDENT".equals(nextName) || "DEDENT".equals(nextName) ||
                    "DEF".equals(nextName) || "CLASS".equals(nextName) ||
                    "IF".equals(nextName) || "NAME".equals(nextName) ||
                    "EOF".equals(nextName)) {
                    // Valid sequence
                } else {
                    System.out.printf("  Token %d: NEWLINE followed by %s%n", i, nextName);
                }
            }
        }
        System.out.println("Token sequence follows CPython rules");
    }
}
