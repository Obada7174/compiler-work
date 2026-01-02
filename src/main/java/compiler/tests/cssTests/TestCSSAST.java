package compiler.tests.cssTests;

import compiler.ast.css.CSSASTNode;
import compiler.ast.css.CSSStylesheetNode;
import compiler.visitors.CSSASTBuilder;
import grammar.CSSLexer;
import grammar.CSSParser;
import org.antlr.v4.runtime.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;


public class TestCSSAST {

    public static void main(String[] args) {
        String[] testFiles = {
                "css/add_products.css",

        };

        for (String filename : testFiles) {
            System.out.println("TESTING CSS AST BUILDER: "
                    + String.format("%-30s", filename));

            try {
                testCSSFile(filename);
            } catch (Exception e) {
                System.err.println("Error processing " + filename);
                e.printStackTrace();
            }
        }
    }


    private static void testCSSFile(String resourcePath) throws IOException {

      InputStream is = TestCSSAST.class
                .getClassLoader()
                .getResourceAsStream(resourcePath);

        if (is == null) {
            throw new IllegalStateException(
                    "Resource not found on classpath: " + resourcePath +
                            "\nExpected location: src/main/resources/" + resourcePath
            );
        }

        String cssCode = new String(is.readAllBytes(), StandardCharsets.UTF_8);
     CharStream input = CharStreams.fromString(cssCode);
        CSSLexer lexer = new CSSLexer(input);

        lexer.removeErrorListeners();
        lexer.addErrorListener(new BaseErrorListener() {
            @Override
            public void syntaxError(
                    Recognizer<?, ?> recognizer,
                    Object offendingSymbol,
                    int line,
                    int charPositionInLine,
                    String msg,
                    RecognitionException e) {

                System.err.printf(
                        "LEXER ERROR at line %d:%d - %s%n",
                        line, charPositionInLine, msg
                );
            }
        });

      CommonTokenStream tokens = new CommonTokenStream(lexer);
        CSSParser parser = new CSSParser(tokens);

        parser.removeErrorListeners();
        parser.addErrorListener(new BaseErrorListener() {
            @Override
            public void syntaxError(
                    Recognizer<?, ?> recognizer,
                    Object offendingSymbol,
                    int line,
                    int charPositionInLine,
                    String msg,
                    RecognitionException e) {

                System.err.printf(
                        "PARSER ERROR at line %d:%d - %s%n",
                        line, charPositionInLine, msg
                );
            }
        });

     CSSParser.StylesheetContext tree = parser.stylesheet();

        System.out.println("✓ Parsing completed successfully");
        System.out.println("  Parse tree root: " + tree.getClass().getSimpleName());
        System.out.println();
    CSSASTBuilder astBuilder = new CSSASTBuilder();
        CSSASTNode ast = astBuilder.visit(tree);

        if (ast == null) {
            throw new IllegalStateException("AST Builder returned null");
        }

        System.out.println("✓ AST built successfully");
        System.out.println("  AST root type: " + ast.getNodeType());
        System.out.println();

      validateAST(ast);
        System.out.println("  ABSTRACT SYNTAX TREE");
        ast.print("");
        System.out.println("─────────────────────────────────────────────────────────────────");

     printSummary(ast);

        System.out.println("\n Test completed successfully! \n");
    }

  private static void validateAST(CSSASTNode ast) {
        System.out.println("  AST VALIDATION");

        // Check 1: Root node is CSSStylesheetNode
        if (!(ast instanceof CSSStylesheetNode)) {
            System.err.println("✗ Root node is not CSSStylesheetNode");
            return;
        }
        System.out.println("✓ Root node is CSSStylesheetNode");

        CSSStylesheetNode stylesheet = (CSSStylesheetNode) ast;

        // Check 2: Has rule sets
        int ruleSetCount = stylesheet.getRuleSets().size();
        int atRuleCount = stylesheet.getAtRules().size();

        System.out.println("✓ Found " + ruleSetCount + " rule set(s)");
        System.out.println("✓ Found " + atRuleCount + " at-rule(s)");

        if (ruleSetCount == 0 && atRuleCount == 0) {
            System.err.println("✗ WARNING: No rules found in stylesheet");
        }

        // Check 3: Rule sets have declarations
        int totalDeclarations = 0;
        for (var ruleSet : stylesheet.getRuleSets()) {
            totalDeclarations += ruleSet.getDeclarations().size();
        }
        System.out.println("✓ Found " + totalDeclarations + " total declaration(s)");

        // Check 4: Children count
        System.out.println("✓ AST has " + ast.getChildren().size() + " direct children");

        System.out.println("─────────────────────────────────────────────────────────────────\n");
    }

   private static void printSummary(CSSASTNode ast) {
        if (!(ast instanceof CSSStylesheetNode)) {
            return;
        }

        CSSStylesheetNode stylesheet = (CSSStylesheetNode) ast;

        System.out.println("\n─────────────────────────────────────────────────────────────────");
        System.out.println("  SUMMARY");
        System.out.println("─────────────────────────────────────────────────────────────────");
        System.out.println("  Total Rule Sets: " + stylesheet.getRuleSets().size());
        System.out.println("  Total At-Rules:  " + stylesheet.getAtRules().size());

        int totalDeclarations = 0;
        for (var ruleSet : stylesheet.getRuleSets()) {
            totalDeclarations += ruleSet.getDeclarations().size();
        }
        System.out.println("  Total Declarations: " + totalDeclarations);
        System.out.println("  Total AST Nodes: " + countNodes(ast));
         }

    private static int countNodes(CSSASTNode node) {
        int count = 1; // count this node
        for (var child : node.getCSSChildren()) {
            count += countNodes(child);
        }
        return count;
    }
}
