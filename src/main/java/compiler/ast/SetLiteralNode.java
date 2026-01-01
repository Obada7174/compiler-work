package compiler.ast;

import java.util.List;
import java.util.ArrayList;


public class SetLiteralNode extends ExpressionNode {
    private List<ExpressionNode> elements;

    public SetLiteralNode(List<ExpressionNode> elements, int lineNumber) {
        super(lineNumber);
        this.elements = elements != null ? elements : new ArrayList<>();

        // Add all elements as children
        for (ExpressionNode element : this.elements) {
            addChild(element);
        }
    }

    public List<ExpressionNode> getElements() {
        return elements;
    }

    @Override
    public String getNodeType() {
        return "SetLiteral";
    }

    @Override
    public String getNodeDetails() {
        return String.format("SetLiteral: %d elements (line %d)", elements.size(), lineNumber);
    }
}
