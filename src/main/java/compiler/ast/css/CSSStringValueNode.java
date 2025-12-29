package compiler.ast.css;

/**
 * AST node representing a string value.
 * Example: "'Arial'", '"Helvetica"'
 */
public class CSSStringValueNode extends CSSValueComponentNode {
    private String stringValue;

    public CSSStringValueNode(String stringValue, int lineNumber) {
        super(lineNumber, stringValue);
        this.stringValue = stringValue;
    }

    public String getStringValue() {
        return stringValue;
    }

    @Override
    public String getComponentText() {
        return stringValue;
    }

    @Override
    public String getNodeType() {
        return "CSSStringValue";
    }

    @Override
    public String getNodeDetails() {
        return String.format("CSSStringValue: %s (line %d)", stringValue, getLineNumber());
    }
}
