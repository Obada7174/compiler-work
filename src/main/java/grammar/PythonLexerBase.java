package grammar;

import org.antlr.v4.runtime.*;
import java.util.*;

public abstract class PythonLexerBase extends Lexer {

    // Indentation stack - tracks nesting levels
    private final Stack<Integer> indentStack = new Stack<>();

    // Queue of pending tokens to emit
    private final LinkedList<Token> pendingTokens = new LinkedList<>();

    // Track nesting depth of (), [], {} for implicit line continuation
    private int parenDepth = 0;

    // Line start state
    private boolean atLineStart = true;

    // Last token emitted (to handle NEWLINE logic)
    private Token lastToken = null;

    public PythonLexerBase(CharStream input) {
        super(input);
        indentStack.push(0); // Base indentation level
    }

    @Override
    public void reset() {
        super.reset();
        indentStack.clear();
        indentStack.push(0);
        pendingTokens.clear();
        parenDepth = 0;
        atLineStart = true;
        lastToken = null;
    }

    @Override
    public Token nextToken() {
        // Check if end of file is reached
        if (_input.LA(1) == IntStream.EOF && !pendingTokens.isEmpty()) {
            return pendingTokens.poll();
        }

        // Return pending tokens first
        if (!pendingTokens.isEmpty()) {
            Token token = pendingTokens.poll();
            lastToken = token;
            return token;
        }

        // Get next token from lexer
        Token next = super.nextToken();

        // Handle EOF - close all indentation levels
        if (next.getType() == Token.EOF) {
            // Emit NEWLINE if we're mid-line
            if (lastToken != null &&
                lastToken.getType() != getNewlineTokenType() &&
                lastToken.getType() != getDedentTokenType()) {
                pendingTokens.add(createToken(getNewlineTokenType(), "\n"));
            }

            // Emit DEDENT for each open indentation level
            while (indentStack.size() > 1) {
                indentStack.pop();
                pendingTokens.add(createToken(getDedentTokenType(), ""));
            }

            pendingTokens.add(next);
            return pendingTokens.poll();
        }

        // Track parentheses/brackets/braces for implicit line continuation
        if (isOpeningBracket(next.getType())) {
            parenDepth++;
        } else if (isClosingBracket(next.getType())) {
            parenDepth--;
        }

        lastToken = next;
        return next;
    }

    protected void handleNewline(String indentText) {
        // Skip if inside parentheses/brackets/braces
        if (parenDepth > 0) {
            return;
        }

        // Skip blank lines
        int la = _input.LA(1);
        if (la == '\r' || la == '\n' || la == IntStream.EOF) {
            return;
        }

        // Skip comment-only lines
        if (la == '#') {
            return;
        }

        // Calculate indentation level
        int indent = getIndentationLength(indentText);
        int current = indentStack.peek();

        if (indent > current) {
            // INDENT - push new level
            indentStack.push(indent);
            pendingTokens.add(createToken(getIndentTokenType(), ""));
        } else if (indent < current) {
            // DEDENT - may need multiple tokens
            while (!indentStack.isEmpty() && indent < indentStack.peek()) {
                indentStack.pop();
                pendingTokens.add(createToken(getDedentTokenType(), ""));
            }

            // Verify dedent matches a previous level
            if (indentStack.isEmpty() || indent != indentStack.peek()) {
                throw new IllegalStateException(
                    "IndentationError: unindent does not match any outer indentation level at line " + getLine()
                );
            }
        }
        // If indent == current, no tokens needed

        atLineStart = false;
    }

    private int getIndentationLength(String indent) {
        int length = 0;
        for (char c : indent.toCharArray()) {
            if (c == '\t') {
                // Tab advances to next multiple of 8
                length = (length / 8 + 1) * 8;
            } else if (c == ' ') {
                length++;
            }
        }
        return length;
    }

    private Token createToken(int type, String text) {
        CommonToken token = new CommonToken(_tokenFactorySourcePair, type, DEFAULT_TOKEN_CHANNEL, -1, -1);
        token.setLine(getLine());
        token.setCharPositionInLine(getCharPositionInLine());
        token.setText(text);
        return token;
    }

    protected boolean isInsideParens() {
        return parenDepth > 0;
    }

    protected void incParenDepth() {
        parenDepth++;
    }
    protected void decParenDepth() {
        parenDepth--;
    }

    // Abstract methods - must be implemented by generated lexer
    protected abstract int getNewlineTokenType();
    protected abstract int getIndentTokenType();
    protected abstract int getDedentTokenType();

    // Helper methods for bracket tracking
    private boolean isOpeningBracket(int type) {
        // Override in subclass if needed, or use direct checks
        return false; // Will be handled via incParenDepth() calls
    }

    private boolean isClosingBracket(int type) {
        // Override in subclass if needed, or use direct checks
        return false; // Will be handled via decParenDepth() calls
    }
}
