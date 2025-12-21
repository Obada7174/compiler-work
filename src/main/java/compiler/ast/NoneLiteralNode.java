package compiler.ast;

/**
 * Node representing the Python literal None
 */
public class NoneLiteralNode extends ExpressionNode {
    public NoneLiteralNode(int lineNumber) { super(lineNumber); }
    @Override
    public String getNodeType() { return "NoneLiteral"; }
}

