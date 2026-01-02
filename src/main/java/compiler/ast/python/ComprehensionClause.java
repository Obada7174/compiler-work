package compiler.ast.python;

import compiler.ast.core.ASTNode;
import compiler.ast.core.ExpressionNode;

import java.util.ArrayList;
import java.util.List;

public class ComprehensionClause extends ASTNode {
    private final String target;
    private final ExpressionNode iterable;
    private final List<ExpressionNode> conditions;
    private final ComprehensionClause nestedFor;

    public ComprehensionClause(String target, ExpressionNode iterable,
                               List<ExpressionNode> conditions,
                               ComprehensionClause nestedFor, int lineNumber) {
        super(lineNumber);
        this.target = target;
        this.iterable = iterable;
        this.conditions = conditions != null ? conditions : new ArrayList<>();
        this.nestedFor = nestedFor;
    }

    public String getTarget() {
        return target;
    }

    public ExpressionNode getIterable() {
        return iterable;
    }

    public List<ExpressionNode> getConditions() {
        return conditions;
    }

    public ComprehensionClause getNestedFor() {
        return nestedFor;
    }

    @Override
    public String getNodeType() {
        return "ComprehensionClause";
    }
}
