package compiler.ast.css;

 // Example: "color: red", "margin: 10px !important"

public class CSSDeclarationNode extends CSSASTNode {
    private String property;
    private CSSValueNode value;
    private boolean important;

    public CSSDeclarationNode(String property, int lineNumber) {
        super(lineNumber, property);
        this.property = property;
        this.important = false;
    }

    public CSSDeclarationNode(String property, CSSValueNode value, boolean important, int lineNumber) {
        this(property, lineNumber);
        setValue(value);
        this.important = important;
    }

    public void setValue(CSSValueNode value) {
        this.value = value;
        if (value != null) {
            addChild(value);
        }
    }

    public void setImportant(boolean important) {
        this.important = important;
    }

    public String getProperty() {
        return property;
    }

    public CSSValueNode getValue() {
        return value;
    }

    public boolean isImportant() {
        return important;
    }

    @Override
    public String getNodeType() {
        return "CSSDeclaration";
    }

    @Override
    public String getNodeDetails() {
        String importantFlag = important ? " !important" : "";
        String valueText = value != null ? value.getValueText() : "null";
        return String.format("CSSDeclaration: %s: %s%s (line %d)",
                property, valueText, importantFlag, getLineNumber());
    }
}
