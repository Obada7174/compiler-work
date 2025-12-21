package compiler.ast;

/**
 * Expression node representing boolean literals (True/False)
 */
public class BooleanLiteralNode extends ExpressionNode {
    private boolean value;
    public BooleanLiteralNode(boolean value, int lineNumber) { super(lineNumber); this.value = value; }
    public boolean getValue() { return value; }
    @Override
    public String getNodeType() { return "BooleanLiteral"; }
}


