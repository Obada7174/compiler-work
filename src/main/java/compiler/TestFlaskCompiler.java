package compiler;

import compiler.ast.*;
import compiler.ast.flask.*;
import compiler.visitors.*;
import compiler.symboltable.*;
import compiler.semantic.*;
import grammar.*;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import java.io.*;
import java.nio.file.*;
import java.util.List;

/**
 * Test harness for Flask app.py compilation.
 *
 * Demonstrates the complete compilation pipeline:
 * 1. Lexical Analysis (ANTLR Lexer)
 * 2. Syntactic Analysis (ANTLR Parser)
 * 3. AST Construction (SimplePythonASTBuilder)
 * 4. Symbol Table Construction (SymbolTableBuilder)
 * 5. Semantic Analysis (FlaskSemanticAnalyzer)
 */
public class TestFlaskCompiler {

    public static void main(String[] args) {
        String filePath = args.length > 0 ? args[0] : "app.py";

        System.out.println("╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║         FLASK APPLICATION COMPILER                        ║");
        System.out.println("║         Python + Flask → AST → Symbol Table               ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝\n");

        try {
            compileFlaskApp(filePath);
        } catch (Exception e) {
            System.err.println("Compilation failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void compileFlaskApp(String filePath) throws IOException {
        // ═══════════════════════════════════════════════════════════════════
        // STAGE 1: LEXICAL ANALYSIS
        // ═══════════════════════════════════════════════════════════════════
        System.out.println("═══════════════════════════════════════════════════════════");
        System.out.println("STAGE 1: LEXICAL ANALYSIS");
        System.out.println("═══════════════════════════════════════════════════════════\n");

        String sourceCode = Files.readString(Path.of(filePath));
        System.out.println("Source file: " + filePath);
        System.out.println("Source size: " + sourceCode.length() + " characters");

        CharStream input = CharStreams.fromString(sourceCode);
        PythonLexer lexer = new PythonLexer(input);

        CommonTokenStream tokens = new CommonTokenStream(lexer);
        tokens.fill();

        System.out.println("Token count: " + tokens.getTokens().size());

        // Print first few tokens
        System.out.println("\nFirst 20 tokens:");
        System.out.println("─────────────────────────────────────────");
        int count = 0;
        for (Token token : tokens.getTokens()) {
            if (count++ >= 20) break;
            String tokenName = PythonLexer.VOCABULARY.getSymbolicName(token.getType());
            if (tokenName == null) tokenName = String.valueOf(token.getType());
            System.out.printf("  %-15s : '%s'%n",
                tokenName,
                token.getText().replace("\n", "\\n").replace("\r", "\\r"));
        }
        System.out.println("  ... (" + (tokens.getTokens().size() - 20) + " more tokens)");

        // ═══════════════════════════════════════════════════════════════════
        // STAGE 2: SYNTACTIC ANALYSIS (PARSING)
        // ═══════════════════════════════════════════════════════════════════
        System.out.println("\n═══════════════════════════════════════════════════════════");
        System.out.println("STAGE 2: SYNTACTIC ANALYSIS (PARSING)");
        System.out.println("═══════════════════════════════════════════════════════════\n");

        tokens.reset();
        PythonParser parser = new PythonParser(tokens);

        // Custom error listener
        parser.removeErrorListeners();
        parser.addErrorListener(new BaseErrorListener() {
            @Override
            public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
                                   int line, int charPositionInLine, String msg,
                                   RecognitionException e) {
                System.err.println("  SYNTAX ERROR at line " + line + ":" +
                                   charPositionInLine + " - " + msg);
            }
        });

        PythonParser.File_inputContext parseTree = parser.file_input();

        int syntaxErrors = parser.getNumberOfSyntaxErrors();
        System.out.println("Parse tree root: " + parseTree.getClass().getSimpleName());
        System.out.println("Syntax errors: " + syntaxErrors);

        if (syntaxErrors > 0) {
            System.err.println("\n⚠ Warning: Source contains syntax errors. AST may be incomplete.");
        }

        // ═══════════════════════════════════════════════════════════════════
        // STAGE 3: AST CONSTRUCTION
        // ═══════════════════════════════════════════════════════════════════
        System.out.println("\n═══════════════════════════════════════════════════════════");
        System.out.println("STAGE 3: AST CONSTRUCTION");
        System.out.println("═══════════════════════════════════════════════════════════\n");

        SimplePythonASTBuilder astBuilder = new SimplePythonASTBuilder();
        ASTNode ast = astBuilder.visit(parseTree);

        System.out.println("AST root: " + ast.getNodeType());
        System.out.println("Flask imports detected: " + astBuilder.getFlaskImports());
        System.out.println("Flask apps detected: " + astBuilder.getFlaskAppNames());

        // Print AST structure
        System.out.println("\nAST Structure:");
        System.out.println("─────────────────────────────────────────");
        printAST(ast, 0, 3);

        // Count Flask-specific nodes
        int[] counts = countFlaskNodes(ast);
        System.out.println("\nFlask AST Node Summary:");
        System.out.println("  FlaskAppNode: " + counts[0]);
        System.out.println("  FlaskRouteFunction: " + counts[1]);
        System.out.println("  RouteDecoratorNode: " + counts[2]);
        System.out.println("  FlaskImportNode: " + counts[3]);

        // ═══════════════════════════════════════════════════════════════════
        // STAGE 4: SYMBOL TABLE CONSTRUCTION
        // ═══════════════════════════════════════════════════════════════════
        System.out.println("\n═══════════════════════════════════════════════════════════");
        System.out.println("STAGE 4: SYMBOL TABLE CONSTRUCTION");
        System.out.println("═══════════════════════════════════════════════════════════\n");

        ClassicalSymbolTable symbolTable = ClassicalSymbolTable.allocate();
        SymbolTableBuilder stBuilder = new SymbolTableBuilder(symbolTable);
        stBuilder.build(ast);

        // Print Flask summary
        stBuilder.printFlaskSummary();

        // Print symbol table
        System.out.println("\nSymbol Table:");
        symbolTable.printTable();

        // ═══════════════════════════════════════════════════════════════════
        // STAGE 5: FLASK SEMANTIC ANALYSIS
        // ═══════════════════════════════════════════════════════════════════
        System.out.println("\n═══════════════════════════════════════════════════════════");
        System.out.println("STAGE 5: FLASK SEMANTIC ANALYSIS");
        System.out.println("═══════════════════════════════════════════════════════════\n");

        FlaskSemanticAnalyzer semanticAnalyzer = new FlaskSemanticAnalyzer();
        semanticAnalyzer.analyze(ast);

        // ═══════════════════════════════════════════════════════════════════
        // COMPILATION SUMMARY
        // ═══════════════════════════════════════════════════════════════════
        System.out.println("\n═══════════════════════════════════════════════════════════");
        System.out.println("COMPILATION SUMMARY");
        System.out.println("═══════════════════════════════════════════════════════════\n");

        boolean success = syntaxErrors == 0 && !semanticAnalyzer.hasErrors();

        System.out.println("Source: " + filePath);
        System.out.println("Status: " + (success ? "✓ SUCCESS" : "✗ FAILED"));
        System.out.println("Syntax Errors: " + syntaxErrors);
        System.out.println("Semantic Errors: " + semanticAnalyzer.getErrors().size());
        System.out.println("Semantic Warnings: " + semanticAnalyzer.getWarnings().size());

        if (stBuilder.isFlaskApplication()) {
            System.out.println("\nFlask Application Info:");
            System.out.println("  Apps: " + stBuilder.getFlaskApps());
            System.out.println("  Routes: " + stBuilder.getRouteEndpoints().size());
            System.out.println("  Imports: " + stBuilder.getFlaskImports());
        }

        // Cleanup
        symbolTable.free();

        System.out.println("\n═══════════════════════════════════════════════════════════");
        System.out.println("COMPILATION COMPLETE");
        System.out.println("═══════════════════════════════════════════════════════════\n");
    }

    /**
     * Print AST structure with indentation.
     */
    private static void printAST(ASTNode node, int depth, int maxDepth) {
        if (node == null || depth > maxDepth) return;

        String indent = "  ".repeat(depth);
        String nodeInfo = node.getNodeType();

        // Add details for specific node types
        if (node instanceof FunctionDefNode func) {
            nodeInfo += ": " + func.getFunctionName() + "()";
        } else if (node instanceof ClassDefNode cls) {
            nodeInfo += ": " + cls.getClassName();
        } else if (node instanceof FlaskAppNode app) {
            nodeInfo += ": " + app.getAppVariableName();
        } else if (node instanceof FlaskRouteFunction route) {
            nodeInfo += ": " + route.getFunctionName() + " " + route.getRoutePaths();
        } else if (node instanceof IdentifierNode id) {
            nodeInfo += ": " + id.getName();
        } else if (node instanceof StringLiteralNode str) {
            String val = str.getValue();
            if (val.length() > 20) val = val.substring(0, 17) + "...";
            nodeInfo += ": \"" + val + "\"";
        }

        System.out.println(indent + "├─ " + nodeInfo);

        List<ASTNode> children = node.getChildren();
        for (int i = 0; i < children.size(); i++) {
            printAST(children.get(i), depth + 1, maxDepth);
        }
    }

    /**
     * Count Flask-specific AST nodes.
     */
    private static int[] countFlaskNodes(ASTNode node) {
        int[] counts = new int[4]; // [FlaskApp, FlaskRoute, RouteDecorator, FlaskImport]
        countFlaskNodesRecursive(node, counts);
        return counts;
    }

    private static void countFlaskNodesRecursive(ASTNode node, int[] counts) {
        if (node == null) return;

        if (node instanceof FlaskAppNode) counts[0]++;
        if (node instanceof FlaskRouteFunction) counts[1]++;
        if (node instanceof RouteDecoratorNode) counts[2]++;
        if (node instanceof FlaskImportNode) counts[3]++;

        for (ASTNode child : node.getChildren()) {
            countFlaskNodesRecursive(child, counts);
        }
    }
}
