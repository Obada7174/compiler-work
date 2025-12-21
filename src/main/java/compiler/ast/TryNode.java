package compiler.ast;

import java.util.List;


public class TryNode extends StatementNode {
    private ASTNode body;
    private List<ExceptNode> exceptBranches;
    private FinallyNode finallyNode;

    public TryNode(ASTNode body, List<ExceptNode> exceptBranches, FinallyNode finallyNode, int lineNumber) {
        super(lineNumber, "Try");
        this.body = body;
        this.exceptBranches = exceptBranches;
        this.finallyNode = finallyNode;
        addChild(body);
        for (ExceptNode ex : exceptBranches) addChild(ex);
        if (finallyNode != null) addChild(finallyNode);
    }

    @Override
    public String getNodeType() {
        return "Try";
    }
}