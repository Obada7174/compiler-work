package compiler.visitors;

import compiler.ast.*;
import grammar.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Enhanced Python AST Builder with support for more Python constructs
 */
public class SimplePythonASTBuilder extends PythonParserBaseVisitor<ASTNode> {

    @Override
    public ASTNode visitFile_input(PythonParser.File_inputContext ctx) {
        int lineNumber = ctx.start != null ? ctx.start.getLine() : 1;
        ProgramNode program = new ProgramNode(lineNumber);

        if (ctx.stmt() != null) {
            for (PythonParser.StmtContext stmtCtx : ctx.stmt()) {
                ASTNode node = visit(stmtCtx);
                if (node != null) {
                    program.addChild(node);
                }
            }
        }

        return program;
    }

    @Override
    public ASTNode visitStmt(PythonParser.StmtContext ctx) {
        if (ctx.simple_stmt() != null) {
            return visit(ctx.simple_stmt());
        } else if (ctx.compound_stmt() != null) {
            return visit(ctx.compound_stmt());
        } else if (ctx.decorated() != null) {
            return visit(ctx.decorated());
        }
        return null;
    }

    @Override
    public ASTNode visitSimple_stmt(PythonParser.Simple_stmtContext ctx) {
        if (ctx.small_stmt() != null && !ctx.small_stmt().isEmpty()) {
            return visit(ctx.small_stmt(0));
        }
        return null;
    }

    @Override
    public ASTNode visitSmall_stmt(PythonParser.Small_stmtContext ctx) {
        if (ctx.assignment() != null) {
            return visit(ctx.assignment());
        } else if (ctx.return_stmt() != null) {
            return visit(ctx.return_stmt());
        } else if (ctx.import_stmt() != null) {
            return visit(ctx.import_stmt());
        } else if (ctx.expr_stmt() != null) {
            return visit(ctx.expr_stmt());
        }
        return null;
    }

    @Override
    public ASTNode visitAssignment(PythonParser.AssignmentContext ctx) {
        int lineNumber = ctx.start.getLine();

        // Get target (left side)
        List<ExpressionNode> targets = new ArrayList<>();
        if (ctx.atom_expr() != null) {
            ASTNode targetNode = visit(ctx.atom_expr());
            if (targetNode instanceof ExpressionNode) {
                targets.add((ExpressionNode) targetNode);
            }
        }

        // Get value (right side)
        ExpressionNode value = null;
        if (ctx.expr() != null) {
            ASTNode valueNode = visit(ctx.expr());
            if (valueNode instanceof ExpressionNode) {
                value = (ExpressionNode) valueNode;
            }
        }

        return new AssignmentNode(targets, value, lineNumber);
    }

    @Override
    public ASTNode visitExpr_stmt(PythonParser.Expr_stmtContext ctx) {
        // Just visit the expression
        if (ctx.expr() != null) {
            return visit(ctx.expr());
        }
        return null;
    }

    @Override
    public ASTNode visitReturn_stmt(PythonParser.Return_stmtContext ctx) {
        int lineNumber = ctx.start.getLine();
        ExpressionNode value = null;

        if (ctx.expr() != null) {
            ASTNode valueNode = visit(ctx.expr());
            if (valueNode instanceof ExpressionNode) {
                value = (ExpressionNode) valueNode;
            }
        }

        return new ReturnStatementNode(value, lineNumber);
    }

    @Override
    public ASTNode visitImport_stmt(PythonParser.Import_stmtContext ctx) {
        int lineNumber = ctx.start.getLine();

        if (ctx.dotted_name() != null && !ctx.dotted_name().isEmpty()) {
            String moduleName = ctx.dotted_name(0).getText();
            return new ImportStatementNode(moduleName, new ArrayList<>(),
                                         new ArrayList<>(), false, lineNumber);
        }

        return null;
    }

    @Override
    public ASTNode visitCompound_stmt(PythonParser.Compound_stmtContext ctx) {
        if (ctx.if_stmt() != null) {
            return visit(ctx.if_stmt());
        } else if (ctx.for_stmt() != null) {
            return visit(ctx.for_stmt());
        } else if (ctx.while_stmt() != null) {
            return visit(ctx.while_stmt());
        } else if (ctx.funcdef() != null) {
            return visit(ctx.funcdef());
        } else if (ctx.classdef() != null) {
            return visit(ctx.classdef());
        } else if (ctx.try_stmt() != null) {
            return visit(ctx.try_stmt());
        }
        return null;
    }

