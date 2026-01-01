package compiler.ast;



public abstract class ExpressionNode extends ASTNode {
    public ExpressionNode(int lineNumber, String name) { super(lineNumber, name); }
    public ExpressionNode(int lineNumber) { super(lineNumber); }
}



