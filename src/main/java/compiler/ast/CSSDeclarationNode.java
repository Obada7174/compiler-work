package compiler.ast;

/**
 * AST node representing CSS declaration: property: value
 */
public class CSSDeclarationNode extends ASTNode {
    private String property;
    private String value;
    private boolean important;

    public CSSDeclarationNode(String property, String value, boolean important, int lineNumber) {
        super(lineNumber);
        this.property = property;
        this.value = value;
        this.important = important;
    }

    public String getProperty() {
        return property;
    }

    public String getValue() {
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
        return String.format("CSSDeclaration: %s: %s%s (line %d)",
            property, value, importantFlag, lineNumber);
    }
}
