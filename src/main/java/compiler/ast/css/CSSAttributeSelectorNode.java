package compiler.ast.css;

public class CSSAttributeSelectorNode extends CSSSelectorPartNode {

    private final String attributeName;
    private final String operator;
    private final CSSValueNode value;

    // Constructor: with operator and value (4 parameters)
    public CSSAttributeSelectorNode(
            String attributeName,
            String operator,
            CSSValueNode value,
            int lineNumber
    ) {
        super(lineNumber, attributeName);
        this.attributeName = attributeName;
        this.operator = operator;
        this.value = value;
    }

    // Constructor: attribute only (no operator, no value)
    public CSSAttributeSelectorNode(String attributeName, int lineNumber) {
        super(lineNumber, attributeName);
        this.attributeName = attributeName;
        this.operator = null;
        this.value = null;
    }

    // Constructor: attribute with value as string (backward compatibility)
    public CSSAttributeSelectorNode(String attributeName, String attributeValue, int lineNumber) {
        super(lineNumber, attributeName);
        this.attributeName = attributeName;
        this.operator = "=";  // Default to exact match
        // Create a simple identifier value node
        this.value = new CSSValueNode(lineNumber);
    }

    public String getAttributeName() {
        return attributeName;
    }

    public String getOperator() {
        return operator;
    }

    public CSSValueNode getValue() {
        return value;
    }

    public boolean hasValue() {
        return value != null;
    }

    @Override
    public String getSelectorPartText() {
        if (value != null && operator != null) {
            return "[" + attributeName + operator + value.getName() + "]";
        }
        return "[" + attributeName + "]";
    }

    @Override
    public String getNodeType() {
        return "CSSAttributeSelector";
    }
}
