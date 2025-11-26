parser grammar PythonParser;

options {
    tokenVocab = PythonLexer;
}

// ═══════════════════════════════════════════════════════════════════════════
// Python Parser for Flask Applications
// Handles proper Python syntax with indentation-based blocks
// ═══════════════════════════════════════════════════════════════════════════

// ═══════════════════════════════════════════════════════════════════════════
// MAIN ENTRY POINT
// ═══════════════════════════════════════════════════════════════════════════

program: (NEWLINE | statement)* EOF;

// ═══════════════════════════════════════════════════════════════════════════
// STATEMENTS
// ═══════════════════════════════════════════════════════════════════════════

statement
    : simpleStmt
    | compoundStmt
    ;

simpleStmt
    : smallStmt (SEMICOLON smallStmt)* SEMICOLON? NEWLINE
    ;

smallStmt
    : assignment
    | augAssignment
    | expressionStmt
    | returnStmt
    | importStmt
    | fromImportStmt
    | PASS
    | BREAK
    | CONTINUE
    | assertStmt
    | delStmt
    | globalStmt
    | nonlocalStmt
    | raiseStmt
    | yieldStmt
    ;

compoundStmt
    : functionDef
    | classDef
    | ifStmt
    | forStmt
    | whileStmt
    | withStmt
    | tryStmt
    | decorated
    ;

// ═══════════════════════════════════════════════════════════════════════════
// SIMPLE STATEMENTS
// ═══════════════════════════════════════════════════════════════════════════

assignment: target ASSIGN expression;

augAssignment
    : target PLUS_ASSIGN expression
    | target MINUS_ASSIGN expression
    | target STAR_ASSIGN expression
    | target SLASH_ASSIGN expression
    ;

target
    : IDENTIFIER
    | primary DOT IDENTIFIER
    | primary LBRACKET expression RBRACKET
    ;

expressionStmt: expression;

returnStmt: RETURN expression?;

importStmt
    : IMPORT moduleName (AS IDENTIFIER)?
    ;

fromImportStmt
    : FROM moduleName IMPORT (STAR | importNames)
    ;

moduleName: IDENTIFIER (DOT IDENTIFIER)*;

importNames
    : IDENTIFIER (AS IDENTIFIER)?
    | LPAREN IDENTIFIER (AS IDENTIFIER)? (COMMA IDENTIFIER (AS IDENTIFIER)?)* COMMA? RPAREN
    ;

assertStmt: ASSERT expression (COMMA expression)?;

delStmt: DEL target (COMMA target)*;

globalStmt: GLOBAL IDENTIFIER (COMMA IDENTIFIER)*;

nonlocalStmt: NONLOCAL IDENTIFIER (COMMA IDENTIFIER)*;

raiseStmt: RAISE (expression (FROM expression)?)?;

yieldStmt: YIELD expression?;

// ═══════════════════════════════════════════════════════════════════════════
// COMPOUND STATEMENTS
// ═══════════════════════════════════════════════════════════════════════════

functionDef
    : DEF IDENTIFIER LPAREN paramList? RPAREN (ARROW expression)? COLON suite
    ;

classDef
    : CLASS IDENTIFIER (LPAREN argList? RPAREN)? COLON suite
    ;

ifStmt
    : IF expression COLON suite
      (ELIF expression COLON suite)*
      (ELSE COLON suite)?
    ;

forStmt
    : FOR target IN expression COLON suite
      (ELSE COLON suite)?
    ;

whileStmt
    : WHILE expression COLON suite
      (ELSE COLON suite)?
    ;

withStmt
    : WITH withItem (COMMA withItem)* COLON suite
    ;

withItem: expression (AS target)?;

tryStmt
    : TRY COLON suite
      exceptClause+
      (ELSE COLON suite)?
      (FINALLY COLON suite)?
    | TRY COLON suite
      FINALLY COLON suite
    ;

exceptClause
    : EXCEPT (expression (AS IDENTIFIER)?)? COLON suite
    ;

decorated
    : decorator+ (functionDef | classDef)
    ;

