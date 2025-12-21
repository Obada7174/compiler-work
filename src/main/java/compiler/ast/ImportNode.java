package compiler.ast;

import java.util.List;

public class ImportNode extends ASTNode {
    private final List<String> names;
    private final List<String> aliases;

    public ImportNode(List<String> names, List<String> aliases, int line) {
        super(line, "import");
        this.names = names;
        this.aliases = aliases;
    }

    public List<String> getNames() {
        return names;
    }

    public List<String> getAliases() {
        return aliases;
    }

    @Override
    public String getNodeType() {
        return "Import";
    }
}
