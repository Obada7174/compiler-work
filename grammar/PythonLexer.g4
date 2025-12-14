lexer grammar PythonLexer;

@header {
package grammar;
}

@members {
    private java.util.Stack<Integer> indents = new java.util.Stack<>();
    private java.util.LinkedList<Token> tokens = new java.util.LinkedList<>();

    @Override
    public Token nextToken() {
        if (!tokens.isEmpty()) return tokens.poll();

        Token t = super.nextToken();
        if (t.getType() == EOF) {
            // Close all remaining indents
            while (!indents.isEmpty()) {
                tokens.add(createIndentToken(DEDENT));
                indents.pop();
            }
            tokens.add(t);
            return tokens.poll();
        }
        return t;
    }

    private Token createIndentToken(int type) {
        return new CommonToken(_tokenFactorySourcePair, type, DEFAULT_TOKEN_CHANNEL, -1, -1);
    }

    private void handleIndent(int indent) {
        int prev = indents.isEmpty() ? 0 : indents.peek();
        if (indent > prev) {
            indents.push(indent);
            tokens.add(createIndentToken(INDENT));
        } else if (indent < prev) {
            while (!indents.isEmpty() && indent < indents.peek()) {
                indents.pop();
                tokens.add(createIndentToken(DEDENT));
            }
        }
    }
}

// ─── COMMENTS ───
COMMENT : '#' ~[\r\n]* -> channel(HIDDEN);

// ─── WHITESPACE + INDENT/DEDENT ───
WS
    : [ \t\r\n]+ {
        String text = getText();
        int pos = 0;
        while (pos < text.length()) {
            char c = text.charAt(pos);
            if (c == '\n' || c == '\r') {
                // Normalize \r\n to \n
                if (c == '\r' && pos + 1 < text.length() && text.charAt(pos + 1) == '\n') {
                    pos += 2;
                } else {
                    pos++;
                }
                // Count indentation on next line
                int indent = 0;
                int temp = pos;
                while (temp < text.length()) {
                    char ch = text.charAt(temp);
                    if (ch == ' ') { indent++; temp++; }
                    else if (ch == '\t') { indent += 8; temp++; } // tab=8
                    else break;
                }
                handleIndent(indent);
            } else { pos++; }
        }
    } -> skip;

// ─── KEYWORDS ───
DEF: 'def';
CLASS: 'class';
IF: 'if';
ELSE: 'else';
ELIF: 'elif';
FOR: 'for';
WHILE: 'while';
RETURN: 'return';
PASS: 'pass';
IMPORT: 'import';
FROM: 'from';
AS: 'as';
TRY: 'try';
EXCEPT: 'except';
FINALLY: 'finally';
WITH: 'with';
AND: 'and';
OR: 'or';
NOT: 'not';
IN: 'in';
IS: 'is';
NONE: 'None';
TRUE: 'True';
FALSE: 'False';

// ─── SYMBOLS ───
LPAREN: '(';
RPAREN: ')';
LBRACK: '[';
RBRACK: ']';
LBRACE: '{';
RBRACE: '}';
COLON: ':';
COMMA: ',';
DOT: '.';
ASSIGN: '=';
PLUS: '+';
MINUS: '-';
STAR: '*';
DIV: '/';
MOD: '%';
POW: '**';
FLOORDIV: '//';
EQ: '==';
NE: '!=';
LE: '<=';
GE: '>=';
LT: '<';
GT: '>';
AT: '@';
SEMI: ';';

// ─── LITERALS ───
NUMBER: [0-9]+ ('.' [0-9]+)?;
STRING
    : '\'' (~['\\\r\n] | '\\' .)* '\''
    | '"' (~["\\\r\n] | '\\' .)* '"'
    ;
FSTRING
    : 'f"' (~["\\\r\n] | '\\' . | '{' .*? '}')* '"'
    | 'f\'' (~['\\\r\n] | '\\' . | '{' .*? '}')* '\''
    ;

// ─── IDENTIFIERS ───
NAME: [a-zA-Z_] [a-zA-Z_0-9]*;

// ─── INDENTATION TOKENS ───
INDENT: '<INDENT>';
DEDENT: '<DEDENT>';
