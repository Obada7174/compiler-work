package compiler.ast.css;

// Example: "10px", "50%", "2.5"

public class CSSNumberExpressionNode extends CSSExpressionNode {
    private String number;
    private String unit;  // Can be null for unitless numbers

    public CSSNumberExpressionNode(String number, int lineNumber) {
        super(lineNumber, number);
        this.number = number;
        this.unit = null;
    }

    public CSSNumberExpressionNode(String number, String unit, int lineNumber) {
        super(lineNumber, number);
        this.number = number;
        this.unit = unit;
    }

    public String getNumber() {
        return number;
    }

    public String getUnit() {
        return unit;
    }

    public boolean hasUnit() {
        return unit != null && !unit.isEmpty();
    }

    @Override
    public String getExpressionText() {
        return hasUnit() ? number + unit : number;
    }

    @Override
    public String getNodeType() {
        return "CSSNumberExpression";
    }

    @Override
    public String getNodeDetails() {
        return String.format("CSSNumberExpression: %s (line %d)",
                getExpressionText(), getLineNumber());
    }
}
