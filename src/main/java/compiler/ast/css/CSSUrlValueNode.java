package compiler.ast.css;

//Example: "url('image.png')", "url(https://example.com/bg.jpg)"

public class CSSUrlValueNode extends CSSValueComponentNode {
    private String url;

    public CSSUrlValueNode(String url, int lineNumber) {
        super(lineNumber, url);
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String getComponentText() {
        return url;
    }

    @Override
    public String getNodeType() {
        return "CSSUrlValue";
    }

    @Override
    public String getNodeDetails() {
        return String.format("CSSUrlValue: %s (line %d)", url, getLineNumber());
    }
}
