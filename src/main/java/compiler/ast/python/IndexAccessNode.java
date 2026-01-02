package compiler.ast.python;

import compiler.ast.core.ExpressionNode;

/**
 * Expression node representing index access (list[index])
 */
public class IndexAccessNode extends ExpressionNode {
    private ExpressionNode object;
    private ExpressionNode index;

    public IndexAccessNode(ExpressionNode object, ExpressionNode index, int lineNumber) {
        super(lineNumber);
        this.object = object;
        this.index = index;
        addChild(object);
        addChild(index);
    }

    public ExpressionNode getObject() {
        return object;
    }

    public ExpressionNode getIndex() {
        return index;
    }

    @Override
    public String getNodeType() {
        return "IndexAccess";
    }

    @Override
    public String getNodeDetails() {
        return String.format("IndexAccess: [index] (line %d)", lineNumber);
    }
}
