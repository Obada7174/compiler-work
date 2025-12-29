package compiler.ast.css;

public class CSSParenNode extends CSSValueComponentNode {

    @Override
    public String getComponentText() {
        return "Paren";
    }

    public enum ParenType {
        LEFT("("),
        RIGHT(")");

        private final String symbol;

        ParenType(String symbol) {
            this.symbol = symbol;
        }

        public String getSymbol() {
            return symbol;
        }
    }

    private final ParenType type;

    public CSSParenNode(ParenType type, int lineNumber) {
        super(lineNumber, type.getSymbol());
        this.type = type;
    }

    public ParenType getType() {
        return type;
    }

    @Override
    public String getNodeType() {
        return "CSSParen";
    }
}
