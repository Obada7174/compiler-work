package compiler.ast;

/**
 * Node representing a parenthesized expression
 */
public class ParenExprNode extends ExpressionNode {
    private final ExpressionNode inner;

    public ParenExprNode(ExpressionNode inner, int lineNumber) {
        super(lineNumber);
        this.inner = inner;
        addChild(inner);
    }

    @Override
    public String getNodeType() {
        return "ParenExpr";
    }

    public ExpressionNode getInner() {
        return inner;
    }
}
