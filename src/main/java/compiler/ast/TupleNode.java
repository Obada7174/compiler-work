package compiler.ast;

import java.util.ArrayList;
import java.util.List;

/**
 * AST node representing a Python tuple.
 * Tuples can be created explicitly with parentheses or implicitly with commas.
 */
public class TupleNode extends ExpressionNode {
    private final List<ExpressionNode> elements;

    public TupleNode(List<ExpressionNode> elements, int lineNumber) {
        super(lineNumber);
        this.elements = elements != null ? elements : new ArrayList<>();
    }

    public List<ExpressionNode> getElements() {
        return elements;
    }

    @Override
    public String getNodeType() {
        return "Tuple";
    }
}
