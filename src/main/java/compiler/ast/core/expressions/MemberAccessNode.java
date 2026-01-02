package compiler.ast.core.expressions;


import compiler.ast.core.ExpressionNode;

public class MemberAccessNode extends ExpressionNode {
    private ExpressionNode object;
    private String memberName;

    public MemberAccessNode(ExpressionNode object, String memberName, int lineNumber) {
        super(lineNumber);
        this.object = object;
        this.memberName = memberName;
        addChild(object);
    }

    public ExpressionNode getObject() {
        return object;
    }

    public String getMemberName() {
        return memberName;
    }

    @Override
    public String getNodeType() {
        return "MemberAccess";
    }

    @Override
    public String getNodeDetails() {
        return String.format("MemberAccess: .%s (line %d)", memberName, lineNumber);
    }
}
