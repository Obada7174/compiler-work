package compiler.ast;


public class TernaryNode extends ExpressionNode {
    private final ExpressionNode condition;
    private final ExpressionNode thenValue;
    private final ExpressionNode elseValue;

    public TernaryNode(ExpressionNode condition, ExpressionNode thenValue,
                       ExpressionNode elseValue, int lineNumber) {
        super(lineNumber);
        this.condition = condition;
        this.thenValue = thenValue;
        this.elseValue = elseValue;
    }

    public ExpressionNode getCondition() {
        return condition;
    }

    public ExpressionNode getThenValue() {
        return thenValue;
    }

    public ExpressionNode getElseValue() {
        return elseValue;
    }

    @Override
    public String getNodeType() {
        return "TernaryExpression";
    }
}
