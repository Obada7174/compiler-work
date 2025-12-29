package compiler.ast.css;

 // Example: "div", "p", "span"

public class CSSElementSelectorNode extends CSSSelectorPartNode {
    private String elementName;

    public CSSElementSelectorNode(String elementName, int lineNumber) {
        super(lineNumber, elementName);
        this.elementName = elementName;
    }

    public String getElementName() {
        return elementName;
    }

    @Override
    public String getSelectorPartText() {
        return elementName;
    }

    @Override
    public String getNodeType() {
        return "CSSElementSelector";
    }

    @Override
    public String getNodeDetails() {
        return String.format("CSSElementSelector: %s (line %d)", elementName, getLineNumber());
    }
}
