package compiler.ast;

import compiler.ast.ASTNode;
import compiler.ast.ExpressionNode;

public class DictItemNode extends ASTNode {
    private ExpressionNode key;
    private ExpressionNode value;

    public DictItemNode(ExpressionNode key, ExpressionNode value, int line) {
        super(line);
        this.key = key;
        this.value = value;
        addChild(key);
        addChild(value);
    }

    public ExpressionNode getKey() { return key; }
    public ExpressionNode getValue() { return value; }

    @Override
    public String getNodeType() { return "DictItem"; }
}
