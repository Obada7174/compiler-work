package compiler.ast.css;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

 //Example: "10px solid red" contains three value components

public class CSSValueNode extends CSSASTNode {
    private List<CSSValueComponentNode> components;

    public CSSValueNode(int lineNumber) {
        super(lineNumber, "value");
        this.components = new ArrayList<>();
    }

    public void addComponent(CSSValueComponentNode component) {
        if (component != null) {
            this.components.add(component);
            addChild(component);
        }
    }

    public List<CSSValueComponentNode> getComponents() {
        return components;
    }

    public String getValueText() {
        return components.stream()
                .map(CSSValueComponentNode::getComponentText)
                .collect(Collectors.joining(" "));
    }

    @Override
    public String getNodeType() {
        return "CSSValue";
    }

    @Override
    public String getNodeDetails() {
        return String.format("CSSValue: %s (line %d)", getValueText(), getLineNumber());
    }
}
