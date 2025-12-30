parser grammar PythonParser;

@header {
package grammar;
}

options {
    tokenVocab = PythonLexer;
}

file_input
    : (stmt | NEWLINE)* EOF
    ;

// ───────────────── STATEMENTS ─────────────────

stmt
    : simple_stmt
    | compound_stmt
    | decorated
    ;

simple_stmt
    : small_stmt (SEMI small_stmt)* SEMI? NEWLINE
    ;

small_stmt
    : assignment        # assignmentStmt
    | return_stmt       # returnStmt
    | import_stmt       # importStmt
    | global_stmt       # globalStmt
    | expr_stmt         # exprStmt
    | PASS              # passStmt
    ;

expr_stmt
    : expr
    ;

// ──────────────── COMPOUND STATEMENTS ────────────────

compound_stmt
    : funcdef           # functionDefStmt
    | classdef          # classDefStmt
    | if_stmt           # ifStmt
    | for_stmt          # forStmt
    | while_stmt        # whileStmt
    | try_stmt          # tryStmt
    ;

decorated
    : decorator+ (funcdef | classdef)
    ;

decorator
    : AT atom_expr call? NEWLINE
    ;

// ───────────────── DEFINITIONS ─────────────────

funcdef
    : DEF NAME parameters COLON suite
    ;

classdef
    : CLASS NAME (LPAREN (dotted_name (COMMA dotted_name)*)? RPAREN)? COLON suite
    ;

// ───────────────── CONTROL FLOW ─────────────────

if_stmt
    : IF expr COLON suite
      (ELIF expr COLON suite)*
      (ELSE COLON suite)?
    ;

for_stmt
    : FOR NAME IN expr COLON suite
      (ELSE COLON suite)?
    ;

while_stmt
    : WHILE expr COLON suite
      (ELSE COLON suite)?
    ;

try_stmt
    : TRY COLON suite
      (EXCEPT (NAME (AS NAME)?)? COLON suite)+
      (FINALLY COLON suite)?
    ;

// ───────────────── SIMPLE STATEMENTS ─────────────────

return_stmt
    : RETURN (expr (COMMA expr)*)?
    ;

import_stmt
    : IMPORT dotted_name (COMMA dotted_name)*         # importNames
    | FROM dotted_name IMPORT (NAME (COMMA NAME)*)?  # importFrom
    ;

global_stmt
    : GLOBAL NAME (COMMA NAME)*
    ;

assignment
    : atom_expr ASSIGN expr
    ;

// ───────────────── NAMES ─────────────────

dotted_name
    : NAME (DOT NAME)*
    ;

// ───────────────── SUITE / BLOCK ─────────────────

suite
    : simple_stmt
    | NEWLINE INDENT stmt+ DEDENT
    ;

// ───────────────── EXPRESSIONS ─────────────────

// Full expression with conditional (ternary)
expr
    : or_test (IF or_test ELSE expr)?    # conditionalExpr
    ;

// Logical OR
or_test
    : and_test (OR and_test)*            # orExpr
    ;

// Logical AND
and_test
    : not_test (AND not_test)*           # andExpr
    ;

// Logical NOT
not_test
    : NOT not_test                       # notExpr
    | comparison                         # comparisonExpr
    ;

// Comparison with chaining support (e.g., a < b < c)
comparison
    : arith_expr (comp_op arith_expr)*
    ;

comp_op
    : EQ | NE | LT | GT | LE | GE
    | IS NOT?                            // is / is not
    | NOT? IN                            // in / not in
    ;

// Arithmetic expressions
arith_expr
    : term ((PLUS | MINUS) term)*
    ;

term
    : factor ((STAR | DIV | MOD | FLOORDIV) factor)*
    ;

factor
    : (PLUS | MINUS)? atom_expr
    ;

atom_expr
    : atom trailer*
    ;

trailer
    : call                              # callTrailer
    | DOT NAME                          # memberAccessTrailer
    | LBRACK subscript RBRACK           # subscriptTrailer
    ;

// Subscript can be index or slice
subscript
    : expr                              # indexSubscript
    | slice_arg? COLON slice_arg? (COLON slice_arg?)?  # sliceSubscript
    ;

slice_arg
    : expr
    ;

call
    : LPAREN arglist? RPAREN
    ;

arglist
    : argument (COMMA argument)* COMMA?       # normalArgList      // Normal arguments (try first)
    | expr comp_for                           # generatorArgList   // Generator as sole argument
    ;

argument
    : NAME ASSIGN expr          # keywordArg
    | expr                      # positionalArg
    ;

// ───────────────── ATOMS ─────────────────

atom
    : NAME                       # nameAtom
    | NUMBER                     # numberAtom
    | STRING+                    # stringAtom
    | FSTRING                    # fstringAtom
    | TRUE                       # trueAtom
    | FALSE                      # falseAtom
    | NONE                       # noneAtom
    | listDisplay                # listAtom
    | dictLit                    # dictAtom
    | LPAREN tupleOrGenExp RPAREN # parenAtom
    ;

// ───────────────── LITERALS & COMPREHENSIONS ─────────────────

// List: can be literal or comprehension
listDisplay
    : LBRACK RBRACK                                     # emptyList
    | LBRACK expr (COMMA expr)* COMMA? RBRACK          # listLiteral
    | LBRACK expr comp_for RBRACK                       # listComprehension
    ;

// Tuple or generator expression inside parentheses
tupleOrGenExp
    :                                                   # emptyParens
    | expr                                              # singleExpr
    | expr COMMA (expr (COMMA expr)*)? COMMA?          # tupleExpr
    | expr comp_for                                     # generatorExpr
    ;

// Comprehension for clause
comp_for
    : FOR NAME IN or_test comp_iter?
    ;

// Comprehension iterator (optional if or nested for)
comp_iter
    : comp_for
    | comp_if
    ;

// Comprehension if filter
comp_if
    : IF or_test comp_iter?
    ;

dictLit
    : LBRACE RBRACE
    | LBRACE dictItem (COMMA dictItem)* COMMA? RBRACE
    ;

dictItem
    : expr COLON expr
    ;

// ───────────────── PARAMETERS ─────────────────

parameters
    : LPAREN (NAME (COMMA NAME)*)? RPAREN
    ;
