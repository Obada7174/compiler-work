package compiler.ast;

import java.util.Map;
import java.util.HashMap;

public class HTMLElementNode extends ASTNode {
    private String tagName;
    private Map<String, String> attributes;
    private boolean selfClosing;

    public HTMLElementNode(String tagName, Map<String, String> attributes, int lineNumber) {
        super(lineNumber);
        this.tagName = tagName;
        this.attributes = attributes != null ? attributes : new HashMap<>();
        this.selfClosing = false;
    }

    public HTMLElementNode(String tagName, Map<String, String> attributes, boolean selfClosing, int lineNumber) {
        super(lineNumber);
        this.tagName = tagName;
        this.attributes = attributes != null ? attributes : new HashMap<>();
        this.selfClosing = selfClosing;
    }

    public String getTagName() {
        return tagName;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public boolean isSelfClosing() {
        return selfClosing;
    }

    @Override
    public String getNodeType() {
        return "HTMLElement";
    }

    @Override
    public String getNodeDetails() {
        String attrInfo = attributes.isEmpty() ? "" : String.format(", %d attributes", attributes.size());
        return String.format("HTMLElement: <%s%s> (line %d)", tagName, attrInfo, lineNumber);
    }
}
