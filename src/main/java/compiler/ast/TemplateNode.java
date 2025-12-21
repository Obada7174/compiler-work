package compiler.ast;

public class TemplateNode extends ASTNode {

    public TemplateNode(int line) {
        super(line, "template");
    }

    @Override
    public String getNodeType() {
        return "Template";
    }
}