    @Override
    public ASTNode visitDecorated(PythonParser.DecoratedContext ctx) {
        int lineNumber = ctx.start.getLine();

        // Collect all decorators
        List<DecoratorNode> decorators = new ArrayList<>();
        if (ctx.decorator() != null) {
            for (PythonParser.DecoratorContext decoratorCtx : ctx.decorator()) {
                ASTNode decoratorNode = visit(decoratorCtx);
                if (decoratorNode instanceof DecoratorNode) {
                    decorators.add((DecoratorNode) decoratorNode);
                }
            }
        }

        // Get the decorated function or class
        ASTNode decorated = null;
        if (ctx.funcdef() != null) {
            decorated = visit(ctx.funcdef());
            if (decorated instanceof FunctionDefNode) {
                ((FunctionDefNode) decorated).setDecorators(decorators);
            }
        } else if (ctx.classdef() != null) {
            decorated = visit(ctx.classdef());
            if (decorated instanceof ClassDefNode) {
                ((ClassDefNode) decorated).setDecorators(decorators);
            }
        }

        return decorated;
    }

    @Override
    public ASTNode visitDecorator(PythonParser.DecoratorContext ctx) {
        int lineNumber = ctx.start.getLine();

        // Get decorator expression (atom_expr)
        ExpressionNode decoratorExpr = null;
        if (ctx.atom_expr() != null) {
            ASTNode exprNode = visit(ctx.atom_expr());
            if (exprNode instanceof ExpressionNode) {
                decoratorExpr = (ExpressionNode) exprNode;
            }
        }

        // Get decorator arguments if present (call)
        List<ExpressionNode> arguments = new ArrayList<>();
        if (ctx.call() != null && ctx.call().arglist() != null) {
            PythonParser.ArglistContext arglist = ctx.call().arglist();
            if (arglist.argument() != null) {
                for (PythonParser.ArgumentContext argCtx : arglist.argument()) {
                    ASTNode argNode = visit(argCtx);
                    if (argNode instanceof ExpressionNode) {
                        arguments.add((ExpressionNode) argNode);
                    }
                }
            }
        }

        return new DecoratorNode(decoratorExpr, arguments, lineNumber);
    }

    @Override
    public ASTNode visitIf_stmt(PythonParser.If_stmtContext ctx) {
        int lineNumber = ctx.start.getLine();

        ExpressionNode condition = null;
        if (ctx.expr() != null && !ctx.expr().isEmpty()) {
            ASTNode condNode = visit(ctx.expr(0));
            if (condNode instanceof ExpressionNode) {
                condition = (ExpressionNode) condNode;
            }
        }

        List<ASTNode> thenBlock = new ArrayList<>();
        List<ASTNode> elseBlock = new ArrayList<>();

        if (ctx.suite() != null && !ctx.suite().isEmpty()) {
            ASTNode suiteNode = visit(ctx.suite(0));
            if (suiteNode != null) {
                thenBlock.addAll(suiteNode.getChildren());
            }

            // Handle else block
            if (ctx.suite().size() > 1) {
                ASTNode elseNode = visit(ctx.suite(1));
                if (elseNode != null) {
                    elseBlock.addAll(elseNode.getChildren());
                }
            }
        }

        return new IfStatementNode(condition, thenBlock, elseBlock, lineNumber);
    }

    @Override
    public ASTNode visitFuncdef(PythonParser.FuncdefContext ctx) {
        int lineNumber = ctx.start.getLine();
        String functionName = ctx.NAME() != null ? ctx.NAME().getText() : "unknown";

        // Parse parameters (simplified)
        List<ParameterNode> parameters = new ArrayList<>();
        if (ctx.parameters() != null) {
            // Try to extract parameter names from text
            String paramsText = ctx.parameters().getText();
            if (!paramsText.equals("()") && paramsText.length() > 2) {
                String[] paramNames = paramsText.substring(1, paramsText.length() - 1).split(",");
                for (String paramName : paramNames) {
                    paramName = paramName.trim();
                    if (!paramName.isEmpty()) {
                        parameters.add(new ParameterNode(paramName, null, null, lineNumber));
                    }
                }
            }
        }

        // Parse function body
        List<ASTNode> body = new ArrayList<>();
        if (ctx.suite() != null) {
            ASTNode suiteNode = visit(ctx.suite());
            if (suiteNode != null) {
                body.addAll(suiteNode.getChildren());
            }
        }

        return new FunctionDefNode(functionName, parameters, body,
                                  new ArrayList<>(), null, lineNumber);
    }

