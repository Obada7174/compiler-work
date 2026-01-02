package compiler.ast.python;

import compiler.ast.core.ASTNode;
import compiler.ast.core.ExpressionNode;

import java.util.List;
import java.util.ArrayList;

/**
 * Statement node representing while loop
 * while condition: ...
 */
public class WhileStatementNode extends StatementNode {
    private ExpressionNode condition;
    private List<ASTNode> body;
    private List<ASTNode> elseBlock;  // Python's while-else construct

    public WhileStatementNode(ExpressionNode condition, List<ASTNode> body,
                             List<ASTNode> elseBlock, int lineNumber) {
        super(lineNumber,"While");
        this.condition = condition;
        this.body = body != null ? body : new ArrayList<>();
        this.elseBlock = elseBlock != null ? elseBlock : new ArrayList<>();

        // Add all as children
        addChild(condition);
        for (ASTNode node : this.body) {
            addChild(node);
        }
        for (ASTNode node : this.elseBlock) {
            addChild(node);
        }
    }

    public ExpressionNode getCondition() {
        return condition;
    }

    public List<ASTNode> getBody() {
        return body;
    }

    public List<ASTNode> getElseBlock() {
        return elseBlock;
    }

    @Override
    public String getNodeType() {
        return "WhileStatement";
    }

    @Override
    public String getNodeDetails() {
        String elseInfo = elseBlock.isEmpty() ? "" : String.format(" + %d else", elseBlock.size());
        return String.format("WhileStatement: %d statements%s (line %d)", body.size(), elseInfo, lineNumber);
    }
}
