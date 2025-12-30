lexer grammar PythonLexer;

@header {
package grammar;
}

options {
    superClass = PythonLexerBase;
}

tokens { INDENT, DEDENT }

@members {
    // Implement abstract methods from PythonLexerBase
    @Override
    protected int getNewlineTokenType() {
        return NEWLINE;
    }

    @Override
    protected int getIndentTokenType() {
        return INDENT;
    }

    @Override
    protected int getDedentTokenType() {
        return DEDENT;
    }
}

// ─── COMMENTS ──────────────────────────────
COMMENT : '#' ~[\r\n]* -> channel(HIDDEN);

// ─── NEWLINE + INDENT/DEDENT ──────────────
NEWLINE
    : ('\r'? '\n' | '\r') [ \t]* {
        // Skip NEWLINE inside parens/brackets/braces
        if (isInsideParens()) {
            skip();
            return;
        }

        // Skip blank lines and comment-only lines
        int la = _input.LA(1);
        if (la == '\r' || la == '\n' || la == '#' || la == -1) {
            skip();
            return;
        }

        // Extract indentation part (everything after newline chars)
        String text = getText();
        int nlEnd = 0;

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c == '\r' || c == '\n') {
                nlEnd = i + 1;
                if (c == '\r' && i + 1 < text.length() && text.charAt(i + 1) == '\n') {
                    nlEnd++;
                }
            } else {
                break;
            }
        }

        String indentText = text.substring(nlEnd);

        // Emit NEWLINE token FIRST
        setType(NEWLINE);

        // Then handle indentation (queues INDENT/DEDENT)
        handleNewline(indentText);
    }
    ;

// ─── LINE CONTINUATION ─────────────────────
LINE_CONTINUATION : '\\' ('\r'? '\n' | '\r') -> skip;

// ─── WHITESPACE ────────────────────────────
WS : [ \t]+ -> skip;

// ─── KEYWORDS ──────────────────────────────
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
MATCH: 'match';
CASE: 'case';
AND: 'and';
OR: 'or';
NOT: 'not';
IN: 'in';
IS: 'is';
GLOBAL: 'global';
NONLOCAL: 'nonlocal';
LAMBDA: 'lambda';
YIELD: 'yield';
RAISE: 'raise';
ASSERT: 'assert';
DEL: 'del';
BREAK: 'break';
CONTINUE: 'continue';
ASYNC: 'async';
AWAIT: 'await';
NONE: 'None';
TRUE: 'True';
FALSE: 'False';

// ─── DELIMITERS ────────────────────────────
LPAREN: '(' {incParenDepth();};
RPAREN: ')' {decParenDepth();};
LBRACK: '[' {incParenDepth();};
RBRACK: ']' {decParenDepth();};
LBRACE: '{' {incParenDepth();};
RBRACE: '}' {decParenDepth();};

COLON: ':'; COMMA: ','; DOT: '.'; ASSIGN: '='; PLUS: '+'; MINUS: '-'; STAR: '*'; DIV: '/';
MOD: '%'; POW: '**'; FLOORDIV: '//'; EQ: '=='; NE: '!='; LE: '<='; GE: '>='; LT: '<'; GT: '>'; AT: '@'; SEMI: ';'; PIPE: '|';

// ─── LITERALS ──────────────────────────────
NUMBER : [0-9]+ ('.' [0-9]+)?;
STRING
    : '\'\'\'' .*? '\'\'\''
    | '"""' .*? '"""'
    | '\'' (~['\\\r\n] | '\\' .)* '\''
    | '"' (~["\\\r\n] | '\\' .)* '"'
    ;
FSTRING
    : 'f"' (~["\\\r\n] | '\\' . | '{' .*? '}')* '"'
    | 'f\'' (~['\\\r\n] | '\\' . | '{' .*? '}')* '\''
    ;
NAME : [a-zA-Z_] [a-zA-Z_0-9]*;
