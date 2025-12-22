package compiler.ast;

import java.util.List;
import java.util.ArrayList;

/**
 * Expression node representing function calls func(arg1, arg2, ...)
 * Used by both Python and Jinja2
 */
public class FunctionCallNode extends ExpressionNode {
    private ExpressionNode function;
    private List<ExpressionNode> arguments;

    public FunctionCallNode(ExpressionNode function, List<ExpressionNode> arguments, int lineNumber) {
        super(lineNumber);
        this.function = function;
        this.arguments = arguments != null ? arguments : new ArrayList<>();

        // Add function and all arguments as children
        addChild(function);
        for (ExpressionNode arg : this.arguments) {
            addChild(arg);
        }
    }

    public ExpressionNode getFunction() {
        return function;
    }

    public List<ExpressionNode> getArguments() {
        return arguments;
    }

    @Override
    public String getNodeType() {
        return "FunctionCall";
    }

    @Override
    public String getNodeDetails() {
        String funcName = "...";
        if (function instanceof IdentifierNode) {
            funcName = ((IdentifierNode) function).getName();
        }
        return String.format("FunctionCall: %s(%d args) (line %d)", funcName, arguments.size(), lineNumber);
    }
}
