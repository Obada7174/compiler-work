package compiler.ast.core;

import java.util.List;
import java.util.ArrayList;


public class ExceptClauseNode extends ASTNode {
    private ExpressionNode exceptionType;
    private String alias;
    private List<ASTNode> body;

    public ExceptClauseNode(ExpressionNode exceptionType, String alias,
                           List<ASTNode> body, int lineNumber) {
        super(lineNumber);
        this.exceptionType = exceptionType;
        this.alias = alias;
        this.body = body != null ? body : new ArrayList<>();

        if (exceptionType != null) {
            addChild(exceptionType);
        }
        for (ASTNode node : this.body) {
            addChild(node);
        }
    }

    public ExpressionNode getExceptionType() {
        return exceptionType;
    }

    public String getAlias() {
        return alias;
    }

    public List<ASTNode> getBody() {
        return body;
    }

    @Override
    public String getNodeType() {
        return "ExceptClause";
    }

    @Override
    public String getNodeDetails() {
        String typeInfo = (exceptionType != null) ? "typed" : "bare";
        String aliasInfo = (alias != null) ? " as " + alias : "";
        return String.format("ExceptClause: %s%s (%d statements) (line %d)",
                           typeInfo, aliasInfo, body.size(), lineNumber);
    }
}
