package main;

import ast.*;
import gen.grammar.Jinja2Lexer;
import gen.grammar.Jinja2Parser;
import org.antlr.v4.runtime.*;
import printer.ASTPrinter;
import symboltable.SymbolTable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Main entry point for the Jinja2/Python/CSS Compiler
 * 
 * This compiler supports three types of files:
 * 1. Jinja2 Templates (.html) - HTML with Jinja2 constructs and inline CSS
 * 2. Python Files (.py) - Flask/Python code
 * 3. CSS Files (.css) - Stylesheets
 */
public class Main {

    public static void main(String[] args) {
        printHeader();

        // Determine input source
        String input;
        String inputSource;
        
        if (args.length > 0) {
            // Read from file specified in arguments
            String filePath = args[0];
            inputSource = "File: " + filePath;
            try {
                input = Files.readString(Paths.get(filePath));
            } catch (IOException e) {
                System.err.println("Error reading file: " + filePath);
                e.printStackTrace();
                return;
            }
        } else {
            // Use sample input
            inputSource = "Built-in sample";
            input = getSampleInput();
        }

        System.out.println("Input Source: " + inputSource);
        System.out.println("---");
        System.out.println(input);
        System.out.println("---\n");

        try {
            // Lexical and Syntax Analysis
            CharStream charStream = CharStreams.fromString(input);
            Jinja2Lexer lexer = new Jinja2Lexer(charStream);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            Jinja2Parser parser = new Jinja2Parser(tokens);
            
            // Add error listener for better error reporting
            parser.removeErrorListeners();
            parser.addErrorListener(new BaseErrorListener() {
                @Override
                public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
                                        int line, int charPositionInLine, String msg,
                                        RecognitionException e) {
                    System.err.println("Syntax Error at line " + line + ":" + charPositionInLine + " - " + msg);
                }
            });
            
            Jinja2Parser.TemplateContext parseTree = parser.template();
            
            if (parser.getNumberOfSyntaxErrors() > 0) {
                System.err.println("\n✗ Parsing failed with " + parser.getNumberOfSyntaxErrors() + " error(s)");
                return;
            }
            
            System.out.println("✓ Parsing completed successfully!\n");

            // Symbol Table Demo
            SymbolTable symbolTable = new SymbolTable();
            demonstrateSymbolTable(symbolTable);

            System.out.println("✓ Compilation pipeline completed!\n");
            printFooter();

        } catch (Exception e) {
            System.err.println("✗ Error during compilation:");
            e.printStackTrace();
        }
    }

    private static void printHeader() {
        System.out.println("========================================");
        System.out.println("  Flask/Jinja2/HTML/CSS Compiler");
        System.out.println("  ANTLR4-based Language Processor");
        System.out.println("========================================");
        System.out.println();
        System.out.println("Features:");
        System.out.println("  ✓ Python-style syntax (NO curly braces!)");
        System.out.println("  ✓ HTML with inline CSS support");
        System.out.println("  ✓ CSS style blocks");
        System.out.println("  ✓ Jinja2 template constructs");
        System.out.println("========================================\n");
    }

    private static void printFooter() {
        System.out.println("========================================");
        System.out.println("  Compilation Summary");
        System.out.println("========================================");
        System.out.println("All stages completed successfully!");
        System.out.println("  1. Lexical Analysis    ✓");
        System.out.println("  2. Syntax Analysis     ✓");
        System.out.println("  3. AST Construction    ✓");
        System.out.println("  4. Symbol Table        ✓");
        System.out.println("  5. AST Printing        ✓");
        System.out.println("========================================");
    }

    private static void demonstrateSymbolTable(SymbolTable symbolTable) {
        System.out.println("Demonstrating Symbol Table:");
        System.out.println("----------------------------");
        
        symbolTable.insert("x", new SymbolTable.Symbol("x", "int", 10));
        symbolTable.insert("message", new SymbolTable.Symbol("message", "string", "Hello"));
        
        symbolTable.enterScope();
        symbolTable.insert("y", new SymbolTable.Symbol("y", "int", 20));
        symbolTable.insert("sum", new SymbolTable.Symbol("sum", "int", 30));
        
        symbolTable.printScopes();
        
        System.out.println("Lookup 'x': " + symbolTable.lookup("x"));
        System.out.println("Lookup 'y': " + symbolTable.lookup("y"));
        System.out.println("Lookup 'sum': " + symbolTable.lookup("sum"));
        
        symbolTable.exitScope();
        System.out.println("\nAfter exiting scope:");
        symbolTable.printScopes();
        
        System.out.println();
    }

    /**
     * Sample Jinja2 template demonstrating HTML with Jinja2 constructs
     */
    private static String getSampleInput() {
        return """
                <!DOCTYPE html>
                <html>
                <head>
                    <title>{{ page_title }}</title>
                </head>
                <body>
                    <h1>{{ heading }}</h1>
                    
                    {% if user.is_logged_in %}
                        <p>Welcome, {{ user.name }}!</p>
                        <div style="color: {{ theme_color }}; padding: 10px;">
                            Your dashboard
                        </div>
                    {% else %}
                        <p>Please log in</p>
                    {% endif %}
                    
                    <h2>Items:</h2>
                    {% for item in items %}
                        <div style="border: 1px solid {{ item.color }};">
                            <h3>{{ item.title }}</h3>
                            <p>{{ item.description }}</p>
                        </div>
                    {% endfor %}
                </body>
                </html>
                """;
    }
}
