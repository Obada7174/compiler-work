// Generated from C:/Users/user/IdeaProjects/compiler-work/grammar/PythonParser.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link PythonParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface PythonParserVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link PythonParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(PythonParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#decorator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDecorator(PythonParser.DecoratorContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#decorators}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDecorators(PythonParser.DecoratorsContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#decorated}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDecorated(PythonParser.DecoratedContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#async_funcdef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAsync_funcdef(PythonParser.Async_funcdefContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#funcdef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncdef(PythonParser.FuncdefContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#parameters}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameters(PythonParser.ParametersContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#typedargslist}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypedargslist(PythonParser.TypedargslistContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#tfpdef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTfpdef(PythonParser.TfpdefContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#varargslist}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarargslist(PythonParser.VarargslistContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#vfpdef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVfpdef(PythonParser.VfpdefContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStmt(PythonParser.StmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#simple_stmts}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimple_stmts(PythonParser.Simple_stmtsContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#simple_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimple_stmt(PythonParser.Simple_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#expr_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr_stmt(PythonParser.Expr_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#annassign}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnnassign(PythonParser.AnnassignContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#testlist_star_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTestlist_star_expr(PythonParser.Testlist_star_exprContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#augassign}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAugassign(PythonParser.AugassignContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#del_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDel_stmt(PythonParser.Del_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#pass_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPass_stmt(PythonParser.Pass_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#flow_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFlow_stmt(PythonParser.Flow_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#break_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBreak_stmt(PythonParser.Break_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#continue_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitContinue_stmt(PythonParser.Continue_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#return_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturn_stmt(PythonParser.Return_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#yield_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitYield_stmt(PythonParser.Yield_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#raise_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRaise_stmt(PythonParser.Raise_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#import_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImport_stmt(PythonParser.Import_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#import_name}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImport_name(PythonParser.Import_nameContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#import_from}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImport_from(PythonParser.Import_fromContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#import_as_name}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImport_as_name(PythonParser.Import_as_nameContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#dotted_as_name}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDotted_as_name(PythonParser.Dotted_as_nameContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#import_as_names}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImport_as_names(PythonParser.Import_as_namesContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#dotted_as_names}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDotted_as_names(PythonParser.Dotted_as_namesContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#dotted_name}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDotted_name(PythonParser.Dotted_nameContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#global_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGlobal_stmt(PythonParser.Global_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#nonlocal_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNonlocal_stmt(PythonParser.Nonlocal_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#assert_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssert_stmt(PythonParser.Assert_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#compound_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompound_stmt(PythonParser.Compound_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#async_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAsync_stmt(PythonParser.Async_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#if_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIf_stmt(PythonParser.If_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#while_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhile_stmt(PythonParser.While_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#for_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFor_stmt(PythonParser.For_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#try_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTry_stmt(PythonParser.Try_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#with_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWith_stmt(PythonParser.With_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#with_item}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWith_item(PythonParser.With_itemContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#except_clause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExcept_clause(PythonParser.Except_clauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(PythonParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#match_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMatch_stmt(PythonParser.Match_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#subject_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubject_expr(PythonParser.Subject_exprContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#star_named_expressions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStar_named_expressions(PythonParser.Star_named_expressionsContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#star_named_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStar_named_expression(PythonParser.Star_named_expressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#case_block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCase_block(PythonParser.Case_blockContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#guard}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGuard(PythonParser.GuardContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#patterns}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPatterns(PythonParser.PatternsContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#pattern}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPattern(PythonParser.PatternContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#as_pattern}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAs_pattern(PythonParser.As_patternContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#or_pattern}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOr_pattern(PythonParser.Or_patternContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#closed_pattern}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClosed_pattern(PythonParser.Closed_patternContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#literal_pattern}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteral_pattern(PythonParser.Literal_patternContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#literal_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteral_expr(PythonParser.Literal_exprContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#complex_number}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComplex_number(PythonParser.Complex_numberContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#signed_number}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSigned_number(PythonParser.Signed_numberContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#signed_real_number}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSigned_real_number(PythonParser.Signed_real_numberContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#real_number}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReal_number(PythonParser.Real_numberContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#imaginary_number}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImaginary_number(PythonParser.Imaginary_numberContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#capture_pattern}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCapture_pattern(PythonParser.Capture_patternContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#pattern_capture_target}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPattern_capture_target(PythonParser.Pattern_capture_targetContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#wildcard_pattern}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWildcard_pattern(PythonParser.Wildcard_patternContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#value_pattern}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValue_pattern(PythonParser.Value_patternContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#attr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAttr(PythonParser.AttrContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#name_or_attr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitName_or_attr(PythonParser.Name_or_attrContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#group_pattern}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGroup_pattern(PythonParser.Group_patternContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#sequence_pattern}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSequence_pattern(PythonParser.Sequence_patternContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#open_sequence_pattern}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOpen_sequence_pattern(PythonParser.Open_sequence_patternContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#maybe_sequence_pattern}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMaybe_sequence_pattern(PythonParser.Maybe_sequence_patternContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#maybe_star_pattern}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMaybe_star_pattern(PythonParser.Maybe_star_patternContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#star_pattern}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStar_pattern(PythonParser.Star_patternContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#mapping_pattern}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMapping_pattern(PythonParser.Mapping_patternContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#items_pattern}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitItems_pattern(PythonParser.Items_patternContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#key_value_pattern}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitKey_value_pattern(PythonParser.Key_value_patternContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#double_star_pattern}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDouble_star_pattern(PythonParser.Double_star_patternContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#class_pattern}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClass_pattern(PythonParser.Class_patternContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#positional_patterns}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPositional_patterns(PythonParser.Positional_patternsContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#keyword_patterns}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitKeyword_patterns(PythonParser.Keyword_patternsContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#keyword_pattern}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitKeyword_pattern(PythonParser.Keyword_patternContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#test}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTest(PythonParser.TestContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#test_nocond}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTest_nocond(PythonParser.Test_nocondContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#lambdef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLambdef(PythonParser.LambdefContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#lambdef_nocond}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLambdef_nocond(PythonParser.Lambdef_nocondContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#or_test}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOr_test(PythonParser.Or_testContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#and_test}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnd_test(PythonParser.And_testContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#not_test}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNot_test(PythonParser.Not_testContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#comparison}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComparison(PythonParser.ComparisonContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#comp_op}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComp_op(PythonParser.Comp_opContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#star_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStar_expr(PythonParser.Star_exprContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr(PythonParser.ExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#atom_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAtom_expr(PythonParser.Atom_exprContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAtom(PythonParser.AtomContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#name}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitName(PythonParser.NameContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#testlist_comp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTestlist_comp(PythonParser.Testlist_compContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#trailer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTrailer(PythonParser.TrailerContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#subscriptlist}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubscriptlist(PythonParser.SubscriptlistContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#subscript_}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubscript_(PythonParser.Subscript_Context ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#sliceop}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSliceop(PythonParser.SliceopContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#exprlist}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprlist(PythonParser.ExprlistContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#testlist}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTestlist(PythonParser.TestlistContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#dictorsetmaker}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDictorsetmaker(PythonParser.DictorsetmakerContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#classdef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassdef(PythonParser.ClassdefContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#arglist}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArglist(PythonParser.ArglistContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#argument}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgument(PythonParser.ArgumentContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#comp_iter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComp_iter(PythonParser.Comp_iterContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#comp_for}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComp_for(PythonParser.Comp_forContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#comp_if}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComp_if(PythonParser.Comp_ifContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#encoding_decl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEncoding_decl(PythonParser.Encoding_declContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#yield_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitYield_expr(PythonParser.Yield_exprContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#yield_arg}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitYield_arg(PythonParser.Yield_argContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#strings}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStrings(PythonParser.StringsContext ctx);
}