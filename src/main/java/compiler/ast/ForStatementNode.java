package compiler.ast;

import java.util.List;
import java.util.ArrayList;

/**
 * Statement node representing for loop
 * for target in iterable: ...
 */
public class ForStatementNode extends StatementNode {
    private ExpressionNode target;
    private ExpressionNode iterable;
    private List<ASTNode> body;
    private List<ASTNode> elseBlock;  // Python's for-else construct

    public ForStatementNode(ExpressionNode target, ExpressionNode iterable,
                           List<ASTNode> body, List<ASTNode> elseBlock, int lineNumber) {
        super(lineNumber);
        this.target = target;
        this.iterable = iterable;
        this.body = body != null ? body : new ArrayList<>();
        this.elseBlock = elseBlock != null ? elseBlock : new ArrayList<>();

        // Add all as children
        addChild(target);
        addChild(iterable);
        for (ASTNode node : this.body) {
            addChild(node);
        }
        for (ASTNode node : this.elseBlock) {
            addChild(node);
        }
    }

    public ExpressionNode getTarget() {
        return target;
    }

    public ExpressionNode getIterable() {
        return iterable;
    }

    public List<ASTNode> getBody() {
        return body;
    }

    public List<ASTNode> getElseBlock() {
        return elseBlock;
    }

    @Override
    public String getNodeType() {
        return "ForStatement";
    }

    @Override
    public String getNodeDetails() {
        String elseInfo = elseBlock.isEmpty() ? "" : String.format(" + %d else", elseBlock.size());
        return String.format("ForStatement: %d statements%s (line %d)", body.size(), elseInfo, lineNumber);
    }
}
