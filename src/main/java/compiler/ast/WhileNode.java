package compiler.ast;

public class WhileNode extends StatementNode {
    private ExpressionNode condition;
    private ASTNode body;
    private ASTNode elseBody;

    public WhileNode(ExpressionNode condition, ASTNode body, ASTNode elseBody, int lineNumber) {
        super(lineNumber, "While");
        this.condition = condition;
        this.body = body;
        this.elseBody = elseBody;
        addChild(condition);
        addChild(body);
        if (elseBody != null) addChild(elseBody);
    }

    @Override
    public String getNodeType() {
        return "While";
    }
}
