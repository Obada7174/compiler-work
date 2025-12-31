package compiler;

import compiler.ast.css.*;
import compiler.visitors.CSSASTBuilder;
import grammar.CSSLexer;
import grammar.CSSParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;


public class CSSASTBuilderTest {

    public static void main(String[] args) {
        System.out.println("CSS AST BUILDER UNIT TESTS :");

        int passed = 0;
        int failed = 0;

        // Test 1: Simple selector and declaration
        if (testSimpleRule()) {
            passed++;
        } else {
            failed++;
        }

        // Test 2: Multiple selectors
        if (testMultipleSelectors()) {
            passed++;
        } else {
            failed++;
        }

        // Test 3: Combinators
        if (testCombinators()) {
            passed++;
        } else {
            failed++;
        }

        // Test 4: Pseudo-classes and pseudo-elements
        if (testPseudoSelectors()) {
            passed++;
        } else {
            failed++;
        }

        // Test 5: Attribute selectors
        if (testAttributeSelectors()) {
            passed++;
        } else {
            failed++;
        }

        // Test 6: Complex values
        if (testComplexValues()) {
            passed++;
        } else {
            failed++;
        }

        // Test 7: Functions (rgb, calc, etc.)
        if (testFunctions()) {
            passed++;
        } else {
            failed++;
        }

        // Test 8: At-rules (@media)
        if (testAtRules()) {
            passed++;
        } else {
            failed++;
        }

        // Test 9: CSS Variables
        if (testCSSVariables()) {
            passed++;
        } else {
            failed++;
        }

        // Test 10: Important declarations
        if (testImportantDeclarations()) {
            passed++;
        } else {
            failed++;
        }

        System.out.println("TEST SUMMARY");
        System.out.println();
        System.out.println("  Total Tests: " + (passed + failed));
        System.out.println("  Passed:      " + passed + " ✓");
        System.out.println("  Failed:      " + failed + (failed > 0 ? " ✗" : ""));
        System.out.println();

        if (failed == 0) {
            System.out.println("✓✓✓ ALL TESTS PASSED! ✓✓✓\n");
        } else {
            System.out.println("✗✗✗ SOME TESTS FAILED ✗✗✗\n");
        }
    }
      // Test 1: Simple Rule

