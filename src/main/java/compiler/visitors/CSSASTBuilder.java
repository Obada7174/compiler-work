package compiler.visitors;

import compiler.ast.css.*;
import grammar.CSSParser;
import grammar.CSSParserBaseVisitor;

public class CSSASTBuilder extends CSSParserBaseVisitor<CSSASTNode> {
    @Override
    public CSSASTNode visitStylesheet(CSSParser.StylesheetContext ctx) {
        int line = ctx.start != null ? ctx.start.getLine() : 1;
        CSSStylesheetNode stylesheet = new CSSStylesheetNode(line);

        // Process rule sets
        for (CSSParser.RuleSetContext ruleSetCtx : ctx.ruleSet()) {
            CSSRuleSetNode ruleSet = (CSSRuleSetNode) visit(ruleSetCtx);
            if (ruleSet != null) {
                stylesheet.addRuleSet(ruleSet);
            }
        }

        // Process at-rules
        for (CSSParser.AtRuleContext atRuleCtx : ctx.atRule()) {
            CSSAtRuleNode atRule = (CSSAtRuleNode) visit(atRuleCtx);
            if (atRule != null) {
                stylesheet.addAtRule(atRule);
            }
        }

        return stylesheet;
    }

    @Override
    public CSSASTNode visitAtRule(CSSParser.AtRuleContext ctx) {
        int line = ctx.start.getLine();
        String keyword = ctx.AT_KEYWORD().getText(); // e.g., "@media", "@import"

        CSSAtRuleNode atRule = new CSSAtRuleNode(keyword, line);

        // Process prelude (the part between @keyword and { or ;)
        if (ctx.atRulePrelude() != null) {
            CSSAtRulePreludeNode prelude = (CSSAtRulePreludeNode) visit(ctx.atRulePrelude());
            if (prelude != null) {
                atRule.setPrelude(prelude);
            }
        }

        // Process nested rule sets (for @media, @supports, etc.)
        for (CSSParser.RuleSetContext ruleSetCtx : ctx.ruleSet()) {
            CSSRuleSetNode ruleSet = (CSSRuleSetNode) visit(ruleSetCtx);
            if (ruleSet != null) {
                atRule.addRuleSet(ruleSet);
            }
        }

        return atRule;
    }


    @Override
    public CSSASTNode visitAtRulePrelude(CSSParser.AtRulePreludeContext ctx) {
        int line = ctx.start.getLine();
        CSSAtRulePreludeNode prelude = new CSSAtRulePreludeNode(line);

        StringBuilder tokenBuilder = new StringBuilder();
        for (var child : ctx.children) {
            String text = child.getText();
            if (text == null || text.trim().isEmpty()) continue;
            if ("()".contains(text)) {
                if (tokenBuilder.length() > 0) {
                    prelude.addToken(tokenBuilder.toString());
                    tokenBuilder.setLength(0);
                }
                prelude.addToken(text);
            } else {
                if (tokenBuilder.length() > 0) tokenBuilder.append(" ");
                tokenBuilder.append(text);
            }
        }

        if (tokenBuilder.length() > 0) {
            prelude.addToken(tokenBuilder.toString());
        }

        return prelude;
    }

    // ==================== RULE SETS ====================

    @Override
    public CSSASTNode visitRuleSetNode(CSSParser.RuleSetNodeContext ctx) {
        int line = ctx.start.getLine();
        CSSRuleSetNode ruleSet = new CSSRuleSetNode(line);

        // Process selector list
        if (ctx.selectorList() != null) {
            CSSSelectorListNode selectorList = (CSSSelectorListNode) visit(ctx.selectorList());
            ruleSet.setSelectorList(selectorList);
        }

        // Process declarations
        for (CSSParser.DeclarationContext declCtx : ctx.declaration()) {
            CSSDeclarationNode declaration = (CSSDeclarationNode) visit(declCtx);
            if (declaration != null) {
                ruleSet.addDeclaration(declaration);
            }
        }

        return ruleSet;
    }

