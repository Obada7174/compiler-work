package compiler;

import compiler.ast.*;
import compiler.symboltable.SymbolTable;
import compiler.visitors.ASTPrinter;
import compiler.visitors.SimplePythonASTBuilder;
import compiler.visitors.SimpleJinja2ASTBuilder;
import grammar.*;
import org.antlr.v4.runtime.*;

/**
 * Comprehensive test class for AST building with inline content
 */
public class TestASTComplete {

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║       COMPREHENSIVE AST AND SYMBOL TABLE TEST SUITE            ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝\n");

        // Inline Python code
        String pythonCode = """
            # Sample Flask/Jinja2 Template with HTML and CSS
            # This demonstrates the CORRECT syntax (NO curly braces for Python blocks!)

            # Python variable assignments
            x = 10
            y = 20
            name = "Alice"

            # Arithmetic
            sum = x + y
            print(sum)

            # Conditional statement - Python style (NO BRACES!)
            if x < y:
                print("x is less than y")
                result = True
            else:
                print("x is greater or equal")
                result = False

            # For loop - Python style (NO BRACES!)
            for i in [1, 2, 3, 4, 5]:
                print(i)
                squared = i * i
                print(squared)

            # Function definition - Python style (NO BRACES!)
            def greet(person):
                message = "Hello, " + person
                return message

            greeting = greet("World")
            print(greeting)
            """;

