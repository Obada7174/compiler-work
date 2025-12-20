package compiler;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import grammar.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class VerifyParseTree {
    public static void main(String[] args) {
        String filename = args.length > 0 ? args[0] : "test_intellij_preview.py";

        try {
            String code = new String(Files.readAllBytes(Paths.get(filename)));

            System.out.println("╔════════════════════════════════════════════╗");
            System.out.println("║   PARSE TREE VERIFICATION                  ║");
            System.out.println("╚════════════════════════════════════════════╝");
            System.out.println("\nFile: " + filename);
            System.out.println("─────────────────────────────────────────────\n");

            // Lexical analysis
            CharStream input = CharStreams.fromString(code);
            PythonLexer lexer = new PythonLexer(input);
            lexer.removeErrorListeners();

            CommonTokenStream tokens = new CommonTokenStream(lexer);
            tokens.fill();

            System.out.println("✓ Lexical Analysis: " + tokens.size() + " tokens");

            // Parsing
            tokens.seek(0);
            PythonParser parser = new PythonParser(tokens);

            final int[] errorCount = {0};
            parser.addErrorListener(new BaseErrorListener() {
                @Override
                public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
                                        int line, int charPositionInLine, String msg,
                                        RecognitionException e) {
                    System.err.println("  ✗ Error at line " + line + ":" + charPositionInLine + " - " + msg);
                    errorCount[0]++;
                }
            });

            PythonParser.File_inputContext tree = parser.file_input();

            if (errorCount[0] == 0) {
                System.out.println("✓ Parsing: SUCCESS\n");

                System.out.println("Parse Tree Structure:");
                System.out.println("═════════════════════");
                printTree(tree, parser, "", true);

                System.out.println("\n✓ Parse tree is valid and accessible!");
                System.out.println("✓ This confirms IntelliJ preview should work!");
            } else {
                System.out.println("✗ Parsing: FAILED with " + errorCount[0] + " errors");
            }

        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void printTree(ParseTree tree, Parser parser, String indent, boolean last) {
        String text = Trees.getNodeText(tree, parser);

        // Format the tree branch
        String branch = last ? "└─ " : "├─ ";
        String continuation = last ? "   " : "│  ";

        System.out.println(indent + branch + text);

        // Print children
        if (tree instanceof RuleContext) {
            RuleContext ctx = (RuleContext) tree;
            int childCount = ctx.getChildCount();

            for (int i = 0; i < childCount; i++) {
                boolean isLast = (i == childCount - 1);
                printTree(ctx.getChild(i), parser, indent + continuation, isLast);
            }
        }
    }
}
