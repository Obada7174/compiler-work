package compiler.ast.css;

/**
 * AST node representing an identifier value.
 * Example: "auto", "inherit", "solid", "red"
 */
public class CSSIdentifierValueNode extends CSSValueComponentNode {
    private String identifier;

    public CSSIdentifierValueNode(String identifier, int lineNumber) {
        super(lineNumber, identifier);
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    @Override
    public String getComponentText() {
        return identifier;
    }

    @Override
    public String getNodeType() {
        return "CSSIdentifierValue";
    }

    @Override
    public String getNodeDetails() {
        return String.format("CSSIdentifierValue: %s (line %d)", identifier, getLineNumber());
    }
}
