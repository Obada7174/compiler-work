parser grammar CSSParser;
@header {
package grammar;
}
options {
    tokenVocab = CSSLexer;
}

stylesheet
    : (ruleSet | atRule)* EOF
    ;

atRule
    : AT_KEYWORD atRulePrelude (SEMICOLON | LBRACE (keyframeRule | ruleSet)* RBRACE)
    ;

atRulePrelude
    : (IDENTIFIER
       | CSS_VAR
       | STRING
       | NUMBER
       | PERCENTAGE
       | UNIT
       | AND
       | LPAREN
       | RPAREN
       | COLON
       | FROM
       | TO
       | value
      )*
    ;

keyframeRule
    : keyframeSelector (COMMA keyframeSelector)* LBRACE declaration* RBRACE
    ;

keyframeSelector
    : FROM
    | TO
    | PERCENTAGE
    ;

ruleSet
    : selectorList LBRACE declaration* RBRACE   # RuleSetNode
    ;

selectorList
    : selector (COMMA selector)*
    ;
selector
    : simpleSelector (combinator simpleSelector)* # SelectorNode
    ;

simpleSelector
    : selectorPart+
    ;

selectorPart
    : PSEUDO_ELEMENT                                              # PseudoElementSelector
    | PSEUDO_CLASS                                                # PseudoClassSelector
    | IDENTIFIER                                                  # ElementSelector
    | DOT IDENTIFIER                                              # ClassSelector
    | HASH IDENTIFIER                                             # IdSelector
    | STAR                                                        # UniversalSelector
    | LBRACKET IDENTIFIER (attributeOperator attributeValue)? RBRACKET  # AttributeSelector
    ;

attributeOperator
    : EQUALS
    | TILDE_EQUALS
    | PIPE_EQUALS
    | CARET_EQUALS
    | DOLLAR_EQUALS
    | STAR_EQUALS
    ;

attributeValue
    : IDENTIFIER
    | STRING
    | NUMBER
    | PERCENTAGE
    ;

combinator
    : GT
    | PLUS
    | TILDE
    ;

declaration
    : (IDENTIFIER | CSS_VAR) COLON value IMPORTANT? SEMICOLON? # DeclarationNode
    ;

value
    : valueComponent+
    ;

valueComponent
    : functionCall        # FunctionValue
    | PERCENTAGE          # PercentageValue     // NEW: Explicit percentage support
    | NUMBER UNIT?        # NumberValue         // Existing (unchanged)
    | COLOR_HEX           # ColorValue          // Existing (unchanged)
    | STRING              # StringValue         // Existing (unchanged)
    | CSS_VAR             # CssVariableValue    // Existing (unchanged)
    | IDENTIFIER          # IdentifierValue     // Existing (unchanged)
    | URL                 # UrlValue            // Existing (unchanged)
    | LPAREN              # LeftParenValue      // Existing (unchanged)
    | RPAREN              # RightParenValue     // Existing (unchanged)
    | PLUS_OP             # PlusOperator        // Existing (unchanged)
    | MINUS_OP            # MinusOperator       // Existing (unchanged)
    | MULTIPLY            # MultiplyOperator    // Existing (unchanged)
    | DIVIDE              # DivideOperator      // Existing (unchanged)
    | COMMA               # CommaValue          // Existing (unchanged)
    ;

functionCall
    : IDENTIFIER LPAREN functionArguments? RPAREN # FunctionCallNode
    ;

functionArguments
    : expression (COMMA expression)*
    ;

expression
    : expression PLUS_OP expression       # AddExpr
    | expression MINUS_OP expression      # SubExpr
    | expression MULTIPLY expression      # MulExpr
    | expression DIVIDE expression        # DivExpr
    | functionCall                        # FunctionExpr
    | IDENTIFIER                          # IdentifierExpr
    | CSS_VAR                             # CssVarExpr
    | STRING                              # StringExpr
    | PERCENTAGE                          # PercentageExpr
    | NUMBER UNIT?                        # NumberExpr
    | LPAREN expression RPAREN            # ParenExpr
    ;
