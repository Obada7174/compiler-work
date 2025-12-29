package compiler.ast.css;


public abstract class CSSExpressionNode extends CSSASTNode {

    public CSSExpressionNode(int lineNumber, String name) {
        super(lineNumber, name);
    }
    public abstract String getExpressionText();
}
