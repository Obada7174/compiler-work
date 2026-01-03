parser grammar Jinja2Parser;
@header {
package grammar;
}

@members {
    private static final java.util.Set<String> VALID_HTML_ELEMENTS = new java.util.HashSet<>(java.util.Arrays.asList(
        // Document structure
        "html", "head", "body", "title", "meta", "link", "base",
        // Sectioning
        "header", "footer", "nav", "main", "section", "article", "aside", "address",
        // Block elements
        "div", "p", "pre", "blockquote", "figure", "figcaption", "hr",
        // Headings
        "h1", "h2", "h3", "h4", "h5", "h6",
        // Lists
        "ul", "ol", "li", "dl", "dt", "dd", "menu",
        // Tables
        "table", "thead", "tbody", "tfoot", "tr", "td", "th", "caption", "col", "colgroup",
        // Forms
        "form", "input", "button", "select", "option", "optgroup", "textarea",
        "label", "fieldset", "legend", "datalist", "output", "progress", "meter",
        // Inline/text semantics
        "span", "a", "em", "strong", "small", "s", "cite", "q", "dfn", "abbr",
        "code", "var", "samp", "kbd", "sub", "sup", "i", "b", "u", "mark",
        "ruby", "rt", "rp", "bdi", "bdo", "br", "wbr",
        // Edits
        "ins", "del",
        // Embedded content
        "img", "picture", "video", "audio", "source", "track",
        "iframe", "embed", "object", "param", "canvas", "svg", "math", "map", "area",
        // Interactive
        "details", "summary", "dialog",
        // Scripting (handled separately but listed for completeness)
        "script", "noscript", "template", "slot", "style",
        // Time and data
        "time", "data"
    ));

    public boolean isValidHtmlElement(String tagName) {
        if (tagName == null || tagName.isEmpty()) {
            return false;
        }
        String lowerName = tagName.toLowerCase();
        if (VALID_HTML_ELEMENTS.contains(lowerName)) {
            return true;
        }
        if (lowerName.contains("-")) {
            return true;
        }
        return false;
    }
}

options { tokenVocab = Jinja2Lexer; }

template
    : HTML_DOCTYPE? content* EOF                 # TemplateNode
    ;

content
    : htmlStyleTag                               # StyleContent
    | htmlScriptTag                              # ScriptContent
    | htmlElement                                # HtmlElementContent
    | htmlCloseTag                               # HtmlCloseContent
    | jinjaVar                                   # JinjaVarContent
    | jinjaControl                               # JinjaControlContent
    | HTML_COMMENT                               # HtmlCommentContent
    | HTML_TEXT                                  # HtmlTextContent
    ;

htmlStyleTag: HTML_STYLE_OPEN HTML_STYLE_CONTENT;
htmlScriptTag: HTML_SCRIPT_OPEN HTML_SCRIPT_CONTENT;

htmlElement
    : HTML_OPEN tag=HTML_TAG_NAME {isValidHtmlElement($tag.text)}? htmlAttribute* HTML_TAG_CLOSE htmlContent* htmlCloseTag?
    | HTML_OPEN tag=HTML_TAG_NAME {isValidHtmlElement($tag.text)}? htmlAttribute* HTML_TAG_SLASH_CLOSE
    ;

htmlCloseTag
    : HTML_OPEN HTML_TAG_SLASH tag=HTML_TAG_NAME {isValidHtmlElement($tag.text)}? HTML_TAG_CLOSE
    ;

htmlAttribute
    : HTML_TAG_NAME (HTML_TAG_EQUALS attrValue)?
    ;

attrValue
    : HTML_ATTR_VALUE_DQ attrValueDQContent* HTML_ATTR_DQ_END
    | HTML_ATTR_VALUE_SQ attrValueSQContent* HTML_ATTR_SQ_END
    | HTML_ATTR_VALUE_UNQUOTED
    ;

attrValueDQContent
    : HTML_ATTR_DQ_TEXT
    | jinjaInAttr
    ;

