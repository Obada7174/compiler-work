package compiler.ast;

/**
 * AST node representing <style> tag
 */
public class StyleTagNode extends ASTNode {

    private final String cssContent;

    public StyleTagNode(String cssContent, int lineNumber) {
        super(lineNumber, "style");
        this.cssContent = cssContent;
    }

    public String getCssContent() {
        return cssContent;
    }

    @Override
    public String getNodeType() {
        return "StyleTag";
    }

    @Override
    public String getNodeDetails() {
        return String.format("StyleTag (line %d)", lineNumber);
    }
}
