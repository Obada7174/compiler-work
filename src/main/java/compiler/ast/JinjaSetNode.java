package compiler.ast;

/**
 * AST node representing {% set var = expr %}
 */
public class JinjaSetNode extends ASTNode {

    public JinjaSetNode(String variableName, int lineNumber) {
        super(lineNumber, variableName);
    }

    @Override
    public String getNodeType() {
        return "JinjaSet";
    }

}
