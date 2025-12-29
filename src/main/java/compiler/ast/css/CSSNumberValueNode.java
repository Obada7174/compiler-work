package compiler.ast.css;

 // Example: "10", "20px", "1.5em", "50%"

public class CSSNumberValueNode extends CSSValueComponentNode {
    private String number;
    private String unit;  // Can be null for unitless numbers

    public CSSNumberValueNode(String number, int lineNumber) {
        super(lineNumber, number);
        this.number = number;
        this.unit = null;
    }

    public CSSNumberValueNode(String number, String unit, int lineNumber) {
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
    public String getComponentText() {
        return unit != null ? number + unit : number;
    }

    @Override
    public String getNodeType() {
        return "CSSNumberValue";
    }

    @Override
    public String getNodeDetails() {
        return String.format("CSSNumberValue: %s (line %d)", getComponentText(), getLineNumber());
    }
}
