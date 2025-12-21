package compiler.ast;

/**
 * AST node representing {% extends "base.html" %}
 */
public class JinjaExtendsNode extends ASTNode {

    public JinjaExtendsNode(String templateName, int lineNumber) {
        super(lineNumber, templateName);
    }

    @Override
    public String getNodeType() {
        return "JinjaExtends";
    }
}
