package compiler.ast;


import java.util.ArrayList;
import java.util.List;

public class WhileNode extends ASTNode {
    private ExpressionNode condition;
    private List<ASTNode> body;

    public WhileNode(ExpressionNode condition, List<ASTNode> body, int lineNumber) {
        super(lineNumber);
        this.condition = condition;
        this.body = body != null ? body : new ArrayList<>();

        addChild(condition);
        for (ASTNode node : this.body) addChild(node);
    }

    public ExpressionNode getCondition() { return condition; }
    public List<ASTNode> getBody() { return body; }

    @Override
    public String getNodeType() { return "While"; }

    @Override
    public String getNodeDetails() {
        return String.format("While: (%d statements) (line %d)", body.size(), lineNumber);
    }
}
