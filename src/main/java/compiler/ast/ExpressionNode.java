package compiler.ast;

/**
 * Abstract base class for all expression nodes.
 * Demonstrates inheritance from ASTNode
 */
public abstract class ExpressionNode extends ASTNode {

    public ExpressionNode(int lineNumber, String name) {
        super(lineNumber, name);
    }

    public ExpressionNode(int lineNumber) {
        super(lineNumber);
    }
}

