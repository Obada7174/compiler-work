parser grammar CSSParser;

options {
    tokenVocab = CSSLexer;
}

// ═══════════════════════════════════════════════════════════════════════════
// CSS Parser for Stylesheets
// Handles CSS3 syntax with selectors, rules, and at-rules
// ═══════════════════════════════════════════════════════════════════════════

// ═══════════════════════════════════════════════════════════════════════════
// MAIN ENTRY POINT
// ═══════════════════════════════════════════════════════════════════════════

stylesheet
    : (ruleSet | atRule)* EOF
    ;

// ═══════════════════════════════════════════════════════════════════════════
// AT-RULES (@media, @import, @keyframes, etc.)
// ═══════════════════════════════════════════════════════════════════════════

atRule
    : AT_KEYWORD (IDENTIFIER | STRING | value)* (SEMICOLON | LBRACE ruleSet* RBRACE)
    ;

// ═══════════════════════════════════════════════════════════════════════════
// RULE SETS
// ═══════════════════════════════════════════════════════════════════════════

ruleSet
    : selectorList LBRACE declaration* RBRACE
    ;

selectorList
    : selector (COMMA selector)*
    ;

// ═══════════════════════════════════════════════════════════════════════════
// SELECTORS (Fixed ambiguity issues)
// ═══════════════════════════════════════════════════════════════════════════

selector
    : simpleSelector (combinator simpleSelector)*
    ;

simpleSelector
    : selectorPart+
    ;

selectorPart
    : IDENTIFIER                                      # ElementSelector
    | DOT IDENTIFIER                                  # ClassSelector
    | HASH IDENTIFIER                                 # IdSelector
    | STAR                                            # UniversalSelector
    | PSEUDO_CLASS                                    # PseudoClassSelector
    | PSEUDO_ELEMENT                                  # PseudoElementSelector
    | LBRACKET IDENTIFIER (EQUALS value)? RBRACKET   # AttributeSelector
    ;

combinator
    : GT          // Child: ul > li
    | PLUS        // Adjacent sibling: h1 + p
    | TILDE       // General sibling: h1 ~ p
    // Descendant combinator (space) is implicit when no combinator token
    ;

// ═══════════════════════════════════════════════════════════════════════════
// DECLARATIONS
// ═══════════════════════════════════════════════════════════════════════════

declaration
    : IDENTIFIER COLON value IMPORTANT? SEMICOLON?
    ;

// ═══════════════════════════════════════════════════════════════════════════
// VALUES
// ═══════════════════════════════════════════════════════════════════════════

value
    : valueItem+
    ;

valueItem
    : NUMBER UNIT?                                    # NumberValue
    | COLOR_HEX                                       # ColorValue
    | STRING                                          # StringValue
    | IDENTIFIER                                      # IdentifierValue
    | functionCall                                    # FunctionValue
    | URL                                             # UrlValue
    | COMMA                                           # CommaValue
    ;

functionCall
    : FUNCTION value RPAREN
    ;

// ═══════════════════════════════════════════════════════════════════════════
// INLINE CSS (for style attribute parsing)
// ═══════════════════════════════════════════════════════════════════════════

inlineStyle
    : declaration+
    ;
