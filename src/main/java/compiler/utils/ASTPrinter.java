package compiler.utils;

import compiler.ast.core.ASTNode;

public class ASTPrinter {

    private static final String INDENT = "  ";
    private static final String BRANCH = "├─";
    private static final String LAST_BRANCH = "└─";
    private static final String VERTICAL = "│ ";
    private static final String SPACE = "  ";


    public static void print(ASTNode root) {
        System.out.println("ABSTRACT SYNTAX TREE");

        if (root == null) {
            System.out.println("(empty tree)");
            return;
        }

        printNode(root, "", true);
        System.out.println();
    }


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

    public static void printWithStats(ASTNode root) {
        print(root);

        // Calculate statistics
        int totalNodes = countNodes(root);
        int maxDepth = calculateDepth(root);

        System.out.println("AST STATISTICS");
        System.out.printf("Total Nodes: %-32d  %n", totalNodes);
        System.out.printf("Maximum Depth: %-29d %n", maxDepth);
        System.out.printf("Root Type: %-33s %n", root.getNodeType());

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

}
