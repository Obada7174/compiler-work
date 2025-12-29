package compiler.ast.css;

 // Example: "*"

public class CSSUniversalSelectorNode extends CSSSelectorPartNode {

    public CSSUniversalSelectorNode(int lineNumber) {
        super(lineNumber, "*");
    }

    @Override
    public String getSelectorPartText() {
        return "*";
    }

    @Override
    public String getNodeType() {
        return "CSSUniversalSelector";
    }

    @Override
    public String getNodeDetails() {
        return String.format("CSSUniversalSelector: * (line %d)", getLineNumber());
    }
}