    // ==================== SELECTORS ====================

    @Override
    public CSSASTNode visitSelectorList(CSSParser.SelectorListContext ctx) {
        int line = ctx.start.getLine();
        CSSSelectorListNode selectorList = new CSSSelectorListNode(line);

        for (CSSParser.SelectorContext selectorCtx : ctx.selector()) {
            CSSSelectorNode selector = (CSSSelectorNode) visit(selectorCtx);
            if (selector != null) {
                selectorList.addSelector(selector);
            }
        }

        return selectorList;
    }

    @Override
    public CSSASTNode visitSelectorNode(CSSParser.SelectorNodeContext ctx) {
        int line = ctx.start.getLine();
        CSSSelectorNode selector = new CSSSelectorNode(line);

        // Process simple selectors and combinators
        for (int i = 0; i < ctx.simpleSelector().size(); i++) {
            CSSSimpleSelectorNode simpleSelector = (CSSSimpleSelectorNode) visit(ctx.simpleSelector(i));
            if (simpleSelector != null) {
                selector.addSimpleSelector(simpleSelector);
            }

            // Add combinator if it exists (between simple selectors)
            if (i < ctx.combinator().size()) {
                CSSCombinatorNode combinator = (CSSCombinatorNode) visit(ctx.combinator(i));
                if (combinator != null) {
                    selector.addCombinator(combinator);
                }
            }
        }

        return selector;
    }

    @Override
    public CSSASTNode visitSimpleSelector(CSSParser.SimpleSelectorContext ctx) {
        int line = ctx.start.getLine();
        CSSSimpleSelectorNode simpleSelector = new CSSSimpleSelectorNode(line);

        for (CSSParser.SelectorPartContext partCtx : ctx.selectorPart()) {
            CSSSelectorPartNode part = (CSSSelectorPartNode) visit(partCtx);
            if (part != null) {
                simpleSelector.addSelectorPart(part);
            }
        }

        return simpleSelector;
    }

    @Override
    public CSSASTNode visitCombinator(CSSParser.CombinatorContext ctx) {
        int line = ctx.start.getLine();
        String symbol = ctx.getText(); // ">", "+", or "~"

        CSSCombinatorNode.CombinatorType type;
        switch (symbol) {
            case ">" -> type = CSSCombinatorNode.CombinatorType.CHILD;
            case "+" -> type = CSSCombinatorNode.CombinatorType.ADJACENT_SIBLING;
            case "~" -> type = CSSCombinatorNode.CombinatorType.GENERAL_SIBLING;
            default -> type = CSSCombinatorNode.CombinatorType.DESCENDANT;
        }

        return new CSSCombinatorNode(type, line);
    }

    // ==================== SELECTOR PARTS ====================

    @Override
    public CSSASTNode visitElementSelector(CSSParser.ElementSelectorContext ctx) {
        int line = ctx.start.getLine();
        String elementName = ctx.IDENTIFIER().getText();
        return new CSSElementSelectorNode(elementName, line);
    }

    @Override
    public CSSASTNode visitClassSelector(CSSParser.ClassSelectorContext ctx) {
        int line = ctx.start.getLine();
        String className = ctx.IDENTIFIER().getText();
        return new CSSClassSelectorNode(className, line);
    }

    @Override
    public CSSASTNode visitIdSelector(CSSParser.IdSelectorContext ctx) {
        int line = ctx.start.getLine();
        String idName = ctx.IDENTIFIER().getText();
        return new CSSIdSelectorNode(idName, line);
    }

    @Override
    public CSSASTNode visitUniversalSelector(CSSParser.UniversalSelectorContext ctx) {
        int line = ctx.start.getLine();
        return new CSSUniversalSelectorNode(line);
    }

