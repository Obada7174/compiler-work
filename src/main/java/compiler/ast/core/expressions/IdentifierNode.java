package compiler.ast.core.expressions;


import compiler.ast.core.ExpressionNode;

public class IdentifierNode extends ExpressionNode {
    public IdentifierNode(String name, int lineNumber) { super(lineNumber, name); }

    @Override
    public String getNodeType() { return "Identifier"; }
}