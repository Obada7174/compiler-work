package compiler.ast.python;

import compiler.ast.core.ExpressionNode;
import compiler.ast.python.ComprehensionClause;

public class GeneratorExpressionNode extends ExpressionNode {
    private final ExpressionNode element;
    private final ComprehensionClause clause;

    public GeneratorExpressionNode(ExpressionNode element, ComprehensionClause clause, int lineNumber) {
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
        return "GeneratorExpression";
    }
}
