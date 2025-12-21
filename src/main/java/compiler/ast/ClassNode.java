package compiler.ast;

import compiler.ast.ASTNode;
import compiler.ast.StatementNode;

import java.util.List;

public class ClassNode extends StatementNode {
    private String className;
    private List<String> baseClasses;

    public ClassNode(String className, List<String> baseClasses, ASTNode body, int lineNumber) {
        super(lineNumber, className);
        this.className = className;
        this.baseClasses = baseClasses;
        addChild(body);
    }

    @Override
    public String getNodeType() {
        return "ClassDef";
    }
}