decorator
    : AT dottedName (LPAREN argList? RPAREN)? NEWLINE
    ;

dottedName: IDENTIFIER (DOT IDENTIFIER)*;

// ═══════════════════════════════════════════════════════════════════════════
// SUITE (INDENTED BLOCK)
// ═══════════════════════════════════════════════════════════════════════════

suite
    : simpleStmt
    | NEWLINE INDENT statement+ DEDENT
    ;

// ═══════════════════════════════════════════════════════════════════════════
// PARAMETERS AND ARGUMENTS
// ═══════════════════════════════════════════════════════════════════════════

paramList
    : param (COMMA param)* (COMMA)?
    ;

param
    : IDENTIFIER (COLON expression)? (ASSIGN expression)?
    | STAR IDENTIFIER?
    | POWER IDENTIFIER
    ;

argList
    : argument (COMMA argument)* (COMMA)?
    ;

argument
    : expression (ASSIGN expression)?
    | STAR expression
    | POWER expression
    ;

// ═══════════════════════════════════════════════════════════════════════════
// EXPRESSIONS (with proper precedence)
// ═══════════════════════════════════════════════════════════════════════════

expression
    : lambdaExpr
    | orExpr
    ;

lambdaExpr
    : LAMBDA paramList? COLON expression
    ;

orExpr
    : andExpr (OR andExpr)*
    ;

andExpr
    : notExpr (AND notExpr)*
    ;

notExpr
    : NOT notExpr
    | comparison
    ;

comparison
    : bitwiseOr (compOp bitwiseOr)*
    ;

compOp
    : LT | GT | EQ | GTE | LTE | NEQ | IN | IS (NOT)? | NOT IN
    ;

bitwiseOr
    : bitwiseXor (PIPE bitwiseXor)*
    ;

bitwiseXor
    : bitwiseAnd (CARET bitwiseAnd)*
    ;

bitwiseAnd
    : shiftExpr (AMPERSAND shiftExpr)*
    ;

shiftExpr
    : arithExpr ((LEFT_SHIFT | RIGHT_SHIFT) arithExpr)*
    ;

arithExpr
    : term ((PLUS | MINUS) term)*
    ;

term
    : factor ((STAR | SLASH | DOUBLE_SLASH | PERCENT) factor)*
    ;

factor
    : (PLUS | MINUS | TILDE) factor
    | power
    ;

power
    : atomExpr (POWER factor)?
    ;

atomExpr
    : atom trailer*
    ;

atom
    : LPAREN testListComp? RPAREN                    # ParenExpr
    | LBRACKET testListComp? RBRACKET                # ListExpr
    | LBRACE dictOrSetMaker? RBRACE                  # DictOrSetExpr
    | NUMBER                                          # NumberExpr
    | STRING+                                         # StringExpr
    | TRUE                                            # TrueExpr
    | FALSE                                           # FalseExpr
    | NONE                                            # NoneExpr
    | IDENTIFIER                                      # IdentifierExpr
    | ELLIPSIS                                        # EllipsisExpr
    ;

trailer
    : LPAREN argList? RPAREN                         # CallTrailer
    | LBRACKET expression RBRACKET                   # IndexTrailer
    | DOT IDENTIFIER                                  # AttrTrailer
    ;

testListComp
    : expression (COMMA expression)* COMMA?
    | expression compFor
    ;

compFor
    : FOR target IN orExpr compIter?
    ;

compIter
    : compFor
    | compIf
    ;

compIf
    : IF expression compIter?
    ;

dictOrSetMaker
    : dictItem (COMMA dictItem)* COMMA?
    | expression (COMMA expression)* COMMA?
    | dictItem compFor
    | expression compFor
    ;

dictItem
    : expression COLON expression
    ;

// ═══════════════════════════════════════════════════════════════════════════
// PRIMARY EXPRESSIONS
// ═══════════════════════════════════════════════════════════════════════════

primary
    : atom
    | primary DOT IDENTIFIER
    | primary LBRACKET expression RBRACKET
    | primary LPAREN argList? RPAREN
    ;
