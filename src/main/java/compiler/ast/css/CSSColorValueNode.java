package compiler.ast.css;

 // Example: "#fff", "#FF0000", "#123456"

public class CSSColorValueNode extends CSSValueComponentNode {
    private String colorHex;

    public CSSColorValueNode(String colorHex, int lineNumber) {
        super(lineNumber, colorHex);
        this.colorHex = colorHex;
    }

    @Override
    public String getComponentText() {
        return colorHex;
    }

    @Override
    public String getNodeType() {
        return "CSSColorValue";
    }

    @Override
    public String getNodeDetails() {
        return String.format("CSSColorValue: %s (line %d)", colorHex, getLineNumber());
    }
}
