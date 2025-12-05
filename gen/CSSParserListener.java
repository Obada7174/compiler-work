// Generated from D:/codes-folder/compiler/Test1/grammar/CSSParser.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link CSSParser}.
 */
public interface CSSParserListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link CSSParser#stylesheet}.
	 * @param ctx the parse tree
	 */
	void enterStylesheet(CSSParser.StylesheetContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSSParser#stylesheet}.
	 * @param ctx the parse tree
	 */
	void exitStylesheet(CSSParser.StylesheetContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSSParser#atRule}.
	 * @param ctx the parse tree
	 */
	void enterAtRule(CSSParser.AtRuleContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSSParser#atRule}.
	 * @param ctx the parse tree
	 */
	void exitAtRule(CSSParser.AtRuleContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSSParser#ruleSet}.
	 * @param ctx the parse tree
	 */
	void enterRuleSet(CSSParser.RuleSetContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSSParser#ruleSet}.
	 * @param ctx the parse tree
	 */
	void exitRuleSet(CSSParser.RuleSetContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSSParser#selectorList}.
	 * @param ctx the parse tree
	 */
	void enterSelectorList(CSSParser.SelectorListContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSSParser#selectorList}.
	 * @param ctx the parse tree
	 */
	void exitSelectorList(CSSParser.SelectorListContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSSParser#selector}.
	 * @param ctx the parse tree
	 */
	void enterSelector(CSSParser.SelectorContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSSParser#selector}.
	 * @param ctx the parse tree
	 */
	void exitSelector(CSSParser.SelectorContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSSParser#simpleSelector}.
	 * @param ctx the parse tree
	 */
	void enterSimpleSelector(CSSParser.SimpleSelectorContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSSParser#simpleSelector}.
	 * @param ctx the parse tree
	 */
	void exitSimpleSelector(CSSParser.SimpleSelectorContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ElementSelector}
	 * labeled alternative in {@link CSSParser#selectorPart}.
	 * @param ctx the parse tree
	 */
	void enterElementSelector(CSSParser.ElementSelectorContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ElementSelector}
	 * labeled alternative in {@link CSSParser#selectorPart}.
	 * @param ctx the parse tree
	 */
	void exitElementSelector(CSSParser.ElementSelectorContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ClassSelector}
	 * labeled alternative in {@link CSSParser#selectorPart}.
	 * @param ctx the parse tree
	 */
	void enterClassSelector(CSSParser.ClassSelectorContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ClassSelector}
	 * labeled alternative in {@link CSSParser#selectorPart}.
	 * @param ctx the parse tree
	 */
	void exitClassSelector(CSSParser.ClassSelectorContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IdSelector}
	 * labeled alternative in {@link CSSParser#selectorPart}.
	 * @param ctx the parse tree
	 */
	void enterIdSelector(CSSParser.IdSelectorContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IdSelector}
	 * labeled alternative in {@link CSSParser#selectorPart}.
	 * @param ctx the parse tree
	 */
	void exitIdSelector(CSSParser.IdSelectorContext ctx);
	/**
	 * Enter a parse tree produced by the {@code UniversalSelector}
	 * labeled alternative in {@link CSSParser#selectorPart}.
	 * @param ctx the parse tree
	 */
	void enterUniversalSelector(CSSParser.UniversalSelectorContext ctx);
	/**
	 * Exit a parse tree produced by the {@code UniversalSelector}
	 * labeled alternative in {@link CSSParser#selectorPart}.
	 * @param ctx the parse tree
	 */
	void exitUniversalSelector(CSSParser.UniversalSelectorContext ctx);
	/**
	 * Enter a parse tree produced by the {@code PseudoClassSelector}
	 * labeled alternative in {@link CSSParser#selectorPart}.
	 * @param ctx the parse tree
	 */
	void enterPseudoClassSelector(CSSParser.PseudoClassSelectorContext ctx);
	/**
	 * Exit a parse tree produced by the {@code PseudoClassSelector}
	 * labeled alternative in {@link CSSParser#selectorPart}.
	 * @param ctx the parse tree
	 */
	void exitPseudoClassSelector(CSSParser.PseudoClassSelectorContext ctx);
	/**
	 * Enter a parse tree produced by the {@code PseudoElementSelector}
	 * labeled alternative in {@link CSSParser#selectorPart}.
	 * @param ctx the parse tree
	 */
	void enterPseudoElementSelector(CSSParser.PseudoElementSelectorContext ctx);
	/**
	 * Exit a parse tree produced by the {@code PseudoElementSelector}
	 * labeled alternative in {@link CSSParser#selectorPart}.
	 * @param ctx the parse tree
	 */
	void exitPseudoElementSelector(CSSParser.PseudoElementSelectorContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AttributeSelector}
	 * labeled alternative in {@link CSSParser#selectorPart}.
	 * @param ctx the parse tree
	 */
	void enterAttributeSelector(CSSParser.AttributeSelectorContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AttributeSelector}
	 * labeled alternative in {@link CSSParser#selectorPart}.
	 * @param ctx the parse tree
	 */
	void exitAttributeSelector(CSSParser.AttributeSelectorContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSSParser#combinator}.
	 * @param ctx the parse tree
	 */
	void enterCombinator(CSSParser.CombinatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSSParser#combinator}.
	 * @param ctx the parse tree
	 */
	void exitCombinator(CSSParser.CombinatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSSParser#declaration}.
	 * @param ctx the parse tree
	 */
	void enterDeclaration(CSSParser.DeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSSParser#declaration}.
	 * @param ctx the parse tree
	 */
	void exitDeclaration(CSSParser.DeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSSParser#value}.
	 * @param ctx the parse tree
	 */
	void enterValue(CSSParser.ValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSSParser#value}.
	 * @param ctx the parse tree
	 */
	void exitValue(CSSParser.ValueContext ctx);
	/**
	 * Enter a parse tree produced by the {@code DimensionValue}
	 * labeled alternative in {@link CSSParser#valueItem}.
	 * @param ctx the parse tree
	 */
	void enterDimensionValue(CSSParser.DimensionValueContext ctx);
	/**
	 * Exit a parse tree produced by the {@code DimensionValue}
	 * labeled alternative in {@link CSSParser#valueItem}.
	 * @param ctx the parse tree
	 */
	void exitDimensionValue(CSSParser.DimensionValueContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NumberValue}
	 * labeled alternative in {@link CSSParser#valueItem}.
	 * @param ctx the parse tree
	 */
	void enterNumberValue(CSSParser.NumberValueContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NumberValue}
	 * labeled alternative in {@link CSSParser#valueItem}.
	 * @param ctx the parse tree
	 */
	void exitNumberValue(CSSParser.NumberValueContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ColorValue}
	 * labeled alternative in {@link CSSParser#valueItem}.
	 * @param ctx the parse tree
	 */
	void enterColorValue(CSSParser.ColorValueContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ColorValue}
	 * labeled alternative in {@link CSSParser#valueItem}.
	 * @param ctx the parse tree
	 */
	void exitColorValue(CSSParser.ColorValueContext ctx);
	/**
	 * Enter a parse tree produced by the {@code StringValue}
	 * labeled alternative in {@link CSSParser#valueItem}.
	 * @param ctx the parse tree
	 */
	void enterStringValue(CSSParser.StringValueContext ctx);
	/**
	 * Exit a parse tree produced by the {@code StringValue}
	 * labeled alternative in {@link CSSParser#valueItem}.
	 * @param ctx the parse tree
	 */
	void exitStringValue(CSSParser.StringValueContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IdentifierValue}
	 * labeled alternative in {@link CSSParser#valueItem}.
	 * @param ctx the parse tree
	 */
	void enterIdentifierValue(CSSParser.IdentifierValueContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IdentifierValue}
	 * labeled alternative in {@link CSSParser#valueItem}.
	 * @param ctx the parse tree
	 */
	void exitIdentifierValue(CSSParser.IdentifierValueContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FunctionValue}
	 * labeled alternative in {@link CSSParser#valueItem}.
	 * @param ctx the parse tree
	 */
	void enterFunctionValue(CSSParser.FunctionValueContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FunctionValue}
	 * labeled alternative in {@link CSSParser#valueItem}.
	 * @param ctx the parse tree
	 */
	void exitFunctionValue(CSSParser.FunctionValueContext ctx);
	/**
	 * Enter a parse tree produced by the {@code UrlValue}
	 * labeled alternative in {@link CSSParser#valueItem}.
	 * @param ctx the parse tree
	 */
	void enterUrlValue(CSSParser.UrlValueContext ctx);
	/**
	 * Exit a parse tree produced by the {@code UrlValue}
	 * labeled alternative in {@link CSSParser#valueItem}.
	 * @param ctx the parse tree
	 */
	void exitUrlValue(CSSParser.UrlValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSSParser#functionCall}.
	 * @param ctx the parse tree
	 */
	void enterFunctionCall(CSSParser.FunctionCallContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSSParser#functionCall}.
	 * @param ctx the parse tree
	 */
	void exitFunctionCall(CSSParser.FunctionCallContext ctx);
}