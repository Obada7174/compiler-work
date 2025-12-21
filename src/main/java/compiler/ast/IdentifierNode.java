package compiler.ast;

/**
 * Expression node representing variable or function identifiers
 */

public class IdentifierNode extends ExpressionNode {
    public IdentifierNode(String name, int lineNumber) { super(lineNumber, name); }

    @Override
    public String getNodeType() { return "Identifier"; }
}