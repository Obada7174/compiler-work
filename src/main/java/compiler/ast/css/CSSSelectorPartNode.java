package compiler.ast.css;

//  Selector parts include: element, class, id, universal, pseudo-class, pseudo-element, attribute.

public abstract class CSSSelectorPartNode extends CSSASTNode {

    public CSSSelectorPartNode(int lineNumber, String name) {
        super(lineNumber, name);
    }


    //  Example: ".class", "#id", "div", ":hover", etc.

    public abstract String getSelectorPartText();
}
