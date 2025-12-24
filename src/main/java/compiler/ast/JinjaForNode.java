package compiler.ast;

import java.util.List;
import java.util.ArrayList;

public class JinjaForNode extends ASTNode {

    private String targetName;
    private ExpressionNode iterable;
    private List<ASTNode> body;

    public JinjaForNode(String targetName, ExpressionNode iterable, int lineNumber) {
        super(lineNumber, targetName);
        this.targetName = targetName;
        this.iterable = iterable;
        this.body = new ArrayList<>();
    }

    public String getTargetName() {
        return targetName;
    }

    public ExpressionNode getIterable() {
        return iterable;
    }

    public void setBody(List<ASTNode> body) {
        this.body = body;
        for (ASTNode child : body) {
            this.addChild(child); // إذا تريد إبقاء hierarchy عامة
        }
    }

    public List<ASTNode> getBody() {
        return body;
    }

    @Override
    public String getNodeType() {
        return "JinjaFor";
    }
}
