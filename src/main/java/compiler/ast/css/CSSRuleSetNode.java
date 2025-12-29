package compiler.ast.css;

import java.util.ArrayList;
import java.util.List;

 // Structure: selectorList { declarations }

public class CSSRuleSetNode extends CSSASTNode {
    private CSSSelectorListNode selectorList;
    private List<CSSDeclarationNode> declarations;

    public CSSRuleSetNode(int lineNumber) {
        super(lineNumber, "ruleset");
        this.declarations = new ArrayList<>();
    }

    public CSSRuleSetNode(CSSSelectorListNode selectorList, int lineNumber) {
        this(lineNumber);
        setSelectorList(selectorList);
    }

    public void setSelectorList(CSSSelectorListNode selectorList) {
        this.selectorList = selectorList;
        if (selectorList != null) {
            addChild(selectorList);
        }
    }

    public void addDeclaration(CSSDeclarationNode declaration) {
        if (declaration != null) {
            this.declarations.add(declaration);
            addChild(declaration);
        }
    }

    public CSSSelectorListNode getSelectorList() {
        return selectorList;
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
        String selectorText = selectorList != null ? selectorList.toString() : "null";
        return String.format("CSSRuleSet: %s { %d declarations } (line %d)",
                selectorText, declarations.size(), getLineNumber());
    }
}
