package compiler.ast.css;

import java.util.ArrayList;
import java.util.List;


public class CSSAtRuleNode extends CSSASTNode {
    private String keyword;
    private CSSAtRulePreludeNode prelude;
    private List<CSSRuleSetNode> ruleSets;
    private boolean hasBlock;

    public CSSAtRuleNode(String keyword, int lineNumber) {
        super(lineNumber, keyword);
        this.keyword = keyword;
        this.ruleSets = new ArrayList<>();
        this.hasBlock = false;
    }

    public void setPrelude(CSSAtRulePreludeNode prelude) {
        this.prelude = prelude;
        if (prelude != null) {
            addChild(prelude);
        }
    }

    public void addRuleSet(CSSRuleSetNode ruleSet) {
        if (ruleSet != null) {
            this.ruleSets.add(ruleSet);
            addChild(ruleSet);
            this.hasBlock = true;
        }
    }

    public void setHasBlock(boolean hasBlock) {
        this.hasBlock = hasBlock;
    }

    public String getKeyword() {
        return keyword;
    }

    public CSSAtRulePreludeNode getPrelude() {
        return prelude;
    }

    public List<CSSRuleSetNode> getRuleSets() {
        return ruleSets;
    }

    public boolean hasBlock() {
        return hasBlock;
    }

    @Override
    public String getNodeType() {
        return "CSSAtRule";
    }

    @Override
    public String getNodeDetails() {
        if (hasBlock) {
            return String.format("CSSAtRule: @%s { %d rule sets } (line %d)",
                    keyword, ruleSets.size(), getLineNumber());
        } else {
            return String.format("CSSAtRule: @%s; (line %d)", keyword, getLineNumber());
        }
    }
}
