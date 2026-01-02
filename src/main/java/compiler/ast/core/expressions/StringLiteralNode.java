package compiler.ast.core.expressions;


import compiler.ast.core.ExpressionNode;

public class StringLiteralNode extends ExpressionNode {
    private String value;
    public StringLiteralNode(String value, int lineNumber) { super(lineNumber); this.value = value; }
    public String getValue() { return value; }
    @Override
    public String getNodeType() { return "StringLiteral"; }
}