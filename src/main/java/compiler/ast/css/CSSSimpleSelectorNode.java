package compiler.ast.css;

import java.util.ArrayList;
import java.util.List;

 // Example: "div.class#id:hover"

public class CSSSimpleSelectorNode extends CSSASTNode {
    private List<CSSSelectorPartNode> selectorParts;

    public CSSSimpleSelectorNode(int lineNumber) {
        super(lineNumber, "simple-selector");
        this.selectorParts = new ArrayList<>();
    }

    public void addSelectorPart(CSSSelectorPartNode selectorPart) {
        if (selectorPart != null) {
            this.selectorParts.add(selectorPart);
            addChild(selectorPart);
        }
    }

    public List<CSSSelectorPartNode> getSelectorParts() {
        return selectorParts;
    }

    public String getSelectorText() {
        StringBuilder sb = new StringBuilder();
        for (CSSSelectorPartNode part : selectorParts) {
            sb.append(part.getSelectorPartText());
        }
        return sb.toString();
    }

    @Override
    public String getNodeType() {
        return "CSSSimpleSelector";
    }

    @Override
    public String getNodeDetails() {
        return String.format("CSSSimpleSelector: %s (line %d)", getSelectorText(), getLineNumber());
    }
}