    @Override
    public CSSASTNode visitPseudoClassSelector(CSSParser.PseudoClassSelectorContext ctx) {
        int line = ctx.start.getLine();
        String pseudoClass = ctx.PSEUDO_CLASS().getText(); // e.g., ":hover"
        return new CSSPseudoClassSelectorNode(pseudoClass, line);
    }

    @Override
    public CSSASTNode visitPseudoElementSelector(CSSParser.PseudoElementSelectorContext ctx) {
        int line = ctx.start.getLine();
        String pseudoElement = ctx.PSEUDO_ELEMENT().getText(); // e.g., "::before"
        return new CSSPseudoElementSelectorNode(pseudoElement, line);
    }

    @Override
    public CSSASTNode visitAttributeSelector(CSSParser.AttributeSelectorContext ctx) {
        int line = ctx.start.getLine();
        String attributeName = ctx.IDENTIFIER().getText();

        // Get operator if present (supports all attribute operators)
        String operator = null;
        if (ctx.attributeOperator() != null) {
            operator = ctx.attributeOperator().getText();
        }

        // Get attribute value if present and create appropriate value node
        CSSValueNode valueNode = null;

        if (ctx.attributeValue() != null) {
            valueNode = new CSSValueNode(line);

            CSSParser.AttributeValueContext valueCtx = ctx.attributeValue();
            String rawValue = valueCtx.getText();

            if (valueCtx.STRING() != null) {
                String stringValue = rawValue.substring(1, rawValue.length() - 1);
                valueNode.addComponent(new CSSStringValueNode(stringValue, line));

            } else if (valueCtx.NUMBER() != null) {
                valueNode.addComponent(new CSSNumberValueNode(rawValue, null, line));

            } else if (valueCtx.PERCENTAGE() != null) {
                valueNode.addComponent(new CSSPercentageValueNode(rawValue, line));

            } else if (valueCtx.IDENTIFIER() != null) {
                valueNode.addComponent(new CSSIdentifierValueNode(rawValue, line));
            }
        }

        // Use 4-parameter constructor for consistency
        return new CSSAttributeSelectorNode(attributeName, operator, valueNode, line);
    }

    // ==================== NEW: Helper Rules ====================

    @Override
    public CSSASTNode visitAttributeOperator(CSSParser.AttributeOperatorContext ctx) {
        // This is a helper rule that returns text, not an AST node
        // The parent visitAttributeSelector handles this
        return null;
    }


    @Override
    public CSSASTNode visitAttributeValue(CSSParser.AttributeValueContext ctx) {
        // This is a helper rule, parent handles the value extraction
        return null;
    }

    // ==================== DECLARATIONS ====================

    @Override
    public CSSASTNode visitDeclarationNode(CSSParser.DeclarationNodeContext ctx) {
        int line = ctx.start.getLine();

        // Get property name (could be IDENTIFIER or CSS_VAR)
        String property;
        if (ctx.IDENTIFIER() != null) {
            property = ctx.IDENTIFIER().getText();
        } else {
            property = ctx.CSS_VAR().getText(); // e.g., "--my-var"
        }

        CSSDeclarationNode declaration = new CSSDeclarationNode(property, line);

        // Process value
        if (ctx.value() != null) {
            CSSValueNode value = (CSSValueNode) visit(ctx.value());
            declaration.setValue(value);
        }

        // Check for !important flag
        boolean important = ctx.IMPORTANT() != null;
        declaration.setImportant(important);

        return declaration;
    }

    // ==================== VALUES ====================

    @Override
    public CSSASTNode visitValue(CSSParser.ValueContext ctx) {
        int line = ctx.start.getLine();
        CSSValueNode value = new CSSValueNode(line);

        for (CSSParser.ValueComponentContext componentCtx : ctx.valueComponent()) {
            CSSValueComponentNode component = (CSSValueComponentNode) visit(componentCtx);
            if (component != null) {
                // دمج الرقم مع الوحدة كمكون واحد
                if (component instanceof CSSNumberValueNode numberNode) {
                    String combined = numberNode.getNumber();
                    if (numberNode.getUnit() != null) {
                        combined += numberNode.getUnit();
                    }
                    CSSNumberValueNode newNode = new CSSNumberValueNode(combined, null, numberNode.getLineNumber());
                    value.addComponent(newNode);
                } else {
                    value.addComponent(component);
                }
            }
        }

        return value;
    }

