package compiler.ast.python;

import compiler.ast.core.ExpressionNode;

import java.util.Map;
import java.util.LinkedHashMap;

public class DictionaryLiteralNode extends ExpressionNode {
    private Map<ExpressionNode, ExpressionNode> entries;

    public DictionaryLiteralNode(Map<ExpressionNode, ExpressionNode> entries, int lineNumber) {
        super(lineNumber);
        this.entries = entries != null ? entries : new LinkedHashMap<>();

        // Add all keys and values as children
        for (Map.Entry<ExpressionNode, ExpressionNode> entry : this.entries.entrySet()) {
            addChild(entry.getKey());
            addChild(entry.getValue());
        }
    }

    public Map<ExpressionNode, ExpressionNode> getEntries() {
        return entries;
    }

    @Override
    public String getNodeType() {
        return "DictionaryLiteral";
    }

    @Override
    public String getNodeDetails() {
        return String.format("DictionaryLiteral: %d entries (line %d)", entries.size(), lineNumber);
    }
}
