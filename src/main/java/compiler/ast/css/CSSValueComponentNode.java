package compiler.ast.css;


public abstract class CSSValueComponentNode extends CSSASTNode {

    public CSSValueComponentNode(int lineNumber, String name) {
        super(lineNumber, name);
    }

    public abstract String getComponentText();
}
