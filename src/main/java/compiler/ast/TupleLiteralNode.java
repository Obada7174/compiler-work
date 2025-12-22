package compiler.ast;

import java.util.List;
import java.util.ArrayList;

/**
 * Expression node representing tuple literals (element1, element2, ...)
 * Python-specific immutable sequence type
 */
public class TupleLiteralNode extends ExpressionNode {
    private List<ExpressionNode> elements;

    public TupleLiteralNode(List<ExpressionNode> elements, int lineNumber) {
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
        return "TupleLiteral";
    }

    @Override
    public String getNodeDetails() {
        return String.format("TupleLiteral: %d elements (line %d)", elements.size(), lineNumber);
    }
}
