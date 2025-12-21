package compiler.ast;

/**
 * AST node representing Jinja2 if statement
 */
public class JinjaIfNode extends ASTNode {

    public JinjaIfNode(int lineNumber) {
        super(lineNumber, "if");
    }

    @Override
    public String getNodeType() {
        return "JinjaIf";
    }

}
