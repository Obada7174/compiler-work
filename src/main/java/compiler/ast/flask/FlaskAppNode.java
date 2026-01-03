package compiler.ast.flask;

import compiler.ast.core.ExpressionNode;
import compiler.ast.python.StatementNode;


public class FlaskAppNode extends StatementNode {
    private final String appVariableName;
    private final String moduleName;
    private final ExpressionNode configExpression;

    public FlaskAppNode(String appVariableName, String moduleName,
                        ExpressionNode configExpression, int lineNumber) {
        super(lineNumber, "FlaskApp");
        this.appVariableName = appVariableName;
        this.moduleName = moduleName;
        this.configExpression = configExpression;
        this.name = appVariableName;

        if (configExpression != null) {
            addChild(configExpression);
        }
    }

    public String getAppVariableName() {
        return appVariableName;
    }

    public String getModuleName() {
        return moduleName;
    }

    public ExpressionNode getConfigExpression() {
        return configExpression;
    }

    @Override
    public String getNodeType() {
        return "FlaskApp";
    }

    @Override
    public String getNodeDetails() {
        return String.format("FlaskApp: %s = Flask(%s) (line %d)",
                           appVariableName, moduleName, lineNumber);
    }
}
