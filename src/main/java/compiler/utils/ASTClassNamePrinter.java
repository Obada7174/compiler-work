package compiler.utils;

import compiler.ast.core.ASTNode;

public class ASTClassNamePrinter {

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
        System.out.println("AST (WITH CLASS NAMES)");


        if (root == null) {
            System.out.println("(empty tree)");
            return;
        }

        printNode(root, 0);
        System.out.println();
    }

    public static void printAST(ASTNode root, String title) {
        System.out.printf("%-56s  %n", title);

        if (root == null) {
            System.out.println("(empty tree)");
            return;
        }

        printNode(root, 0);
        System.out.println();
    }

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
    public static void printASTWithStats(ASTNode root) {
        System.out.println(" AST (WITH CLASS NAMES & STATS) ");

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


    private static void printIndent(int indent) {
        for (int i = 0; i < indent; i++) {
            System.out.print(" ");
        }
    }

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
