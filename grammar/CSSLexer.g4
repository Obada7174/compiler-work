lexer grammar CSSLexer;

// ═══════════════════════════════════════════════════════════════════════════
// CSS Lexer for Stylesheet Parsing
// ═══════════════════════════════════════════════════════════════════════════

// ═══════════════════════════════════════════════════════════════════════════
// SELECTORS
// ═══════════════════════════════════════════════════════════════════════════

HASH: '#';
DOT: '.';
STAR: '*';

// CSS selector identifiers (element names, class names, IDs)
IDENTIFIER: [a-zA-Z_-][a-zA-Z0-9_-]*;

// Pseudo-classes and pseudo-elements
PSEUDO_CLASS: ':' [a-zA-Z-]+;
PSEUDO_ELEMENT: '::' [a-zA-Z-]+;

// ═══════════════════════════════════════════════════════════════════════════
// COMBINATORS
// ═══════════════════════════════════════════════════════════════════════════

GT: '>';           // Child combinator
PLUS: '+';         // Adjacent sibling combinator
TILDE: '~';        // General sibling combinator
// Descendant combinator is implicit (whitespace)

// ═══════════════════════════════════════════════════════════════════════════
// DELIMITERS
// ═══════════════════════════════════════════════════════════════════════════

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

// ═══════════════════════════════════════════════════════════════════════════
// VALUES
// ═══════════════════════════════════════════════════════════════════════════

// Numbers (integer or float)
NUMBER: [0-9]+ ('.' [0-9]+)?;

// CSS Units
UNIT:
    'px' | 'em' | 'rem' | '%' | 'vh' | 'vw' | 'vmin' | 'vmax'
    | 'pt' | 'pc' | 'in' | 'cm' | 'mm' | 'ex' | 'ch'
    | 'deg' | 'rad' | 'grad' | 'turn'
    | 's' | 'ms'
    | 'fr'
    ;

// Color values
COLOR_HEX: '#' [0-9a-fA-F]+;

// Strings
STRING:
    '\'' (~['\r\n\\] | '\\' .)* '\''
    | '"' (~["\r\n\\] | '\\' .)* '"'
    ;

// CSS Functions (rgb(), calc(), var(), etc.)
FUNCTION: [a-zA-Z_-][a-zA-Z0-9_-]* '(';

// ═══════════════════════════════════════════════════════════════════════════
// SPECIAL
// ═══════════════════════════════════════════════════════════════════════════

IMPORTANT: '!important';

// URL
URL: 'url(' (~[)]* | '\\' .)* ')';

// At-rules (@media, @import, @keyframes, etc.)
AT_KEYWORD: '@' [a-zA-Z-]+;

// ═══════════════════════════════════════════════════════════════════════════
// WHITESPACE AND COMMENTS
// ═══════════════════════════════════════════════════════════════════════════

WS: [ \t\r\n]+ -> skip;

COMMENT: '/*' .*? '*/' -> skip;
