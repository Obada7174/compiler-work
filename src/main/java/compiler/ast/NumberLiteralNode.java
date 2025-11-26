package compiler.ast;

/**
 * Expression node representing numeric literals
 */
public class NumberLiteralNode extends ExpressionNode {
    private double value;

    public NumberLiteralNode(double value, int lineNumber) {
        super(lineNumber);
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    @Override
    public String getNodeType() {
        return "NumberLiteral";
    }

    @Override
    public String getNodeDetails() {
        return String.format("NumberLiteral: %s (line %d)",
            (value == (long) value) ? String.valueOf((long) value) : String.valueOf(value),
            lineNumber);
    }
}
