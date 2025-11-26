package compiler.ast;

/**
 * Abstract base class for all expression nodes.
 * Demonstrates inheritance from ASTNode
 */
public abstract class ExpressionNode extends ASTNode {

    public ExpressionNode(int lineNumber) {
        super(lineNumber);
    }

    @Override
    public abstract String getNodeType();
}
