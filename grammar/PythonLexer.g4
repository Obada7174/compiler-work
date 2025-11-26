lexer grammar PythonLexer;

// ═══════════════════════════════════════════════════════════════════════════
// Python Lexer for Flask Applications
// Handles proper Python indentation with INDENT/DEDENT tokens
// ═══════════════════════════════════════════════════════════════════════════

@lexer::header {
import java.util.*;
}

@lexer::members {
    // Stack to track indentation levels
    private final Deque<Integer> indentStack = new ArrayDeque<>();
    private final Queue<Token> pendingTokens = new LinkedList<>();
    private int lastIndent = 0;

    {
        indentStack.push(0); // Initial indentation level
    }

    @Override
    public Token nextToken() {
        // Return any pending tokens first
        if (!pendingTokens.isEmpty()) {
            return pendingTokens.poll();
        }

        Token token = super.nextToken();

        // Handle NEWLINE + indentation
        if (token.getType() == NEWLINE) {
            // Calculate indentation on next line
            Token next = super.nextToken();
            int indent = 0;

            while (next.getType() == WS_CHAR) {
                String ws = next.getText();
                for (char c : ws.toCharArray()) {
                    indent += (c == '\t') ? 4 : 1;
                }
                next = super.nextToken();
            }

            // Skip blank lines and comments
            if (next.getType() == NEWLINE || next.getType() == COMMENT) {
                pendingTokens.offer(token);
                pendingTokens.offer(next);
                return nextToken();
            }

            // Generate INDENT/DEDENT tokens
            int currentIndent = indentStack.peek();
            if (indent > currentIndent) {
                indentStack.push(indent);
                pendingTokens.offer(token);
                CommonToken indentToken = new CommonToken(INDENT, "<INDENT>");
                indentToken.setLine(next.getLine());
                indentToken.setCharPositionInLine(0);
                pendingTokens.offer(indentToken);
                pendingTokens.offer(next);
                return nextToken();
            } else if (indent < currentIndent) {
                pendingTokens.offer(token);
                while (!indentStack.isEmpty() && indentStack.peek() > indent) {
                    indentStack.pop();
                    CommonToken dedentToken = new CommonToken(DEDENT, "<DEDENT>");
                    dedentToken.setLine(next.getLine());
                    dedentToken.setCharPositionInLine(0);
                    pendingTokens.offer(dedentToken);
                }
                pendingTokens.offer(next);
                return nextToken();
            } else {
                pendingTokens.offer(token);
                pendingTokens.offer(next);
                return nextToken();
            }
        }

        return token;
    }
}

// ═══════════════════════════════════════════════════════════════════════════
// KEYWORDS
// ═══════════════════════════════════════════════════════════════════════════

DEF: 'def';
CLASS: 'class';
IF: 'if';
ELIF: 'elif';
ELSE: 'else';
FOR: 'for';
WHILE: 'while';
RETURN: 'return';
IMPORT: 'import';
FROM: 'from';
AS: 'as';
IN: 'in';
AND: 'and';
OR: 'or';
NOT: 'not';
TRUE: 'True';
FALSE: 'False';
NONE: 'None';
PASS: 'pass';
BREAK: 'break';
CONTINUE: 'continue';
LAMBDA: 'lambda';
WITH: 'with';
TRY: 'try';
EXCEPT: 'except';
FINALLY: 'finally';
RAISE: 'raise';
YIELD: 'yield';
GLOBAL: 'global';
NONLOCAL: 'nonlocal';
ASSERT: 'assert';
DEL: 'del';
IS: 'is';

// ═══════════════════════════════════════════════════════════════════════════
// OPERATORS
// ═══════════════════════════════════════════════════════════════════════════

AT: '@';
PLUS: '+';
MINUS: '-';
STAR: '*';
SLASH: '/';
DOUBLE_SLASH: '//';
PERCENT: '%';
POWER: '**';
ASSIGN: '=';
PLUS_ASSIGN: '+=';
MINUS_ASSIGN: '-=';
STAR_ASSIGN: '*=';
SLASH_ASSIGN: '/=';
EQ: '==';
NEQ: '!=';
LT: '<';
GT: '>';
LTE: '<=';
GTE: '>=';
AMPERSAND: '&';
PIPE: '|';
CARET: '^';
TILDE: '~';
LEFT_SHIFT: '<<';
RIGHT_SHIFT: '>>';

// ═══════════════════════════════════════════════════════════════════════════
// DELIMITERS
// ═══════════════════════════════════════════════════════════════════════════

LPAREN: '(';
RPAREN: ')';
LBRACKET: '[';
RBRACKET: ']';
LBRACE: '{';
RBRACE: '}';
COMMA: ',';
COLON: ':';
SEMICOLON: ';';
DOT: '.';
ARROW: '->';
ELLIPSIS: '...';

// ═══════════════════════════════════════════════════════════════════════════
// LITERALS
// ═══════════════════════════════════════════════════════════════════════════

NUMBER:
    [0-9]+ ('.' [0-9]+)? ([eE] [+-]? [0-9]+)?  // Float or integer with optional exponent
    | '0x' [0-9a-fA-F]+                         // Hexadecimal
    | '0o' [0-7]+                                // Octal
    | '0b' [01]+                                 // Binary
    ;

STRING:
    '\'' (~['\r\n\\] | '\\' .)* '\''           // Single-quoted string
    | '"' (~["\r\n\\] | '\\' .)* '"'           // Double-quoted string
    | '\'\'\'' .*? '\'\'\''                     // Triple single-quoted string
    | '"""' .*? '"""'                           // Triple double-quoted string
    | 'r\'' (~['\r\n])* '\''                    // Raw string (single)
    | 'r"' (~["\r\n])* '"'                      // Raw string (double)
    | 'f\'' (~['\r\n\\] | '\\' .)* '\''        // F-string (single)
    | 'f"' (~["\r\n\\] | '\\' .)* '"'          // F-string (double)
    ;

IDENTIFIER: [a-zA-Z_][a-zA-Z0-9_]*;

// ═══════════════════════════════════════════════════════════════════════════
// INDENTATION AND WHITESPACE
// ═══════════════════════════════════════════════════════════════════════════

NEWLINE: '\r'? '\n';
WS_CHAR: [ \t]+ -> channel(HIDDEN);
INDENT: '<INDENT>' -> channel(HIDDEN);  // Generated programmatically
DEDENT: '<DEDENT>' -> channel(HIDDEN);  // Generated programmatically

// ═══════════════════════════════════════════════════════════════════════════
// COMMENTS
// ═══════════════════════════════════════════════════════════════════════════

COMMENT: '#' ~[\r\n]* -> skip;
