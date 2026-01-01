package compiler.ast;


public class NumberLiteralNode extends ExpressionNode {
    private double value;
    public NumberLiteralNode(double value, int lineNumber) { super(lineNumber); this.value = value; }
    public double getValue() { return value; }
    @Override
    public String getNodeType() { return "NumberLiteral"; }
}