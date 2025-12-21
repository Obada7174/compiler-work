package compiler.ast;

public class AssignmentNode extends ASTNode {
    private ExpressionNode target;
    private ExpressionNode value;

    public AssignmentNode(ExpressionNode target, ExpressionNode value, int lineNumber) {
        super(lineNumber);
        this.target = target;
        this.value = value;

        addChild(target);
        addChild(value);
    }

    public ExpressionNode getTarget() {
        return target;
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
        return String.format("Assignment: %s = ... (line %d)", target.getNodeDetails(), lineNumber);
    }
}
