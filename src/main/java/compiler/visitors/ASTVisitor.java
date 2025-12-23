package compiler.visitors;

import compiler.ast.*;

/**
 * Visitor interface for traversing the Abstract Syntax Tree
 * Implements the Visitor design pattern for AST traversal
 */
public interface ASTVisitor<T> {

    // Base visit method
    T visit(ASTNode node);

    // Program
    T visitProgram(ProgramNode node);

    // Statements
    T visitAssignment(AssignmentNode node);
    T visitIfStatement(IfStatementNode node);
    T visitForStatement(ForStatementNode node);
    T visitWhileStatement(WhileStatementNode node);
    T visitFunctionDef(FunctionDefNode node);
    T visitClassDef(ClassDefNode node);
    T visitReturn(ReturnStatementNode node);
    T visitImport(ImportStatementNode node);
    T visitTryStatement(TryStatementNode node);

    // Expressions
    T visitBinaryOp(BinaryOpNode node);
    T visitUnaryOp(UnaryOpNode node);
    T visitComparison(ComparisonNode node);
    T visitFunctionCall(FunctionCallNode node);
    T visitIdentifier(IdentifierNode node);
    T visitMemberAccess(MemberAccessNode node);
    T visitIndexAccess(IndexAccessNode node);
    T visitLambda(LambdaNode node);
    T visitComprehension(ComprehensionNode node);

    // Literals
    T visitNumberLiteral(NumberLiteralNode node);
    T visitStringLiteral(StringLiteralNode node);
    T visitBooleanLiteral(BooleanLiteralNode node);
    T visitNoneLiteral(NoneLiteralNode node);
    T visitListLiteral(ListLiteralNode node);
    T visitDictionaryLiteral(DictionaryLiteralNode node);
    T visitTupleLiteral(TupleLiteralNode node);
    T visitSetLiteral(SetLiteralNode node);

    // Helper nodes
    T visitParameter(ParameterNode node);
    T visitDecorator(DecoratorNode node);
    T visitExceptClause(ExceptClauseNode node);

    // Jinja2 specific
    T visitJinjaIf(JinjaIfNode node);
    T visitJinjaFor(JinjaForNode node);
    T visitJinjaBlock(JinjaBlockNode node);
    T visitJinja2Var(Jinja2VarNode node);

    // HTML/CSS specific (if needed)
    T visitHTMLElement(HTMLElementNode node);
    T visitHTMLText(HTMLTextNode node);
    T visitCSSRuleSet(CSSRuleSetNode node);
    T visitCSSDeclaration(CSSDeclarationNode node);
}
