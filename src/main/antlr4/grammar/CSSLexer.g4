lexer grammar CSSLexer;
@header {
package grammar;
}
HASH: '#';
DOT: '.';
STAR: '*';
CSS_VAR: '--' [a-zA-Z0-9_-]+;
AND: 'and';

IDENTIFIER: [a-zA-Z_-][a-zA-Z0-9_-]*;

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
MULTIPLY: STAR;
PLUS_OP: PLUS;
MINUS_OP: '-';
DIVIDE: '/';

NUMBER: [0-9]+ ('.' [0-9]+)?;


UNIT:
    'px' | 'em' | 'rem' | '%' | 'vh' | 'vw' | 'vmin' | 'vmax'
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
URL
    : 'url(' ( ESCAPED_CHAR | ~[)\\] )* ')'
    ;

fragment ESCAPED_CHAR
    : '\\' .
    ;

AT_KEYWORD: '@' [a-zA-Z-]+;


WS: [ \t\r\n]+ -> skip;

COMMENT: '/*' .*? '*/' -> skip;