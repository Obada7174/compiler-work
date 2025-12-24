package compiler.ast;

import java.util.List;
import java.util.ArrayList;

/**
 * Statement node representing try-except-else-finally statement
 * try: ... except Exception: ... [else: ...] [finally: ...]
 */
public class TryStatementNode extends StatementNode {
    private List<ASTNode> tryBlock;
    private List<ExceptClauseNode> exceptClauses;
    private List<ASTNode> elseBlock;
    private List<ASTNode> finallyBlock;

    public TryStatementNode(List<ASTNode> tryBlock, List<ExceptClauseNode> exceptClauses,
                           List<ASTNode> elseBlock, List<ASTNode> finallyBlock, int lineNumber) {
        super(lineNumber,"Try");
        this.tryBlock = tryBlock != null ? tryBlock : new ArrayList<>();
        this.exceptClauses = exceptClauses != null ? exceptClauses : new ArrayList<>();
        this.elseBlock = elseBlock != null ? elseBlock : new ArrayList<>();
        this.finallyBlock = finallyBlock != null ? finallyBlock : new ArrayList<>();

        // Add all as children
        for (ASTNode node : this.tryBlock) {
            addChild(node);
        }
        for (ExceptClauseNode except : this.exceptClauses) {
            addChild(except);
        }
        for (ASTNode node : this.elseBlock) {
            addChild(node);
        }
        for (ASTNode node : this.finallyBlock) {
            addChild(node);
        }
    }

    public List<ASTNode> getTryBlock() {
        return tryBlock;
    }

    public List<ExceptClauseNode> getExceptClauses() {
        return exceptClauses;
    }

    public List<ASTNode> getElseBlock() {
        return elseBlock;
    }

    public List<ASTNode> getFinallyBlock() {
        return finallyBlock;
    }

    @Override
    public String getNodeType() {
        return "TryStatement";
    }

    @Override
    public String getNodeDetails() {
        return String.format("TryStatement: %d try, %d except, %d else, %d finally (line %d)",
                           tryBlock.size(), exceptClauses.size(), elseBlock.size(),
                           finallyBlock.size(), lineNumber);
    }
}
