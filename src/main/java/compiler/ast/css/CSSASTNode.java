package compiler.ast.css;

import compiler.ast.core.ASTNode;

import java.util.ArrayList;
import java.util.List;


public abstract class CSSASTNode extends ASTNode {

    public CSSASTNode(int lineNumber, String name) {
        super(lineNumber, name);
    }

    public CSSASTNode(int lineNumber) {
        super(lineNumber);
    }

    @Override
    public abstract String getNodeType();

    public void addCSSChild(CSSASTNode child) {
        addChild(child);
    }

    public List<CSSASTNode> getCSSChildren() {
        List<CSSASTNode> cssChildren = new ArrayList<>();
        for (ASTNode child : getChildren()) {
            if (child instanceof CSSASTNode) {
                cssChildren.add((CSSASTNode) child);
            }
        }
        return cssChildren;
    }
}