attrValueSQContent
    : HTML_ATTR_SQ_TEXT
    | jinjaInAttr
    ;

htmlContent
    : htmlStyleTag
    | htmlScriptTag
    | htmlElement
    | jinjaVar
    | jinjaControl
    | HTML_COMMENT
    | HTML_TEXT
    ;

jinjaVar
    : JINJA_VAR_OPEN jinjaExpression (JINJA_VAR_PIPE jinjaFilter)* JINJA_VAR_CLOSE
    ;

jinjaFilter
    : JINJA_VAR_IDENTIFIER (JINJA_VAR_LPAREN jinjaArgList? JINJA_VAR_RPAREN)?
    ;

jinjaExpression
    : jinjaExpression JINJA_VAR_OR jinjaExpression                      # JinjaOrExpr
    | jinjaExpression JINJA_VAR_AND jinjaExpression                     # JinjaAndExpr
    | JINJA_VAR_NOT jinjaExpression                                      # JinjaNotExpr
    | jinjaExpression comparisonOp jinjaExpression                      # JinjaComparisonExpr
    | jinjaExpression JINJA_VAR_IN jinjaExpression                      # JinjaInExpr
    | jinjaExpression JINJA_VAR_IS jinjaExpression                      # JinjaIsExpr
    | jinjaExpression JINJA_VAR_PLUS jinjaExpression                    # JinjaAddExpr
    | jinjaExpression JINJA_VAR_MINUS jinjaExpression                   # JinjaSubExpr
    | jinjaExpression JINJA_VAR_STAR jinjaExpression                    # JinjaMulExpr
    | jinjaExpression JINJA_VAR_SLASH jinjaExpression                   # JinjaDivExpr
    | jinjaExpression JINJA_VAR_DOUBLE_SLASH jinjaExpression           # JinjaFloorDivExpr
    | jinjaExpression JINJA_VAR_PERCENT jinjaExpression                 # JinjaModExpr
    | jinjaExpression JINJA_VAR_POWER jinjaExpression                   # JinjaPowerExpr
    | jinjaExpression JINJA_VAR_DOT JINJA_VAR_IDENTIFIER               # JinjaMemberAccessExpr
    | jinjaExpression JINJA_VAR_LBRACKET jinjaExpression JINJA_VAR_RBRACKET  # JinjaIndexAccessExpr
    | JINJA_VAR_IDENTIFIER JINJA_VAR_LPAREN jinjaArgList? JINJA_VAR_RPAREN   # JinjaFunctionCallExpr
    | JINJA_VAR_LBRACKET jinjaArgList? JINJA_VAR_RBRACKET              # JinjaListExpr
    | JINJA_VAR_LBRACE jinjaDictItems? JINJA_VAR_RBRACE                # JinjaDictExpr
    | JINJA_VAR_LPAREN jinjaExpression JINJA_VAR_RPAREN                # JinjaParenExpr
    | JINJA_VAR_NUMBER                                                   # JinjaNumberExpr
    | JINJA_VAR_STRING                                                   # JinjaStringExpr
    | JINJA_VAR_TRUE                                                     # JinjaTrueExpr
    | JINJA_VAR_FALSE                                                    # JinjaFalseExpr
    | JINJA_VAR_NONE                                                     # JinjaNoneExpr
    | JINJA_VAR_IDENTIFIER                                               # JinjaIdentifierExpr
    ;

comparisonOp
    : JINJA_VAR_EQ | JINJA_VAR_NEQ | JINJA_VAR_LT | JINJA_VAR_GT | JINJA_VAR_LTE | JINJA_VAR_GTE
    ;

jinjaArgList: jinjaExpression (JINJA_VAR_COMMA jinjaExpression)* JINJA_VAR_COMMA?;

jinjaDictItems: jinjaDictItem (JINJA_VAR_COMMA jinjaDictItem)* JINJA_VAR_COMMA?;

jinjaDictItem: jinjaExpression JINJA_VAR_COLON jinjaExpression;

