package compiler.ast;


public class BinaryOpNode extends ExpressionNode {
    private final String operator;
    private final ExpressionNode left;
    private final ExpressionNode right;

    public BinaryOpNode(String operator, ExpressionNode left, ExpressionNode right, int lineNumber) {
        super(lineNumber);
        this.operator = operator;
        this.left = left;
        this.right = right;
        addChild(left);
        addChild(right);
    }

    @Override
    public String getNodeType() {
        return "BinaryOp";
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
    public String getNodeDetails() {
        return String.format("BinaryOp: '%s' (line %d)", operator, lineNumber);
    }
}
