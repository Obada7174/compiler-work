package compiler.ast;

import java.util.List;
import java.util.ArrayList;

/**
 * AST node representing Jinja2 for loop {% for item in items %} ... {% endfor %}
 */
public class JinjaForNode extends ASTNode {
    private String variable;
    private ExpressionNode iterable;
    private List<ASTNode> body;

    public JinjaForNode(String variable, ExpressionNode iterable, List<ASTNode> body, int lineNumber) {
        super(lineNumber);
        this.variable = variable;
        this.iterable = iterable;
        this.body = body != null ? body : new ArrayList<>();

        addChild(iterable);
        for (ASTNode node : this.body) {
            addChild(node);
        }
    }

    public String getVariable() {
        return variable;
    }

    public ExpressionNode getIterable() {
        return iterable;
    }

    public List<ASTNode> getBody() {
        return body;
    }

    @Override
    public String getNodeType() {
        return "JinjaFor";
    }

    @Override
    public String getNodeDetails() {
        return String.format("JinjaFor: {%% for %s in ... %%} (%d statements) (line %d)",
            variable, body.size(), lineNumber);
    }
}
