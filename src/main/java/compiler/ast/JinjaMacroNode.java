package compiler.ast;


public class JinjaMacroNode extends ASTNode {

    public JinjaMacroNode(String macroName, int lineNumber) {
        super(lineNumber, macroName);
    }

    @Override
    public String getNodeType() {
        return "JinjaMacro";
    }

    /*
     * name     -> macro name
     * children -> macro body
     */
}
