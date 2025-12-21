package compiler.ast;

public class ExceptNode extends StatementNode {
    private String exceptionName;
    private String alias;
    private ASTNode body;

    public ExceptNode(String exceptionName, String alias, ASTNode body, int lineNumber) {
        super(lineNumber, "Exception");
        this.exceptionName = exceptionName;
        this.alias = alias;
        this.body = body;
        addChild(body);
    }

    @Override
    public String getNodeType() {
        return "Except";
    }
}
