package compiler.ast.css;

 // Example: "(100% - 20px)", "(2 * 3)"

public class CSSParenExpressionNode extends CSSExpressionNode {
    private CSSExpressionNode expression;

    public CSSParenExpressionNode(CSSExpressionNode expression, int lineNumber) {
        super(lineNumber, "paren");
        this.expression = expression;
        if (expression != null) {
            addChild(expression);
        }
    }

    public CSSExpressionNode getExpression() {
        return expression;
    }

    @Override
    public String getExpressionText() {
        String exprText = expression != null ? expression.getExpressionText() : "";
        return String.format("(%s)", exprText);
    }

    @Override
    public String getNodeType() {
        return "CSSParenExpression";
    }

    @Override
    public String getNodeDetails() {
        return String.format("CSSParenExpression: %s (line %d)",
                getExpressionText(), getLineNumber());
    }
}
