package compiler.ast;

import java.util.List;
import java.util.ArrayList;

/**
 * Statement node representing function definition
 * def name(parameters): ...
 */
public class FunctionDefNode extends StatementNode {
    private String functionName;
    private List<ParameterNode> parameters;
    private List<ASTNode> body;
    private List<DecoratorNode> decorators;
    private ExpressionNode returnType;  // For type hints

    public FunctionDefNode(String functionName, List<ParameterNode> parameters,
                          List<ASTNode> body, List<DecoratorNode> decorators,
                          ExpressionNode returnType, int lineNumber) {
        super(lineNumber);
        this.functionName = functionName;
        this.name = functionName;
        this.parameters = parameters != null ? parameters : new ArrayList<>();
        this.body = body != null ? body : new ArrayList<>();
        this.decorators = decorators != null ? decorators : new ArrayList<>();
        this.returnType = returnType;

        // Add all as children
        for (DecoratorNode decorator : this.decorators) {
            addChild(decorator);
        }
        for (ParameterNode param : this.parameters) {
            addChild(param);
        }
        if (returnType != null) {
            addChild(returnType);
        }
        for (ASTNode node : this.body) {
            addChild(node);
        }
    }

    public String getFunctionName() {
        return functionName;
    }

    public List<ParameterNode> getParameters() {
        return parameters;
    }

    public List<ASTNode> getBody() {
        return body;
    }

    public List<DecoratorNode> getDecorators() {
        return decorators;
    }

    public ExpressionNode getReturnType() {
        return returnType;
    }

    @Override
    public String getNodeType() {
        return "FunctionDef";
    }

    @Override
    public String getNodeDetails() {
        return String.format("FunctionDef: '%s'(%d params, %d statements) (line %d)",
                           functionName, parameters.size(), body.size(), lineNumber);
    }
}