        // Inline Jinja2 template
        String jinjaCode = """
                <!DOCTYPE html>
                     <html lang="en">
                     <head>
                         <meta charset="UTF-8">
                         <meta name="viewport" content="width=device-width, initial-scale=1.0">
                         <title>{{ page_title }}</title>
                         <style>
                             body {
                                 font-family: Arial, sans-serif;
                                 margin: 0;
                                 padding: 20px;
                                 background-color: #f0f2f5;
                             }
                             .container {
                                 max-width: 800px;
                                 margin: 0 auto;
                                 background: white;
                                 padding: 30px;
                                 border-radius: 12px;
                                 box-shadow: 0 4px 6px rgba(0,0,0,0.1);
                             }
                             .header {
                                 border-bottom: 2px solid #e0e0e0;
                                 padding-bottom: 20px;
                                 margin-bottom: 30px;
                             }
                             h1 {
                                 color: #333;
                                 margin: 0;
                             }
                             .breadcrumb {
                                 color: #666;
                                 font-size: 14px;
                                 margin-top: 10px;
                             }
                             .form-group {
                                 margin-bottom: 20px;
                             }
                             label {
                                 display: block;
                                 font-weight: bold;
                                 margin-bottom: 8px;
                                 color: #333;
                             }
                             input, textarea, select {
                                 width: 100%;
                                 padding: 12px;
                                 border: 1px solid #ddd;
                                 border-radius: 6px;
                                 font-size: 14px;
                                 box-sizing: border-box;
                             }
                             textarea {
                                 min-height: 100px;
                                 resize: vertical;
                             }
                             .form-row {
                                 display: grid;
                                 grid-template-columns: 1fr 1fr;
                                 gap: 20px;
                             }
                             .required {
                                 color: #dc3545;
                             }
                             .btn {
                                 padding: 12px 24px;
                                 border: none;
                                 border-radius: 6px;
                                 font-size: 16px;
                                 cursor: pointer;
                                 margin-right: 10px;
                             }
                             .btn-primary {
                                 background-color: #007bff;
                                 color: white;
                             }
                             .btn-primary:hover {
                                 background-color: #0056b3;
                             }
                             .btn-secondary {
                                 background-color: #6c757d;
                                 color: white;
                             }
                             .help-text {
                                 font-size: 12px;
                                 color: #666;
                                 margin-top: 5px;
                             }
                             .preview {
                                 background-color: #f8f9fa;
                                 padding: 20px;
                                 border-radius: 6px;
                                 margin-top: 30px;
                                 border: 1px solid #e0e0e0;
                             }
                             .preview h3 {
                                 margin-top: 0;
                                 color: #495057;
                             }
                         </style>
                     </head>
                     <body>
                         <div class="container">
                             <div class="header">
                                 <h1>{{ form_title }}</h1>
                                 <div class="breadcrumb">{{ store_name }} > Admin > Add New Product</div>
                            
                                 {% if user.is_admin %}
                                     <p style="color: #28a745; margin-top: 10px;">✓ Authorized as {{ user.role }}</p>
                                 {% else %}
                                     <p style="color: #dc3545; margin-top: 10px;">✗ Access Denied: Admin privileges required</p>
                                 {% endif %}
                             </div>
                            
                             {% if user.is_admin %}
                                 <form action="/products/add" method="POST" enctype="multipart/form-data">
                                     <div class="form-group">
                                         <label for="name">Product Name <span class="required">*</span></label>
                                         <input type="text" id="name" name="name" placeholder="Enter product name" required>
                                         <div class="help-text">Enter a clear and descriptive product name</div>
                                     </div>
                            
                                     <div class="form-row">
                                         <div class="form-group">
                                             <label for="category">Category <span class="required">*</span></label>
                                             <select id="category" name="category" required>
                                                 <option value="">-- Select Category --</option>
                                                 {% for category in categories %}
                                                     <option value="{{ category.id }}">{{ category.name }}</option>
                                                 {% endfor %}
                                             </select>
                                         </div>
                            
                                         <div class="form-group">
                                             <label for="price">Price ($) <span class="required">*</span></label>
                                             <input type="number" id="price" name="price" step="0.01" min="0" placeholder="0.00" required>
                                         </div>
                                     </div>
                            
                                     <div class="form-group">
                                         <label for="description">Description <span class="required">*</span></label>
                                         <textarea id="description" name="description" placeholder="Enter detailed product description" required></textarea>
                                         <div class="help-text">Minimum 50 characters, maximum 500 characters</div>
                                     </div>
                            
                                     <div class="form-row">
                                         <div class="form-group">
                                             <label for="stock">Stock Quantity <span class="required">*</span></label>
                                             <input type="number" id="stock" name="stock" min="0" value="0" required>
                                         </div>
                            
                                         <div class="form-group">
                                             <label for="sku">SKU Code</label>
                                             <input type="text" id="sku" name="sku" placeholder="e.g., PROD-001">
                                             <div class="help-text">Unique product identifier</div>
                                         </div>
                                     </div>
                            
                                     <div class="form-group">
                                         <label for="image">Product Image</label>
                                         <input type="file" id="image" name="image" accept="image/*">
                                         <div class="help-text">Supported formats: JPG, PNG, WebP (Max 5MB)</div>
                                     </div>
                            
                                     <div class="form-group">
                                         <label>
                                             <input type="checkbox" name="featured" value="1">
                                             Mark as featured product
                                         </label>
                                     </div>
                            
                                     <div class="form-group">
                                         <label>
                                             <input type="checkbox" name="active" value="1" checked>
                                             Activate product immediately
                                         </label>
                                     </div>
                            
                                     <div style="margin-top: 30px;">
                                         <button type="submit" class="btn btn-primary">Add Product</button>
                                         <button type="reset" class="btn btn-secondary">Reset Form</button>
                                     </div>
                                 </form>
                            
                                 <div class="preview">
                                     <h3>Product Preview</h3>
                                     <p>Your product will appear like this:</p>
                                     <div style="background: white; padding: 15px; border-radius: 6px; margin-top: 10px;">
                                         <h4 style="margin: 0 0 10px 0;">[Product Name]</h4>
                                         <div style="color: #28a745; font-size: 20px; margin: 10px 0;">$[Price]</div>
                                         <p style="color: #666; font-size: 14px;">[Description]</p>
                                         <span style="background: #d4edda; color: #155724; padding: 5px 10px; border-radius: 4px; font-size: 12px;">
                                             Stock: [Quantity]
                                         </span>
                                     </div>
                                 </div>
                            
                                 <div style="margin-top: 20px; padding: 15px; background-color: #fff3cd; border-radius: 6px; border-left: 4px solid #ffc107;">
                                     <strong>Note:</strong> All fields marked with <span class="required">*</span> are required.
                                     Please ensure all information is accurate before submitting.
                                 </div>
                            
                             {% else %}
                                 <div style="padding: 40px; text-align: center; color: #666;">
                                     <h2>Access Restricted</h2>
                                     <p>You need administrator privileges to add products.</p>
                                     <p>Current role: {{ user.role }}</p>
                                 </div>
                             {% endif %}
                            
                             <footer style="margin-top: 40px; padding-top: 20px; border-top: 1px solid #e0e0e0; text-align: center; color: #666;">
                                 <p>&copy; 2025 {{ store_name }} Admin Panel</p>
                             </footer>
                         </div>
                     </body>
                     </html>
                            
                 """;

        // Run Python AST test
        System.out.println("\n═══════════════════════════════════════════════════════════");
        System.out.println("  PART 1: PYTHON AST GENERATION");
        System.out.println("═══════════════════════════════════════════════════════════\n");
        testPythonFromString(pythonCode);

        // Run Jinja2 AST test
        System.out.println("\n═══════════════════════════════════════════════════════════");
        System.out.println("  PART 2: JINJA2 AST GENERATION");
        System.out.println("═══════════════════════════════════════════════════════════\n");
        testJinja2FromString(jinjaCode);

        // Symbol table test
        System.out.println("\n═══════════════════════════════════════════════════════════");
        System.out.println("  PART 3: SYMBOL TABLE FUNCTIONALITY");
        System.out.println("═══════════════════════════════════════════════════════════\n");
        testSymbolTable();
    }

    private static void testPythonFromString(String code) {
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

    private static void testJinja2FromString(String code) {
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
