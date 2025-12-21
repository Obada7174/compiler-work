package compiler.ast;

/**
 * AST node representing Jinja2 for loop
 */
public class JinjaForNode extends ASTNode {

    public JinjaForNode(String targetName, int lineNumber) {
        super(lineNumber, targetName);
    }

    @Override
    public String getNodeType() {
        return "JinjaFor";
    }

    /*
     * name        -> loop target (e.g. item)
     * children:
     * [0] iterable expression
     * [1..n] loop body
     */
}
