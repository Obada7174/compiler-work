package compiler.ast;

public class FinallyNode extends StatementNode {
    private ASTNode body;

    public FinallyNode(ASTNode body, int lineNumber) {
        super(lineNumber, "Finally");
        this.body = body;
        addChild(body);
    }

    @Override
    public String getNodeType() {
        return "Finally";
    }
}