    @Override
    public ASTNode visitClassdef(PythonParser.ClassdefContext ctx) {
        int lineNumber = ctx.start.getLine();
        String className = ctx.NAME() != null ? ctx.NAME().getText() : "unknown";

        // Get base classes (simplified)
        List<ExpressionNode> baseClasses = new ArrayList<>();

        // Get class body
        List<ASTNode> body = new ArrayList<>();
        if (ctx.suite() != null) {
            ASTNode suiteNode = visit(ctx.suite());
            if (suiteNode != null) {
                body.addAll(suiteNode.getChildren());
            }
        }

        return new ClassDefNode(className, baseClasses, body, new ArrayList<>(), lineNumber);
    }

    @Override
    public ASTNode visitFor_stmt(PythonParser.For_stmtContext ctx) {
        int lineNumber = ctx.start.getLine();

        // Get loop variable
        String target = "";
        if (ctx.NAME() != null) {
            target = ctx.NAME().getText();
        }

        // Get iterable expression
        ExpressionNode iterable = null;
        if (ctx.expr() != null) {
            ASTNode iterNode = visit(ctx.expr());
            if (iterNode instanceof ExpressionNode) {
                iterable = (ExpressionNode) iterNode;
            }
        }

        // Get loop body
        List<ASTNode> body = new ArrayList<>();
        if (ctx.suite() != null && !ctx.suite().isEmpty()) {
            ASTNode suiteNode = visit(ctx.suite(0));
            if (suiteNode != null) {
                body.addAll(suiteNode.getChildren());
            }
        }

        List<ASTNode> elseBlock = new ArrayList<>();
        IdentifierNode targetNode = new IdentifierNode(target, lineNumber);
        return new ForStatementNode(targetNode, iterable, body, elseBlock, lineNumber);
    }

    @Override
    public ASTNode visitWhile_stmt(PythonParser.While_stmtContext ctx) {
        int lineNumber = ctx.start.getLine();

        // Get condition
        ExpressionNode condition = null;
        if (ctx.expr() != null) {
            ASTNode condNode = visit(ctx.expr());
            if (condNode instanceof ExpressionNode) {
                condition = (ExpressionNode) condNode;
            }
        }

        // Get loop body
        List<ASTNode> body = new ArrayList<>();
        if (ctx.suite() != null && !ctx.suite().isEmpty()) {
            ASTNode suiteNode = visit(ctx.suite(0));
            if (suiteNode != null) {
                body.addAll(suiteNode.getChildren());
            }
        }

        List<ASTNode> elseBlock = new ArrayList<>();
        return new WhileStatementNode(condition, body, elseBlock, lineNumber);
    }

    @Override
    public ASTNode visitTry_stmt(PythonParser.Try_stmtContext ctx) {
        int lineNumber = ctx.start.getLine();

        // Get try block
        List<ASTNode> tryBlock = new ArrayList<>();
        if (ctx.suite() != null && !ctx.suite().isEmpty()) {
            ASTNode suiteNode = visit(ctx.suite(0));
            if (suiteNode != null) {
                tryBlock.addAll(suiteNode.getChildren());
            }
        }

        List<ExceptClauseNode> exceptClauses = new ArrayList<>();
        List<ASTNode> elseBlock = new ArrayList<>();
        List<ASTNode> finallyBlock = new ArrayList<>();

        return new TryStatementNode(tryBlock, exceptClauses, elseBlock, finallyBlock, lineNumber);
    }

    @Override
    public ASTNode visitSuite(PythonParser.SuiteContext ctx) {
        ProgramNode container = new ProgramNode(ctx.start != null ? ctx.start.getLine() : 1);

        if (ctx.stmt() != null) {
            for (PythonParser.StmtContext stmtCtx : ctx.stmt()) {
                ASTNode node = visit(stmtCtx);
                if (node != null) {
                    container.addChild(node);
                }
            }
        }

        return container;
    }

