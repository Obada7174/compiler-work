package compiler.ast;


public class ListComprehensionNode extends ExpressionNode {
    private final ExpressionNode element;
    private final ComprehensionClause clause;

    public ListComprehensionNode(ExpressionNode element, ComprehensionClause clause, int lineNumber) {
        super(lineNumber);
        this.element = element;
        this.clause = clause;
    }

    public ExpressionNode getElement() {
        return element;
    }

    public ComprehensionClause getClause() {
        return clause;
    }

    @Override
    public String getNodeType() {
        return "ListComprehension";
    }
}
