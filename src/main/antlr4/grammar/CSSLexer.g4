lexer grammar CSSLexer;
@header {
package grammar;
}

HASH: '#';
DOT: '.';
STAR: '*';

CSS_VAR: '--' [a-zA-Z0-9_-]+;

// Keywords
AND: 'and';
FROM: 'from';
TO: 'to';

TILDE_EQUALS: '~=';    // Class selector: [class~="value"]
PIPE_EQUALS: '|=';     // Language selector: [lang|="en"]
CARET_EQUALS: '^=';    // Starts with: [href^="https://"]
DOLLAR_EQUALS: '$=';   // Ends with: [href$=".pdf"]
STAR_EQUALS: '*=';     // Contains: [href*="example"]

IDENTIFIER: [a-zA-Z_][a-zA-Z0-9_-]*;

PSEUDO_CLASS: ':' [a-zA-Z-]+;
PSEUDO_ELEMENT: '::' [a-zA-Z-]+;

GT: '>';
PLUS: '+';
TILDE: '~';

LBRACE: '{';
RBRACE: '}';
LPAREN: '(';
RPAREN: ')';
LBRACKET: '[';
RBRACKET: ']';
COLON: ':';
SEMICOLON: ';';
COMMA: ',';
EQUALS: '=';

// Operators
MULTIPLY: STAR;
PLUS_OP: PLUS;
MINUS_OP: '-';
DIVIDE: '/';

NUMBER: '-'? DIGIT+ ('.' DIGIT+)?;

fragment DIGIT: [0-9];

PERCENTAGE: '-'? DIGIT+ ('.' DIGIT+)? '%';

// Units (% removed - now handled by PERCENTAGE token)
UNIT:
    'px' | 'em' | 'rem' | 'vh' | 'vw' | 'vmin' | 'vmax'
    | 'pt' | 'pc' | 'in' | 'cm' | 'mm' | 'ex' | 'ch'
    | 'deg' | 'rad' | 'grad' | 'turn'
    | 's' | 'ms'
    | 'fr'
    ;

COLOR_HEX: '#' [0-9a-fA-F]+;

STRING:
    '\'' (~['\r\n\\] | '\\' .)* '\''
    | '"' (~["\r\n\\] | '\\' .)* '"'
    ;

IMPORTANT: '!important';

URL: 'url(' ( ESCAPED_CHAR | ~[)\\] )* ')';

fragment ESCAPED_CHAR: '\\' .;

AT_KEYWORD: '@' [a-zA-Z-]+;

WS: [ \t\r\n]+ -> skip;
COMMENT: '/*' .*? '*/' -> skip;
