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
    : AT_KEYWORD atRulePrelude (SEMICOLON | LBRACE ruleSet* RBRACE)
    ;

atRulePrelude
    : (IDENTIFIER
       | CSS_VAR
       | STRING
       | NUMBER
       | UNIT
       | AND
       | LPAREN
       | RPAREN
       | COLON
       | value
      )*
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
    : PSEUDO_ELEMENT                               # PseudoElementSelector
    | PSEUDO_CLASS                                 # PseudoClassSelector
    | IDENTIFIER                                   # ElementSelector
    | DOT IDENTIFIER                               # ClassSelector
    | HASH IDENTIFIER                              # IdSelector
    | STAR                                         # UniversalSelector
    | LBRACKET IDENTIFIER (EQUALS value)? RBRACKET # AttributeSelector
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
    | NUMBER UNIT?        # NumberValue
    | COLOR_HEX           # ColorValue
    | STRING              # StringValue
    | CSS_VAR             # CssVariableValue
    | IDENTIFIER          # IdentifierValue
    | URL                 # UrlValue
    | LPAREN              # LeftParenValue
    | RPAREN              # RightParenValue
    | PLUS_OP             # PlusOperator
    | MINUS_OP            # MinusOperator
    | MULTIPLY            # MultiplyOperator
    | DIVIDE              # DivideOperator
    | COMMA               # CommaValue
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
    | NUMBER UNIT?                        # NumberExpr
    | LPAREN expression RPAREN            # ParenExpr
    ;
