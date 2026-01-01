package compiler.ast;

public class KeywordArgNode extends ExpressionNode {
    private final String key;
    private final ExpressionNode value;

    public KeywordArgNode(String key, ExpressionNode value, int lineNumber) {
        super(lineNumber);
        this.key = key;
        this.value = value;
        addChild(value);
    }

    @Override
    public String getNodeType() {
        return "KeywordArg";
    }

    public String getKey() {
        return key;
    }

    public ExpressionNode getValue() {
        return value;
    }

    @Override
    public String getNodeDetails() {
        return String.format("KeywordArg: %s (line %d)", key, lineNumber);
    }
}
