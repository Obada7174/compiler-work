package compiler.ast;

import compiler.ast.ASTNode;
import compiler.ast.ExpressionNode;
public class DictItemNode extends ExpressionNode {
    private ExpressionNode key;
    private ExpressionNode value;
    public DictItemNode(ExpressionNode key, ExpressionNode value, int lineNumber) {
        super(lineNumber);
        this.key = key; this.value = value;
        addChild(key);
        addChild(value);
    }

    @Override
    public String getNodeType() {
        return "Dict item";
    }
}
