package compiler.ast;

/**
 * AST node representing {% include "file.html" %}
 */
public class JinjaIncludeNode extends ASTNode {

    public JinjaIncludeNode(String templateName, int lineNumber) {
        super(lineNumber, templateName);
    }

    @Override
    public String getNodeType() {
        return "JinjaInclude";
    }
}
