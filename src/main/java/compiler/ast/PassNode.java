package compiler.ast;

public class PassNode extends StatementNode {
    public PassNode(int lineNumber) {
        super(lineNumber, "Pass");
    }

    @Override
    public String getNodeType() {
        return "Pass";
    }
}
