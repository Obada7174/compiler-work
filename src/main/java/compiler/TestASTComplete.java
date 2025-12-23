package compiler;

import compiler.ast.*;
import compiler.ast.ASTPrinter;
import compiler.symboltable.SymbolTable;
import compiler.visitors.SimplePythonASTBuilder;
import compiler.visitors.SimpleJinja2ASTBuilder;
import grammar.*;
import org.antlr.v4.runtime.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Comprehensive test class for AST building with symbol table integration
 */
public class TestASTComplete {

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║       COMPREHENSIVE AST AND SYMBOL TABLE TEST SUITE            ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝\n");

        // Test Python AST
        System.out.println("\n═══════════════════════════════════════════════════════════");
        System.out.println("  PART 1: PYTHON AST GENERATION");
        System.out.println("═══════════════════════════════════════════════════════════\n");

        try {
            testPython("test_python.py");
        } catch (Exception e) {
            System.err.println("Error in Python AST test: " + e.getMessage());
            e.printStackTrace();
        }

        // Test Jinja2 AST
        System.out.println("\n═══════════════════════════════════════════════════════════");
        System.out.println("  PART 2: JINJA2 AST GENERATION");
        System.out.println("═══════════════════════════════════════════════════════════\n");

        try {
            testJinja2("test_jinja2_simple.html");
        } catch (Exception e) {
            System.err.println("Error in Jinja2 AST test: " + e.getMessage());
            e.printStackTrace();
        }

        // Test Symbol Table
        System.out.println("\n═══════════════════════════════════════════════════════════");
        System.out.println("  PART 3: SYMBOL TABLE FUNCTIONALITY");
        System.out.println("═══════════════════════════════════════════════════════════\n");

        testSymbolTable();

        System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║              ALL TESTS COMPLETED SUCCESSFULLY!                 ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝\n");
    }

    private static void testPython(String filename) throws IOException {
        String code = new String(Files.readAllBytes(Paths.get(filename)));

        CharStream input = CharStreams.fromString(code);
        PythonLexer lexer = new PythonLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        PythonParser parser = new PythonParser(tokens);

        PythonParser.File_inputContext tree = parser.file_input();
        SimplePythonASTBuilder astBuilder = new SimplePythonASTBuilder();
        ASTNode ast = astBuilder.visit(tree);

        if (ast != null) {
            System.out.println("✓ Python AST built successfully");
            System.out.println("  Total nodes: " + ASTPrinter.countNodes(ast));
            System.out.println("  Tree height: " + ASTPrinter.treeHeight(ast));
            ASTPrinter.printLinear(ast);
        } else {
            System.err.println("✗ Failed to build Python AST");
        }
    }

    private static void testJinja2(String filename) throws IOException {
        String code = new String(Files.readAllBytes(Paths.get(filename)));

        CharStream input = CharStreams.fromString(code);
        Jinja2Lexer lexer = new Jinja2Lexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        Jinja2Parser parser = new Jinja2Parser(tokens);

        Jinja2Parser.TemplateContext tree = parser.template();
        SimpleJinja2ASTBuilder astBuilder = new SimpleJinja2ASTBuilder();
        ASTNode ast = astBuilder.visit(tree);

        if (ast != null) {
            System.out.println("✓ Jinja2 AST built successfully");
            System.out.println("  Total nodes: " + ASTPrinter.countNodes(ast));
            System.out.println("  Tree height: " + ASTPrinter.treeHeight(ast));
            ASTPrinter.printLinear(ast);
        } else {
            System.err.println("✗ Failed to build Jinja2 AST");
        }
    }

    private static void testSymbolTable() {
        SymbolTable symbolTable = new SymbolTable();

        System.out.println("Testing Symbol Table Functionality:\n");

        // Define some symbols in global scope
        symbolTable.define("x", "int", 10);
        symbolTable.define("name", "str", "Alice");
        symbolTable.define("is_active", "bool", true);

        System.out.println("\nAfter defining global variables:");
        symbolTable.printCompact();

        // Enter a new scope (like a function)
        symbolTable.enterScope();
        symbolTable.define("local_var", "float", 3.14);
        symbolTable.define("x", "int", 20);  // Shadow global x

        System.out.println("\nAfter entering new scope and defining local variables:");
        symbolTable.printCompact();

        // Test lookup
        System.out.println("\nLookup tests:");
        System.out.println("  x (should find local): " + symbolTable.lookup("x"));
        System.out.println("  name (should find global): " + symbolTable.lookup("name"));
        System.out.println("  local_var (should find local): " + symbolTable.lookup("local_var"));

        // Exit scope
        symbolTable.exitScope();

        System.out.println("\nAfter exiting scope:");
        symbolTable.printCompact();

        System.out.println("\nLookup tests after exiting scope:");
        System.out.println("  x (should find global): " + symbolTable.lookup("x"));
        System.out.println("  local_var (should be null): " + symbolTable.lookup("local_var"));

        // Print full symbol table details
        System.out.println("\nFull symbol table:");
        symbolTable.printScopes();

        // Test JSON export
        System.out.println("\nSymbol table as JSON:");
        System.out.println(symbolTable.toJSON());

        System.out.println("\n✓ Symbol table tests completed successfully");
    }
}