    @Override
    public ASTNode visitExpr(PythonParser.ExprContext ctx) {
        if (ctx.comparison() != null) {
            return visit(ctx.comparison());
        }
        return null;
    }

    @Override
    public ASTNode visitComparison(PythonParser.ComparisonContext ctx) {
        if (ctx.arith_expr() != null && !ctx.arith_expr().isEmpty()) {
            ASTNode left = visit(ctx.arith_expr(0));

            // Check if there's a comparison operator
            if (ctx.arith_expr().size() > 1) {
                int lineNumber = ctx.start.getLine();
                String operator = "";

                if (ctx.EQ() != null) operator = "==";
                else if (ctx.NE() != null) operator = "!=";
                else if (ctx.LT() != null) operator = "<";
                else if (ctx.GT() != null) operator = ">";
                else if (ctx.LE() != null) operator = "<=";
                else if (ctx.GE() != null) operator = ">=";

                ASTNode right = visit(ctx.arith_expr(1));

                if (left instanceof ExpressionNode && right instanceof ExpressionNode) {
                    return new ComparisonNode(operator, (ExpressionNode) left, (ExpressionNode) right, lineNumber);
                }
            }

            return left;
        }
        return null;
    }

    @Override
    public ASTNode visitArith_expr(PythonParser.Arith_exprContext ctx) {
        if (ctx.atom_expr() != null && !ctx.atom_expr().isEmpty()) {
            ASTNode left = visit(ctx.atom_expr(0));

            // Handle binary operations (PLUS, MINUS, STAR, DIV, MOD)
            if (ctx.atom_expr().size() > 1) {
                int lineNumber = ctx.start.getLine();

                // Process all operators from left to right
                for (int i = 1; i < ctx.atom_expr().size(); i++) {
                    String operator = "";

                    // Determine which operator is at position i-1
                    if (ctx.PLUS() != null && !ctx.PLUS().isEmpty() && ctx.PLUS().size() >= i) {
                        operator = "+";
                    } else if (ctx.MINUS() != null && !ctx.MINUS().isEmpty() && ctx.MINUS().size() >= i) {
                        operator = "-";
                    } else if (ctx.STAR() != null && !ctx.STAR().isEmpty() && ctx.STAR().size() >= i) {
                        operator = "*";
                    } else if (ctx.DIV() != null && !ctx.DIV().isEmpty() && ctx.DIV().size() >= i) {
                        operator = "/";
                    } else if (ctx.MOD() != null && !ctx.MOD().isEmpty() && ctx.MOD().size() >= i) {
                        operator = "%";
                    }

                    ASTNode right = visit(ctx.atom_expr(i));

                    if (left instanceof ExpressionNode && right instanceof ExpressionNode && !operator.isEmpty()) {
                        left = new BinaryOpNode(operator, (ExpressionNode) left, (ExpressionNode) right, lineNumber);
                    }
                }
            }

            return left;
        }
        return null;
    }

    @Override
    public ASTNode visitAtom_expr(PythonParser.Atom_exprContext ctx) {
        int lineNumber = ctx.start.getLine();
        ASTNode base = null;

        // Get the base atom
        if (ctx.atom() != null) {
            base = visit(ctx.atom());
        }

        if (base == null) {
            return null;
        }

        // Handle trailers (function calls, attribute access)
        if (ctx.trailer() != null && !ctx.trailer().isEmpty()) {
            for (PythonParser.TrailerContext trailerCtx : ctx.trailer()) {
                base = visitTrailerWithBase(trailerCtx, base);
            }
        }

        return base;
    }

    // Helper method to process trailers
    private ASTNode visitTrailerWithBase(PythonParser.TrailerContext ctx, ASTNode base) {
        int lineNumber = ctx.start.getLine();

        // Function call: has call() child
        if (ctx.call() != null) {
            List<ExpressionNode> arguments = new ArrayList<>();

            // Extract arguments from arglist
            if (ctx.call().arglist() != null) {
                PythonParser.ArglistContext arglist = ctx.call().arglist();
                if (arglist.argument() != null) {
                    for (PythonParser.ArgumentContext argCtx : arglist.argument()) {
                        ASTNode argNode = visit(argCtx);
                        if (argNode instanceof ExpressionNode) {
                            arguments.add((ExpressionNode) argNode);
                        }
                    }
                }
            }

            if (base instanceof ExpressionNode) {
                return new FunctionCallNode((ExpressionNode) base, arguments, lineNumber);
            }
        }

        // Attribute access: has DOT and NAME
        if (ctx.DOT() != null && ctx.NAME() != null) {
            String memberName = ctx.NAME().getText();
            if (base instanceof ExpressionNode) {
                return new MemberAccessNode((ExpressionNode) base, memberName, lineNumber);
            }
        }

        return base;
    }

