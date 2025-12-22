package compiler.ast;

import java.util.List;
import java.util.ArrayList;

/**
 * Expression node representing lambda expression
 * lambda params: expression
 */
public class LambdaNode extends ExpressionNode {
    private List<ParameterNode> parameters;
    private ExpressionNode body;

    public LambdaNode(List<ParameterNode> parameters, ExpressionNode body, int lineNumber) {
        super(lineNumber);
        this.parameters = parameters != null ? parameters : new ArrayList<>();
        this.body = body;

        for (ParameterNode param : this.parameters) {
            addChild(param);
        }
        addChild(body);
    }

    public List<ParameterNode> getParameters() {
        return parameters;
    }

    public ExpressionNode getBody() {
        return body;
    }

    @Override
    public String getNodeType() {
        return "Lambda";
    }

    @Override
    public String getNodeDetails() {
        return String.format("Lambda: %d params (line %d)", parameters.size(), lineNumber);
    }
}
