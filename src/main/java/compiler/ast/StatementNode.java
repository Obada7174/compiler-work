package compiler.ast;

/**
 * Abstract base class for all statement nodes.
 * Demonstrates inheritance from ASTNode
 */
public abstract class StatementNode extends ASTNode {

    public StatementNode(int lineNumber) {
        super(lineNumber);
    }

    @Override
    public abstract String getNodeType();
}
