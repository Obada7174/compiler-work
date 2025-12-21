package compiler.ast;

import java.util.ArrayList;
import java.util.List;


public abstract class ASTNode {
    protected int lineNumber;
    protected String name;                 // ← أضف هذا!
    protected List<ASTNode> children;
    protected ASTNode parent;

    // constructor جديد يقبل name
    public ASTNode(int lineNumber, String name) {
        this.lineNumber = lineNumber;
        this.name = name;
        this.children = new ArrayList<>();
        this.parent = null;
    }

    public ASTNode(int lineNumber) {
        this(lineNumber, ""); // اسم فارغ افتراضي
    }

    public abstract String getNodeType();

    public void addChild(ASTNode child) {
        if (child != null) {
            this.children.add(child);
            child.setParent(this);
        }
    }

    public void setParent(ASTNode parent) {
        this.parent = parent;
    }

    public ASTNode getParent() {
        return parent;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public String getName() {
        return name;
    }

    public List<ASTNode> getChildren() {
        return children;
    }

    public String getNodeDetails() {
        if (name != null && !name.isEmpty()) {
            return String.format("%s '%s' (line %d)", getNodeType(), name, lineNumber);
        } else {
            return String.format("%s (line %d)", getNodeType(), lineNumber);
        }
    }

    @Override
    public String toString() {
        return getNodeDetails();
    }
    public void print(String indent) {
        System.out.println(indent + getNodeType() + " (" + getNodeDetails() + ")");
        for (ASTNode child : children) {
            child.print(indent + "  ");
        }
    }
}