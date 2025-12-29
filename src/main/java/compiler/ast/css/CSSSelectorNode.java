package compiler.ast.css;

import java.util.ArrayList;
import java.util.List;

 //Example: "div > .class + p"

public class CSSSelectorNode extends CSSASTNode {
    private List<CSSSimpleSelectorNode> simpleSelectors;
    private List<CSSCombinatorNode> combinators;

    public CSSSelectorNode(int lineNumber) {
        super(lineNumber, "selector");
        this.simpleSelectors = new ArrayList<>();
        this.combinators = new ArrayList<>();
    }

    public void addSimpleSelector(CSSSimpleSelectorNode simpleSelector) {
        if (simpleSelector != null) {
            this.simpleSelectors.add(simpleSelector);
            addChild(simpleSelector);
        }
    }

    public void addCombinator(CSSCombinatorNode combinator) {
        if (combinator != null) {
            this.combinators.add(combinator);
            addChild(combinator);
        }
    }

    public List<CSSSimpleSelectorNode> getSimpleSelectors() {
        return simpleSelectors;
    }

    public List<CSSCombinatorNode> getCombinators() {
        return combinators;
    }

    public String getSelectorText() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < simpleSelectors.size(); i++) {
            sb.append(simpleSelectors.get(i).getSelectorText());
            if (i < combinators.size()) {
                sb.append(" ").append(combinators.get(i).getCombinatorSymbol()).append(" ");
            }
        }
        return sb.toString();
    }

    @Override
    public String getNodeType() {
        return "CSSSelector";
    }

    @Override
    public String getNodeDetails() {
        return String.format("CSSSelector: %s (line %d)", getSelectorText(), getLineNumber());
    }
}
