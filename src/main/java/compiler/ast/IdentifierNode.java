package compiler.ast;


public class IdentifierNode extends ExpressionNode {
    public IdentifierNode(String name, int lineNumber) { super(lineNumber, name); }

    @Override
    public String getNodeType() { return "Identifier"; }
}