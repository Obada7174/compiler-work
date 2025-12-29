package compiler.ast.css;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

 // Example: "rgb(255, 0, 0)", "calc(100% - 20px)", "linear-gradient(...)"

public class CSSFunctionCallNode extends CSSValueComponentNode {
    private String functionName;
    private List<CSSExpressionNode> arguments;

    public CSSFunctionCallNode(String functionName, int lineNumber) {
        super(lineNumber, functionName);
        this.functionName = functionName;
        this.arguments = new ArrayList<>();
    }

    public void addArgument(CSSExpressionNode argument) {
        if (argument != null) {
            this.arguments.add(argument);
            addChild(argument);
        }
    }

    public String getFunctionName() {
        return functionName;
    }

    public List<CSSExpressionNode> getArguments() {
        return arguments;
    }

    @Override
    public String getComponentText() {
        String argsText = arguments.stream()
                .map(CSSExpressionNode::getExpressionText)
                .collect(Collectors.joining(", "));
        return String.format("%s(%s)", functionName, argsText);
    }

    @Override
    public String getNodeType() {
        return "CSSFunctionCall";
    }

    @Override
    public String getNodeDetails() {
        return String.format("CSSFunctionCall: %s (line %d)", getComponentText(), getLineNumber());
    }
}
