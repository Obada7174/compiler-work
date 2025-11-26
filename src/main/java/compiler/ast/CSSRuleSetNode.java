package compiler.ast;

import java.util.List;
import java.util.ArrayList;

/**
 * AST node representing CSS rule set: selector { declarations }
 */
public class CSSRuleSetNode extends ASTNode {
    private String selector;
    private List<CSSDeclarationNode> declarations;

    public CSSRuleSetNode(String selector, List<CSSDeclarationNode> declarations, int lineNumber) {
        super(lineNumber);
        this.selector = selector;
        this.declarations = declarations != null ? declarations : new ArrayList<>();

        for (CSSDeclarationNode decl : this.declarations) {
            addChild(decl);
        }
    }

    public String getSelector() {
        return selector;
    }

    public List<CSSDeclarationNode> getDeclarations() {
        return declarations;
    }

    @Override
    public String getNodeType() {
        return "CSSRuleSet";
    }

    @Override
    public String getNodeDetails() {
        return String.format("CSSRuleSet: %s { %d declarations } (line %d)",
            selector, declarations.size(), lineNumber);
    }
}
