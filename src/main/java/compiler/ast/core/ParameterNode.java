package compiler.ast.core;


public class ParameterNode extends ASTNode {
    private String parameterName;
    private ExpressionNode typeAnnotation;
    private ExpressionNode defaultValue;

    public ParameterNode(String parameterName, ExpressionNode typeAnnotation,
                        ExpressionNode defaultValue, int lineNumber) {
        super(lineNumber);
        this.parameterName = parameterName;
        this.name = parameterName;
        this.typeAnnotation = typeAnnotation;
        this.defaultValue = defaultValue;

        if (typeAnnotation != null) {
            addChild(typeAnnotation);
        }
        if (defaultValue != null) {
            addChild(defaultValue);
        }
    }

    public String getParameterName() {
        return parameterName;
    }

    public ExpressionNode getTypeAnnotation() {
        return typeAnnotation;
    }

    public ExpressionNode getDefaultValue() {
        return defaultValue;
    }

    @Override
    public String getNodeType() {
        return "Parameter";
    }

    @Override
    public String getNodeDetails() {
        String typeInfo = (typeAnnotation != null) ? ": typed" : "";
        String defaultInfo = (defaultValue != null) ? " = default" : "";
        return String.format("Parameter: '%s'%s%s (line %d)",
                           parameterName, typeInfo, defaultInfo, lineNumber);
    }
}
