package compiler.ast;

/**
 * Expression node representing binary operations (+, -, *, /, ==, !=, etc.)
 * Demonstrates composition pattern
 */
public class BinaryOpNode extends ExpressionNode {
    private String operator;
    private ExpressionNode left;
    private ExpressionNode right;

    public BinaryOpNode(String operator, ExpressionNode left, ExpressionNode right, int lineNumber) {
        super(lineNumber);
        this.operator = operator;
        this.left = left;
        this.right = right;
        addChild(left);
        addChild(right);
    }

    public String getOperator() {
        return operator;
    }

    public ExpressionNode getLeft() {
        return left;
    }

    public ExpressionNode getRight() {
        return right;
    }

    @Override
    public String getNodeType() {
        return "BinaryOp";
    }

    @Override
    public String getNodeDetails() {
        return String.format("BinaryOp: '%s' (line %d)", operator, lineNumber);
    }
}
