package compiler.ast.css;

 //Example: "#idName"

public class CSSIdSelectorNode extends CSSSelectorPartNode {
    private String idName;

    public CSSIdSelectorNode(String idName, int lineNumber) {
        super(lineNumber, idName);
        this.idName = idName;
    }

    public String getIdName() {
        return idName;
    }

    @Override
    public String getSelectorPartText() {
        return "#" + idName;
    }

    @Override
    public String getNodeType() {
        return "CSSIdSelector";
    }

    @Override
    public String getNodeDetails() {
        return String.format("CSSIdSelector: #%s (line %d)", idName, getLineNumber());
    }
}
