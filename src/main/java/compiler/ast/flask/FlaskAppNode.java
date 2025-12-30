package compiler.ast.flask;

import compiler.ast.*;

/**
 * AST node representing Flask application instantiation.
 *
 * Pattern: app = Flask(__name__)
 *
 * This node captures:
 * - The variable name (e.g., "app")
 * - The module name passed to Flask constructor (typically "__name__")
 * - Configuration options if present
 */
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
