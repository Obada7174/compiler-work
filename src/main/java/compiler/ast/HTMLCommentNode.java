package compiler.ast;


public class HTMLCommentNode extends ASTNode {

    private final String comment;

    public HTMLCommentNode(String comment, int lineNumber) {
        super(lineNumber, "html_comment");
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    @Override
    public String getNodeType() {
        return "HTMLComment";
    }

    @Override
    public String getNodeDetails() {
        String preview = comment.length() > 30
                ? comment.substring(0, 27) + "..."
                : comment;
        return String.format("HTMLComment: <!-- %s --> (line %d)", preview.trim(), lineNumber);
    }
}
