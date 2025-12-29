package compiler.ast.css;

import java.util.ArrayList;
import java.util.List;

 //Contains rule sets and at-rules.
public class CSSStylesheetNode extends CSSASTNode {
    private List<CSSRuleSetNode> ruleSets;
    private List<CSSAtRuleNode> atRules;

    public CSSStylesheetNode(int lineNumber) {
        super(lineNumber, "stylesheet");
        this.ruleSets = new ArrayList<>();
        this.atRules = new ArrayList<>();
    }

    public void addRuleSet(CSSRuleSetNode ruleSet) {
        if (ruleSet != null) {
            this.ruleSets.add(ruleSet);
            addChild(ruleSet);
        }
    }

    public void addAtRule(CSSAtRuleNode atRule) {
        if (atRule != null) {
            this.atRules.add(atRule);
            addChild(atRule);
        }
    }

    public List<CSSRuleSetNode> getRuleSets() {
        return ruleSets;
    }

    public List<CSSAtRuleNode> getAtRules() {
        return atRules;
    }

    @Override
    public String getNodeType() {
        return "CSSStylesheet";
    }

    @Override
    public String getNodeDetails() {
        return String.format("CSSStylesheet { %d rule sets, %d at-rules } (line %d)",
                ruleSets.size(), atRules.size(), getLineNumber());
    }
}
