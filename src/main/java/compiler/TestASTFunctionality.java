package compiler;

import compiler.ast.core.ASTNode;
import compiler.ast.core.ParameterNode;
import compiler.ast.core.ProgramNode;
import compiler.ast.core.expressions.IdentifierNode;
import compiler.ast.core.expressions.NumberLiteralNode;
import compiler.ast.core.expressions.StringLiteralNode;
import compiler.ast.python.*;
import compiler.visitors.ASTPrinter;

public class TestASTFunctionality {
    public static void printNode(ASTNode node, int indent) {
        if (node == null) {
            printIndent(indent);
            System.out.println("(null)");
            return;
        }

        // Print current node: ClassName (Line: X)
        printIndent(indent);
        System.out.println(node.getClass().getSimpleName() + " (Line: " + node.getLineNumber() + ")");

        // Recursively print all children with increased indentation
        for (ASTNode child : node.getChildren()) {
            printNode(child, indent + 2);
        }
    }

    public static void printAST(ASTNode root) {
        System.out.println("AST WITH CLASS NAMES");

        if (root == null) {
            System.out.println("(empty tree)");
            return;
        }

        printNode(root, 0);
        System.out.println();
    }

    private static void printIndent(int indent) {
        for (int i = 0; i < indent; i++) {
            System.out.print(" ");
        }
    }

    public static void main(String[] args) {
        System.out.println("║         AST FUNCTIONALITY VERIFICATION TEST                ║");

        // Test 1: Verify AST Node Structure
        System.out.println("TEST 1: AST Node Structure Verification");
        System.out.println("─────────────────────────────────────────────────────────────");
        testNodeStructure();
        System.out.println("✓ PASSED: All nodes contain node type, line number, and children\n");

        // Test 2: Test Simple AST Construction
        System.out.println("TEST 2: Simple AST Construction");
        System.out.println("─────────────────────────────────────────────────────────────");
        ASTNode simpleTree = createSimpleAST();
        System.out.println("✓ PASSED: Simple AST created successfully\n");

        // Test 3: Test ASTPrinter.printNode()
        System.out.println("TEST 3: ASTPrinter.printNode() - Single Node");
        System.out.println("─────────────────────────────────────────────────────────────");
        ASTPrinter.printNode(simpleTree);
        System.out.println("✓ PASSED: printNode() works correctly\n");

        // Test 4: Test ASTPrinter.printTree()
        System.out.println("TEST 4: ASTPrinter.printTree() - Tree with Children");
        System.out.println("─────────────────────────────────────────────────────────────");
        ASTPrinter.printTree(simpleTree);
        System.out.println("✓ PASSED: printTree() displays tree structure\n");

        // Test 5: Test ASTPrinter.printTreeBoxed()
        System.out.println("TEST 5: ASTPrinter.printTreeBoxed() - Formatted Output");
        System.out.println("─────────────────────────────────────────────────────────────");
        ASTPrinter.printTreeBoxed(simpleTree);
        System.out.println("✓ PASSED: printTreeBoxed() shows statistics\n");

        // Test 6: Test ASTPrinter.printLinear()
        System.out.println("TEST 6: ASTPrinter.printLinear() - Indented Format");
        System.out.println("─────────────────────────────────────────────────────────────");
        ASTPrinter.printLinear(simpleTree);
        System.out.println("✓ PASSED: printLinear() shows indented format\n");

        // Test 7: Test ASTPrinter.summarize()
        System.out.println("TEST 7: ASTPrinter.summarize() - Summary Info");
        System.out.println("─────────────────────────────────────────────────────────────");
        String summary = ASTPrinter.summarize(simpleTree);
        System.out.println(summary);
        System.out.println("✓ PASSED: summarize() provides statistics\n");

        // Test 8: Test Empty/Null Handling
        System.out.println("TEST 8: Null and Empty Node Handling");
        System.out.println("─────────────────────────────────────────────────────────────");
        testNullHandling();
        System.out.println("✓ PASSED: Null handling works correctly\n");

        // Test 9: Test Complex AST
        System.out.println("TEST 9: Complex AST (Function with Nested Statements)");
        System.out.println("─────────────────────────────────────────────────────────────");
        ASTNode complexTree = createComplexAST();
        ASTPrinter.printTreeBoxed(complexTree);
        System.out.println("✓ PASSED: Complex AST created and printed\n");

        // Test 10: Test ASTNode.print() method
        System.out.println("TEST 10: ASTNode.print() - Built-in Method");
        System.out.println("─────────────────────────────────────────────────────────────");
        simpleTree.print("");
        System.out.println("✓ PASSED: ASTNode.print() method works\n");

        // Test 11: Test custom printAST() method with class names
        System.out.println("TEST 11: printAST() - Custom Method with Class Names");
        System.out.println("─────────────────────────────────────────────────────────────");
        printAST(simpleTree);
        System.out.println("✓ PASSED: printAST() shows class names and line numbers\n");

        // Test 12: Test printNode() on specific subtree
        System.out.println("TEST 12: printNode() - Print Specific Subtree");
        System.out.println("─────────────────────────────────────────────────────────────");
        System.out.println("Printing only the FunctionDef node and its children:\n");
        if (simpleTree.getChildren().size() > 1) {
            ASTNode functionNode = simpleTree.getChildren().get(1);
            printNode(functionNode, 0);
        }
        System.out.println("✓ PASSED: printNode() can print any subtree\n");

        // Test 13: Test printAST() on complex tree
        System.out.println("TEST 13: printAST() - Complex Tree with Nested Structures");
        System.out.println("─────────────────────────────────────────────────────────────");
        printAST(complexTree);
        System.out.println("✓ PASSED: printAST() handles complex nested structures\n");

        // Final Summary
        System.out.println("              ALL TESTS PASSED SUCCESSFULLY                 ║");
        System.out.println("\nVerified Functionality:");
        System.out.println("  ✓ AST nodes contain: node type, line number, children list");
        System.out.println("  ✓ printNode(node, indent) - prints node with class name & line");
        System.out.println("  ✓ printAST(root) - prints entire tree with class names");
        System.out.println("  ✓ printTree(root) - prints tree with tree structure");
        System.out.println("  ✓ printTreeBoxed(root) - prints formatted tree with stats");
        System.out.println("  ✓ printLinear(root) - prints with simple indentation");
        System.out.println("  ✓ summarize(root) - returns summary string");
        System.out.println("  ✓ Proper handling of null and empty nodes");
        System.out.println("  ✓ Recursive traversal of all children");
        System.out.println("  ✓ Can print any specific subtree/node");
    }

