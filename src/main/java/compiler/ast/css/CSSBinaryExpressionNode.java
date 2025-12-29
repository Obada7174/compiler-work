package compiler.ast.css;

 // Example: "100% - 20px", "2 * 10px", "calc(10px + 5%)"

public class CSSBinaryExpressionNode extends CSSExpressionNode {
    public enum Operator {
        ADD("+"),
        SUBTRACT("-"),
        MULTIPLY("*"),
        DIVIDE("/");

        private final String symbol;

        Operator(String symbol) {
            this.symbol = symbol;
        }

        public String getSymbol() {
            return symbol;
        }
    }

    private CSSExpressionNode left;
    private Operator operator;
    private CSSExpressionNode right;

    public CSSBinaryExpressionNode(CSSExpressionNode left, Operator operator,
                                    CSSExpressionNode right, int lineNumber) {
        super(lineNumber, operator.getSymbol());
        this.left = left;
        this.operator = operator;
        this.right = right;

        if (left != null) addChild(left);
        if (right != null) addChild(right);
    }

    public CSSExpressionNode getLeft() {
        return left;
    }

    public Operator getOperator() {
        return operator;
    }

    public CSSExpressionNode getRight() {
        return right;
    }

    @Override
    public String getExpressionText() {
        String leftText = left != null ? left.getExpressionText() : "null";
        String rightText = right != null ? right.getExpressionText() : "null";
        return String.format("%s %s %s", leftText, operator.getSymbol(), rightText);
    }

    @Override
    public String getNodeType() {
        return "CSSBinaryExpression";
    }

    @Override
    public String getNodeDetails() {
        return String.format("CSSBinaryExpression: %s (line %d)",
                getExpressionText(), getLineNumber());
    }
}
