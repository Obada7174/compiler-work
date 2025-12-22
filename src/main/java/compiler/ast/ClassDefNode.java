package compiler.ast;

import java.util.List;
import java.util.ArrayList;

/**
 * Statement node representing class definition
 * class ClassName(bases): ...
 */
public class ClassDefNode extends StatementNode {
    private String className;
    private List<ExpressionNode> baseClasses;
    private List<ASTNode> body;
    private List<DecoratorNode> decorators;

    public ClassDefNode(String className, List<ExpressionNode> baseClasses,
                       List<ASTNode> body, List<DecoratorNode> decorators, int lineNumber) {
        super(lineNumber);
        this.className = className;
        this.name = className;
        this.baseClasses = baseClasses != null ? baseClasses : new ArrayList<>();
        this.body = body != null ? body : new ArrayList<>();
        this.decorators = decorators != null ? decorators : new ArrayList<>();

        // Add all as children
        for (DecoratorNode decorator : this.decorators) {
            addChild(decorator);
        }
        for (ExpressionNode base : this.baseClasses) {
            addChild(base);
        }
        for (ASTNode node : this.body) {
            addChild(node);
        }
    }

    public String getClassName() {
        return className;
    }

    public List<ExpressionNode> getBaseClasses() {
        return baseClasses;
    }

    public List<ASTNode> getBody() {
        return body;
    }

    public List<DecoratorNode> getDecorators() {
        return decorators;
    }

    @Override
    public String getNodeType() {
        return "ClassDef";
    }

    @Override
    public String getNodeDetails() {
        String bases = baseClasses.isEmpty() ? "" : String.format(", %d base(s)", baseClasses.size());
        return String.format("ClassDef: '%s'(%d members%s) (line %d)",
                           className, body.size(), bases, lineNumber);
    }
}
