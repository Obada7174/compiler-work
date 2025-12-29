package compiler.ast.css;

/**
 * AST node representing a CSS percentage value.
 * Example: "50%", "-100%", "12.5%"
 */
public class CSSPercentageValueNode extends CSSValueComponentNode {
    private String percentage;

    public CSSPercentageValueNode(String percentage, int lineNumber) {
        super(lineNumber, percentage);
        this.percentage = percentage;
    }

    public String getPercentage() {
        return percentage;
    }

    @Override
    public String getComponentText() {
        return percentage;
    }

    @Override
    public String getNodeType() {
        return "CSSPercentage";
    }

    @Override
    public String getNodeDetails() {
        return String.format("CSSPercentage: %s (line %d)", percentage, getLineNumber());
    }
}
