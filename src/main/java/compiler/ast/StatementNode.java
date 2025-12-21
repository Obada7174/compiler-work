package compiler.ast;

public abstract class StatementNode extends ASTNode {
    public StatementNode(int lineNumber, String className) {
        super(lineNumber);
    }

    @Override
    public abstract String getNodeType();
}
