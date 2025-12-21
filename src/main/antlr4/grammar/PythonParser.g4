parser grammar PythonParser;
@header {
package grammar;
}
options { tokenVocab=PythonLexer; }


file_input
    : stmt* EOF
    ;

stmt
    : simple_stmt
    | compound_stmt
    | decorated
    ;

simple_stmt
    : small_stmt (SEMI small_stmt)* SEMI?
    ;

small_stmt
    : assignment
    | return_stmt
    | import_stmt
    | expr_stmt
    | PASS
    ;

expr_stmt
    : expr
    ;

compound_stmt
    : funcdef
    | classdef
    | if_stmt
    | for_stmt
    | while_stmt
    | try_stmt
    ;

decorated
    : decorator+ (funcdef | classdef)
    ;

decorator
    : AT atom_expr call
    ;

funcdef
    : DEF NAME parameters COLON suite
    ;

classdef
    : CLASS NAME (LPAREN (dotted_name (COMMA dotted_name)*)? RPAREN)? COLON suite
    ;

if_stmt
    : IF expr COLON suite (ELIF expr COLON suite)* (ELSE COLON suite)?
    ;

for_stmt
    : FOR NAME IN expr COLON suite
    ;

while_stmt
    : WHILE expr COLON suite
    ;

try_stmt
    : TRY COLON suite (EXCEPT (NAME (AS NAME)?)? COLON suite)+ (FINALLY COLON suite)?
    ;

return_stmt
    : RETURN expr?
    ;

import_stmt
    : IMPORT dotted_name (COMMA dotted_name)*
    | FROM dotted_name IMPORT (NAME (COMMA NAME)*)?
    ;

dotted_name
    : NAME (DOT NAME)*
    ;

suite
    : simple_stmt
    | stmt+
    ;

expr
    : comparison
    ;

comparison
    : arith_expr ((EQ|NE|LT|GT|LE|GE) arith_expr)?
    ;

arith_expr
    : atom_expr ((PLUS|MINUS|STAR|DIV|MOD) atom_expr)*
    ;

atom_expr
    : atom trailer*
    ;

trailer
    : call
    | DOT NAME
    | LBRACK expr RBRACK
    ;

call
    : LPAREN (arglist)? RPAREN
    ;

arglist
    : argument (COMMA argument)* COMMA?
    ;

argument
    : NAME ASSIGN expr   # keywordArg
    | expr               # positionalArg
    ;

// ─── ATOMS ───
atom
    : NAME
    | NUMBER
    | STRING
    | FSTRING
    | TRUE
    | FALSE
    | NONE
    | listLit
    | dictLit
    | LPAREN expr? RPAREN
    ;

listLit
    : LBRACK (expr (COMMA expr)*)? RBRACK
    ;

dictLit
    : LBRACE (dictItem (COMMA dictItem)*)? RBRACE
    ;


dictItem
    : (STRING | NAME) COLON expr
    ;


parameters
    : LPAREN (NAME (COMMA NAME)*)? RPAREN
    ;

assignment
    : atom_expr ASSIGN expr
    ;