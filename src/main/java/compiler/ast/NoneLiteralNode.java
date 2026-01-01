package compiler.ast;


public class NoneLiteralNode extends ExpressionNode {

    public NoneLiteralNode(int lineNumber) {
        super(lineNumber);
    }

    @Override
    public String getNodeType() {
        return "NoneLiteral";
    }

    @Override
    public String getNodeDetails() {
        return String.format("NoneLiteral: None (line %d)", lineNumber);
    }
}
