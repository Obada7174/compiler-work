package compiler.symboltable.css;

import java.util.ArrayList;
import java.util.List;

public class CSSSymbolTableEntry {
    private String name;
    private CSSSymbolType type;
    private String scope;           // "global", "media-query", selector path
    private int lineNumber;
    private String value;           // For variables: the CSS value; for others: may be null
    private List<String> properties; // Properties used within this symbol (for selectors)
    private String fullSelector;    // Full selector text (for compound selectors)
    private CSSSymbolTableEntry parent; // Parent scope (for nested symbols)
    private List<CSSSymbolTableEntry> children; // Nested symbols

    public CSSSymbolTableEntry(String name, CSSSymbolType type, String scope, int lineNumber) {
        this.name = name;
        this.type = type;
        this.scope = scope;
        this.lineNumber = lineNumber;
        this.value = null;
        this.properties = new ArrayList<>();
        this.fullSelector = null;
        this.parent = null;
        this.children = new ArrayList<>();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CSSSymbolType getType() {
        return type;
    }

    public void setType(CSSSymbolType type) {
        this.type = type;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<String> getProperties() {
        return properties;
    }

    public void addProperty(String property) {
        if (!this.properties.contains(property)) {
            this.properties.add(property);
        }
    }

    public void setFullSelector(String fullSelector) {
        this.fullSelector = fullSelector;
    }

    public CSSSymbolTableEntry getParent() {
        return parent;
    }

    public void setParent(CSSSymbolTableEntry parent) {
        this.parent = parent;
    }

    public List<CSSSymbolTableEntry> getChildren() {
        return children;
    }

    public void addChild(CSSSymbolTableEntry child) {
        this.children.add(child);
        child.setParent(this);
    }


    public String toJSON(int indent) {
        String ind = "  ".repeat(indent);
        String ind2 = "  ".repeat(indent + 1);
        StringBuilder sb = new StringBuilder();

        sb.append(ind).append("{\n");
        sb.append(ind2).append("\"name\": \"").append(escapedName()).append("\",\n");
        sb.append(ind2).append("\"type\": \"").append(type).append("\",\n");
        sb.append(ind2).append("\"scope\": \"").append(scope).append("\",\n");
        sb.append(ind2).append("\"line\": ").append(lineNumber);

        if (value != null && !value.isEmpty()) {
            sb.append(",\n").append(ind2).append("\"value\": \"").append(escapeJSON(value)).append("\"");
        }

        if (fullSelector != null && !fullSelector.isEmpty()) {
            sb.append(",\n").append(ind2).append("\"fullSelector\": \"").append(escapeJSON(fullSelector)).append("\"");
        }

        if (!properties.isEmpty()) {
            sb.append(",\n").append(ind2).append("\"properties\": [");
            for (int i = 0; i < properties.size(); i++) {
                sb.append("\"").append(properties.get(i)).append("\"");
                if (i < properties.size() - 1) sb.append(", ");
            }
            sb.append("]");
        }

        if (!children.isEmpty()) {
            sb.append(",\n").append(ind2).append("\"children\": [\n");
            for (int i = 0; i < children.size(); i++) {
                sb.append(children.get(i).toJSON(indent + 2));
                if (i < children.size() - 1) sb.append(",");
                sb.append("\n");
            }
            sb.append(ind2).append("]");
        }

        sb.append("\n").append(ind).append("}");
        return sb.toString();
    }

    private String escapedName() {
        return escapeJSON(name);
    }

    private String escapeJSON(String str) {
        if (str == null) return "";
        return str.replace("\\", "\\\\")
                  .replace("\"", "\\\"")
                  .replace("\n", "\\n")
                  .replace("\r", "\\r")
                  .replace("\t", "\\t");
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-20s | %-18s | %-15s | Line %-4d",
                name, type, scope, lineNumber));
        if (value != null) {
            sb.append(" | Value: ").append(value);
        }
        return sb.toString();
    }
}
