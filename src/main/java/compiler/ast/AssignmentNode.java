package compiler.ast;

public class AssignmentNode extends StatementNode {
    private IdentifierNode target;
    private ExpressionNode value;

    public AssignmentNode(IdentifierNode target, ExpressionNode value, int lineNumber) {
        super(lineNumber, "assignment");
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
