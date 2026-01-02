package compiler.ast.core;

import java.util.List;
import java.util.ArrayList;


public class DecoratorNode extends ASTNode {
    private ExpressionNode decoratorExpr;
    private List<ExpressionNode> arguments;

    public DecoratorNode(ExpressionNode decoratorExpr, List<ExpressionNode> arguments, int lineNumber) {
        super(lineNumber);
        this.decoratorExpr = decoratorExpr;
        this.arguments = arguments != null ? arguments : new ArrayList<>();

        addChild(decoratorExpr);
        for (ExpressionNode arg : this.arguments) {
            addChild(arg);
        }
    }

    public ExpressionNode getDecoratorExpr() {
        return decoratorExpr;
    }

    public List<ExpressionNode> getArguments() {
        return arguments;
    }

    @Override
    public String getNodeType() {
        return "Decorator";
    }

    @Override
    public String getNodeDetails() {
        String argsInfo = arguments.isEmpty() ? "" : String.format("(%d args)", arguments.size());
        return String.format("Decorator: @...%s (line %d)", argsInfo, lineNumber);
    }
}
