package compiler.ast;

import java.util.ArrayList;
import java.util.List;

public class FunctionCallNode extends ASTNode {
    private ExpressionNode function;
    private List<ExpressionNode> args;

    public FunctionCallNode(ExpressionNode function, List<ExpressionNode> args, int lineNumber) {
        super(lineNumber);
        this.function = function;
        this.args = args != null ? args : new ArrayList<>();

        addChild(function);
        for (ExpressionNode arg : this.args) addChild(arg);
    }

    public ExpressionNode getFunction() { return function; }
    public List<ExpressionNode> getArgs() { return args; }

    @Override
    public String getNodeType() { return "FunctionCall"; }

    @Override
    public String getNodeDetails() {
        return String.format("Call: %s(%d args) (line %d)", function.getName(), args.size(), lineNumber);
    }
}
