package compiler.ast.css;

/**
 * AST node representing an operator in CSS values.
 * Operators: +, -, *, /, , (comma)
 */
public class CSSOperatorNode extends CSSValueComponentNode {
    public enum OperatorType {
        PLUS("+"),
        MINUS("-"),
        MULTIPLY("*"),
        DIVIDE("/"),
        COMMA(",");

        private final String symbol;

        OperatorType(String symbol) {
            this.symbol = symbol;
        }

        public String getSymbol() {
            return symbol;
        }
    }

    private OperatorType operatorType;

    public CSSOperatorNode(OperatorType operatorType, int lineNumber) {
        super(lineNumber, operatorType.getSymbol());
        this.operatorType = operatorType;
    }

    public OperatorType getOperatorType() {
        return operatorType;
    }

    public String getOperatorSymbol() {
        return operatorType.getSymbol();
    }

    @Override
    public String getComponentText() {
        return operatorType.getSymbol();
    }

    @Override
    public String getNodeType() {
        return "CSSOperator";
    }

    @Override
    public String getNodeDetails() {
        return String.format("CSSOperator: '%s' (line %d)",
                operatorType.getSymbol(), getLineNumber());
    }
}
