// Generated from D:/codes-folder/compiler/Test1/grammar/CSSParser.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link CSSParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface CSSParserVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link CSSParser#stylesheet}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStylesheet(CSSParser.StylesheetContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSSParser#atRule}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAtRule(CSSParser.AtRuleContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSSParser#ruleSet}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRuleSet(CSSParser.RuleSetContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSSParser#selectorList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelectorList(CSSParser.SelectorListContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSSParser#selector}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelector(CSSParser.SelectorContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSSParser#simpleSelector}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleSelector(CSSParser.SimpleSelectorContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ElementSelector}
	 * labeled alternative in {@link CSSParser#selectorPart}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElementSelector(CSSParser.ElementSelectorContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ClassSelector}
	 * labeled alternative in {@link CSSParser#selectorPart}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassSelector(CSSParser.ClassSelectorContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IdSelector}
	 * labeled alternative in {@link CSSParser#selectorPart}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdSelector(CSSParser.IdSelectorContext ctx);
	/**
	 * Visit a parse tree produced by the {@code UniversalSelector}
	 * labeled alternative in {@link CSSParser#selectorPart}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUniversalSelector(CSSParser.UniversalSelectorContext ctx);
	/**
	 * Visit a parse tree produced by the {@code PseudoClassSelector}
	 * labeled alternative in {@link CSSParser#selectorPart}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPseudoClassSelector(CSSParser.PseudoClassSelectorContext ctx);
	/**
	 * Visit a parse tree produced by the {@code PseudoElementSelector}
	 * labeled alternative in {@link CSSParser#selectorPart}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPseudoElementSelector(CSSParser.PseudoElementSelectorContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AttributeSelector}
	 * labeled alternative in {@link CSSParser#selectorPart}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAttributeSelector(CSSParser.AttributeSelectorContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSSParser#combinator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCombinator(CSSParser.CombinatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSSParser#declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclaration(CSSParser.DeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSSParser#value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValue(CSSParser.ValueContext ctx);
	/**
	 * Visit a parse tree produced by the {@code DimensionValue}
	 * labeled alternative in {@link CSSParser#valueItem}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDimensionValue(CSSParser.DimensionValueContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NumberValue}
	 * labeled alternative in {@link CSSParser#valueItem}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumberValue(CSSParser.NumberValueContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ColorValue}
	 * labeled alternative in {@link CSSParser#valueItem}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitColorValue(CSSParser.ColorValueContext ctx);
	/**
	 * Visit a parse tree produced by the {@code StringValue}
	 * labeled alternative in {@link CSSParser#valueItem}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringValue(CSSParser.StringValueContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IdentifierValue}
	 * labeled alternative in {@link CSSParser#valueItem}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentifierValue(CSSParser.IdentifierValueContext ctx);
	/**
	 * Visit a parse tree produced by the {@code FunctionValue}
	 * labeled alternative in {@link CSSParser#valueItem}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionValue(CSSParser.FunctionValueContext ctx);
	/**
	 * Visit a parse tree produced by the {@code UrlValue}
	 * labeled alternative in {@link CSSParser#valueItem}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUrlValue(CSSParser.UrlValueContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSSParser#functionCall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionCall(CSSParser.FunctionCallContext ctx);
}