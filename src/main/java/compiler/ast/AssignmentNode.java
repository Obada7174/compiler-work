package compiler.ast;

import java.util.List;
import java.util.ArrayList;

/**
 * Statement node representing variable assignment
 * target = value or target1, target2 = value1, value2
 */
public class AssignmentNode extends StatementNode {
    private List<ExpressionNode> targets;
    private ExpressionNode value;

    public AssignmentNode(List<ExpressionNode> targets, ExpressionNode value, int lineNumber) {
        super(lineNumber,"Assignment");
        this.targets = targets != null ? targets : new ArrayList<>();
        this.value = value;

        // Add targets and value as children
        for (ExpressionNode target : this.targets) {
            addChild(target);
        }
        addChild(value);
    }

    public List<ExpressionNode> getTargets() {
        return targets;
    }

    public ExpressionNode getValue() {
        return value;
    }

    @Override
    public String getNodeType() {
        return "Assignment";
    }

    @Override
    public String getNodeDetails() {
        return String.format("Assignment: %d target(s) (line %d)", targets.size(), lineNumber);
    }
}
