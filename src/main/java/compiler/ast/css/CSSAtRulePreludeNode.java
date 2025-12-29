package compiler.ast.css;

import java.util.ArrayList;
import java.util.List;

 // Example: in "@media screen and (max-width: 600px)", the prelude is "screen and (max-width: 600px)"

public class CSSAtRulePreludeNode extends CSSASTNode {
    private List<String> tokens;

    public CSSAtRulePreludeNode(int lineNumber) {
        super(lineNumber, "at-rule-prelude");
        this.tokens = new ArrayList<>();
    }

    public void addToken(String token) {
        if (token != null) {
            this.tokens.add(token);
        }
    }

    public List<String> getTokens() {
        return tokens;
    }

    public String getPreludeText() {
        return String.join(" ", tokens);
    }

    @Override
    public String getNodeType() {
        return "CSSAtRulePrelude";
    }

    @Override
    public String getNodeDetails() {
        return String.format("CSSAtRulePrelude: %s (line %d)",
                getPreludeText(), getLineNumber());
    }
}
