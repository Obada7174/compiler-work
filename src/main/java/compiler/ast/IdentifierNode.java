package compiler.ast;

/**
 * Expression node representing variable or function identifiers
 */
public class IdentifierNode extends ExpressionNode {
    private String name;

    public IdentifierNode(String name, int lineNumber) {
        super(lineNumber);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getNodeType() {
        return "Identifier";
    }

    @Override
    public String getNodeDetails() {
        return String.format("Identifier: %s (line %d)", name, lineNumber);
    }
}
