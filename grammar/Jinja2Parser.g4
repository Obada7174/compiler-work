parser grammar Jinja2Parser;

options { tokenVocab = Jinja2Lexer; }

@header {
  // package or imports if needed
}

/*
  Parser for HTML + Jinja2 using tokens from Jinja2Lexer.
  Produces a well-structured parse tree for HTML elements and Jinja constructs.
*/

/* ---------- ENTRY ---------- */
template
  : ( HTML_DOCTYPE | content )* EOF
  ;

/* ---------- CONTENT ---------- */
content
  : htmlElement
  | htmlStyleTag
  | htmlScriptTag
  | jinjaVar
  | jinjaControl
  | HTML_TEXT
  ;

/* Style/script wrappers (lexer provides the full content tokens) */
htmlStyleTag
  : HTML_STYLE_OPEN HTML_STYLE_CONTENT
  ;

htmlScriptTag
  : HTML_SCRIPT_OPEN HTML_SCRIPT_CONTENT
  ;

/* ---------- HTML ELEMENTS ---------- */
/*
  Two forms:
   1) <name ...> ... </name>
   2) <name ... />
*/
htmlElement
  : HTML_LT HTML_TAG_NAME htmlAttribute* HTML_TAG_CLOSE htmlContent* htmlCloseTag
  | HTML_LT HTML_TAG_NAME htmlAttribute* HTML_TAG_SLASH_CLOSE
  ;

htmlCloseTag
  : HTML_LT HTML_TAG_SLASH HTML_TAG_NAME HTML_TAG_CLOSE
  ;/* attribute: name (= value)? */

htmlAttribute
  : HTML_ATTR_NAME ( HTML_TAG_EQUALS attrValue )?
  ;

/* attribute values (lexer manages quoting) */
attrValue
  : HTML_ATTR_VALUE_DQ attrValueDQContent* HTML_ATTR_DQ_END
  | HTML_ATTR_VALUE_SQ attrValueSQContent* HTML_ATTR_SQ_END
  | HTML_ATTR_VALUE_UNQUOTED
  ;

/* inside double quoted attribute we can have text and jinja-in-attr productions */
attrValueDQContent
  : HTML_ATTR_DQ_TEXT
  | jinjaInAttr
  ;

/* inside single quoted attribute */
attrValueSQContent
  : HTML_ATTR_SQ_TEXT
  | jinjaInAttr
  ;

/* html content inside element */
htmlContent
  : htmlElement
  | jinjaVar
  | jinjaControl
  | HTML_TEXT
  ;

/* ---------------- JINJA VARIABLE ---------------- */
jinjaVar
  : JINJA_VAR_OPEN jinjaExpression ( JINJA_VAR_PIPE jinjaFilter )* JINJA_VAR_CLOSE
  ;

/* filter form: name(args?) */
jinjaFilter
  : JINJA_VAR_IDENTIFIER ( JINJA_VAR_LPAREN jinjaArgList? JINJA_VAR_RPAREN )?
  ;

/* expressions (recursive) */
jinjaExpression
  : jinjaBinaryExpr
  ;

jinjaBinaryExpr
  : jinjaPrimary ( (
        JINJA_VAR_DOT JINJA_VAR_IDENTIFIER
      | JINJA_VAR_LBRACKET jinjaExpression JINJA_VAR_RBRACKET
      | JINJA_VAR_PLUS jinjaExpression
      | JINJA_VAR_MINUS jinjaExpression
      | JINJA_VAR_STAR jinjaExpression
      | JINJA_VAR_SLASH jinjaExpression
      | JINJA_VAR_DOUBLE_SLASH jinjaExpression
      | JINJA_VAR_PERCENT jinjaExpression
      | JINJA_VAR_POWER jinjaExpression
      | JINJA_VAR_OR jinjaExpression
      | JINJA_VAR_AND jinjaExpression
      | JINJA_VAR_EQ jinjaExpression
      | JINJA_VAR_NEQ jinjaExpression
      | JINJA_VAR_LT jinjaExpression
      | JINJA_VAR_GT jinjaExpression
      | JINJA_VAR_LTE jinjaExpression
      | JINJA_VAR_GTE jinjaExpression
      | JINJA_VAR_IN jinjaExpression
      | JINJA_VAR_IS jinjaExpression
    ) )*
  ;

