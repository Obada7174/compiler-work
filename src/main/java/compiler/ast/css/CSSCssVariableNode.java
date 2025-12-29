package compiler.ast.css;

 // Example: "--primary-color", "--spacing-unit"

public class CSSCssVariableNode extends CSSValueComponentNode {
    private String variableName;

    public CSSCssVariableNode(String variableName, int lineNumber) {
        super(lineNumber, variableName);
        this.variableName = variableName;
    }

    public String getVariableName() {
        return variableName;
    }

    @Override
    public String getComponentText() {
        return variableName;
    }

    @Override
    public String getNodeType() {
        return "CSSCssVariable";
    }

    @Override
    public String getNodeDetails() {
        return String.format("CSSCssVariable: %s (line %d)", variableName, getLineNumber());
    }
}
