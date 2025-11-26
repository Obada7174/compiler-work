lexer grammar Jinja2Lexer;

// ═══════════════════════════════════════════════════════════════════════════
// Jinja2 Template Lexer - HTML Templates with Jinja2 Constructs
// Handles HTML tags, attributes, Jinja2 variables, and control structures
// ═══════════════════════════════════════════════════════════════════════════

// ═══════════════════════════════════════════════════════════════════════════
// DEFAULT MODE - HTML Content
// ═══════════════════════════════════════════════════════════════════════════

// Jinja2 Delimiters (highest priority)
JINJA_VAR_OPEN: '{{' -> pushMode(JINJA_VAR_MODE);
JINJA_STMT_OPEN: '{%' -> pushMode(JINJA_STMT_MODE);
JINJA_COMMENT: '{#' ~[#]* '#}' -> skip;

// HTML Tags
HTML_OPEN: '<' -> pushMode(HTML_TAG_MODE);

// HTML Text content (anything that's not a tag or Jinja2)
HTML_TEXT: ~[<{]+ ;

// Whitespace
WS: [ \t\r\n]+ -> skip;

// ═══════════════════════════════════════════════════════════════════════════
// JINJA VARIABLE MODE {{ }}
// ═══════════════════════════════════════════════════════════════════════════

mode JINJA_VAR_MODE;

JINJA_VAR_CLOSE: '}}' -> popMode;
JINJA_VAR_PIPE: '|';
JINJA_VAR_DOT: '.';
JINJA_VAR_LPAREN: '(';
JINJA_VAR_RPAREN: ')';
JINJA_VAR_LBRACKET: '[';
JINJA_VAR_RBRACKET: ']';
JINJA_VAR_COMMA: ',';
JINJA_VAR_COLON: ':';
JINJA_VAR_ASSIGN: '=';

// Operators
JINJA_VAR_PLUS: '+';
JINJA_VAR_MINUS: '-';
JINJA_VAR_STAR: '*';
JINJA_VAR_SLASH: '/';
JINJA_VAR_PERCENT: '%';
JINJA_VAR_POWER: '**';
JINJA_VAR_DOUBLE_SLASH: '//';

// Comparison
JINJA_VAR_EQ: '==';
JINJA_VAR_NEQ: '!=';
JINJA_VAR_LT: '<';
JINJA_VAR_GT: '>';
JINJA_VAR_LTE: '<=';
JINJA_VAR_GTE: '>=';

// Literals
JINJA_VAR_NUMBER: [0-9]+ ('.' [0-9]+)?;
JINJA_VAR_STRING: '\'' (~['\r\n])* '\'' | '"' (~["\r\n])* '"';
JINJA_VAR_TRUE: 'True' | 'true';
JINJA_VAR_FALSE: 'False' | 'false';
JINJA_VAR_NONE: 'None' | 'none';

// Keywords
JINJA_VAR_AND: 'and';
JINJA_VAR_OR: 'or';
JINJA_VAR_NOT: 'not';
JINJA_VAR_IN: 'in';
JINJA_VAR_IS: 'is';

JINJA_VAR_IDENTIFIER: [a-zA-Z_][a-zA-Z0-9_]*;
JINJA_VAR_WS: [ \t\r\n]+ -> skip;

// ═══════════════════════════════════════════════════════════════════════════
// JINJA STATEMENT MODE {% %}
// ═══════════════════════════════════════════════════════════════════════════

mode JINJA_STMT_MODE;

JINJA_STMT_CLOSE: '%}' -> popMode;

// Jinja2 keywords
JINJA_STMT_IF: 'if';
JINJA_STMT_ELIF: 'elif';
JINJA_STMT_ELSE: 'else';
JINJA_STMT_ENDIF: 'endif';
JINJA_STMT_FOR: 'for';
JINJA_STMT_ENDFOR: 'endfor';
JINJA_STMT_IN: 'in';
JINJA_STMT_BLOCK: 'block';
JINJA_STMT_ENDBLOCK: 'endblock';
JINJA_STMT_EXTENDS: 'extends';
JINJA_STMT_INCLUDE: 'include';
JINJA_STMT_IMPORT: 'import';
JINJA_STMT_FROM: 'from';
JINJA_STMT_SET: 'set';
JINJA_STMT_MACRO: 'macro';
JINJA_STMT_ENDMACRO: 'endmacro';
JINJA_STMT_CALL: 'call';
JINJA_STMT_ENDCALL: 'endcall';
JINJA_STMT_FILTER: 'filter';
JINJA_STMT_ENDFILTER: 'endfilter';
JINJA_STMT_WITH: 'with';
JINJA_STMT_ENDWITH: 'endwith';
JINJA_STMT_AUTOESCAPE: 'autoescape';
JINJA_STMT_ENDAUTOESCAPE: 'endautoescape';

// Operators
JINJA_STMT_PIPE: '|';
JINJA_STMT_DOT: '.';
JINJA_STMT_LPAREN: '(';
JINJA_STMT_RPAREN: ')';
JINJA_STMT_LBRACKET: '[';
JINJA_STMT_RBRACKET: ']';
JINJA_STMT_LBRACE: '{';
JINJA_STMT_RBRACE: '}';
JINJA_STMT_COMMA: ',';
JINJA_STMT_COLON: ':';
JINJA_STMT_ASSIGN: '=';

// Comparison
JINJA_STMT_EQ: '==';
JINJA_STMT_NEQ: '!=';
JINJA_STMT_LT: '<';
JINJA_STMT_GT: '>';
JINJA_STMT_LTE: '<=';
JINJA_STMT_GTE: '>=';

// Logical
JINJA_STMT_AND: 'and';
JINJA_STMT_OR: 'or';
JINJA_STMT_NOT: 'not';
JINJA_STMT_IS: 'is';

// Arithmetic
JINJA_STMT_PLUS: '+';
JINJA_STMT_MINUS: '-';
JINJA_STMT_STAR: '*';
JINJA_STMT_SLASH: '/';
JINJA_STMT_PERCENT: '%';
JINJA_STMT_POWER: '**';
JINJA_STMT_DOUBLE_SLASH: '//';

// Literals
JINJA_STMT_NUMBER: [0-9]+ ('.' [0-9]+)?;
JINJA_STMT_STRING: '\'' (~['\r\n])* '\'' | '"' (~["\r\n])* '"';
JINJA_STMT_TRUE: 'True' | 'true';
JINJA_STMT_FALSE: 'False' | 'false';
JINJA_STMT_NONE: 'None' | 'none';

JINJA_STMT_IDENTIFIER: [a-zA-Z_][a-zA-Z0-9_]*;
JINJA_STMT_WS: [ \t\r\n]+ -> skip;

// ═══════════════════════════════════════════════════════════════════════════
// HTML TAG MODE
// ═══════════════════════════════════════════════════════════════════════════

mode HTML_TAG_MODE;

HTML_TAG_CLOSE: '>' -> popMode;
HTML_TAG_SLASH_CLOSE: '/>' -> popMode;
HTML_TAG_SLASH: '/';
HTML_TAG_NAME: [a-zA-Z][a-zA-Z0-9]*;
HTML_ATTR_NAME: [a-zA-Z][a-zA-Z0-9_:-]*;
HTML_ATTR_EQ: '=' -> pushMode(HTML_ATTR_VALUE_MODE);
HTML_TAG_WS: [ \t\r\n]+ -> skip;

// ═══════════════════════════════════════════════════════════════════════════
// HTML ATTRIBUTE VALUE MODE
// ═══════════════════════════════════════════════════════════════════════════

mode HTML_ATTR_VALUE_MODE;

HTML_ATTR_VALUE_DQ: '"' -> pushMode(HTML_ATTR_DQ_MODE);
HTML_ATTR_VALUE_SQ: '\'' -> pushMode(HTML_ATTR_SQ_MODE);
HTML_ATTR_VALUE_UNQUOTED: ~[ \t\r\n"'=<>`]+ -> popMode;

// ═══════════════════════════════════════════════════════════════════════════
// DOUBLE QUOTED ATTRIBUTE
// ═══════════════════════════════════════════════════════════════════════════

mode HTML_ATTR_DQ_MODE;

HTML_ATTR_DQ_TEXT: ~["{]+ ;
HTML_ATTR_DQ_JINJA_OPEN: '{{' -> pushMode(JINJA_IN_ATTR_MODE);
HTML_ATTR_DQ_END: '"' -> popMode, popMode;

// ═══════════════════════════════════════════════════════════════════════════
// SINGLE QUOTED ATTRIBUTE
// ═══════════════════════════════════════════════════════════════════════════

mode HTML_ATTR_SQ_MODE;

HTML_ATTR_SQ_TEXT: ~['{]+ ;
HTML_ATTR_SQ_JINJA_OPEN: '{{' -> pushMode(JINJA_IN_ATTR_MODE);
HTML_ATTR_SQ_END: '\'' -> popMode, popMode;

// ═══════════════════════════════════════════════════════════════════════════
// JINJA IN ATTRIBUTE MODE
// ═══════════════════════════════════════════════════════════════════════════

mode JINJA_IN_ATTR_MODE;

JINJA_IN_ATTR_CLOSE: '}}' -> popMode;
JINJA_IN_ATTR_PIPE: '|';
JINJA_IN_ATTR_DOT: '.';
JINJA_IN_ATTR_LPAREN: '(';
JINJA_IN_ATTR_RPAREN: ')';
JINJA_IN_ATTR_LBRACKET: '[';
JINJA_IN_ATTR_RBRACKET: ']';
JINJA_IN_ATTR_COMMA: ',';
JINJA_IN_ATTR_NUMBER: [0-9]+ ('.' [0-9]+)?;
JINJA_IN_ATTR_STRING: '\'' (~['\r\n])* '\'' | '"' (~["\r\n])* '"';
JINJA_IN_ATTR_IDENTIFIER: [a-zA-Z_][a-zA-Z0-9_]*;
JINJA_IN_ATTR_WS: [ \t\r\n]+ -> skip;
