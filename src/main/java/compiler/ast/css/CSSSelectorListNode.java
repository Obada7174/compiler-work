package compiler.ast.css;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


 // Example: "div, .class, #id"

public class CSSSelectorListNode extends CSSASTNode {
    private List<CSSSelectorNode> selectors;

    public CSSSelectorListNode(int lineNumber) {
        super(lineNumber, "selector-list");
        this.selectors = new ArrayList<>();
    }

    public void addSelector(CSSSelectorNode selector) {
        if (selector != null) {
            this.selectors.add(selector);
            addChild(selector);
        }
    }

    public List<CSSSelectorNode> getSelectors() {
        return selectors;
    }

    @Override
    public String getNodeType() {
        return "CSSSelectorList";
    }

    @Override
    public String getNodeDetails() {
        String selectorText = selectors.stream()
                .map(CSSSelectorNode::getSelectorText)
                .collect(Collectors.joining(", "));
        return String.format("CSSSelectorList: %s (line %d)", selectorText, getLineNumber());
    }

    @Override
    public String toString() {
        return selectors.stream()
                .map(CSSSelectorNode::getSelectorText)
                .collect(Collectors.joining(", "));
    }
}