jinjaInAttr
    : HTML_ATTR_DQ_JINJA_OPEN jinjaInAttrExpr (JINJA_IN_ATTR_PIPE JINJA_IN_ATTR_IDENTIFIER)* JINJA_IN_ATTR_CLOSE
    | HTML_ATTR_SQ_JINJA_OPEN jinjaInAttrExpr (JINJA_IN_ATTR_PIPE JINJA_IN_ATTR_IDENTIFIER)* JINJA_IN_ATTR_CLOSE
    ;

jinjaInAttrExpr
    : JINJA_IN_ATTR_IDENTIFIER                                                              # JinjaAttrIdentifier
    | JINJA_IN_ATTR_NUMBER                                                                  # JinjaAttrNumber
    | JINJA_IN_ATTR_STRING                                                                  # JinjaAttrString
    | jinjaInAttrExpr JINJA_IN_ATTR_DOT JINJA_IN_ATTR_IDENTIFIER                          # JinjaAttrMemberAccess
    | jinjaInAttrExpr JINJA_IN_ATTR_LBRACKET jinjaInAttrExpr JINJA_IN_ATTR_RBRACKET      # JinjaAttrIndexAccess
    | JINJA_IN_ATTR_IDENTIFIER JINJA_IN_ATTR_LPAREN jinjaAttrArgList? JINJA_IN_ATTR_RPAREN # JinjaAttrFunctionCall
    ;

jinjaAttrArgList: jinjaInAttrExpr (JINJA_IN_ATTR_COMMA jinjaInAttrExpr)*;

jinjaControl
    : jinjaIf    # JinjaIfControl
    | jinjaFor   # JinjaForControl
    | jinjaBlock # JinjaBlockControl
    | jinjaExtends  # JinjaExtendsControl
    | jinjaInclude  # JinjaIncludeControl
    | jinjaImport  # JinjaImportControl
    | jinjaSet  # JinjaSetControl
    | jinjaMacro # JinjaMacroControl
    | jinjaCall  # JinjaCallControl
    | jinjaFilterBlock # JinjaFilterBlockControl
    | jinjaWith  # JinjaWithControl
    | jinjaAutoescape # JinjaAutoescapeControl
    ;

jinjaIf
    : JINJA_STMT_OPEN JINJA_STMT_IF jinjaStmtExpression JINJA_STMT_CLOSE
      content*
      (JINJA_STMT_OPEN JINJA_STMT_ELIF jinjaStmtExpression JINJA_STMT_CLOSE content*)*
      (JINJA_STMT_OPEN JINJA_STMT_ELSE JINJA_STMT_CLOSE content*)?
      JINJA_STMT_OPEN JINJA_STMT_ENDIF JINJA_STMT_CLOSE
      # JinjaIfStmt
    ;


jinjaFor
    : JINJA_STMT_OPEN JINJA_STMT_FOR jinjaStmtTarget JINJA_STMT_IN jinjaStmtExpression JINJA_STMT_CLOSE
      content*
      (JINJA_STMT_OPEN JINJA_STMT_ELSE JINJA_STMT_CLOSE content*)?
      JINJA_STMT_OPEN JINJA_STMT_ENDFOR JINJA_STMT_CLOSE
      # JinjaForStmt
    ;


jinjaStmtTarget
    : JINJA_STMT_IDENTIFIER
    | JINJA_STMT_LPAREN JINJA_STMT_IDENTIFIER (JINJA_STMT_COMMA JINJA_STMT_IDENTIFIER)* JINJA_STMT_RPAREN
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
    : JINJA_STMT_OPEN JINJA_STMT_WITH jinjaStmtExpression (JINJA_STMT_COMMA jinjaStmtExpression)* JINJA_STMT_CLOSE
      content*
      JINJA_STMT_OPEN JINJA_STMT_ENDWITH JINJA_STMT_CLOSE
    ;

jinjaAutoescape
    : JINJA_STMT_OPEN JINJA_STMT_AUTOESCAPE jinjaStmtExpression JINJA_STMT_CLOSE
      content*
      JINJA_STMT_OPEN JINJA_STMT_ENDAUTOESCAPE JINJA_STMT_CLOSE
    ;

