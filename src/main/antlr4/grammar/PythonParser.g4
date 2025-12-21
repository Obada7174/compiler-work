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
    : RETURN expr?
    ;

import_stmt
    : IMPORT dotted_name (COMMA dotted_name)*         # importNames
    | FROM dotted_name IMPORT (NAME (COMMA NAME)*)?  # importFrom
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

expr
    : comparison
    ;

comparison
    : arith_expr
      ((EQ | NE | LT | GT | LE | GE) arith_expr)?
    ;

arith_expr
    : atom_expr ((PLUS | MINUS | STAR | DIV | MOD) atom_expr)*
    ;

atom_expr
    : atom trailer*
    ;

trailer
    : call               # callTrailer
    | DOT NAME           # memberAccessTrailer
    | LBRACK expr RBRACK # indexAccessTrailer
    ;

call
    : LPAREN arglist? RPAREN
    ;

arglist
    : argument (COMMA argument)* COMMA?
    ;

argument
    : NAME ASSIGN expr   # keywordArg
    | expr               # positionalArg
    ;

// ───────────────── ATOMS ─────────────────

atom
    : NAME                       # nameAtom
    | NUMBER                     # numberAtom
    | STRING                     # stringAtom
    | FSTRING                    # fstringAtom
    | TRUE                       # trueAtom
    | FALSE                      # falseAtom
    | NONE                       # noneAtom
    | listLit                    # listAtom
    | dictLit                    # dictAtom
    | LPAREN expr? RPAREN        # parenAtom
    ;

// ───────────────── LITERALS ─────────────────

listLit
    : LBRACK (expr (COMMA expr)*)? RBRACK
    ;

dictLit
    : LBRACE (dictItem (COMMA dictItem)*)? RBRACE
    ;

dictItem
    : (STRING | NAME) COLON expr
    ;

// ───────────────── PARAMETERS ─────────────────

parameters
    : LPAREN (NAME (COMMA NAME)*)? RPAREN
    ;