    @Override
    public CSSASTNode visitNumberValue(CSSParser.NumberValueContext ctx) {
        int line = ctx.start.getLine();
        String number = ctx.NUMBER().getText();
        String unit = ctx.UNIT() != null ? ctx.UNIT().getText() : "";
        // دمج الرقم مع الوحدة مباشرة في النص
        return new CSSNumberValueNode(number + unit, null, line);
    }

    @Override
    public CSSASTNode visitPercentageValue(CSSParser.PercentageValueContext ctx) {
        int line = ctx.start.getLine();
        String percentage = ctx.PERCENTAGE().getText();
        return new CSSPercentageValueNode(percentage, line);
    }

    @Override
    public CSSASTNode visitColorValue(CSSParser.ColorValueContext ctx) {
        int line = ctx.start.getLine();
        String colorHex = ctx.COLOR_HEX().getText();
        return new CSSColorValueNode(colorHex, line);
    }

    @Override
    public CSSASTNode visitStringValue(CSSParser.StringValueContext ctx) {
        int line = ctx.start.getLine();
        String stringValue = ctx.STRING().getText();
        // Remove quotes
        if (stringValue.length() >= 2) {
            stringValue = stringValue.substring(1, stringValue.length() - 1);
        }
        return new CSSStringValueNode(stringValue, line);
    }

    @Override
    public CSSASTNode visitIdentifierValue(CSSParser.IdentifierValueContext ctx) {
        int line = ctx.start.getLine();
        String identifier = ctx.IDENTIFIER().getText();
        return new CSSIdentifierValueNode(identifier, line);
    }

    @Override
    public CSSASTNode visitCssVariableValue(CSSParser.CssVariableValueContext ctx) {
        int line = ctx.start.getLine();
        String varName = ctx.CSS_VAR().getText();
        return new CSSCssVariableNode(varName, line);
    }

    @Override
    public CSSASTNode visitUrlValue(CSSParser.UrlValueContext ctx) {
        int line = ctx.start.getLine();
        String url = ctx.URL().getText();
        return new CSSUrlValueNode(url, line);
    }

    @Override
    public CSSASTNode visitFunctionValue(CSSParser.FunctionValueContext ctx) {
        return visit(ctx.functionCall());
    }

    @Override
    public CSSASTNode visitFunctionCallNode(CSSParser.FunctionCallNodeContext ctx) {
        int line = ctx.start.getLine();
        String functionName = ctx.IDENTIFIER().getText();

        CSSFunctionCallNode functionCall = new CSSFunctionCallNode(functionName, line);

        // Process function arguments (expressions)
        if (ctx.functionArguments() != null) {
            for (CSSParser.ExpressionContext exprCtx : ctx.functionArguments().expression()) {
                CSSExpressionNode expr = (CSSExpressionNode) visit(exprCtx);
                if (expr != null) {
                    functionCall.addArgument(expr);
                }
            }
        }

        return functionCall;
    }

    @Override
    public CSSASTNode visitPlusOperator(CSSParser.PlusOperatorContext ctx) {
        int line = ctx.start.getLine();
        return new CSSOperatorNode(CSSOperatorNode.OperatorType.PLUS, line);
    }

    @Override
    public CSSASTNode visitMinusOperator(CSSParser.MinusOperatorContext ctx) {
        int line = ctx.start.getLine();
        return new CSSOperatorNode(CSSOperatorNode.OperatorType.MINUS, line);
    }

