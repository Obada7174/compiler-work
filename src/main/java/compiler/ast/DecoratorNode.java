package compiler.ast;

/**
 * Represents a single decorator (@something)
 */
public class DecoratorNode extends ASTNode {
    private final ASTNode expression; // عادة IdentifierNode أو أي ExpressionNode
    private final FunctionCallNode call; // optional, null if no call

    public DecoratorNode(int lineNumber, String name, ASTNode expression, FunctionCallNode call) {
        super(lineNumber, name);
        this.expression = expression;
        this.call = call;

        addChild(expression);
        if (call != null) addChild(call);
    }

    @Override
    public String getNodeType() {
        return "DecoratorNode";
    }

    public ASTNode getExpression() { return expression; }
    public FunctionCallNode getCall() { return call; }
}
