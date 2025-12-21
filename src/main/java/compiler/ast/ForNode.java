package compiler.ast;

public class ForNode extends StatementNode {
    private IdentifierNode target;
    private ExpressionNode iterable;
    private BlockNode body;
    private BlockNode elseBlock;

    public ForNode(int lineNumber, IdentifierNode target, ExpressionNode iterable, BlockNode body, BlockNode elseBlock) {
        super(lineNumber, "For");
        this.target = target;
        this.iterable = iterable;
        this.body = body;
        this.elseBlock = elseBlock;

        addChild(target);
        addChild(iterable);
        addChild(body);
        if (elseBlock != null) addChild(elseBlock);
    }
    @Override
    public String getNodeType() {
        return "For";
    }
}