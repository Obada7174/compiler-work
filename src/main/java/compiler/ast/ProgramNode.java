package compiler.ast;

/**
 * Node representing the entire program
 */
public class ProgramNode extends ASTNode {
    public ProgramNode(int lineNumber) {
        super(lineNumber);
    }

    @Override
    public String getNodeType() {
        return "Program";
    }

    @Override
    public String getNodeDetails() {
        return String.format("Program (line %d, %d children)", lineNumber, children.size());
    }
}
