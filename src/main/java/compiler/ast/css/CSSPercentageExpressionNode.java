package compiler.ast.css;


 // Example: "50%", "100%", "-25%"

public class CSSPercentageExpressionNode extends CSSExpressionNode {
    private String percentage;

    public CSSPercentageExpressionNode(String percentage, int lineNumber) {
        super(lineNumber, percentage);
        this.percentage = percentage;
    }

    public String getPercentage() {
        return percentage;
    }

    @Override
    public String getExpressionText() {
        return percentage;
    }

    @Override
    public String getNodeType() {
        return "CSSPercentageExpression";
    }

    @Override
    public String getNodeDetails() {
        return String.format("CSSPercentageExpression: %s (line %d)",
                percentage, getLineNumber());
    }
}
