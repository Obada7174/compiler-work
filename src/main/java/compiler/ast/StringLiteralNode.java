package compiler.ast;

/**
 * Expression node representing string literals
 */
public class StringLiteralNode extends ExpressionNode {
    private String value;

    public StringLiteralNode(String value, int lineNumber) {
        super(lineNumber);
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String getNodeType() {
        return "StringLiteral";
    }

    @Override
    public String getNodeDetails() {
        return String.format("StringLiteral: \"%s\" (line %d)", value, lineNumber);
    }
}
