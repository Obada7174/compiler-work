package compiler.ast.python;


import compiler.ast.core.ExpressionNode;

public class BooleanLiteralNode extends ExpressionNode {
    private boolean value;
    public BooleanLiteralNode(boolean value, int lineNumber) { super(lineNumber); this.value = value; }
    public boolean getValue() { return value; }
    @Override
    public String getNodeType() { return "BooleanLiteral"; }
}


