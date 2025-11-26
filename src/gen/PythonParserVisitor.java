// Generated from D:/codes-folder/compiler/Test1/grammar/PythonParser.g4 by ANTLR 4.13.2
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
	 * Visit a parse tree produced by {@link PythonParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(PythonParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#simpleStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleStmt(PythonParser.SimpleStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#smallStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSmallStmt(PythonParser.SmallStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#compoundStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompoundStmt(PythonParser.CompoundStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignment(PythonParser.AssignmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#augAssignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAugAssignment(PythonParser.AugAssignmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#target}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTarget(PythonParser.TargetContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#expressionStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionStmt(PythonParser.ExpressionStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#returnStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnStmt(PythonParser.ReturnStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#importStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImportStmt(PythonParser.ImportStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#fromImportStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFromImportStmt(PythonParser.FromImportStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#moduleName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitModuleName(PythonParser.ModuleNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#importNames}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImportNames(PythonParser.ImportNamesContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#assertStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssertStmt(PythonParser.AssertStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#delStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDelStmt(PythonParser.DelStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#globalStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGlobalStmt(PythonParser.GlobalStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#nonlocalStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNonlocalStmt(PythonParser.NonlocalStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#raiseStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRaiseStmt(PythonParser.RaiseStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#yieldStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitYieldStmt(PythonParser.YieldStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#functionDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionDef(PythonParser.FunctionDefContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#classDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassDef(PythonParser.ClassDefContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#ifStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStmt(PythonParser.IfStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#forStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForStmt(PythonParser.ForStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#whileStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileStmt(PythonParser.WhileStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#withStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWithStmt(PythonParser.WithStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#withItem}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWithItem(PythonParser.WithItemContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#tryStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTryStmt(PythonParser.TryStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#exceptClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExceptClause(PythonParser.ExceptClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#decorated}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDecorated(PythonParser.DecoratedContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#decorator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDecorator(PythonParser.DecoratorContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#dottedName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDottedName(PythonParser.DottedNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#suite}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSuite(PythonParser.SuiteContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#paramList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParamList(PythonParser.ParamListContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#param}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParam(PythonParser.ParamContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#argList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgList(PythonParser.ArgListContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#argument}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgument(PythonParser.ArgumentContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(PythonParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#lambdaExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLambdaExpr(PythonParser.LambdaExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#orExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrExpr(PythonParser.OrExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#andExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAndExpr(PythonParser.AndExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#notExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNotExpr(PythonParser.NotExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#comparison}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComparison(PythonParser.ComparisonContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#compOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompOp(PythonParser.CompOpContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#bitwiseOr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBitwiseOr(PythonParser.BitwiseOrContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#bitwiseXor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBitwiseXor(PythonParser.BitwiseXorContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#bitwiseAnd}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBitwiseAnd(PythonParser.BitwiseAndContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#shiftExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitShiftExpr(PythonParser.ShiftExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#arithExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithExpr(PythonParser.ArithExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTerm(PythonParser.TermContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#factor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFactor(PythonParser.FactorContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#power}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPower(PythonParser.PowerContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#atomExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAtomExpr(PythonParser.AtomExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ParenExpr}
	 * labeled alternative in {@link PythonParser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParenExpr(PythonParser.ParenExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ListExpr}
	 * labeled alternative in {@link PythonParser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitListExpr(PythonParser.ListExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code DictOrSetExpr}
	 * labeled alternative in {@link PythonParser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDictOrSetExpr(PythonParser.DictOrSetExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NumberExpr}
	 * labeled alternative in {@link PythonParser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumberExpr(PythonParser.NumberExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code StringExpr}
	 * labeled alternative in {@link PythonParser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringExpr(PythonParser.StringExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TrueExpr}
	 * labeled alternative in {@link PythonParser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTrueExpr(PythonParser.TrueExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code FalseExpr}
	 * labeled alternative in {@link PythonParser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFalseExpr(PythonParser.FalseExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NoneExpr}
	 * labeled alternative in {@link PythonParser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNoneExpr(PythonParser.NoneExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IdentifierExpr}
	 * labeled alternative in {@link PythonParser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentifierExpr(PythonParser.IdentifierExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code EllipsisExpr}
	 * labeled alternative in {@link PythonParser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEllipsisExpr(PythonParser.EllipsisExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code CallTrailer}
	 * labeled alternative in {@link PythonParser#trailer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCallTrailer(PythonParser.CallTrailerContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IndexTrailer}
	 * labeled alternative in {@link PythonParser#trailer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIndexTrailer(PythonParser.IndexTrailerContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AttrTrailer}
	 * labeled alternative in {@link PythonParser#trailer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAttrTrailer(PythonParser.AttrTrailerContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#testListComp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTestListComp(PythonParser.TestListCompContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#compFor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompFor(PythonParser.CompForContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#compIter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompIter(PythonParser.CompIterContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#compIf}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompIf(PythonParser.CompIfContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#dictOrSetMaker}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDictOrSetMaker(PythonParser.DictOrSetMakerContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#dictItem}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDictItem(PythonParser.DictItemContext ctx);
	/**
	 * Visit a parse tree produced by {@link PythonParser#primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimary(PythonParser.PrimaryContext ctx);
}