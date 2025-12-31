package compiler.utils;

import compiler.ast.ASTNode;

/**
 * Utility class for printing AST nodes with their actual class names
 * (using getClass().getSimpleName()) and line numbers.
 *
 * This is useful for debugging and understanding the exact Java class
 * hierarchy of the AST nodes.
 */
public class ASTClassNamePrinter {

    /**
     * Print a single AST node and all its children recursively.
     * Shows the class name (using getClass().getSimpleName()), line number, and tree structure.
     *
     * @param node The AST node to print
     * @param indent The indentation level (number of spaces)
     */
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

    /**
     * Print the entire AST starting from the root node.
     * This is a convenience method that calls printNode with indent = 0.
     *
     * @param root The root node of the AST
     */
    public static void printAST(ASTNode root) {
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║            AST (WITH CLASS NAMES)                          ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝\n");

        if (root == null) {
            System.out.println("(empty tree)");
            return;
        }

        printNode(root, 0);
        System.out.println();
    }

    /**
     * Print AST with a custom title/header.
     *
     * @param root The root node of the AST
     * @param title Title to display in the header
     */
    public static void printAST(ASTNode root, String title) {
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.printf("║  %-56s  ║%n", title);
        System.out.println("╚════════════════════════════════════════════════════════════╝\n");

        if (root == null) {
            System.out.println("(empty tree)");
            return;
        }

        printNode(root, 0);
        System.out.println();
    }

    /**
     * Print a subtree starting from a specific node.
     * Useful for examining a particular branch of the AST.
     *
     * @param node The node to start printing from
     * @param label A label describing this subtree
     */
    public static void printSubtree(ASTNode node, String label) {
        if (label != null && !label.isEmpty()) {
            System.out.println("\n=== " + label + " ===\n");
        }

        if (node == null) {
            System.out.println("(null node)");
            return;
        }

        printNode(node, 0);
        System.out.println();
    }

    /**
     * Print AST with statistics and metadata.
     *
     * @param root The root node of the AST
     */
    public static void printASTWithStats(ASTNode root) {
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║            AST (WITH CLASS NAMES & STATS)                  ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝\n");

        if (root == null) {
            System.out.println("(empty tree)");
            return;
        }

        // Print tree
        printNode(root, 0);

        // Print statistics
        int nodeCount = countNodes(root);
        int depth = calculateDepth(root);
        int leafCount = countLeaves(root);

        System.out.println("────────────────────────────────────────────────────────────");
        System.out.println("Statistics:");
        System.out.println("  Total nodes: " + nodeCount);
        System.out.println("  Tree depth: " + depth);
        System.out.println("  Leaf nodes: " + leafCount);
        System.out.println("  Root class: " + root.getClass().getSimpleName());
        System.out.println("  Root line: " + root.getLineNumber());
        System.out.println("────────────────────────────────────────────────────────────\n");
    }

    /**
     * Helper method to print indentation spaces.
     *
     * @param indent Number of spaces to print
     */
    private static void printIndent(int indent) {
        for (int i = 0; i < indent; i++) {
            System.out.print(" ");
        }
    }

    /**
     * Count total nodes in the AST.
     *
     * @param node The root node
     * @return Total number of nodes
     */
    private static int countNodes(ASTNode node) {
        if (node == null) {
            return 0;
        }

        int count = 1; // Count current node
        for (ASTNode child : node.getChildren()) {
            count += countNodes(child);
        }
        return count;
    }

    /**
     * Calculate the depth/height of the AST.
     *
     * @param node The root node
     * @return The depth of the tree
     */
    private static int calculateDepth(ASTNode node) {
        if (node == null || node.getChildren().isEmpty()) {
            return 1;
        }

        int maxChildDepth = 0;
        for (ASTNode child : node.getChildren()) {
            int childDepth = calculateDepth(child);
            if (childDepth > maxChildDepth) {
                maxChildDepth = childDepth;
            }
        }

        return 1 + maxChildDepth;
    }

    /**
     * Count leaf nodes (nodes with no children) in the AST.
     *
     * @param node The root node
     * @return Number of leaf nodes
     */
    private static int countLeaves(ASTNode node) {
        if (node == null) {
            return 0;
        }

        if (node.getChildren().isEmpty()) {
            return 1;
        }

        int leafCount = 0;
        for (ASTNode child : node.getChildren()) {
            leafCount += countLeaves(child);
        }
        return leafCount;
    }
}
