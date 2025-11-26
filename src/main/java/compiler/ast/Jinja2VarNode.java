package compiler.ast;

import java.util.List;
import java.util.ArrayList;

/**
 * AST node representing Jinja2 variable interpolation {{ variable }}
 */
public class Jinja2VarNode extends ASTNode {
    private ExpressionNode expression;
    private List<String> filters;

    public Jinja2VarNode(ExpressionNode expression, int lineNumber) {
        super(lineNumber);
        this.expression = expression;
        this.filters = new ArrayList<>();
        addChild(expression);
    }

    public Jinja2VarNode(ExpressionNode expression, List<String> filters, int lineNumber) {
        super(lineNumber);
        this.expression = expression;
        this.filters = filters != null ? filters : new ArrayList<>();
        addChild(expression);
    }

    public ExpressionNode getExpression() {
        return expression;
    }

    public List<String> getFilters() {
        return filters;
    }

    @Override
    public String getNodeType() {
        return "Jinja2Var";
    }

    @Override
    public String getNodeDetails() {
        String filterInfo = filters.isEmpty() ? "" : String.format(" | %s", String.join(" | ", filters));
        return String.format("Jinja2Var: {{ expr%s }} (line %d)", filterInfo, lineNumber);
    }
}
