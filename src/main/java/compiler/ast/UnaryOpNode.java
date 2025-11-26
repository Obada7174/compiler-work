package compiler.ast;

/**
 * Expression node representing unary operations (-, not, ~)
 */
public class UnaryOpNode extends ExpressionNode {
    private String operator;
    private ExpressionNode operand;

    public UnaryOpNode(String operator, ExpressionNode operand, int lineNumber) {
        super(lineNumber);
        this.operator = operator;
        this.operand = operand;
        addChild(operand);
    }

    public String getOperator() {
        return operator;
    }

    public ExpressionNode getOperand() {
        return operand;
    }

    @Override
    public String getNodeType() {
        return "UnaryOp";
    }

    @Override
    public String getNodeDetails() {
        return String.format("UnaryOp: '%s' (line %d)", operator, lineNumber);
    }
}
