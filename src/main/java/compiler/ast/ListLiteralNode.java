package compiler.ast;

import java.util.List;

/**
 * Expression node representing list literals [1, 2, 3]
 */
public class ListLiteralNode extends ExpressionNode {
    public ListLiteralNode(int lineNumber) { super(lineNumber); }

    @Override
    public String getNodeType() {
        return "ListLiteral";
    }
}
