lexer grammar CSSLexer;

@header {
package grammar;
}

HASH: '#';
DOT: '.';
STAR: '*';

PSEUDO_CLASS: ':' [a-zA-Z-]+;
PSEUDO_ELEMENT: '::' [a-zA-Z-]+;

GT: '>';           // Child combinator
PLUS: '+';         // Adjacent sibling combinator
TILDE: '~';        // General sibling combinator


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

NUMBER: [0-9]+ ('.' [0-9]+)?;

DIMENSION: NUMBER [a-zA-Z%]+;

// Color values
COLOR_HEX
    : '#' [0-9a-fA-F][0-9a-fA-F][0-9a-fA-F]
    | '#' [0-9a-fA-F][0-9a-fA-F][0-9a-fA-F][0-9a-fA-F]
    | '#' [0-9a-fA-F][0-9a-fA-F][0-9a-fA-F][0-9a-fA-F][0-9a-fA-F][0-9a-fA-F]
    | '#' [0-9a-fA-F][0-9a-fA-F][0-9a-fA-F][0-9a-fA-F][0-9a-fA-F][0-9a-fA-F][0-9a-fA-F][0-9a-fA-F]
    ;
// Strings
STRING:
    '\'' (~['\r\n\\] | '\\' .)* '\''
    | '"' (~["\r\n\\] | '\\' .)* '"'
    ;
CUSTOM_PROPERTY: '--' [a-zA-Z_-][a-zA-Z0-9_-]*;



KEYWORD
        : 'auto' | 'inherit' | 'initial' | 'unset'
        | 'solid' | 'dashed' | 'dotted' | 'none'
        | 'block' | 'inline' | 'inline-block' | 'flex' | 'grid'
        | 'red' | 'blue' | 'green' | 'yellow' | 'black' | 'white' | 'transparent'
        ;


IDENTIFIER: [a-zA-Z_-][a-zA-Z0-9_-]*;


// CSS Functions (rgb(), calc(), var(), etc.)
FUNCTION_NAME: [a-zA-Z_-][a-zA-Z0-9_-]* '(';

IMPORTANT: '!important';

// URL
URL
    : 'url(' [ \t\r\n\u000C]* STRING [ \t\r\n\u000C]* ')'
    | 'url(' [ \t\r\n\u000C]* URL_UNQUOTED [ \t\r\n\u000C]* ')'
    ;

fragment URL_UNQUOTED
    : ( ~[ \t\r\n\u000C()"'\\] | '\\' . )+
    ;

AT_KEYWORD: '@' [a-zA-Z-]+;


WS: [ \t\r\n]+ -> skip;

COMMENT: '/*' .*? '*/' -> skip;
