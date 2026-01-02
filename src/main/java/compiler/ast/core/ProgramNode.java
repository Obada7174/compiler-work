package compiler.ast.core;

import compiler.ast.core.ASTNode;

import java.util.ArrayList;
import java.util.List;

public class ProgramNode extends ASTNode {

    private final List<ASTNode> children;

    public ProgramNode(int lineNumber) {
        super(lineNumber);
        this.children = new ArrayList<>();
    }

    @Override
    public String getNodeType() {
        return "Program";
    }

    @Override
    public String getNodeDetails() {
        return String.format("Program (line %d, %d children)", lineNumber, children.size());
    }

    // Add a single child
    public void addChild(ASTNode child) {
        if (child != null) {
            children.add(child);
        }
    }

    // Add multiple children at once
    public void addChildren(List<ASTNode> childNodes) {
        if (childNodes != null) {
            children.addAll(childNodes);
        }
    }

    // Getter for children
    public List<ASTNode> getChildren() {
        return children;
    }
}
