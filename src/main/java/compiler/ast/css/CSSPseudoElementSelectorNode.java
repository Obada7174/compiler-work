package compiler.ast.css;


 // Example: "::before", "::after", "::first-line"

public class CSSPseudoElementSelectorNode extends CSSSelectorPartNode {
    private String pseudoElementName;

    public CSSPseudoElementSelectorNode(String pseudoElementName, int lineNumber) {
        super(lineNumber, pseudoElementName);
        this.pseudoElementName = pseudoElementName;
    }

    public String getPseudoElementName() {
        return pseudoElementName;
    }

    @Override
    public String getSelectorPartText() {
        return "::" + pseudoElementName;
    }

    @Override
    public String getNodeType() {
        return "CSSPseudoElementSelector";
    }

    @Override
    public String getNodeDetails() {
        return String.format("CSSPseudoElementSelector: ::%s (line %d)",
                pseudoElementName, getLineNumber());
    }
}
