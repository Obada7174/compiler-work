package compiler.ast;

public class ReturnNode extends StatementNode {
    private ExpressionNode returnValue;

    public ReturnNode(ExpressionNode returnValue, int lineNumber) {
        super(lineNumber, "Return");
        this.returnValue = returnValue;
        addChild(returnValue);
    }

    @Override
    public String getNodeType() {
        return "Return";
    }
}
