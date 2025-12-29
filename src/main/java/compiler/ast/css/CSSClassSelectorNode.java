package compiler.ast.css;


 // Example: ".className"

public class CSSClassSelectorNode extends CSSSelectorPartNode {
    private String className;

    public CSSClassSelectorNode(String className, int lineNumber) {
        super(lineNumber, className);
        this.className = className;
    }

    public String getClassName() {
        return className;
    }

    @Override
    public String getSelectorPartText() {
        return "." + className;
    }

    @Override
    public String getNodeType() {
        return "CSSClassSelector";
    }

    @Override
    public String getNodeDetails() {
        return String.format("CSSClassSelector: .%s (line %d)", className, getLineNumber());
    }
}
