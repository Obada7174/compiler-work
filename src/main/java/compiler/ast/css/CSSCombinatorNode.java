package compiler.ast.css;


 // Combinators: descendant (space), child (>), adjacent sibling (+), general sibling (~)

public class CSSCombinatorNode extends CSSASTNode {
    public enum CombinatorType {
        DESCENDANT(" "),     // space
        CHILD(">"),          // >
        ADJACENT_SIBLING("+"), // +
        GENERAL_SIBLING("~");  // ~


        private final String symbol;

        CombinatorType(String symbol) {
            this.symbol = symbol;
        }

        public String getSymbol() {
            return symbol;
        }
    }

    private CombinatorType combinatorType;

    public CSSCombinatorNode(CombinatorType combinatorType, int lineNumber) {
        super(lineNumber, combinatorType.getSymbol());
        this.combinatorType = combinatorType;
    }

    public CombinatorType getCombinatorType() {
        return combinatorType;
    }

    public String getCombinatorSymbol() {
        return combinatorType.getSymbol();
    }

    @Override
    public String getNodeType() {
        return "CSSCombinator";
    }

    @Override
    public String getNodeDetails() {
        return String.format("CSSCombinator: '%s' (line %d)",
                combinatorType.getSymbol(), getLineNumber());
    }
}