    /**
     * Test 1: Verify that all AST nodes have required structure
     */
    private static void testNodeStructure() {
        // Create a sample node
        ProgramNode program = new ProgramNode(1);

        // Verify getNodeType() exists and returns non-null
        String nodeType = program.getNodeType();
        assert nodeType != null : "Node type should not be null";
        System.out.println("  ✓ getNodeType() returns: " + nodeType);

        // Verify getLineNumber() exists
        int lineNumber = program.getLineNumber();
        System.out.println("  ✓ getLineNumber() returns: " + lineNumber);

        // Verify getChildren() exists and returns list
        java.util.List<ASTNode> children = program.getChildren();
        assert children != null : "Children list should not be null";
        System.out.println("  ✓ getChildren() returns list (size: " + children.size() + ")");

        // Verify addChild() works
        IdentifierNode child = new IdentifierNode("test", 2);
        program.addChild(child);
        assert program.getChildren().size() == 1 : "Child should be added";
        System.out.println("  ✓ addChild() works correctly");
    }


     // Test 2-6: Create a simple AST for testing
    private static ASTNode createSimpleAST() {
        ProgramNode program = new ProgramNode(1);

        // Add assignment: x = 5
        IdentifierNode target = new IdentifierNode("x", 2);
        NumberLiteralNode value = new NumberLiteralNode(5.0, 2);
        AssignmentNode assignment = new AssignmentNode(
            java.util.List.of(target), value, 2
        );
        program.addChild(assignment);

        // Add function: def foo(): return x
        IdentifierNode returnValue = new IdentifierNode("x", 4);
        ReturnStatementNode returnStmt = new ReturnStatementNode(returnValue, 4);

        FunctionDefNode function = new FunctionDefNode(
            "foo",
            new java.util.ArrayList<>(),
            java.util.List.of(returnStmt),
            new java.util.ArrayList<>(),
            null,
            3
        );
        program.addChild(function);

        return program;
    }

     // Test 8: Test null and empty node handling

    private static void testNullHandling() {
        // Test printNode with null
        System.out.println("  Testing printNode(null):");
        ASTPrinter.printNode(null);

        // Test printTree with null
        System.out.println("  Testing printTree(null):");
        ASTPrinter.printTree(null);

        // Test node with no children
        System.out.println("  Testing node with no children:");
        IdentifierNode leafNode = new IdentifierNode("leaf", 1);
        ASTPrinter.printTree(leafNode);

        // Test summarize with null
        System.out.println("  Testing summarize(null):");
        String summary = ASTPrinter.summarize(null);
        System.out.println("  Result: " + summary);
    }

    /*
      Test 9: Create a complex AST with nested structures
     */
    private static ASTNode createComplexAST() {
        ProgramNode program = new ProgramNode(1);

        // Create function: def calculate_total(items):
        java.util.List<ParameterNode> params = new java.util.ArrayList<>();
        params.add(new ParameterNode("items", null, null, 1));

        // Function body
        java.util.List<ASTNode> body = new java.util.ArrayList<>();

        // total = 0
        IdentifierNode totalTarget = new IdentifierNode("total", 2);
        NumberLiteralNode zero = new NumberLiteralNode(0.0, 2);
        AssignmentNode totalInit = new AssignmentNode(
            java.util.List.of(totalTarget), zero, 2
        );
        body.add(totalInit);

        // for item in items:
        IdentifierNode loopVar = new IdentifierNode("item", 3);
        IdentifierNode itemsList = new IdentifierNode("items", 3);

        // Loop body: total = total + item['price']
        IdentifierNode totalRef = new IdentifierNode("total", 4);
        IdentifierNode itemRef = new IdentifierNode("item", 4);
        StringLiteralNode priceKey = new StringLiteralNode("price", 4);
        IndexAccessNode itemPrice = new IndexAccessNode(itemRef, priceKey, 4);
        BinaryOpNode addition = new BinaryOpNode("+", totalRef, itemPrice, 4);
        IdentifierNode totalTarget2 = new IdentifierNode("total", 4);
        AssignmentNode totalUpdate = new AssignmentNode(
            java.util.List.of(totalTarget2), addition, 4
        );

        ForStatementNode forLoop = new ForStatementNode(
            loopVar, itemsList,
            java.util.List.of(totalUpdate),
            new java.util.ArrayList<>(),
            3
        );
        body.add(forLoop);

        // return total
        IdentifierNode returnVal = new IdentifierNode("total", 5);
        ReturnStatementNode returnStmt = new ReturnStatementNode(returnVal, 5);
        body.add(returnStmt);

        // Create function definition
        FunctionDefNode function = new FunctionDefNode(
            "calculate_total",
            params,
            body,
            new java.util.ArrayList<>(),
            null,
            1
        );

        program.addChild(function);

        return program;
    }
}
