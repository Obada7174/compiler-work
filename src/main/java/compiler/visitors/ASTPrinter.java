package compiler.visitors;

import compiler.ast.ASTNode;


public class ASTPrinter {

    public static void printNode(ASTNode node) {
        if (node == null) {
            System.out.println("(null node)");
            return;
        }
        System.out.println(node.getNodeDetails());
    }

    public static void printTree(ASTNode root) {
        printTree(root, "", true);
    }


    private static void printTree(ASTNode node, String prefix, boolean isTail) {
        if (node == null) {
            return;
        }

        // Print current node
        System.out.println(prefix + (isTail ? "└── " : "├── ") + node.getNodeDetails());

        // Print children
        java.util.List<ASTNode> children = node.getChildren();
        for (int i = 0; i < children.size(); i++) {
            boolean isLastChild = (i == children.size() - 1);
            printTree(children.get(i), prefix + (isTail ? "    " : "│   "), isLastChild);
        }
    }


    public static void printTreeBoxed(ASTNode root) {
        System.out.println("ABSTRACT SYNTAX TREE");
        System.out.println();
        printTree(root);
        System.out.println();
        System.out.println("────────────────────────────────────────────────────────────");
        System.out.println("Total nodes: " + countNodes(root));
        System.out.println("Tree height: " + treeHeight(root));
        System.out.println("────────────────────────────────────────────────────────────");
    }


    public static int countNodes(ASTNode root) {
        if (root == null) {
            return 0;
        }
        int count = 1;
        for (ASTNode child : root.getChildren()) {
            count += countNodes(child);
        }
        return count;
    }


    public static int treeHeight(ASTNode root) {
        if (root == null || root.getChildren().isEmpty()) {
            return 1;
        }
        int maxHeight = 0;
        for (ASTNode child : root.getChildren()) {
            maxHeight = Math.max(maxHeight, treeHeight(child));
        }
        return 1 + maxHeight;
    }

    public static void traverse(ASTNode root, NodeVisitor visitor) {
        if (root == null || visitor == null) {
            return;
        }
        visitor.visit(root);
        for (ASTNode child : root.getChildren()) {
            traverse(child, visitor);
        }
    }


    @FunctionalInterface
    public interface NodeVisitor {
        void visit(ASTNode node);
    }

    public static void printLinear(ASTNode root) {
        printLinear(root, 0);
    }

    private static void printLinear(ASTNode node, int depth) {
        if (node == null) {
            return;
        }

        // Print indentation
        for (int i = 0; i < depth; i++) {
            System.out.print("  ");
        }

        // Print node info
        System.out.println(node.getNodeDetails());

        // Print children
        for (ASTNode child : node.getChildren()) {
            printLinear(child, depth + 1);
        }
    }


    public static String summarize(ASTNode root) {
        if (root == null) {
            return "(empty tree)";
        }

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("AST Summary: %s\n", root.getNodeType()));
        sb.append(String.format("  Total nodes: %d\n", countNodes(root)));
        sb.append(String.format("  Tree height: %d\n", treeHeight(root)));
        sb.append(String.format("  Root line: %d\n", root.getLineNumber()));

        return sb.toString();
    }
}