jinjaStmtParamList: JINJA_STMT_IDENTIFIER (JINJA_STMT_COMMA JINJA_STMT_IDENTIFIER)*;

jinjaStmtExpression
    : jinjaStmtExpression JINJA_STMT_OR jinjaStmtExpression                           # JinjaStmtOrExpr
    | jinjaStmtExpression JINJA_STMT_AND jinjaStmtExpression                          # JinjaStmtAndExpr
    | JINJA_STMT_NOT jinjaStmtExpression                                               # JinjaStmtNotExpr
    | jinjaStmtExpression stmtComparisonOp jinjaStmtExpression                        # JinjaStmtComparisonExpr
    | jinjaStmtExpression JINJA_STMT_IN jinjaStmtExpression                           # JinjaStmtInExpr
    | jinjaStmtExpression JINJA_STMT_IS jinjaStmtExpression                           # JinjaStmtIsExpr
    | jinjaStmtExpression JINJA_STMT_PLUS jinjaStmtExpression                         # JinjaStmtAddExpr
    | jinjaStmtExpression JINJA_STMT_MINUS jinjaStmtExpression                        # JinjaStmtSubExpr
    | jinjaStmtExpression JINJA_STMT_STAR jinjaStmtExpression                         # JinjaStmtMulExpr
    | jinjaStmtExpression JINJA_STMT_SLASH jinjaStmtExpression                        # JinjaStmtDivExpr
    | jinjaStmtExpression JINJA_STMT_DOUBLE_SLASH jinjaStmtExpression                # JinjaStmtFloorDivExpr
    | jinjaStmtExpression JINJA_STMT_PERCENT jinjaStmtExpression                      # JinjaStmtModExpr
    | jinjaStmtExpression JINJA_STMT_POWER jinjaStmtExpression                        # JinjaStmtPowerExpr
    | jinjaStmtExpression JINJA_STMT_DOT JINJA_STMT_IDENTIFIER                       # JinjaStmtMemberAccessExpr
    | jinjaStmtExpression JINJA_STMT_LBRACKET jinjaStmtExpression JINJA_STMT_RBRACKET # JinjaStmtIndexAccessExpr
    | JINJA_STMT_IDENTIFIER JINJA_STMT_LPAREN jinjaStmtArgList? JINJA_STMT_RPAREN   # JinjaStmtFunctionCallExpr
    | JINJA_STMT_LBRACKET jinjaStmtArgList? JINJA_STMT_RBRACKET                     # JinjaStmtListExpr
    | JINJA_STMT_LBRACE jinjaStmtDictItems? JINJA_STMT_RBRACE                       # JinjaStmtDictExpr
    | JINJA_STMT_LPAREN jinjaStmtExpression JINJA_STMT_RPAREN                        # JinjaStmtParenExpr
    | JINJA_STMT_NUMBER                                                                # JinjaStmtNumberExpr
    | JINJA_STMT_STRING                                                                # JinjaStmtStringExpr
    | JINJA_STMT_TRUE                                                                  # JinjaStmtTrueExpr
    | JINJA_STMT_FALSE                                                                 # JinjaStmtFalseExpr
    | JINJA_STMT_NONE                                                                  # JinjaStmtNoneExpr
    | JINJA_STMT_IDENTIFIER                                                            # JinjaStmtIdentifierExpr
    ;

stmtComparisonOp
    : JINJA_STMT_EQ | JINJA_STMT_NEQ | JINJA_STMT_LT | JINJA_STMT_GT | JINJA_STMT_LTE | JINJA_STMT_GTE
    ;

jinjaStmtArgList: jinjaStmtExpression (JINJA_STMT_COMMA jinjaStmtExpression)* JINJA_STMT_COMMA?;

jinjaStmtDictItems: jinjaStmtDictItem (JINJA_STMT_COMMA jinjaStmtDictItem)* JINJA_STMT_COMMA?;

jinjaStmtDictItem: jinjaStmtExpression JINJA_STMT_COLON jinjaStmtExpression;
