package compiler.ast;


public class JinjaSetNode extends ASTNode {

    public JinjaSetNode(String variableName, int lineNumber) {
        super(lineNumber, variableName);
    }

    @Override
    public String getNodeType() {
        return "JinjaSet";
    }

}
