package compiler.ast.python;

import java.util.List;
import java.util.ArrayList;

/**
 * Statement node representing import statement
 * import module [as alias] or from module import name [as alias]
 */
public class ImportStatementNode extends StatementNode {
    private String moduleName;
    private List<String> importedNames;
    private List<String> aliases;
    private boolean isFromImport;

    public ImportStatementNode(String moduleName, List<String> importedNames,
                              List<String> aliases, boolean isFromImport, int lineNumber) {
        super(lineNumber,"Import Statement");
        this.moduleName = moduleName;
        this.importedNames = importedNames != null ? importedNames : new ArrayList<>();
        this.aliases = aliases != null ? aliases : new ArrayList<>();
        this.isFromImport = isFromImport;
    }

    public String getModuleName() {
        return moduleName;
    }

    public List<String> getImportedNames() {
        return importedNames;
    }

    public List<String> getAliases() {
        return aliases;
    }

    public boolean isFromImport() {
        return isFromImport;
    }

    @Override
    public String getNodeType() {
        return "Import";
    }

    @Override
    public String getNodeDetails() {
        if (isFromImport) {
            return String.format("Import: from %s import %d name(s) (line %d)",
                               moduleName, importedNames.size(), lineNumber);
        } else {
            return String.format("Import: %s (line %d)", moduleName, lineNumber);
        }
    }
}
