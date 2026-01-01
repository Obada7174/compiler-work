package compiler.ast;


public class ComparisonNode extends ExpressionNode {
    private String operator;
    private ExpressionNode left;
    private ExpressionNode right;

    public ComparisonNode(String operator, ExpressionNode left, ExpressionNode right, int lineNumber) {
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
        return "Comparison";
    }

    @Override
    public String getNodeDetails() {
        return String.format("Comparison: '%s' (line %d)", operator, lineNumber);
    }
}
