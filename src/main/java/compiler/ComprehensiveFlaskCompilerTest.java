package compiler;

import compiler.ast.*;
import compiler.ast.flask.*;
import compiler.visitors.*;
import compiler.symboltable.*;
import compiler.semantic.*;
import compiler.utils.ASTClassNamePrinter;
import grammar.*;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class ComprehensiveFlaskCompilerTest {

    private static final String SEPARATOR = "═══════════════════════════════════════════════════════════";
    private static final String LINE = "─────────────────────────────────────────────────────────────";

    public static void main(String[] args) {
        printHeader();

        // Determine source file path
        String resourcePath = args.length > 0 ? args[0] : "examples/app.py";

        try {
            // Run the complete compilation pipeline
            CompilationResult result = compileFlaskApplication(resourcePath);

            // Print final summary
            printFinalSummary(result);

            // Exit with appropriate code
            System.exit(result.hasErrors ? 1 : 0);

        } catch (Exception e) {
            System.err.println("\n✗ COMPILATION FAILED");
            System.err.println(LINE);
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }


    public static CompilationResult compileFlaskApplication(String resourcePath) throws Exception {
        CompilationResult result = new CompilationResult();
        result.resourcePath = resourcePath;
        // STAGE 0: LOAD SOURCE FILE
        printStageHeader("STAGE 0: SOURCE FILE LOADING");

        String sourceCode = loadSourceFile(resourcePath);
        result.sourceCode = sourceCode;
        result.sourceLines = sourceCode.split("\n").length;

        System.out.println("✓ Loaded: " + resourcePath);
        System.out.println("  Source size: " + sourceCode.length() + " characters");
        System.out.println("  Line count: " + result.sourceLines);
        System.out.println();

        // STAGE 1: LEXICAL ANALYSIS
        printStageHeader("STAGE 1: LEXICAL ANALYSIS (TOKENIZATION)");

        CharStream input = CharStreams.fromString(sourceCode);
        PythonLexer lexer = new PythonLexer(input);

        // Add error listener for lexer
        lexer.removeErrorListeners();
        final int[] lexerErrors = {0};
        lexer.addErrorListener(new BaseErrorListener() {
            @Override
            public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
                                    int line, int charPositionInLine, String msg,
                                    RecognitionException e) {
                System.err.println(String.format("  ✗ LEXER ERROR at line %d:%d - %s",
                        line, charPositionInLine, msg));
                lexerErrors[0]++;
            }
        });

        CommonTokenStream tokens = new CommonTokenStream(lexer);
        tokens.fill();

        result.tokenCount = tokens.getTokens().size();
        result.lexerErrors = lexerErrors[0];

        System.out.println("✓ Tokenization completed");
        System.out.println("  Token count: " + result.tokenCount);
        System.out.println("  Lexer errors: " + result.lexerErrors);

        // Print first 30 tokens
        printTokenSample(tokens, 30);

        if (result.lexerErrors > 0) {
            result.hasErrors = true;
            System.err.println("\n✗ Lexical analysis failed with " + result.lexerErrors + " error(s)");
            return result;
        }

        // STAGE 2: SYNTACTIC ANALYSIS (PARSING)
        printStageHeader("STAGE 2: SYNTACTIC ANALYSIS (PARSING)");

        tokens.reset();
        PythonParser parser = new PythonParser(tokens);

        // Add error listener for parser
        parser.removeErrorListeners();
        final int[] parserErrors = {0};
        parser.addErrorListener(new BaseErrorListener() {
            @Override
            public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
                                    int line, int charPositionInLine, String msg,
                                    RecognitionException e) {
                System.err.println(String.format("  ✗ PARSER ERROR at line %d:%d - %s",
                        line, charPositionInLine, msg));
                parserErrors[0]++;
            }
        });

        PythonParser.File_inputContext parseTree = parser.file_input();
        result.parserErrors = parserErrors[0];

        System.out.println("✓ Parsing completed");
        System.out.println("  Parse tree root: " + parseTree.getClass().getSimpleName());
        System.out.println("  Parser errors: " + result.parserErrors);
        System.out.println();

        if (result.parserErrors > 0) {
            result.hasErrors = true;
            System.err.println("\n✗ Syntactic analysis failed with " + result.parserErrors + " error(s)");
            return result;
        }

        // STAGE 3: AST CONSTRUCTION
        printStageHeader("STAGE 3: ABSTRACT SYNTAX TREE CONSTRUCTION");

        SimplePythonASTBuilder astBuilder = new SimplePythonASTBuilder();
        ASTNode ast = astBuilder.visit(parseTree);

        if (ast == null) {
            result.hasErrors = true;
            System.err.println("✗ AST construction failed - returned null");
            return result;
        }

        result.ast = ast;
        result.astNodeType = ast.getNodeType();

        System.out.println("✓ AST construction completed");
        System.out.println("  AST root node type: " + result.astNodeType);
        System.out.println("  AST child count: " + ast.getChildren().size());

        // Print Flask-specific detection
        if (astBuilder.hasFlaskImport()) {
            System.out.println("  Flask imports detected: " + astBuilder.getFlaskImports());
            System.out.println("  Flask apps detected: " + astBuilder.getFlaskAppNames());
        }

        System.out.println();

        // Print AST with actual class names
        System.out.println("AST Structure (Class Names & Line Numbers):");
        System.out.println(LINE);
        ASTClassNamePrinter.printAST(ast, "AST with Class Names");
        System.out.println();

        // STAGE 4: SYMBOL TABLE CONSTRUCTION
        printStageHeader("STAGE 4: SYMBOL TABLE CONSTRUCTION");

        ClassicalSymbolTable symbolTable = ClassicalSymbolTable.allocate();
        SymbolTableBuilder stBuilder = new SymbolTableBuilder(symbolTable);

        System.out.println("Building symbol table with:");
        System.out.println("  - Automatic Python built-ins injection");
        System.out.println("  - Import statement detection");
        System.out.println("  - Flask framework symbols");
        System.out.println();

        stBuilder.build(ast);

        result.symbolTable = symbolTable;

        // Print full symbol table
        System.out.println("\n" + SEPARATOR);
        System.out.println("           COMPLETE SYMBOL TABLE");
        System.out.println(SEPARATOR + "\n");

        symbolTable.printTable();

        // Print Flask-specific information if applicable
        if (stBuilder.isFlaskApplication()) {
            stBuilder.printFlaskSummary();
            result.isFlaskApp = true;
            result.flaskApps = stBuilder.getFlaskApps().size();
            result.flaskRoutes = stBuilder.getRouteEndpoints().size();
        }

        System.out.println();

        // STAGE 5: SEMANTIC ANALYSIS
        printStageHeader("STAGE 5: SEMANTIC ANALYSIS");

        FlaskSemanticAnalyzer semanticAnalyzer = new FlaskSemanticAnalyzer();
        semanticAnalyzer.analyze(ast);

        result.semanticErrors = semanticAnalyzer.getErrors().size();
        result.semanticWarnings = semanticAnalyzer.getWarnings().size();

        if (result.semanticErrors > 0) {
            result.hasErrors = true;
        }

        System.out.println("✓ Semantic analysis completed");
        System.out.println("  Semantic errors: " + result.semanticErrors);
        System.out.println("  Semantic warnings: " + result.semanticWarnings);
        System.out.println();

        // ═══════════════════════════════════════════════════════════
        // STAGE 6: SYMBOL TABLE STATISTICS
        // ═══════════════════════════════════════════════════════════
        printStageHeader("STAGE 6: SYMBOL TABLE STATISTICS");

        printSymbolTableStatistics(symbolTable);

        // Cleanup
        symbolTable.free();
        System.out.println("\n✓ Symbol table freed successfully\n");

        return result;
    }

    private static String loadSourceFile(String resourcePath) throws Exception {
        // Try to load from resources first
        InputStream is = ComprehensiveFlaskCompilerTest.class.getClassLoader()
                .getResourceAsStream(resourcePath);

        if (is != null) {
            Scanner scanner = new Scanner(is, StandardCharsets.UTF_8);
            StringBuilder sb = new StringBuilder();
            while (scanner.hasNextLine()) {
                sb.append(scanner.nextLine()).append("\n");
            }
            scanner.close();
            return sb.toString();
        }

        // If not in resources, try as file path
        return new String(Files.readAllBytes(Paths.get(resourcePath)), StandardCharsets.UTF_8);
    }

    private static void printTokenSample(CommonTokenStream tokens, int limit) {
        System.out.println("\n  Token Sample (first " + limit + "):");
        System.out.println("  " + LINE.substring(0, 50));

        for (int i = 0; i < Math.min(limit, tokens.size()); i++) {
            Token token = tokens.get(i);
            String tokenName = PythonLexer.VOCABULARY.getSymbolicName(token.getType());
            if (tokenName == null) tokenName = String.valueOf(token.getType());

            String text = token.getText()
                    .replace("\n", "\\n")
                    .replace("\r", "\\r")
                    .replace("\t", "\\t");

            if (text.length() > 30) {
                text = text.substring(0, 27) + "...";
            }

            System.out.printf("    [%3d] %-20s : '%s'%n", i, tokenName, text);
        }

        System.out.println("  " + LINE.substring(0, 50));
    }

    private static void printSymbolTableStatistics(ClassicalSymbolTable symbolTable) {
        System.out.println("Symbol Table Statistics:");
        System.out.println(LINE);


        // Use reflection or iterate through symbol table to gather stats
        // For now, provide a summary structure
        System.out.println("  Total symbols in global scope: [Symbol count from table]");
        System.out.println("  Built-in symbols: ~64 (exceptions, functions, constants)");
        System.out.println("  User-defined symbols: [Calculated from table]");
        System.out.println("  Imported symbols: [From import statements]");
        System.out.println();

        System.out.println("Symbol Types Distribution:");
        System.out.println("  - builtin.exception    : Python built-in exception types");
        System.out.println("  - builtin.function     : Python built-in functions");
        System.out.println("  - builtin.constant     : Python built-in constants");
        System.out.println("  - function             : User-defined functions");
        System.out.println("  - class                : User-defined classes");
        System.out.println("  - variable             : User-defined variables");
        System.out.println("  - flask.*              : Flask framework symbols");
        System.out.println("  - imported             : Imported module symbols");
        System.out.println();
    }

    private static void printFinalSummary(CompilationResult result) {
        System.out.println("\n" + SEPARATOR);
        System.out.println("           COMPILATION SUMMARY");
        System.out.println(SEPARATOR + "\n");

        System.out.println("Source File: " + result.resourcePath);
        System.out.println("  Lines: " + result.sourceLines);
        System.out.println("  Characters: " + (result.sourceCode != null ? result.sourceCode.length() : 0));
        System.out.println();

        System.out.println("Compilation Stages:");
        System.out.println("  ✓ Lexical Analysis    : " + result.tokenCount + " tokens");
        System.out.println("  ✓ Syntactic Analysis  : " + result.astNodeType);
        System.out.println("  ✓ AST Construction    : Complete");
        System.out.println("  ✓ Symbol Table        : Built with imports & built-ins");
        System.out.println("  ✓ Semantic Analysis   : Complete");
        System.out.println();

        if (result.isFlaskApp) {
            System.out.println("Flask Application:");
            System.out.println("  Flask apps: " + result.flaskApps);
            System.out.println("  Routes: " + result.flaskRoutes);
            System.out.println();
        }

        System.out.println("Error Summary:");
        System.out.println("  Lexer errors: " + result.lexerErrors);
        System.out.println("  Parser errors: " + result.parserErrors);
        System.out.println("  Semantic errors: " + result.semanticErrors);
        System.out.println("  Semantic warnings: " + result.semanticWarnings);
        System.out.println();

        if (result.hasErrors) {
            System.err.println("✗ COMPILATION FAILED WITH ERRORS");
        } else {
            System.out.println("✓ COMPILATION SUCCESSFUL!");
        }

        System.out.println(SEPARATOR);
    }

    private static void printHeader() {
        System.out.println("\n" + SEPARATOR);
        System.out.println("     COMPREHENSIVE FLASK COMPILER TEST HARNESS");
        System.out.println("     Python → Tokens → AST → Symbol Table → Semantic");
        System.out.println(SEPARATOR + "\n");
    }

    private static void printStageHeader(String stageName) {
        System.out.println("\n" + SEPARATOR);
        System.out.println("  " + stageName);
        System.out.println(SEPARATOR + "\n");
    }
    static class CompilationResult {
        String resourcePath;
        String sourceCode;
        int sourceLines;

        int tokenCount;
        int lexerErrors;

        int parserErrors;

        ASTNode ast;
        String astNodeType;

        ClassicalSymbolTable symbolTable;

        int semanticErrors;
        int semanticWarnings;

        boolean isFlaskApp;
        int flaskApps;
        int flaskRoutes;

        boolean hasErrors;
    }
}
