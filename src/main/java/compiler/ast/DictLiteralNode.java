package compiler.ast;

import compiler.ast.DictItemNode;
import compiler.ast.ExpressionNode;

import java.util.List;

public class DictLiteralNode extends ExpressionNode {
    private List<DictItemNode> items;

    public DictLiteralNode(List<DictItemNode> items, int line) {
        super(line);
        this.items = items;
        for (DictItemNode item : items) addChild(item);
    }

    public List<DictItemNode> getItems() { return items; }

    @Override
    public String getNodeType() { return "DictLiteral"; }
}