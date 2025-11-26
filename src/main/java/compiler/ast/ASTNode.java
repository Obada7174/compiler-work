package compiler.ast;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract base class for all AST nodes.
 * Demonstrates OOP principles: Abstraction and Polymorphism
 */
public abstract class ASTNode {
    protected int lineNumber;
    protected List<ASTNode> children;
    protected ASTNode parent;

    public ASTNode(int lineNumber) {
        this.lineNumber = lineNumber;
        this.children = new ArrayList<>();
        this.parent = null;
    }

    // Polymorphic method - subclasses override to provide specific node type
    public abstract String getNodeType();

    // Template method pattern - defines the algorithm structure
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

    public List<ASTNode> getChildren() {
        return children;
    }

    // Polymorphic method for printing node details
    public String getNodeDetails() {
        return String.format("%s (line %d)", getNodeType(), lineNumber);
    }

    @Override
    public String toString() {
        return getNodeDetails();
    }
}