/* primary expressions */
jinjaPrimary
  : JINJA_VAR_LPAREN jinjaExpression JINJA_VAR_RPAREN
  | JINJA_VAR_NUMBER
  | JINJA_VAR_STRING
  | JINJA_VAR_TRUE
  | JINJA_VAR_FALSE
  | JINJA_VAR_NONE
  | JINJA_VAR_IDENTIFIER
  | JINJA_VAR_LBRACKET jinjaArgList? JINJA_VAR_RBRACKET
  | JINJA_VAR_LBRACE jinjaDictItems? JINJA_VAR_RBRACE
  ;

/* argument list for functions/filters */
jinjaArgList
  : jinjaExpression ( JINJA_VAR_COMMA jinjaExpression )* JINJA_VAR_COMMA?
  ;

/* dict items */
jinjaDictItems
  : jinjaDictItem ( JINJA_VAR_COMMA jinjaDictItem )* JINJA_VAR_COMMA?
  ;

jinjaDictItem
  : jinjaExpression JINJA_VAR_COLON jinjaExpression
  ;

/* ---------------- JINJA INSIDE ATTRIBUTE (mini grammar) ---------------- */
jinjaInAttr
  : ( JINJA_IN_ATTR_IDENTIFIER ( JINJA_IN_ATTR_DOT JINJA_IN_ATTR_IDENTIFIER )* ( JINJA_IN_ATTR_LPAREN jinjaAttrArgList? JINJA_IN_ATTR_RPAREN )? )
  | JINJA_IN_ATTR_NUMBER
  | JINJA_IN_ATTR_STRING
  ;

/* attr args */
jinjaAttrArgList
  : jinjaInAttr ( JINJA_IN_ATTR_COMMA jinjaInAttr )*
  ;

/* ---------------- JINJA CONTROL STRUCTURES (statements) ---------------- */
jinjaControl
  : jinjaIf
  | jinjaFor
  | jinjaBlock
  | jinjaExtends
  | jinjaInclude
  | jinjaImport
  | jinjaSet
  | jinjaMacro
  | jinjaCall
  | jinjaFilterBlock
  | jinjaWith
  | jinjaAutoescape
  ;

/* IF block */
jinjaIf
  : JINJA_STMT_OPEN JINJA_STMT_IF jinjaStmtExpression JINJA_STMT_CLOSE
    content*
    ( JINJA_STMT_OPEN JINJA_STMT_ELIF jinjaStmtExpression JINJA_STMT_CLOSE content* )*
    ( JINJA_STMT_OPEN JINJA_STMT_ELSE JINJA_STMT_CLOSE content* )?
    JINJA_STMT_OPEN JINJA_STMT_ENDIF JINJA_STMT_CLOSE
  ;

/* FOR block */
jinjaFor
  : JINJA_STMT_OPEN JINJA_STMT_FOR jinjaStmtTarget JINJA_STMT_IN jinjaStmtExpression JINJA_STMT_CLOSE
    content*
    ( JINJA_STMT_OPEN JINJA_STMT_ELSE JINJA_STMT_CLOSE content* )?
    JINJA_STMT_OPEN JINJA_STMT_ENDFOR JINJA_STMT_CLOSE
  ;

jinjaStmtTarget
  : JINJA_STMT_IDENTIFIER
  | JINJA_STMT_LPAREN JINJA_STMT_IDENTIFIER ( JINJA_STMT_COMMA JINJA_STMT_IDENTIFIER )* JINJA_STMT_RPAREN
  ;

jinjaBlock
  : JINJA_STMT_OPEN JINJA_STMT_BLOCK JINJA_STMT_IDENTIFIER JINJA_STMT_CLOSE
    content*
    JINJA_STMT_OPEN JINJA_STMT_ENDBLOCK JINJA_STMT_IDENTIFIER? JINJA_STMT_CLOSE
  ;

jinjaExtends
  : JINJA_STMT_OPEN JINJA_STMT_EXTENDS JINJA_STMT_STRING JINJA_STMT_CLOSE
  ;

jinjaInclude
  : JINJA_STMT_OPEN JINJA_STMT_INCLUDE JINJA_STMT_STRING JINJA_STMT_CLOSE
  ;

jinjaImport
  : JINJA_STMT_OPEN JINJA_STMT_IMPORT JINJA_STMT_STRING JINJA_STMT_AS JINJA_STMT_IDENTIFIER JINJA_STMT_CLOSE
  | JINJA_STMT_OPEN JINJA_STMT_FROM JINJA_STMT_STRING JINJA_STMT_IMPORT JINJA_STMT_IDENTIFIER JINJA_STMT_CLOSE
  ;

