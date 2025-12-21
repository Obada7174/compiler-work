package compiler.ast;

import java.util.ArrayList;
import java.util.List;

public class FunctionNode extends StatementNode {
    private String functionName;
    private List<IdentifierNode> parameters;

    public FunctionNode(String functionName, int lineNumber) {
        super(lineNumber, "FunctionDef");
        this.functionName = functionName;
        this.parameters = new ArrayList<>();
    }
    public void setBody(BlockNode body) {
        addChild(body);
    }
    public void addParameter(IdentifierNode param) {
        parameters.add(param);
        addChild(param);
    }
    @Override
    public String getNodeType() {
        return "FunctionDef";
    }
}
