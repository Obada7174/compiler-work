package compiler.ast;

import java.util.List;
import java.util.ArrayList;


public class CSSRuleSetNode extends ASTNode {
    private List<CSSDeclarationNode> declarations;

    public CSSRuleSetNode(String selector, List<CSSDeclarationNode> declarations, int lineNumber) {
        super(lineNumber, selector); // ← name = selector
        this.declarations = declarations != null ? declarations : new ArrayList<>();

        for (CSSDeclarationNode decl : this.declarations) {
            addChild(decl);
        }
    }

    // نستخدم getName() من الكلاس الأب
    public String getSelector() {
        return getName();
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
                getName(), declarations.size(), getLineNumber());
    }
}