    @Override
    public CSSASTNode visitMultiplyOperator(CSSParser.MultiplyOperatorContext ctx) {
        int line = ctx.start.getLine();
        return new CSSOperatorNode(CSSOperatorNode.OperatorType.MULTIPLY, line);
    }

    @Override
    public CSSASTNode visitDivideOperator(CSSParser.DivideOperatorContext ctx) {
        int line = ctx.start.getLine();
        return new CSSOperatorNode(CSSOperatorNode.OperatorType.DIVIDE, line);
    }

    @Override
    public CSSASTNode visitCommaValue(CSSParser.CommaValueContext ctx) {
        int line = ctx.start.getLine();
        return new CSSOperatorNode(CSSOperatorNode.OperatorType.COMMA, line);
    }

    @Override
    public CSSASTNode visitLeftParenValue(CSSParser.LeftParenValueContext ctx) {
        return new CSSParenNode(
                CSSParenNode.ParenType.LEFT,
                ctx.start.getLine()
        );
    }

    @Override
    public CSSASTNode visitRightParenValue(CSSParser.RightParenValueContext ctx) {
        return new CSSParenNode(
                CSSParenNode.ParenType.RIGHT,
                ctx.start.getLine()
        );
    }



    // ==================== EXPRESSIONS ====================

    @Override
    public CSSASTNode visitNumberExpr(CSSParser.NumberExprContext ctx) {
        int line = ctx.start.getLine();
        String number = ctx.NUMBER().getText();
        String unit = ctx.UNIT() != null ? ctx.UNIT().getText() : null;
        return new CSSNumberExpressionNode(number, unit, line);
    }

    @Override
    public CSSASTNode visitPercentageExpr(CSSParser.PercentageExprContext ctx) {
        int line = ctx.start.getLine();
        String percentage = ctx.PERCENTAGE().getText();
        return new CSSPercentageExpressionNode(percentage, line);
    }

    @Override
    public CSSASTNode visitParenExpr(CSSParser.ParenExprContext ctx) {
        int line = ctx.start.getLine();
        CSSExpressionNode innerExpr = (CSSExpressionNode) visit(ctx.expression());
        return new CSSParenExpressionNode(innerExpr, line);
    }

    @Override
    public CSSASTNode visitAddExpr(CSSParser.AddExprContext ctx) {
        int line = ctx.start.getLine();
        CSSExpressionNode left = (CSSExpressionNode) visit(ctx.expression(0));
        CSSExpressionNode right = (CSSExpressionNode) visit(ctx.expression(1));
        return new CSSBinaryExpressionNode(left, CSSBinaryExpressionNode.Operator.ADD, right, line);
    }

    @Override
    public CSSASTNode visitSubExpr(CSSParser.SubExprContext ctx) {
        int line = ctx.start.getLine();
        CSSExpressionNode left = (CSSExpressionNode) visit(ctx.expression(0));
        CSSExpressionNode right = (CSSExpressionNode) visit(ctx.expression(1));
        return new CSSBinaryExpressionNode(left, CSSBinaryExpressionNode.Operator.SUBTRACT, right, line);
    }

    @Override
    public CSSASTNode visitMulExpr(CSSParser.MulExprContext ctx) {
        int line = ctx.start.getLine();
        CSSExpressionNode left = (CSSExpressionNode) visit(ctx.expression(0));
        CSSExpressionNode right = (CSSExpressionNode) visit(ctx.expression(1));
        return new CSSBinaryExpressionNode(left, CSSBinaryExpressionNode.Operator.MULTIPLY, right, line);
    }

    @Override
    public CSSASTNode visitDivExpr(CSSParser.DivExprContext ctx) {
        int line = ctx.start.getLine();
        CSSExpressionNode left = (CSSExpressionNode) visit(ctx.expression(0));
        CSSExpressionNode right = (CSSExpressionNode) visit(ctx.expression(1));
        return new CSSBinaryExpressionNode(left, CSSBinaryExpressionNode.Operator.DIVIDE, right, line);
    }
}