    @Override
    public ASTNode visitPositionalArg(PythonParser.PositionalArgContext ctx) {
        // Visit the expression in the positional argument
        if (ctx.expr() != null) {
            return visit(ctx.expr());
        }
        return null;
    }

    @Override
    public ASTNode visitKeywordArg(PythonParser.KeywordArgContext ctx) {
        // Visit the expression in the keyword argument (value part)
        if (ctx.expr() != null) {
            return visit(ctx.expr());
        }
        return null;
    }

    @Override
    public ASTNode visitAtom(PythonParser.AtomContext ctx) {
        int lineNumber = ctx.start.getLine();

        if (ctx.NAME() != null) {
            return new IdentifierNode(ctx.NAME().getText(), lineNumber);
        } else if (ctx.NUMBER() != null) {
            return new NumberLiteralNode(Double.parseDouble(ctx.NUMBER().getText()), lineNumber);
        } else if (ctx.STRING() != null) {
            String text = ctx.STRING().getText();
            String value = text.length() > 2 ? text.substring(1, text.length() - 1) : text;
            return new StringLiteralNode(value, lineNumber);
        } else if (ctx.FSTRING() != null) {
            String text = ctx.FSTRING().getText();
            String value = text.length() > 3 ? text.substring(2, text.length() - 1) : text;
            return new StringLiteralNode(value, lineNumber);
        } else if (ctx.TRUE() != null) {
            return new BooleanLiteralNode(true, lineNumber);
        } else if (ctx.FALSE() != null) {
            return new BooleanLiteralNode(false, lineNumber);
        } else if (ctx.NONE() != null) {
            return new NoneLiteralNode(lineNumber);
        } else if (ctx.listLit() != null) {
            return visit(ctx.listLit());
        } else if (ctx.dictLit() != null) {
            return visit(ctx.dictLit());
        } else if (ctx.expr() != null) {
            // Parenthesized expression
            return visit(ctx.expr());
        }

        return null;
    }

    @Override
    public ASTNode visitListLit(PythonParser.ListLitContext ctx) {
        int lineNumber = ctx.start.getLine();
        List<ExpressionNode> elements = new ArrayList<>();

        if (ctx.expr() != null) {
            for (PythonParser.ExprContext exprCtx : ctx.expr()) {
                ASTNode element = visit(exprCtx);
                if (element instanceof ExpressionNode) {
                    elements.add((ExpressionNode) element);
                }
            }
        }

        return new ListLiteralNode(elements, lineNumber);
    }

    @Override
    public ASTNode visitDictLit(PythonParser.DictLitContext ctx) {
        int lineNumber = ctx.start.getLine();
        java.util.Map<ExpressionNode, ExpressionNode> entries = new java.util.LinkedHashMap<>();

        if (ctx.dictItem() != null) {
            for (PythonParser.DictItemContext itemCtx : ctx.dictItem()) {
                // Get key
                ExpressionNode key = null;
                if (itemCtx.STRING() != null) {
                    String keyText = itemCtx.STRING().getText();
                    String keyValue = keyText.length() > 2 ? keyText.substring(1, keyText.length() - 1) : keyText;
                    key = new StringLiteralNode(keyValue, lineNumber);
                } else if (itemCtx.NAME() != null) {
                    String keyText = itemCtx.NAME().getText();
                    key = new StringLiteralNode(keyText, lineNumber);
                }

                // Get value
                ExpressionNode value = null;
                if (itemCtx.expr() != null) {
                    ASTNode valueNode = visit(itemCtx.expr());
                    if (valueNode instanceof ExpressionNode) {
                        value = (ExpressionNode) valueNode;
                    }
                }

                if (key != null && value != null) {
                    entries.put(key, value);
                }
            }
        }

        return new DictionaryLiteralNode(entries, lineNumber);
    }
}
