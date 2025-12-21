package compiler.ast;

import java.util.List;
import java.util.ArrayList;

public class ForNode extends ASTNode {
    private String variable;
    private ExpressionNode iterable;
    private List<ASTNode> body;

    public ForNode(String variable, ExpressionNode iterable, List<ASTNode> body, int lineNumber)
    {
        super(lineNumber);
        this.variable = variable;
        this.iterable = iterable;
        this.body = body != null ? body : new ArrayList<>();

        addChild(iterable);
        for (ASTNode node : this.body) addChild(node);
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
        return "For";
    }

    @Override
    public String getNodeDetails() {
        return String.format("For: (%s in ...) (%d statements) (line %d)", variable, body.size(), lineNumber);
    }
}
