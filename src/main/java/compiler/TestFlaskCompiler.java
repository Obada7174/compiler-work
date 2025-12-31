package compiler;

import compiler.ast.*;
import compiler.ast.flask.*;
import compiler.visitors.*;
import compiler.symboltable.*;
import compiler.semantic.*;
import grammar.*;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

/**
 * Test harness for Flask app.py compilation using Maven-friendly ClassLoader
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
        String resourcePath = args.length > 0 ? args[0] : "examples/app.py";

        System.out.println("╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║         FLASK APPLICATION COMPILER                        ║");
        System.out.println("║         Python + Flask → AST → Symbol Table               ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝\n");

        try {
            compileFlaskApp(resourcePath);
        } catch (Exception e) {
            System.err.println("Compilation failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void compileFlaskApp(String resourcePath) throws Exception {
        // ═══════════════════════════════════════════════════════════
        // LOAD SOURCE FILE VIA CLASSLOADER
        // ═══════════════════════════════════════════════════════════
        InputStream is = TestFlaskCompiler.class.getClassLoader().getResourceAsStream(resourcePath);
        if (is == null) {
            throw new RuntimeException("Resource not found: " + resourcePath);
        }

        Scanner scanner = new Scanner(is, StandardCharsets.UTF_8);
        StringBuilder sb = new StringBuilder();
        while (scanner.hasNextLine()) {
            sb.append(scanner.nextLine()).append("\n");
        }
        scanner.close();
        String sourceCode = sb.toString();

        System.out.println("Loaded resource: " + resourcePath);
        System.out.println("Source size: " + sourceCode.length() + " characters");

        // ═══════════════════════════════════════════════════════════
        // STAGE 1: LEXICAL ANALYSIS
        // ═══════════════════════════════════════════════════════════
        CharStream input = CharStreams.fromString(sourceCode);
        PythonLexer lexer = new PythonLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        tokens.fill();

        System.out.println("Token count: " + tokens.getTokens().size());

        // Print first 20 tokens
        System.out.println("\nFirst 20 tokens:");
        System.out.println("─────────────────────────────────────────");
        for (int i = 0; i < Math.min(20, tokens.size()); i++) {
            Token token = tokens.get(i);
            String tokenName = PythonLexer.VOCABULARY.getSymbolicName(token.getType());
            if (tokenName == null) tokenName = String.valueOf(token.getType());
            System.out.printf("  %-15s : '%s'%n",
                    tokenName,
                    token.getText().replace("\n", "\\n").replace("\r", "\\r"));
        }

        // ═══════════════════════════════════════════════════════════
        // STAGE 2: SYNTACTIC ANALYSIS
        // ═══════════════════════════════════════════════════════════
        tokens.reset();
        PythonParser parser = new PythonParser(tokens);
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

        // ═══════════════════════════════════════════════════════════
        // STAGE 3: AST CONSTRUCTION
        // ═══════════════════════════════════════════════════════════
        SimplePythonASTBuilder astBuilder = new SimplePythonASTBuilder();
        ASTNode ast = astBuilder.visit(parseTree);

        System.out.println("AST root: " + ast.getNodeType());

        // ═══════════════════════════════════════════════════════════
        // STAGE 4: SYMBOL TABLE CONSTRUCTION
        // ═══════════════════════════════════════════════════════════
        ClassicalSymbolTable symbolTable = ClassicalSymbolTable.allocate();
        SymbolTableBuilder stBuilder = new SymbolTableBuilder(symbolTable);
        stBuilder.build(ast);

        System.out.println("\nSymbol Table:");
        symbolTable.printTable();

        // ═══════════════════════════════════════════════════════════
        // STAGE 5: FLASK SEMANTIC ANALYSIS
        // ═══════════════════════════════════════════════════════════
        FlaskSemanticAnalyzer semanticAnalyzer = new FlaskSemanticAnalyzer();
        semanticAnalyzer.analyze(ast);

        System.out.println("\nSemantic Errors: " + semanticAnalyzer.getErrors().size());
        System.out.println("Semantic Warnings: " + semanticAnalyzer.getWarnings().size());

        // CLEANUP
        symbolTable.free();
        System.out.println("\nSymbol Table freed successfully");
    }
}