    private static boolean testSimpleRule() {
        System.out.println("Test 1: Simple selector and declaration");
        String css = ".container { color: red; }";

        try {
            CSSStylesheetNode ast = parseCSS(css);

            // Validate structure
            if (ast.getRuleSets().size() != 1) {
                System.err.println("  ✗ Expected 1 rule set, got " + ast.getRuleSets().size());
                return false;
            }

            CSSRuleSetNode ruleSet = ast.getRuleSets().get(0);
            if (ruleSet.getDeclarations().size() != 1) {
                System.err.println("  ✗ Expected 1 declaration, got " + ruleSet.getDeclarations().size());
                return false;
            }

            CSSDeclarationNode decl = ruleSet.getDeclarations().get(0);
            if (!decl.getProperty().equals("color")) {
                System.err.println("  ✗ Expected property 'color', got '" + decl.getProperty() + "'");
                return false;
            }

            System.out.println("  ✓ Passed");
            return true;
        } catch (Exception e) {
            System.err.println("  ✗ Exception: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }


    // Test 2: Multiple Selectors

    private static boolean testMultipleSelectors() {
        System.out.println("Test 2: Multiple selectors");
        String css = "div, p, .class { margin: 0; }";

        try {
            CSSStylesheetNode ast = parseCSS(css);
            CSSRuleSetNode ruleSet = ast.getRuleSets().get(0);
            CSSSelectorListNode selectorList = ruleSet.getSelectorList();

            if (selectorList.getSelectors().size() != 3) {
                System.err.println("  ✗ Expected 3 selectors, got " + selectorList.getSelectors().size());
                return false;
            }

            System.out.println("  ✓ Passed");
            return true;
        } catch (Exception e) {
            System.err.println("  ✗ Exception: " + e.getMessage());
            return false;
        }
    }
    // Test 3: Combinators

    private static boolean testCombinators() {
        System.out.println("Test 3: Selector combinators");
        String css = "div > p { color: blue; }";

        try {
            CSSStylesheetNode ast = parseCSS(css);
            CSSRuleSetNode ruleSet = ast.getRuleSets().get(0);
            CSSSelectorNode selector = ruleSet.getSelectorList().getSelectors().get(0);

            if (selector.getSimpleSelectors().size() != 2) {
                System.err.println("  ✗ Expected 2 simple selectors");
                return false;
            }

            if (selector.getCombinators().size() != 1) {
                System.err.println("  ✗ Expected 1 combinator");
                return false;
            }

            CSSCombinatorNode combinator = selector.getCombinators().get(0);
            if (combinator.getCombinatorType() != CSSCombinatorNode.CombinatorType.CHILD) {
                System.err.println("  ✗ Expected CHILD combinator");
                return false;
            }

            System.out.println("  ✓ Passed");
            return true;
        } catch (Exception e) {
            System.err.println("  ✗ Exception: " + e.getMessage());
            return false;
        }
    }

    // Test 4: Pseudo-selectors

    private static boolean testPseudoSelectors() {
        System.out.println("Test 4: Pseudo-classes and pseudo-elements");
        String css = "a:hover::before { content: '→'; }";

        try {
            CSSStylesheetNode ast = parseCSS(css);
            CSSRuleSetNode ruleSet = ast.getRuleSets().get(0);
            CSSSimpleSelectorNode simpleSelector = ruleSet.getSelectorList()
                    .getSelectors().get(0)
                    .getSimpleSelectors().get(0);

            if (simpleSelector.getSelectorParts().size() != 3) {
                System.err.println("  ✗ Expected 3 selector parts (element, pseudo-class, pseudo-element)");
                return false;
            }

            System.out.println("  ✓ Passed");
            return true;
        } catch (Exception e) {
            System.err.println("  ✗ Exception: " + e.getMessage());
            return false;
        }
    }

    // Test 5: Attribute Selectors

    private static boolean testAttributeSelectors() {
        System.out.println("Test 5: Attribute selectors");
        String css = "input[type=\"text\"] { border: 1px solid; }";

        try {
            CSSStylesheetNode ast = parseCSS(css);
            CSSRuleSetNode ruleSet = ast.getRuleSets().get(0);
            CSSSimpleSelectorNode simpleSelector = ruleSet.getSelectorList()
                    .getSelectors().get(0)
                    .getSimpleSelectors().get(0);

            boolean hasAttributeSelector = simpleSelector.getSelectorParts().stream()
                    .anyMatch(part -> part instanceof CSSAttributeSelectorNode);

            if (!hasAttributeSelector) {
                System.err.println("  ✗ Expected attribute selector");
                return false;
            }

            System.out.println("  ✓ Passed");
            return true;
        } catch (Exception e) {
            System.err.println("  ✗ Exception: " + e.getMessage());
            return false;
        }
    }

    private static boolean testComplexValues() {
        System.out.println("Test 6: Complex values");
        String css = "div { margin: 10px 20px 30px 40px; }";

        try {
            CSSStylesheetNode ast = parseCSS(css);
            CSSRuleSetNode ruleSet = ast.getRuleSets().get(0);
            CSSDeclarationNode decl = ruleSet.getDeclarations().get(0);
            CSSValueNode value = decl.getValue();

            if (value.getComponents().size() != 4) {
                System.err.println("  ✗ Expected 4 value components, got " + value.getComponents().size());
                return false;
            }

            System.out.println("  ✓ Passed");
            return true;
        } catch (Exception e) {
            System.err.println("  ✗ Exception: " + e.getMessage());
            return false;
        }
    }

    // Test 7: Functions

    private static boolean testFunctions() {
        System.out.println("Test 7: CSS functions");
        String css = "div { background: rgb(255, 0, 0); }";

        try {
            CSSStylesheetNode ast = parseCSS(css);
            CSSRuleSetNode ruleSet = ast.getRuleSets().get(0);
            CSSDeclarationNode decl = ruleSet.getDeclarations().get(0);
            CSSValueNode value = decl.getValue();

            boolean hasFunction = value.getComponents().stream()
                    .anyMatch(comp -> comp instanceof CSSFunctionCallNode);

            if (!hasFunction) {
                System.err.println("  ✗ Expected function call");
                return false;
            }

            CSSFunctionCallNode func = (CSSFunctionCallNode) value.getComponents().get(0);
            if (!func.getFunctionName().equals("rgb")) {
                System.err.println("  ✗ Expected function name 'rgb', got '" + func.getFunctionName() + "'");
                return false;
            }

            if (func.getArguments().size() != 3) {
                System.err.println("  ✗ Expected 3 arguments, got " + func.getArguments().size());
                return false;
            }

            System.out.println("  ✓ Passed");
            return true;
        } catch (Exception e) {
            System.err.println("  ✗ Exception: " + e.getMessage());
            return false;
        }
    }

    // Test 8: At-Rules

    private static boolean testAtRules() {
        System.out.println("Test 8: At-rules (@media)");
        String css = "@media screen and (max-width: 768px) { .container { width: 100%; } }";

        try {
            CSSStylesheetNode ast = parseCSS(css);

            if (ast.getAtRules().size() != 1) {
                System.err.println("  ✗ Expected 1 at-rule, got " + ast.getAtRules().size());
                return false;
            }

            CSSAtRuleNode atRule = ast.getAtRules().get(0);
            if (!atRule.getKeyword().equals("@media")) {
                System.err.println("  ✗ Expected keyword '@media', got '" + atRule.getKeyword() + "'");
                return false;
            }

            if (atRule.getRuleSets().size() != 1) {
                System.err.println("  ✗ Expected 1 nested rule set, got " + atRule.getRuleSets().size());
                return false;
            }

            System.out.println("  ✓ Passed");
            return true;
        } catch (Exception e) {
            System.err.println("  ✗ Exception: " + e.getMessage());
            return false;
        }
    }

    // Test 9: CSS Variables

    private static boolean testCSSVariables() {
        System.out.println("Test 9: CSS variables");
        String css = ":root { --main-color: #06c; } div { color: var(--main-color); }";

        try {
            CSSStylesheetNode ast = parseCSS(css);

            if (ast.getRuleSets().size() != 2) {
                System.err.println("  ✗ Expected 2 rule sets");
                return false;
            }

            // Check variable declaration
            CSSDeclarationNode varDecl = ast.getRuleSets().get(0).getDeclarations().get(0);
            if (!varDecl.getProperty().startsWith("--")) {
                System.err.println("  ✗ Expected CSS variable declaration");
                return false;
            }

            System.out.println("  ✓ Passed");
            return true;
        } catch (Exception e) {
            System.err.println("  ✗ Exception: " + e.getMessage());
            return false;
        }
    }

    // Test 10: Important Declarations

    private static boolean testImportantDeclarations() {
        System.out.println("Test 10: !important declarations");
        String css = "div { color: red !important; }";

        try {
            CSSStylesheetNode ast = parseCSS(css);
            CSSDeclarationNode decl = ast.getRuleSets().get(0).getDeclarations().get(0);

            if (!decl.isImportant()) {
                System.err.println("  ✗ Expected !important flag to be true");
                return false;
            }

            System.out.println("  ✓ Passed");
            return true;
        } catch (Exception e) {
            System.err.println("  ✗ Exception: " + e.getMessage());
            return false;
        }
    }

    // Helper: Parse CSS and build AST

    private static CSSStylesheetNode parseCSS(String cssCode) {
        CSSLexer lexer = new CSSLexer(CharStreams.fromString(cssCode));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CSSParser parser = new CSSParser(tokens);

        CSSParser.StylesheetContext tree = parser.stylesheet();

        CSSASTBuilder builder = new CSSASTBuilder();
        CSSASTNode ast = builder.visit(tree);

        if (!(ast instanceof CSSStylesheetNode)) {
            throw new RuntimeException("AST root is not CSSStylesheetNode");
        }

        return (CSSStylesheetNode) ast;
    }
}
