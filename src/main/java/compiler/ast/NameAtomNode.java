package compiler.ast;

public class NameAtomNode extends AtomNode {
    public final String name;

    public NameAtomNode(int line, String name) {
        super(line);
        this.name = name;
    }

    @Override
    public String getNodeType() {
        return null;
    }
}
