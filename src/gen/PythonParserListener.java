// Generated from C:/Users/user/IdeaProjects/compiler-work/grammar/PythonParser.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link PythonParser}.
 */
public interface PythonParserListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link PythonParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(PythonParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(PythonParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#decorator}.
	 * @param ctx the parse tree
	 */
	void enterDecorator(PythonParser.DecoratorContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#decorator}.
	 * @param ctx the parse tree
	 */
	void exitDecorator(PythonParser.DecoratorContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#decorators}.
	 * @param ctx the parse tree
	 */
	void enterDecorators(PythonParser.DecoratorsContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#decorators}.
	 * @param ctx the parse tree
	 */
	void exitDecorators(PythonParser.DecoratorsContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#decorated}.
	 * @param ctx the parse tree
	 */
	void enterDecorated(PythonParser.DecoratedContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#decorated}.
	 * @param ctx the parse tree
	 */
	void exitDecorated(PythonParser.DecoratedContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#async_funcdef}.
	 * @param ctx the parse tree
	 */
	void enterAsync_funcdef(PythonParser.Async_funcdefContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#async_funcdef}.
	 * @param ctx the parse tree
	 */
	void exitAsync_funcdef(PythonParser.Async_funcdefContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#funcdef}.
	 * @param ctx the parse tree
	 */
	void enterFuncdef(PythonParser.FuncdefContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#funcdef}.
	 * @param ctx the parse tree
	 */
	void exitFuncdef(PythonParser.FuncdefContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#parameters}.
	 * @param ctx the parse tree
	 */
	void enterParameters(PythonParser.ParametersContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#parameters}.
	 * @param ctx the parse tree
	 */
	void exitParameters(PythonParser.ParametersContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#typedargslist}.
	 * @param ctx the parse tree
	 */
	void enterTypedargslist(PythonParser.TypedargslistContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#typedargslist}.
	 * @param ctx the parse tree
	 */
	void exitTypedargslist(PythonParser.TypedargslistContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#tfpdef}.
	 * @param ctx the parse tree
	 */
	void enterTfpdef(PythonParser.TfpdefContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#tfpdef}.
	 * @param ctx the parse tree
	 */
	void exitTfpdef(PythonParser.TfpdefContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#varargslist}.
	 * @param ctx the parse tree
	 */
	void enterVarargslist(PythonParser.VarargslistContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#varargslist}.
	 * @param ctx the parse tree
	 */
	void exitVarargslist(PythonParser.VarargslistContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#vfpdef}.
	 * @param ctx the parse tree
	 */
	void enterVfpdef(PythonParser.VfpdefContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#vfpdef}.
	 * @param ctx the parse tree
	 */
	void exitVfpdef(PythonParser.VfpdefContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterStmt(PythonParser.StmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitStmt(PythonParser.StmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#simple_stmts}.
	 * @param ctx the parse tree
	 */
	void enterSimple_stmts(PythonParser.Simple_stmtsContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#simple_stmts}.
	 * @param ctx the parse tree
	 */
	void exitSimple_stmts(PythonParser.Simple_stmtsContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#simple_stmt}.
	 * @param ctx the parse tree
	 */
	void enterSimple_stmt(PythonParser.Simple_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#simple_stmt}.
	 * @param ctx the parse tree
	 */
	void exitSimple_stmt(PythonParser.Simple_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#expr_stmt}.
	 * @param ctx the parse tree
	 */
	void enterExpr_stmt(PythonParser.Expr_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#expr_stmt}.
	 * @param ctx the parse tree
	 */
	void exitExpr_stmt(PythonParser.Expr_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#annassign}.
	 * @param ctx the parse tree
	 */
	void enterAnnassign(PythonParser.AnnassignContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#annassign}.
	 * @param ctx the parse tree
	 */
	void exitAnnassign(PythonParser.AnnassignContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#testlist_star_expr}.
	 * @param ctx the parse tree
	 */
	void enterTestlist_star_expr(PythonParser.Testlist_star_exprContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#testlist_star_expr}.
	 * @param ctx the parse tree
	 */
	void exitTestlist_star_expr(PythonParser.Testlist_star_exprContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#augassign}.
	 * @param ctx the parse tree
	 */
	void enterAugassign(PythonParser.AugassignContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#augassign}.
	 * @param ctx the parse tree
	 */
	void exitAugassign(PythonParser.AugassignContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#del_stmt}.
	 * @param ctx the parse tree
	 */
	void enterDel_stmt(PythonParser.Del_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#del_stmt}.
	 * @param ctx the parse tree
	 */
	void exitDel_stmt(PythonParser.Del_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#pass_stmt}.
	 * @param ctx the parse tree
	 */
	void enterPass_stmt(PythonParser.Pass_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#pass_stmt}.
	 * @param ctx the parse tree
	 */
	void exitPass_stmt(PythonParser.Pass_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#flow_stmt}.
	 * @param ctx the parse tree
	 */
	void enterFlow_stmt(PythonParser.Flow_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#flow_stmt}.
	 * @param ctx the parse tree
	 */
	void exitFlow_stmt(PythonParser.Flow_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#break_stmt}.
	 * @param ctx the parse tree
	 */
	void enterBreak_stmt(PythonParser.Break_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#break_stmt}.
	 * @param ctx the parse tree
	 */
	void exitBreak_stmt(PythonParser.Break_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#continue_stmt}.
	 * @param ctx the parse tree
	 */
	void enterContinue_stmt(PythonParser.Continue_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#continue_stmt}.
	 * @param ctx the parse tree
	 */
	void exitContinue_stmt(PythonParser.Continue_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#return_stmt}.
	 * @param ctx the parse tree
	 */
	void enterReturn_stmt(PythonParser.Return_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#return_stmt}.
	 * @param ctx the parse tree
	 */
	void exitReturn_stmt(PythonParser.Return_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#yield_stmt}.
	 * @param ctx the parse tree
	 */
	void enterYield_stmt(PythonParser.Yield_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#yield_stmt}.
	 * @param ctx the parse tree
	 */
	void exitYield_stmt(PythonParser.Yield_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#raise_stmt}.
	 * @param ctx the parse tree
	 */
	void enterRaise_stmt(PythonParser.Raise_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#raise_stmt}.
	 * @param ctx the parse tree
	 */
	void exitRaise_stmt(PythonParser.Raise_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#import_stmt}.
	 * @param ctx the parse tree
	 */
	void enterImport_stmt(PythonParser.Import_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#import_stmt}.
	 * @param ctx the parse tree
	 */
	void exitImport_stmt(PythonParser.Import_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#import_name}.
	 * @param ctx the parse tree
	 */
	void enterImport_name(PythonParser.Import_nameContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#import_name}.
	 * @param ctx the parse tree
	 */
	void exitImport_name(PythonParser.Import_nameContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#import_from}.
	 * @param ctx the parse tree
	 */
	void enterImport_from(PythonParser.Import_fromContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#import_from}.
	 * @param ctx the parse tree
	 */
	void exitImport_from(PythonParser.Import_fromContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#import_as_name}.
	 * @param ctx the parse tree
	 */
	void enterImport_as_name(PythonParser.Import_as_nameContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#import_as_name}.
	 * @param ctx the parse tree
	 */
	void exitImport_as_name(PythonParser.Import_as_nameContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#dotted_as_name}.
	 * @param ctx the parse tree
	 */
	void enterDotted_as_name(PythonParser.Dotted_as_nameContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#dotted_as_name}.
	 * @param ctx the parse tree
	 */
	void exitDotted_as_name(PythonParser.Dotted_as_nameContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#import_as_names}.
	 * @param ctx the parse tree
	 */
	void enterImport_as_names(PythonParser.Import_as_namesContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#import_as_names}.
	 * @param ctx the parse tree
	 */
	void exitImport_as_names(PythonParser.Import_as_namesContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#dotted_as_names}.
	 * @param ctx the parse tree
	 */
	void enterDotted_as_names(PythonParser.Dotted_as_namesContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#dotted_as_names}.
	 * @param ctx the parse tree
	 */
	void exitDotted_as_names(PythonParser.Dotted_as_namesContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#dotted_name}.
	 * @param ctx the parse tree
	 */
	void enterDotted_name(PythonParser.Dotted_nameContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#dotted_name}.
	 * @param ctx the parse tree
	 */
	void exitDotted_name(PythonParser.Dotted_nameContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#global_stmt}.
	 * @param ctx the parse tree
	 */
	void enterGlobal_stmt(PythonParser.Global_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#global_stmt}.
	 * @param ctx the parse tree
	 */
	void exitGlobal_stmt(PythonParser.Global_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#nonlocal_stmt}.
	 * @param ctx the parse tree
	 */
	void enterNonlocal_stmt(PythonParser.Nonlocal_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#nonlocal_stmt}.
	 * @param ctx the parse tree
	 */
	void exitNonlocal_stmt(PythonParser.Nonlocal_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#assert_stmt}.
	 * @param ctx the parse tree
	 */
	void enterAssert_stmt(PythonParser.Assert_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#assert_stmt}.
	 * @param ctx the parse tree
	 */
	void exitAssert_stmt(PythonParser.Assert_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#compound_stmt}.
	 * @param ctx the parse tree
	 */
	void enterCompound_stmt(PythonParser.Compound_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#compound_stmt}.
	 * @param ctx the parse tree
	 */
	void exitCompound_stmt(PythonParser.Compound_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#async_stmt}.
	 * @param ctx the parse tree
	 */
	void enterAsync_stmt(PythonParser.Async_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#async_stmt}.
	 * @param ctx the parse tree
	 */
	void exitAsync_stmt(PythonParser.Async_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#if_stmt}.
	 * @param ctx the parse tree
	 */
	void enterIf_stmt(PythonParser.If_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#if_stmt}.
	 * @param ctx the parse tree
	 */
	void exitIf_stmt(PythonParser.If_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#while_stmt}.
	 * @param ctx the parse tree
	 */
	void enterWhile_stmt(PythonParser.While_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#while_stmt}.
	 * @param ctx the parse tree
	 */
	void exitWhile_stmt(PythonParser.While_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#for_stmt}.
	 * @param ctx the parse tree
	 */
	void enterFor_stmt(PythonParser.For_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#for_stmt}.
	 * @param ctx the parse tree
	 */
	void exitFor_stmt(PythonParser.For_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#try_stmt}.
	 * @param ctx the parse tree
	 */
	void enterTry_stmt(PythonParser.Try_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#try_stmt}.
	 * @param ctx the parse tree
	 */
	void exitTry_stmt(PythonParser.Try_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#with_stmt}.
	 * @param ctx the parse tree
	 */
	void enterWith_stmt(PythonParser.With_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#with_stmt}.
	 * @param ctx the parse tree
	 */
	void exitWith_stmt(PythonParser.With_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#with_item}.
	 * @param ctx the parse tree
	 */
	void enterWith_item(PythonParser.With_itemContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#with_item}.
	 * @param ctx the parse tree
	 */
	void exitWith_item(PythonParser.With_itemContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#except_clause}.
	 * @param ctx the parse tree
	 */
	void enterExcept_clause(PythonParser.Except_clauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#except_clause}.
	 * @param ctx the parse tree
	 */
	void exitExcept_clause(PythonParser.Except_clauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(PythonParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(PythonParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#match_stmt}.
	 * @param ctx the parse tree
	 */
	void enterMatch_stmt(PythonParser.Match_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#match_stmt}.
	 * @param ctx the parse tree
	 */
	void exitMatch_stmt(PythonParser.Match_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#subject_expr}.
	 * @param ctx the parse tree
	 */
	void enterSubject_expr(PythonParser.Subject_exprContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#subject_expr}.
	 * @param ctx the parse tree
	 */
	void exitSubject_expr(PythonParser.Subject_exprContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#star_named_expressions}.
	 * @param ctx the parse tree
	 */
	void enterStar_named_expressions(PythonParser.Star_named_expressionsContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#star_named_expressions}.
	 * @param ctx the parse tree
	 */
	void exitStar_named_expressions(PythonParser.Star_named_expressionsContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#star_named_expression}.
	 * @param ctx the parse tree
	 */
	void enterStar_named_expression(PythonParser.Star_named_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#star_named_expression}.
	 * @param ctx the parse tree
	 */
	void exitStar_named_expression(PythonParser.Star_named_expressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#case_block}.
	 * @param ctx the parse tree
	 */
	void enterCase_block(PythonParser.Case_blockContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#case_block}.
	 * @param ctx the parse tree
	 */
	void exitCase_block(PythonParser.Case_blockContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#guard}.
	 * @param ctx the parse tree
	 */
	void enterGuard(PythonParser.GuardContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#guard}.
	 * @param ctx the parse tree
	 */
	void exitGuard(PythonParser.GuardContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#patterns}.
	 * @param ctx the parse tree
	 */
	void enterPatterns(PythonParser.PatternsContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#patterns}.
	 * @param ctx the parse tree
	 */
	void exitPatterns(PythonParser.PatternsContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#pattern}.
	 * @param ctx the parse tree
	 */
	void enterPattern(PythonParser.PatternContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#pattern}.
	 * @param ctx the parse tree
	 */
	void exitPattern(PythonParser.PatternContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#as_pattern}.
	 * @param ctx the parse tree
	 */
	void enterAs_pattern(PythonParser.As_patternContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#as_pattern}.
	 * @param ctx the parse tree
	 */
	void exitAs_pattern(PythonParser.As_patternContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#or_pattern}.
	 * @param ctx the parse tree
	 */
	void enterOr_pattern(PythonParser.Or_patternContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#or_pattern}.
	 * @param ctx the parse tree
	 */
	void exitOr_pattern(PythonParser.Or_patternContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#closed_pattern}.
	 * @param ctx the parse tree
	 */
	void enterClosed_pattern(PythonParser.Closed_patternContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#closed_pattern}.
	 * @param ctx the parse tree
	 */
	void exitClosed_pattern(PythonParser.Closed_patternContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#literal_pattern}.
	 * @param ctx the parse tree
	 */
	void enterLiteral_pattern(PythonParser.Literal_patternContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#literal_pattern}.
	 * @param ctx the parse tree
	 */
	void exitLiteral_pattern(PythonParser.Literal_patternContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#literal_expr}.
	 * @param ctx the parse tree
	 */
	void enterLiteral_expr(PythonParser.Literal_exprContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#literal_expr}.
	 * @param ctx the parse tree
	 */
	void exitLiteral_expr(PythonParser.Literal_exprContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#complex_number}.
	 * @param ctx the parse tree
	 */
	void enterComplex_number(PythonParser.Complex_numberContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#complex_number}.
	 * @param ctx the parse tree
	 */
	void exitComplex_number(PythonParser.Complex_numberContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#signed_number}.
	 * @param ctx the parse tree
	 */
	void enterSigned_number(PythonParser.Signed_numberContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#signed_number}.
	 * @param ctx the parse tree
	 */
	void exitSigned_number(PythonParser.Signed_numberContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#signed_real_number}.
	 * @param ctx the parse tree
	 */
	void enterSigned_real_number(PythonParser.Signed_real_numberContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#signed_real_number}.
	 * @param ctx the parse tree
	 */
	void exitSigned_real_number(PythonParser.Signed_real_numberContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#real_number}.
	 * @param ctx the parse tree
	 */
	void enterReal_number(PythonParser.Real_numberContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#real_number}.
	 * @param ctx the parse tree
	 */
	void exitReal_number(PythonParser.Real_numberContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#imaginary_number}.
	 * @param ctx the parse tree
	 */
	void enterImaginary_number(PythonParser.Imaginary_numberContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#imaginary_number}.
	 * @param ctx the parse tree
	 */
	void exitImaginary_number(PythonParser.Imaginary_numberContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#capture_pattern}.
	 * @param ctx the parse tree
	 */
	void enterCapture_pattern(PythonParser.Capture_patternContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#capture_pattern}.
	 * @param ctx the parse tree
	 */
	void exitCapture_pattern(PythonParser.Capture_patternContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#pattern_capture_target}.
	 * @param ctx the parse tree
	 */
	void enterPattern_capture_target(PythonParser.Pattern_capture_targetContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#pattern_capture_target}.
	 * @param ctx the parse tree
	 */
	void exitPattern_capture_target(PythonParser.Pattern_capture_targetContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#wildcard_pattern}.
	 * @param ctx the parse tree
	 */
	void enterWildcard_pattern(PythonParser.Wildcard_patternContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#wildcard_pattern}.
	 * @param ctx the parse tree
	 */
	void exitWildcard_pattern(PythonParser.Wildcard_patternContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#value_pattern}.
	 * @param ctx the parse tree
	 */
	void enterValue_pattern(PythonParser.Value_patternContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#value_pattern}.
	 * @param ctx the parse tree
	 */
	void exitValue_pattern(PythonParser.Value_patternContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#attr}.
	 * @param ctx the parse tree
	 */
	void enterAttr(PythonParser.AttrContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#attr}.
	 * @param ctx the parse tree
	 */
	void exitAttr(PythonParser.AttrContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#name_or_attr}.
	 * @param ctx the parse tree
	 */
	void enterName_or_attr(PythonParser.Name_or_attrContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#name_or_attr}.
	 * @param ctx the parse tree
	 */
	void exitName_or_attr(PythonParser.Name_or_attrContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#group_pattern}.
	 * @param ctx the parse tree
	 */
	void enterGroup_pattern(PythonParser.Group_patternContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#group_pattern}.
	 * @param ctx the parse tree
	 */
	void exitGroup_pattern(PythonParser.Group_patternContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#sequence_pattern}.
	 * @param ctx the parse tree
	 */
	void enterSequence_pattern(PythonParser.Sequence_patternContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#sequence_pattern}.
	 * @param ctx the parse tree
	 */
	void exitSequence_pattern(PythonParser.Sequence_patternContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#open_sequence_pattern}.
	 * @param ctx the parse tree
	 */
	void enterOpen_sequence_pattern(PythonParser.Open_sequence_patternContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#open_sequence_pattern}.
	 * @param ctx the parse tree
	 */
	void exitOpen_sequence_pattern(PythonParser.Open_sequence_patternContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#maybe_sequence_pattern}.
	 * @param ctx the parse tree
	 */
	void enterMaybe_sequence_pattern(PythonParser.Maybe_sequence_patternContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#maybe_sequence_pattern}.
	 * @param ctx the parse tree
	 */
	void exitMaybe_sequence_pattern(PythonParser.Maybe_sequence_patternContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#maybe_star_pattern}.
	 * @param ctx the parse tree
	 */
	void enterMaybe_star_pattern(PythonParser.Maybe_star_patternContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#maybe_star_pattern}.
	 * @param ctx the parse tree
	 */
	void exitMaybe_star_pattern(PythonParser.Maybe_star_patternContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#star_pattern}.
	 * @param ctx the parse tree
	 */
	void enterStar_pattern(PythonParser.Star_patternContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#star_pattern}.
	 * @param ctx the parse tree
	 */
	void exitStar_pattern(PythonParser.Star_patternContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#mapping_pattern}.
	 * @param ctx the parse tree
	 */
	void enterMapping_pattern(PythonParser.Mapping_patternContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#mapping_pattern}.
	 * @param ctx the parse tree
	 */
	void exitMapping_pattern(PythonParser.Mapping_patternContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#items_pattern}.
	 * @param ctx the parse tree
	 */
	void enterItems_pattern(PythonParser.Items_patternContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#items_pattern}.
	 * @param ctx the parse tree
	 */
	void exitItems_pattern(PythonParser.Items_patternContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#key_value_pattern}.
	 * @param ctx the parse tree
	 */
	void enterKey_value_pattern(PythonParser.Key_value_patternContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#key_value_pattern}.
	 * @param ctx the parse tree
	 */
	void exitKey_value_pattern(PythonParser.Key_value_patternContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#double_star_pattern}.
	 * @param ctx the parse tree
	 */
	void enterDouble_star_pattern(PythonParser.Double_star_patternContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#double_star_pattern}.
	 * @param ctx the parse tree
	 */
	void exitDouble_star_pattern(PythonParser.Double_star_patternContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#class_pattern}.
	 * @param ctx the parse tree
	 */
	void enterClass_pattern(PythonParser.Class_patternContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#class_pattern}.
	 * @param ctx the parse tree
	 */
	void exitClass_pattern(PythonParser.Class_patternContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#positional_patterns}.
	 * @param ctx the parse tree
	 */
	void enterPositional_patterns(PythonParser.Positional_patternsContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#positional_patterns}.
	 * @param ctx the parse tree
	 */
	void exitPositional_patterns(PythonParser.Positional_patternsContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#keyword_patterns}.
	 * @param ctx the parse tree
	 */
	void enterKeyword_patterns(PythonParser.Keyword_patternsContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#keyword_patterns}.
	 * @param ctx the parse tree
	 */
	void exitKeyword_patterns(PythonParser.Keyword_patternsContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#keyword_pattern}.
	 * @param ctx the parse tree
	 */
	void enterKeyword_pattern(PythonParser.Keyword_patternContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#keyword_pattern}.
	 * @param ctx the parse tree
	 */
	void exitKeyword_pattern(PythonParser.Keyword_patternContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#test}.
	 * @param ctx the parse tree
	 */
	void enterTest(PythonParser.TestContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#test}.
	 * @param ctx the parse tree
	 */
	void exitTest(PythonParser.TestContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#test_nocond}.
	 * @param ctx the parse tree
	 */
	void enterTest_nocond(PythonParser.Test_nocondContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#test_nocond}.
	 * @param ctx the parse tree
	 */
	void exitTest_nocond(PythonParser.Test_nocondContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#lambdef}.
	 * @param ctx the parse tree
	 */
	void enterLambdef(PythonParser.LambdefContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#lambdef}.
	 * @param ctx the parse tree
	 */
	void exitLambdef(PythonParser.LambdefContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#lambdef_nocond}.
	 * @param ctx the parse tree
	 */
	void enterLambdef_nocond(PythonParser.Lambdef_nocondContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#lambdef_nocond}.
	 * @param ctx the parse tree
	 */
	void exitLambdef_nocond(PythonParser.Lambdef_nocondContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#or_test}.
	 * @param ctx the parse tree
	 */
	void enterOr_test(PythonParser.Or_testContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#or_test}.
	 * @param ctx the parse tree
	 */
	void exitOr_test(PythonParser.Or_testContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#and_test}.
	 * @param ctx the parse tree
	 */
	void enterAnd_test(PythonParser.And_testContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#and_test}.
	 * @param ctx the parse tree
	 */
	void exitAnd_test(PythonParser.And_testContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#not_test}.
	 * @param ctx the parse tree
	 */
	void enterNot_test(PythonParser.Not_testContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#not_test}.
	 * @param ctx the parse tree
	 */
	void exitNot_test(PythonParser.Not_testContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#comparison}.
	 * @param ctx the parse tree
	 */
	void enterComparison(PythonParser.ComparisonContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#comparison}.
	 * @param ctx the parse tree
	 */
	void exitComparison(PythonParser.ComparisonContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#comp_op}.
	 * @param ctx the parse tree
	 */
	void enterComp_op(PythonParser.Comp_opContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#comp_op}.
	 * @param ctx the parse tree
	 */
	void exitComp_op(PythonParser.Comp_opContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#star_expr}.
	 * @param ctx the parse tree
	 */
	void enterStar_expr(PythonParser.Star_exprContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#star_expr}.
	 * @param ctx the parse tree
	 */
	void exitStar_expr(PythonParser.Star_exprContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(PythonParser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(PythonParser.ExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#atom_expr}.
	 * @param ctx the parse tree
	 */
	void enterAtom_expr(PythonParser.Atom_exprContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#atom_expr}.
	 * @param ctx the parse tree
	 */
	void exitAtom_expr(PythonParser.Atom_exprContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterAtom(PythonParser.AtomContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitAtom(PythonParser.AtomContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#name}.
	 * @param ctx the parse tree
	 */
	void enterName(PythonParser.NameContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#name}.
	 * @param ctx the parse tree
	 */
	void exitName(PythonParser.NameContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#testlist_comp}.
	 * @param ctx the parse tree
	 */
	void enterTestlist_comp(PythonParser.Testlist_compContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#testlist_comp}.
	 * @param ctx the parse tree
	 */
	void exitTestlist_comp(PythonParser.Testlist_compContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#trailer}.
	 * @param ctx the parse tree
	 */
	void enterTrailer(PythonParser.TrailerContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#trailer}.
	 * @param ctx the parse tree
	 */
	void exitTrailer(PythonParser.TrailerContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#subscriptlist}.
	 * @param ctx the parse tree
	 */
	void enterSubscriptlist(PythonParser.SubscriptlistContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#subscriptlist}.
	 * @param ctx the parse tree
	 */
	void exitSubscriptlist(PythonParser.SubscriptlistContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#subscript_}.
	 * @param ctx the parse tree
	 */
	void enterSubscript_(PythonParser.Subscript_Context ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#subscript_}.
	 * @param ctx the parse tree
	 */
	void exitSubscript_(PythonParser.Subscript_Context ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#sliceop}.
	 * @param ctx the parse tree
	 */
	void enterSliceop(PythonParser.SliceopContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#sliceop}.
	 * @param ctx the parse tree
	 */
	void exitSliceop(PythonParser.SliceopContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#exprlist}.
	 * @param ctx the parse tree
	 */
	void enterExprlist(PythonParser.ExprlistContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#exprlist}.
	 * @param ctx the parse tree
	 */
	void exitExprlist(PythonParser.ExprlistContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#testlist}.
	 * @param ctx the parse tree
	 */
	void enterTestlist(PythonParser.TestlistContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#testlist}.
	 * @param ctx the parse tree
	 */
	void exitTestlist(PythonParser.TestlistContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#dictorsetmaker}.
	 * @param ctx the parse tree
	 */
	void enterDictorsetmaker(PythonParser.DictorsetmakerContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#dictorsetmaker}.
	 * @param ctx the parse tree
	 */
	void exitDictorsetmaker(PythonParser.DictorsetmakerContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#classdef}.
	 * @param ctx the parse tree
	 */
	void enterClassdef(PythonParser.ClassdefContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#classdef}.
	 * @param ctx the parse tree
	 */
	void exitClassdef(PythonParser.ClassdefContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#arglist}.
	 * @param ctx the parse tree
	 */
	void enterArglist(PythonParser.ArglistContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#arglist}.
	 * @param ctx the parse tree
	 */
	void exitArglist(PythonParser.ArglistContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#argument}.
	 * @param ctx the parse tree
	 */
	void enterArgument(PythonParser.ArgumentContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#argument}.
	 * @param ctx the parse tree
	 */
	void exitArgument(PythonParser.ArgumentContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#comp_iter}.
	 * @param ctx the parse tree
	 */
	void enterComp_iter(PythonParser.Comp_iterContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#comp_iter}.
	 * @param ctx the parse tree
	 */
	void exitComp_iter(PythonParser.Comp_iterContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#comp_for}.
	 * @param ctx the parse tree
	 */
	void enterComp_for(PythonParser.Comp_forContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#comp_for}.
	 * @param ctx the parse tree
	 */
	void exitComp_for(PythonParser.Comp_forContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#comp_if}.
	 * @param ctx the parse tree
	 */
	void enterComp_if(PythonParser.Comp_ifContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#comp_if}.
	 * @param ctx the parse tree
	 */
	void exitComp_if(PythonParser.Comp_ifContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#encoding_decl}.
	 * @param ctx the parse tree
	 */
	void enterEncoding_decl(PythonParser.Encoding_declContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#encoding_decl}.
	 * @param ctx the parse tree
	 */
	void exitEncoding_decl(PythonParser.Encoding_declContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#yield_expr}.
	 * @param ctx the parse tree
	 */
	void enterYield_expr(PythonParser.Yield_exprContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#yield_expr}.
	 * @param ctx the parse tree
	 */
	void exitYield_expr(PythonParser.Yield_exprContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#yield_arg}.
	 * @param ctx the parse tree
	 */
	void enterYield_arg(PythonParser.Yield_argContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#yield_arg}.
	 * @param ctx the parse tree
	 */
	void exitYield_arg(PythonParser.Yield_argContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#strings}.
	 * @param ctx the parse tree
	 */
	void enterStrings(PythonParser.StringsContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#strings}.
	 * @param ctx the parse tree
	 */
	void exitStrings(PythonParser.StringsContext ctx);
}