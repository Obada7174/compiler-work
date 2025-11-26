package compiler.utils;

import compiler.ast.*;

/**
 * AST Printer - Displays the Abstract Syntax Tree in a readable format
 * Demonstrates tree traversal and visualization
 */
public class ASTPrinter {

    private static final String INDENT = "  ";
    private static final String BRANCH = "├─";
    private static final String LAST_BRANCH = "└─";
    private static final String VERTICAL = "│ ";
    private static final String SPACE = "  ";

    /**
     * Print the entire AST starting from root
     */
    public static void print(ASTNode root) {
        System.out.println("\n╔═══════════════════════════════════════════════╗");
        System.out.println("║          ABSTRACT SYNTAX TREE                 ║");
        System.out.println("╚═══════════════════════════════════════════════╝\n");

        if (root == null) {
            System.out.println("(empty tree)");
            return;
        }

        printNode(root, "", true);
        System.out.println();
    }

    /**
     * Recursively print a node and its children
     */
    private static void printNode(ASTNode node, String prefix, boolean isLast) {
        if (node == null) {
            return;
        }

        // Print current node
        System.out.print(prefix);
        System.out.print(isLast ? LAST_BRANCH : BRANCH);
        System.out.print(" ");
        System.out.println(node.getNodeDetails());

        // Print children
        int childCount = node.getChildren().size();
        for (int i = 0; i < childCount; i++) {
            ASTNode child = node.getChildren().get(i);
            boolean isLastChild = (i == childCount - 1);

            String newPrefix = prefix + (isLast ? SPACE : VERTICAL);
            printNode(child, newPrefix, isLastChild);
        }
    }

    /**
     * Print with statistics
     */
    public static void printWithStats(ASTNode root) {
        print(root);

        // Calculate statistics
        int totalNodes = countNodes(root);
        int maxDepth = calculateDepth(root);

        System.out.println("╔═══════════════════════════════════════════════╗");
        System.out.println("║          AST STATISTICS                       ║");
        System.out.println("╠═══════════════════════════════════════════════╣");
        System.out.printf("║  Total Nodes: %-32d║%n", totalNodes);
        System.out.printf("║  Maximum Depth: %-29d║%n", maxDepth);
        System.out.printf("║  Root Type: %-33s║%n", root.getNodeType());
        System.out.println("╚═══════════════════════════════════════════════╝");
    }

    /**
     * Count total nodes in the tree
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
     * Calculate maximum depth of the tree
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
     * Print in compact format (single line per node)
     */
    public static void printCompact(ASTNode root) {
        System.out.println("\n=== AST (Compact Format) ===\n");
        printCompactNode(root, 0);
        System.out.println();
    }

    private static void printCompactNode(ASTNode node, int depth) {
        if (node == null) {
            return;
        }

        // Print indentation
        for (int i = 0; i < depth; i++) {
            System.out.print("  ");
        }

        // Print node
        System.out.println(node.getNodeDetails());

        // Print children
        for (ASTNode child : node.getChildren()) {
            printCompactNode(child, depth + 1);
        }
    }

    /**
     * Print only node types (for structural overview)
     */
    public static void printStructure(ASTNode root) {
        System.out.println("\n=== AST Structure ===\n");
        printStructureNode(root, "", true);
        System.out.println();
    }

    private static void printStructureNode(ASTNode node, String prefix, boolean isLast) {
        if (node == null) {
            return;
        }

        System.out.print(prefix);
        System.out.print(isLast ? LAST_BRANCH : BRANCH);
        System.out.print(" ");
        System.out.println(node.getNodeType());

        int childCount = node.getChildren().size();
        for (int i = 0; i < childCount; i++) {
            ASTNode child = node.getChildren().get(i);
            boolean isLastChild = (i == childCount - 1);
            String newPrefix = prefix + (isLast ? SPACE : VERTICAL);
            printStructureNode(child, newPrefix, isLastChild);
        }
    }
}
