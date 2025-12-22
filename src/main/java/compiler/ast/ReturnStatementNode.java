package compiler.ast;

/**
 * Statement node representing return statement
 * return [expression]
 */
public class ReturnStatementNode extends StatementNode {
    private ExpressionNode value;

    public ReturnStatementNode(ExpressionNode value, int lineNumber) {
        super(lineNumber);
        this.value = value;
        if (value != null) {
            addChild(value);
        }
    }

    public ExpressionNode getValue() {
        return value;
    }

    @Override
    public String getNodeType() {
        return "Return";
    }

    @Override
    public String getNodeDetails() {
        String valueInfo = (value != null) ? "with value" : "void";
        return String.format("Return: %s (line %d)", valueInfo, lineNumber);
    }
}
