package compiler.ast.css;

 // Example: ":hover", ":active", ":nth-child"

public class CSSPseudoClassSelectorNode extends CSSSelectorPartNode {
    private String pseudoClassName;

    public CSSPseudoClassSelectorNode(String pseudoClassName, int lineNumber) {
        super(lineNumber, pseudoClassName);
        this.pseudoClassName = pseudoClassName;
    }

    public String getPseudoClassName() {
        return pseudoClassName;
    }

    @Override
    public String getSelectorPartText() {
        return ":" + pseudoClassName;
    }

    @Override
    public String getNodeType() {
        return "CSSPseudoClassSelector";
    }

    @Override
    public String getNodeDetails() {
        return String.format("CSSPseudoClassSelector: :%s (line %d)",
                pseudoClassName, getLineNumber());
    }
}
