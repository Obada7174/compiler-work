package compiler.ast;

import java.util.List;
import java.util.ArrayList;

/**
 * Statement node representing if/elif/else statement
 * if condition: ... [elif condition: ...] [else: ...]
 */
public class IfStatementNode extends StatementNode {
    private ExpressionNode condition;
    private List<ASTNode> thenBlock;
    private List<ASTNode> elseBlock;

    public IfStatementNode(ExpressionNode condition, List<ASTNode> thenBlock, List<ASTNode> elseBlock, int lineNumber) {
        super(lineNumber,"If Statement");
        this.condition = condition;
        this.thenBlock = thenBlock != null ? thenBlock : new ArrayList<>();
        this.elseBlock = elseBlock != null ? elseBlock : new ArrayList<>();

        // Add condition and all blocks as children
        addChild(condition);
        for (ASTNode node : this.thenBlock) {
            addChild(node);
        }
        for (ASTNode node : this.elseBlock) {
            addChild(node);
        }
    }

    public ExpressionNode getCondition() {
        return condition;
    }

    public List<ASTNode> getThenBlock() {
        return thenBlock;
    }

    public List<ASTNode> getElseBlock() {
        return elseBlock;
    }

    @Override
    public String getNodeType() {
        return "IfStatement";
    }

    @Override
    public String getNodeDetails() {
        String elseInfo = elseBlock.isEmpty() ? "" : String.format(" + %d else", elseBlock.size());
        return String.format("IfStatement: (%d then%s) (line %d)", thenBlock.size(), elseInfo, lineNumber);
    }
}
