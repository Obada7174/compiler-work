// Generated from D:/codes-folder/compiler/Test1/grammar/PythonParser.g4 by ANTLR 4.13.2
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
	 * Enter a parse tree produced by {@link PythonParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(PythonParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(PythonParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#simpleStmt}.
	 * @param ctx the parse tree
	 */
	void enterSimpleStmt(PythonParser.SimpleStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#simpleStmt}.
	 * @param ctx the parse tree
	 */
	void exitSimpleStmt(PythonParser.SimpleStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#smallStmt}.
	 * @param ctx the parse tree
	 */
	void enterSmallStmt(PythonParser.SmallStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#smallStmt}.
	 * @param ctx the parse tree
	 */
	void exitSmallStmt(PythonParser.SmallStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#compoundStmt}.
	 * @param ctx the parse tree
	 */
	void enterCompoundStmt(PythonParser.CompoundStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#compoundStmt}.
	 * @param ctx the parse tree
	 */
	void exitCompoundStmt(PythonParser.CompoundStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterAssignment(PythonParser.AssignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitAssignment(PythonParser.AssignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#augAssignment}.
	 * @param ctx the parse tree
	 */
	void enterAugAssignment(PythonParser.AugAssignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#augAssignment}.
	 * @param ctx the parse tree
	 */
	void exitAugAssignment(PythonParser.AugAssignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#target}.
	 * @param ctx the parse tree
	 */
	void enterTarget(PythonParser.TargetContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#target}.
	 * @param ctx the parse tree
	 */
	void exitTarget(PythonParser.TargetContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#expressionStmt}.
	 * @param ctx the parse tree
	 */
	void enterExpressionStmt(PythonParser.ExpressionStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#expressionStmt}.
	 * @param ctx the parse tree
	 */
	void exitExpressionStmt(PythonParser.ExpressionStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#returnStmt}.
	 * @param ctx the parse tree
	 */
	void enterReturnStmt(PythonParser.ReturnStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#returnStmt}.
	 * @param ctx the parse tree
	 */
	void exitReturnStmt(PythonParser.ReturnStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#importStmt}.
	 * @param ctx the parse tree
	 */
	void enterImportStmt(PythonParser.ImportStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#importStmt}.
	 * @param ctx the parse tree
	 */
	void exitImportStmt(PythonParser.ImportStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#fromImportStmt}.
	 * @param ctx the parse tree
	 */
	void enterFromImportStmt(PythonParser.FromImportStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#fromImportStmt}.
	 * @param ctx the parse tree
	 */
	void exitFromImportStmt(PythonParser.FromImportStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#moduleName}.
	 * @param ctx the parse tree
	 */
	void enterModuleName(PythonParser.ModuleNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#moduleName}.
	 * @param ctx the parse tree
	 */
	void exitModuleName(PythonParser.ModuleNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#importNames}.
	 * @param ctx the parse tree
	 */
	void enterImportNames(PythonParser.ImportNamesContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#importNames}.
	 * @param ctx the parse tree
	 */
	void exitImportNames(PythonParser.ImportNamesContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#assertStmt}.
	 * @param ctx the parse tree
	 */
	void enterAssertStmt(PythonParser.AssertStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#assertStmt}.
	 * @param ctx the parse tree
	 */
	void exitAssertStmt(PythonParser.AssertStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#delStmt}.
	 * @param ctx the parse tree
	 */
	void enterDelStmt(PythonParser.DelStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#delStmt}.
	 * @param ctx the parse tree
	 */
	void exitDelStmt(PythonParser.DelStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#globalStmt}.
	 * @param ctx the parse tree
	 */
	void enterGlobalStmt(PythonParser.GlobalStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#globalStmt}.
	 * @param ctx the parse tree
	 */
	void exitGlobalStmt(PythonParser.GlobalStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#nonlocalStmt}.
	 * @param ctx the parse tree
	 */
	void enterNonlocalStmt(PythonParser.NonlocalStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#nonlocalStmt}.
	 * @param ctx the parse tree
	 */
	void exitNonlocalStmt(PythonParser.NonlocalStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#raiseStmt}.
	 * @param ctx the parse tree
	 */
	void enterRaiseStmt(PythonParser.RaiseStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#raiseStmt}.
	 * @param ctx the parse tree
	 */
	void exitRaiseStmt(PythonParser.RaiseStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#yieldStmt}.
	 * @param ctx the parse tree
	 */
	void enterYieldStmt(PythonParser.YieldStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#yieldStmt}.
	 * @param ctx the parse tree
	 */
	void exitYieldStmt(PythonParser.YieldStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#functionDef}.
	 * @param ctx the parse tree
	 */
	void enterFunctionDef(PythonParser.FunctionDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#functionDef}.
	 * @param ctx the parse tree
	 */
	void exitFunctionDef(PythonParser.FunctionDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#classDef}.
	 * @param ctx the parse tree
	 */
	void enterClassDef(PythonParser.ClassDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#classDef}.
	 * @param ctx the parse tree
	 */
	void exitClassDef(PythonParser.ClassDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#ifStmt}.
	 * @param ctx the parse tree
	 */
	void enterIfStmt(PythonParser.IfStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#ifStmt}.
	 * @param ctx the parse tree
	 */
	void exitIfStmt(PythonParser.IfStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#forStmt}.
	 * @param ctx the parse tree
	 */
	void enterForStmt(PythonParser.ForStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#forStmt}.
	 * @param ctx the parse tree
	 */
	void exitForStmt(PythonParser.ForStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#whileStmt}.
	 * @param ctx the parse tree
	 */
	void enterWhileStmt(PythonParser.WhileStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#whileStmt}.
	 * @param ctx the parse tree
	 */
	void exitWhileStmt(PythonParser.WhileStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#withStmt}.
	 * @param ctx the parse tree
	 */
	void enterWithStmt(PythonParser.WithStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#withStmt}.
	 * @param ctx the parse tree
	 */
	void exitWithStmt(PythonParser.WithStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#withItem}.
	 * @param ctx the parse tree
	 */
	void enterWithItem(PythonParser.WithItemContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#withItem}.
	 * @param ctx the parse tree
	 */
	void exitWithItem(PythonParser.WithItemContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#tryStmt}.
	 * @param ctx the parse tree
	 */
	void enterTryStmt(PythonParser.TryStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#tryStmt}.
	 * @param ctx the parse tree
	 */
	void exitTryStmt(PythonParser.TryStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#exceptClause}.
	 * @param ctx the parse tree
	 */
	void enterExceptClause(PythonParser.ExceptClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#exceptClause}.
	 * @param ctx the parse tree
	 */
	void exitExceptClause(PythonParser.ExceptClauseContext ctx);
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
	 * Enter a parse tree produced by {@link PythonParser#dottedName}.
	 * @param ctx the parse tree
	 */
	void enterDottedName(PythonParser.DottedNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#dottedName}.
	 * @param ctx the parse tree
	 */
	void exitDottedName(PythonParser.DottedNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#suite}.
	 * @param ctx the parse tree
	 */
	void enterSuite(PythonParser.SuiteContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#suite}.
	 * @param ctx the parse tree
	 */
	void exitSuite(PythonParser.SuiteContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#paramList}.
	 * @param ctx the parse tree
	 */
	void enterParamList(PythonParser.ParamListContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#paramList}.
	 * @param ctx the parse tree
	 */
	void exitParamList(PythonParser.ParamListContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#param}.
	 * @param ctx the parse tree
	 */
	void enterParam(PythonParser.ParamContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#param}.
	 * @param ctx the parse tree
	 */
	void exitParam(PythonParser.ParamContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#argList}.
	 * @param ctx the parse tree
	 */
	void enterArgList(PythonParser.ArgListContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#argList}.
	 * @param ctx the parse tree
	 */
	void exitArgList(PythonParser.ArgListContext ctx);
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
	 * Enter a parse tree produced by {@link PythonParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(PythonParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(PythonParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#lambdaExpr}.
	 * @param ctx the parse tree
	 */
	void enterLambdaExpr(PythonParser.LambdaExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#lambdaExpr}.
	 * @param ctx the parse tree
	 */
	void exitLambdaExpr(PythonParser.LambdaExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#orExpr}.
	 * @param ctx the parse tree
	 */
	void enterOrExpr(PythonParser.OrExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#orExpr}.
	 * @param ctx the parse tree
	 */
	void exitOrExpr(PythonParser.OrExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#andExpr}.
	 * @param ctx the parse tree
	 */
	void enterAndExpr(PythonParser.AndExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#andExpr}.
	 * @param ctx the parse tree
	 */
	void exitAndExpr(PythonParser.AndExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#notExpr}.
	 * @param ctx the parse tree
	 */
	void enterNotExpr(PythonParser.NotExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#notExpr}.
	 * @param ctx the parse tree
	 */
	void exitNotExpr(PythonParser.NotExprContext ctx);
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
	 * Enter a parse tree produced by {@link PythonParser#compOp}.
	 * @param ctx the parse tree
	 */
	void enterCompOp(PythonParser.CompOpContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#compOp}.
	 * @param ctx the parse tree
	 */
	void exitCompOp(PythonParser.CompOpContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#bitwiseOr}.
	 * @param ctx the parse tree
	 */
	void enterBitwiseOr(PythonParser.BitwiseOrContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#bitwiseOr}.
	 * @param ctx the parse tree
	 */
	void exitBitwiseOr(PythonParser.BitwiseOrContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#bitwiseXor}.
	 * @param ctx the parse tree
	 */
	void enterBitwiseXor(PythonParser.BitwiseXorContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#bitwiseXor}.
	 * @param ctx the parse tree
	 */
	void exitBitwiseXor(PythonParser.BitwiseXorContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#bitwiseAnd}.
	 * @param ctx the parse tree
	 */
	void enterBitwiseAnd(PythonParser.BitwiseAndContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#bitwiseAnd}.
	 * @param ctx the parse tree
	 */
	void exitBitwiseAnd(PythonParser.BitwiseAndContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#shiftExpr}.
	 * @param ctx the parse tree
	 */
	void enterShiftExpr(PythonParser.ShiftExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#shiftExpr}.
	 * @param ctx the parse tree
	 */
	void exitShiftExpr(PythonParser.ShiftExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#arithExpr}.
	 * @param ctx the parse tree
	 */
	void enterArithExpr(PythonParser.ArithExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#arithExpr}.
	 * @param ctx the parse tree
	 */
	void exitArithExpr(PythonParser.ArithExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#term}.
	 * @param ctx the parse tree
	 */
	void enterTerm(PythonParser.TermContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#term}.
	 * @param ctx the parse tree
	 */
	void exitTerm(PythonParser.TermContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#factor}.
	 * @param ctx the parse tree
	 */
	void enterFactor(PythonParser.FactorContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#factor}.
	 * @param ctx the parse tree
	 */
	void exitFactor(PythonParser.FactorContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#power}.
	 * @param ctx the parse tree
	 */
	void enterPower(PythonParser.PowerContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#power}.
	 * @param ctx the parse tree
	 */
	void exitPower(PythonParser.PowerContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#atomExpr}.
	 * @param ctx the parse tree
	 */
	void enterAtomExpr(PythonParser.AtomExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#atomExpr}.
	 * @param ctx the parse tree
	 */
	void exitAtomExpr(PythonParser.AtomExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ParenExpr}
	 * labeled alternative in {@link PythonParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterParenExpr(PythonParser.ParenExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ParenExpr}
	 * labeled alternative in {@link PythonParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitParenExpr(PythonParser.ParenExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ListExpr}
	 * labeled alternative in {@link PythonParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterListExpr(PythonParser.ListExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ListExpr}
	 * labeled alternative in {@link PythonParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitListExpr(PythonParser.ListExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code DictOrSetExpr}
	 * labeled alternative in {@link PythonParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterDictOrSetExpr(PythonParser.DictOrSetExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code DictOrSetExpr}
	 * labeled alternative in {@link PythonParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitDictOrSetExpr(PythonParser.DictOrSetExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NumberExpr}
	 * labeled alternative in {@link PythonParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterNumberExpr(PythonParser.NumberExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NumberExpr}
	 * labeled alternative in {@link PythonParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitNumberExpr(PythonParser.NumberExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code StringExpr}
	 * labeled alternative in {@link PythonParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterStringExpr(PythonParser.StringExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code StringExpr}
	 * labeled alternative in {@link PythonParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitStringExpr(PythonParser.StringExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code TrueExpr}
	 * labeled alternative in {@link PythonParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterTrueExpr(PythonParser.TrueExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code TrueExpr}
	 * labeled alternative in {@link PythonParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitTrueExpr(PythonParser.TrueExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FalseExpr}
	 * labeled alternative in {@link PythonParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterFalseExpr(PythonParser.FalseExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FalseExpr}
	 * labeled alternative in {@link PythonParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitFalseExpr(PythonParser.FalseExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NoneExpr}
	 * labeled alternative in {@link PythonParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterNoneExpr(PythonParser.NoneExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NoneExpr}
	 * labeled alternative in {@link PythonParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitNoneExpr(PythonParser.NoneExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IdentifierExpr}
	 * labeled alternative in {@link PythonParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterIdentifierExpr(PythonParser.IdentifierExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IdentifierExpr}
	 * labeled alternative in {@link PythonParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitIdentifierExpr(PythonParser.IdentifierExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code EllipsisExpr}
	 * labeled alternative in {@link PythonParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterEllipsisExpr(PythonParser.EllipsisExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code EllipsisExpr}
	 * labeled alternative in {@link PythonParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitEllipsisExpr(PythonParser.EllipsisExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code CallTrailer}
	 * labeled alternative in {@link PythonParser#trailer}.
	 * @param ctx the parse tree
	 */
	void enterCallTrailer(PythonParser.CallTrailerContext ctx);
	/**
	 * Exit a parse tree produced by the {@code CallTrailer}
	 * labeled alternative in {@link PythonParser#trailer}.
	 * @param ctx the parse tree
	 */
	void exitCallTrailer(PythonParser.CallTrailerContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IndexTrailer}
	 * labeled alternative in {@link PythonParser#trailer}.
	 * @param ctx the parse tree
	 */
	void enterIndexTrailer(PythonParser.IndexTrailerContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IndexTrailer}
	 * labeled alternative in {@link PythonParser#trailer}.
	 * @param ctx the parse tree
	 */
	void exitIndexTrailer(PythonParser.IndexTrailerContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AttrTrailer}
	 * labeled alternative in {@link PythonParser#trailer}.
	 * @param ctx the parse tree
	 */
	void enterAttrTrailer(PythonParser.AttrTrailerContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AttrTrailer}
	 * labeled alternative in {@link PythonParser#trailer}.
	 * @param ctx the parse tree
	 */
	void exitAttrTrailer(PythonParser.AttrTrailerContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#testListComp}.
	 * @param ctx the parse tree
	 */
	void enterTestListComp(PythonParser.TestListCompContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#testListComp}.
	 * @param ctx the parse tree
	 */
	void exitTestListComp(PythonParser.TestListCompContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#compFor}.
	 * @param ctx the parse tree
	 */
	void enterCompFor(PythonParser.CompForContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#compFor}.
	 * @param ctx the parse tree
	 */
	void exitCompFor(PythonParser.CompForContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#compIter}.
	 * @param ctx the parse tree
	 */
	void enterCompIter(PythonParser.CompIterContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#compIter}.
	 * @param ctx the parse tree
	 */
	void exitCompIter(PythonParser.CompIterContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#compIf}.
	 * @param ctx the parse tree
	 */
	void enterCompIf(PythonParser.CompIfContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#compIf}.
	 * @param ctx the parse tree
	 */
	void exitCompIf(PythonParser.CompIfContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#dictOrSetMaker}.
	 * @param ctx the parse tree
	 */
	void enterDictOrSetMaker(PythonParser.DictOrSetMakerContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#dictOrSetMaker}.
	 * @param ctx the parse tree
	 */
	void exitDictOrSetMaker(PythonParser.DictOrSetMakerContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#dictItem}.
	 * @param ctx the parse tree
	 */
	void enterDictItem(PythonParser.DictItemContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#dictItem}.
	 * @param ctx the parse tree
	 */
	void exitDictItem(PythonParser.DictItemContext ctx);
	/**
	 * Enter a parse tree produced by {@link PythonParser#primary}.
	 * @param ctx the parse tree
	 */
	void enterPrimary(PythonParser.PrimaryContext ctx);
	/**
	 * Exit a parse tree produced by {@link PythonParser#primary}.
	 * @param ctx the parse tree
	 */
	void exitPrimary(PythonParser.PrimaryContext ctx);
}