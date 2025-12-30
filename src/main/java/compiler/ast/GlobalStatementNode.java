package compiler.ast;

import java.util.ArrayList;
import java.util.List;

/**
 * AST node representing a Python global statement.
 * Syntax: global name1, name2, ...
 */
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
