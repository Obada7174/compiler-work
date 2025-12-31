package compiler.ast;

import java.util.List;


public class ListLiteralNode extends ExpressionNode {
    private final List<ExpressionNode> elements;

    public ListLiteralNode(List<ExpressionNode> elements, int lineNumber) {
        super(lineNumber);
        this.elements = elements;
    }

    public List<ExpressionNode> getElements() {
        return elements;
    }

    @Override
    public String getNodeType() {
        return "ListLiteral";
    }
}
