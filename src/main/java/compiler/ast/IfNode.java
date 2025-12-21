package compiler.ast;

import java.util.ArrayList;
import java.util.List;
public class IfNode extends StatementNode {
    private ExpressionNode condition;
    private BlockNode thenBlock;
    private BlockNode elseBlock;
    private List<IfNode> elifBranches;

    public IfNode(int lineNumber, ExpressionNode condition, BlockNode thenBlock, BlockNode elseBlock, List<IfNode> elifBranches) {
        super(lineNumber, "If");
        this.condition = condition;
        this.thenBlock = thenBlock;
        this.elseBlock = elseBlock;
        this.elifBranches = elifBranches != null ? elifBranches : new ArrayList<>();

        addChild(condition);
        addChild(thenBlock);
        if (elseBlock != null) addChild(elseBlock);
        for (IfNode elif : this.elifBranches) addChild(elif);
    }

    @Override
    public String getNodeType() {
        return "If";
    }
}