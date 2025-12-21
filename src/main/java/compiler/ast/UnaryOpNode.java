package compiler.ast;

/**
 * Node representing a unary operation (-, not, ~)
 */
public class UnaryOpNode extends ExpressionNode {
    private final String operator;
    private final ExpressionNode operand;

    public UnaryOpNode(String operator, ExpressionNode operand, int lineNumber) {
        super(lineNumber);
        this.operator = operator;
        this.operand = operand;
        addChild(operand);
    }

    @Override
    public String getNodeType() {
        return "UnaryOp";
    }

    public String getOperator() {
        return operator;
    }

    public ExpressionNode getOperand() {
        return operand;
    }

    @Override
    public String getNodeDetails() {
        return String.format("UnaryOp: '%s' (line %d)", operator, lineNumber);
    }
}
