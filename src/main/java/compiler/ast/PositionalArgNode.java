package compiler.ast;

/**
 * Node representing a positional argument in a function call
 */
public class PositionalArgNode extends ExpressionNode {
    private final ExpressionNode value;

    public PositionalArgNode(ExpressionNode value, int lineNumber) {
        super(lineNumber,"PositionalArg");
        this.value = value;
        addChild(value);
    }

    @Override
    public String getNodeType() {
        return "PositionalArg";
    }

    public ExpressionNode getValue() {
        return value;
    }
}
