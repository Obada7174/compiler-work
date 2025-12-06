parser grammar CSSParser;

options {
    tokenVocab = CSSLexer;
}

@header {
package grammar;
}

stylesheet
    : (ruleSet | atRule)* EOF
    ;

atRule
    : AT_KEYWORD value? LBRACE ruleSet* RBRACE
    | AT_KEYWORD value? SEMICOLON
    ;

ruleSet
    : selectorList LBRACE declaration* RBRACE
    ;

selectorList
    : selector (COMMA selector)*
    ;


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


declaration
    : IDENTIFIER COLON value IMPORTANT? SEMICOLON?
    ;


value
    : valueItem (COMMA valueItem)*
    ;

valueItem
    : DIMENSION                                       # DimensionValue
    | NUMBER                                          # NumberValue
    | COLOR_HEX                                       # ColorValue
    | STRING                                          # StringValue
    | IDENTIFIER                                      # IdentifierValue
    | functionCall                                    # FunctionValue
    | URL                                             # UrlValue
    ;

functionCall
    : IDENTIFIER LPAREN value? RPAREN   // rgb(255,0,0)
    ;