jinjaSet
  : JINJA_STMT_OPEN JINJA_STMT_SET JINJA_STMT_IDENTIFIER JINJA_STMT_ASSIGN jinjaStmtExpression JINJA_STMT_CLOSE
  ;

jinjaMacro
  : JINJA_STMT_OPEN JINJA_STMT_MACRO JINJA_STMT_IDENTIFIER JINJA_STMT_LPAREN jinjaStmtParamList? JINJA_STMT_RPAREN JINJA_STMT_CLOSE
    content*
    JINJA_STMT_OPEN JINJA_STMT_ENDMACRO JINJA_STMT_CLOSE
  ;

jinjaCall
  : JINJA_STMT_OPEN JINJA_STMT_CALL jinjaStmtExpression JINJA_STMT_CLOSE
    content*
    JINJA_STMT_OPEN JINJA_STMT_ENDCALL JINJA_STMT_CLOSE
  ;

jinjaFilterBlock
  : JINJA_STMT_OPEN JINJA_STMT_FILTER JINJA_STMT_IDENTIFIER JINJA_STMT_CLOSE
    content*
    JINJA_STMT_OPEN JINJA_STMT_ENDFILTER JINJA_STMT_CLOSE
  ;

jinjaWith
  : JINJA_STMT_OPEN JINJA_STMT_WITH jinjaStmtExpression ( JINJA_STMT_COMMA jinjaStmtExpression )* JINJA_STMT_CLOSE
    content*
    JINJA_STMT_OPEN JINJA_STMT_ENDWITH JINJA_STMT_CLOSE
  ;

jinjaAutoescape
  : JINJA_STMT_OPEN JINJA_STMT_AUTOESCAPE jinjaStmtExpression JINJA_STMT_CLOSE
    content*
    JINJA_STMT_OPEN JINJA_STMT_ENDAUTOESCAPE JINJA_STMT_CLOSE
  ;

jinjaStmtParamList
  : JINJA_STMT_IDENTIFIER ( JINJA_STMT_COMMA JINJA_STMT_IDENTIFIER )*
  ;

jinjaStmtExpression
  : jinjaStmtPrimary ( (
        JINJA_STMT_DOT JINJA_STMT_IDENTIFIER
      | JINJA_STMT_LBRACKET jinjaStmtExpression JINJA_STMT_RBRACKET
      | JINJA_STMT_PLUS jinjaStmtExpression
      | JINJA_STMT_MINUS jinjaStmtExpression
      | JINJA_STMT_STAR jinjaStmtExpression
      | JINJA_STMT_SLASH jinjaStmtExpression
      | JINJA_STMT_DOUBLE_SLASH jinjaStmtExpression
      | JINJA_STMT_PERCENT jinjaStmtExpression
      | JINJA_STMT_POWER jinjaStmtExpression
      | JINJA_STMT_OR jinjaStmtExpression
      | JINJA_STMT_AND jinjaStmtExpression
      | JINJA_STMT_EQ jinjaStmtExpression
      | JINJA_STMT_NEQ jinjaStmtExpression
      | JINJA_STMT_LT jinjaStmtExpression
      | JINJA_STMT_GT jinjaStmtExpression
      | JINJA_STMT_LTE jinjaStmtExpression
      | JINJA_STMT_GTE jinjaStmtExpression
      | JINJA_STMT_IN jinjaStmtExpression
      | JINJA_STMT_IS jinjaStmtExpression
    ) )*
  ;

jinjaStmtPrimary
  : JINJA_STMT_LPAREN jinjaStmtExpression JINJA_STMT_RPAREN
  | JINJA_STMT_NUMBER
  | JINJA_STMT_STRING
  | JINJA_STMT_TRUE
  | JINJA_STMT_FALSE
  | JINJA_STMT_NONE
  | JINJA_STMT_IDENTIFIER
  | JINJA_STMT_LBRACKET jinjaStmtArgList? JINJA_STMT_RBRACKET
  | JINJA_STMT_LBRACE jinjaStmtDictItems? JINJA_STMT_RBRACE
  ;

jinjaStmtArgList
  : jinjaStmtExpression ( JINJA_STMT_COMMA jinjaStmtExpression )* JINJA_STMT_COMMA?
  ;

jinjaStmtDictItems
  : jinjaStmtDictItem ( JINJA_STMT_COMMA jinjaStmtDictItem )* JINJA_STMT_COMMA?
  ;

jinjaStmtDictItem
  : jinjaStmtExpression JINJA_STMT_COLON jinjaStmtExpression
  ;
