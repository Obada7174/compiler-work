package compiler.ast;

import compiler.ast.DictItemNode;
import compiler.ast.ExpressionNode;

import java.util.List;

public class DictLiteralNode extends ExpressionNode {
    public DictLiteralNode(int lineNumber) { super(lineNumber); }

    @Override
    public String getNodeType() {
        return "Dict Literal";
    }
}