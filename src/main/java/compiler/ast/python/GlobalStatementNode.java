package compiler.ast.python;

import java.util.ArrayList;
import java.util.List;


public class GlobalStatementNode extends StatementNode {
    private final List<String> names;

    public GlobalStatementNode(List<String> names, int lineNumber) {
        super(lineNumber, "GlobalStatement");
        this.names = names != null ? names : new ArrayList<>();
    }

    public List<String> getNames() {
        return names;
    }

    @Override
    public String getNodeType() {
        return "GlobalStatement";
    }
}
