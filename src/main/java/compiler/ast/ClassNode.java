package compiler.ast;

import java.util.ArrayList;
import java.util.List;

public class ClassNode extends ASTNode {
    private List<ASTNode> body;
    private String name;

    public ClassNode(String name, int lineNumber) {
        super(lineNumber, name);
        this.name = name;
        this.body = new ArrayList<>();
    }

    @Override
    public String getNodeType() {
        return "Class";
    }

    public void setBody(List<ASTNode> body) {
        this.body = body;
        for (ASTNode child : body) {
            addChild(child);
        }
    }

    public List<ASTNode> getBody() {
        return body;
    }
}
