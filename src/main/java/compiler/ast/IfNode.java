package compiler.ast;

import java.util.List;
import java.util.ArrayList;

public class IfNode extends ASTNode {
    private ExpressionNode condition;
    private List<ASTNode> thenBlock;
    private List<ASTNode> elseBlock;

    // Constructor مطابقة للـ visitor
    public IfNode(ExpressionNode condition, List<ASTNode> thenBlock, List<ASTNode> elseBlock, int lineNumber) {
        super(lineNumber);
        this.condition = condition;
        this.thenBlock = thenBlock != null ? thenBlock : new ArrayList<>();
        this.elseBlock = elseBlock != null ? elseBlock : new ArrayList<>();

        addChild(condition);
        for (ASTNode node : this.thenBlock) addChild(node);
        for (ASTNode node : this.elseBlock) addChild(node);
    }


    public void setElseBlock(List<ASTNode> elseBlock) {
        this.elseBlock = elseBlock != null ? elseBlock : new ArrayList<>();
        for (ASTNode node : this.elseBlock) addChild(node);
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
        return "If";
    }

    @Override
    public String getNodeDetails() {
        String elseInfo = elseBlock.isEmpty() ? "" : String.format(" + %d else", elseBlock.size());
        return String.format("If: (%d then%s) (line %d)", thenBlock.size(), elseInfo, lineNumber);
    }


}
