package compiler.ast;

/**
 * AST node representing plain text content in HTML
 */
public class HTMLTextNode extends ASTNode {
    private String text;

    public HTMLTextNode(String text, int lineNumber) {
        super(lineNumber);
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public String getNodeType() {
        return "HTMLText";
    }

    @Override
    public String getNodeDetails() {
        String preview = text.length() > 30 ? text.substring(0, 27) + "..." : text;
        return String.format("HTMLText: \"%s\" (line %d)", preview.trim(), lineNumber);
    }
}
