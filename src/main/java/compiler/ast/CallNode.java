package compiler.ast;


public class CallNode extends ExpressionNode {
    private ExpressionNode function;         // The function being called
    private java.util.List<ExpressionNode> arguments; // List of arguments

    public CallNode(ExpressionNode function, java.util.List<ExpressionNode> arguments, int lineNumber) {
        super(lineNumber);
        this.function = function;
        this.arguments = arguments;

        // Add the function as a child
        addChild(function);

        // Add all arguments as children
        if (arguments != null) {
            for (ExpressionNode arg : arguments) {
                addChild(arg);
            }
        }
    }

    public ExpressionNode getFunction() {
        return function;
    }

    public java.util.List<ExpressionNode> getArguments() {
        return arguments;
    }

    @Override
    public String getNodeType() {
        return "Call";
    }

    @Override
    public String getNodeDetails() {
        return String.format("Call (line %d, args=%d)", lineNumber, arguments != null ? arguments.size() : 0);
    }
}
