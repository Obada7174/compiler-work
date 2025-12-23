package compiler.symboltable;

import compiler.ast.*;
import java.util.*;

/**
 * Symbol Table Builder
 * Traverses the AST and populates the symbol table following classical compiler design principles.
 *
 * This class performs semantic analysis by:
 * 1. Extracting declarations (variables, functions, classes, parameters)
 * 2. Recording usage of identifiers
 * 3. Detecting redeclaration errors
 * 4. Detecting undeclared identifier errors
 * 5. Managing scope entry/exit
 *
 * Usage:
 *   ClassicalSymbolTable symbolTable = ClassicalSymbolTable.allocate();
 *   SymbolTableBuilder builder = new SymbolTableBuilder(symbolTable);
 *   builder.build(astRootNode);
 *   symbolTable.printTable();
 */
public class SymbolTableBuilder {

    private ClassicalSymbolTable symbolTable;
    private boolean isInDeclarationContext;

    /**
     * Constructor
     */
    public SymbolTableBuilder(ClassicalSymbolTable symbolTable) {
        this.symbolTable = symbolTable;
        this.isInDeclarationContext = false;
    }

    /**
     * Build symbol table from AST root
     */
    public void build(ASTNode root) {
        if (root == null) {
            return;
        }

        System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║         BUILDING SYMBOL TABLE FROM AST                    ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝\n");

        traverse(root);

        System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║         SYMBOL TABLE BUILD COMPLETED                      ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝\n");

        // Print any errors or warnings
        symbolTable.printErrors();
        symbolTable.printWarnings();
    }

    /**
     * Traverse AST node and its children recursively
     */
    private void traverse(ASTNode node) {
        if (node == null) {
            return;
        }

        // Process current node based on its type
        processNode(node);

        // Recursively traverse children
        for (ASTNode child : node.getChildren()) {
            traverse(child);
        }

        // Post-processing for certain node types
        postProcessNode(node);
    }

    /**
     * Process a single node
     */
    private void processNode(ASTNode node) {
        if (node instanceof AssignmentNode) {
            processAssignment((AssignmentNode) node);
        }
        else if (node instanceof FunctionDefNode) {
            processFunctionDef((FunctionDefNode) node);
        }
        else if (node instanceof ClassDefNode) {
            processClassDef((ClassDefNode) node);
        }
        else if (node instanceof ParameterNode) {
            processParameter((ParameterNode) node);
        }
        else if (node instanceof ForStatementNode) {
            processForStatement((ForStatementNode) node);
        }
        else if (node instanceof IdentifierNode) {
            processIdentifier((IdentifierNode) node);
        }
        else if (node instanceof JinjaForNode) {
            processJinjaFor((JinjaForNode) node);
        }
        // Add more node types as needed
    }

    /**
     * Post-process node after children are traversed
     */
    private void postProcessNode(ASTNode node) {
        // Exit scope for function and class definitions
        if (node instanceof FunctionDefNode || node instanceof ClassDefNode) {
            symbolTable.exitScope();
        }
    }

    /**
     * Process assignment: x = value
     * This is a declaration if identifier doesn't exist, otherwise it's a usage
     */
    private void processAssignment(AssignmentNode node) {
        List<ExpressionNode> targets = node.getTargets();

        for (ExpressionNode target : targets) {
            if (target instanceof IdentifierNode) {
                IdentifierNode identifier = (IdentifierNode) target;
                String name = identifier.getName();
                int line = identifier.getLineNumber();

                // Check if already declared in current scope
                if (!symbolTable.containsInCurrentScope(name)) {
                    // New declaration - infer type from value
                    String type = inferType(node.getValue());

                    SymbolTableEntry entry = new SymbolTableEntry(name, type, line);
                    entry.setInitialized(true);

                    boolean success = symbolTable.insert(name, entry);
                    if (success) {
                        System.out.println(String.format(
                            "  [DECL] Variable '%s' declared at line %d (type: %s, scope: %d)",
                            name, line, type, symbolTable.getCurrentScopeLevel()
                        ));
                    }
                } else {
                    // Already declared - this is a usage (assignment)
                    symbolTable.recordUsage(name, line);
                    symbolTable.set_attribute(name, "initialized", true);

                    System.out.println(String.format(
                        "  [USE]  Variable '%s' assigned at line %d",
                        name, line
                    ));
                }
            }
        }

        // Mark that we're not in declaration context when processing the value
        isInDeclarationContext = false;
    }

    /**
     * Process function definition: def name(params): ...
     */
    private void processFunctionDef(FunctionDefNode node) {
        String name = node.getFunctionName();
        int line = node.getLineNumber();

        // Insert function into symbol table
        SymbolTableEntry entry = new SymbolTableEntry(name, "function", line);
        entry.setInitialized(true);

        boolean success = symbolTable.insert(name, entry);
        if (success) {
            System.out.println(String.format(
                "  [DECL] Function '%s' declared at line %d (params: %d, scope: %d)",
                name, line, node.getParameters().size(), symbolTable.getCurrentScopeLevel()
            ));
        }

        // Enter new scope for function body
        symbolTable.enterScope();
        System.out.println(String.format(
            "  → Entering function '%s' scope (level %d)",
            name, symbolTable.getCurrentScopeLevel()
        ));

        // Parameters will be processed as children
    }

    /**
     * Process class definition: class Name: ...
     */
    private void processClassDef(ClassDefNode node) {
        String name = node.getClassName();
        int line = node.getLineNumber();

        // Insert class into symbol table
        SymbolTableEntry entry = new SymbolTableEntry(name, "class", line);
        entry.setInitialized(true);

        boolean success = symbolTable.insert(name, entry);
        if (success) {
            System.out.println(String.format(
                "  [DECL] Class '%s' declared at line %d (scope: %d)",
                name, line, symbolTable.getCurrentScopeLevel()
            ));
        }

        // Enter new scope for class body
        symbolTable.enterScope();
        System.out.println(String.format(
            "  → Entering class '%s' scope (level %d)",
            name, symbolTable.getCurrentScopeLevel()
        ));
    }

    /**
     * Process function parameter
     */
    private void processParameter(ParameterNode node) {
        String name = node.getParameterName();
        int line = node.getLineNumber();

        // Infer type from type annotation or default to "unknown"
        String type = "unknown";
        if (node.getTypeAnnotation() != null) {
            type = inferType(node.getTypeAnnotation());
        }

        // Insert parameter into current scope (function scope)
        SymbolTableEntry entry = new SymbolTableEntry(name, type, line);
        entry.setInitialized(true);

        boolean success = symbolTable.insert(name, entry);
        if (success) {
            System.out.println(String.format(
                "  [DECL] Parameter '%s' declared at line %d (type: %s, scope: %d)",
                name, line, type, symbolTable.getCurrentScopeLevel()
            ));
        }
    }

    /**
     * Process for statement: for var in iterable: ...
     */
    private void processForStatement(ForStatementNode node) {
        ExpressionNode target = node.getTarget();

        if (target instanceof IdentifierNode) {
            IdentifierNode identifier = (IdentifierNode) target;
            String name = identifier.getName();
            int line = identifier.getLineNumber();

            // Check if loop variable is already declared in current scope
            if (!symbolTable.containsInCurrentScope(name)) {
                // Declare loop variable
                SymbolTableEntry entry = new SymbolTableEntry(name, "unknown", line);
                entry.setInitialized(true);

                boolean success = symbolTable.insert(name, entry);
                if (success) {
                    System.out.println(String.format(
                        "  [DECL] Loop variable '%s' declared at line %d (scope: %d)",
                        name, line, symbolTable.getCurrentScopeLevel()
                    ));
                }
            } else {
                // Already declared - record usage
                symbolTable.recordUsage(name, line);
            }
        }

        // Enter new scope for loop body
        symbolTable.enterScope();
        System.out.println(String.format(
            "  → Entering for-loop scope (level %d)",
            symbolTable.getCurrentScopeLevel()
        ));

        // Process loop body
        for (ASTNode bodyNode : node.getBody()) {
            traverse(bodyNode);
        }

        // Exit loop scope
        symbolTable.exitScope();
        System.out.println(String.format(
            "  ← Exiting for-loop scope (back to level %d)",
            symbolTable.getCurrentScopeLevel()
        ));
    }

    /**
     * Process Jinja2 for loop: {% for var in iterable %}
     */
    private void processJinjaFor(JinjaForNode node) {
        String variableName = node.getVariable();
        int line = node.getLineNumber();

        // Check if loop variable is already declared
        if (!symbolTable.containsInCurrentScope(variableName)) {
            // Declare loop variable
            SymbolTableEntry entry = new SymbolTableEntry(variableName, "unknown", line);
            entry.setInitialized(true);

            boolean success = symbolTable.insert(variableName, entry);
            if (success) {
                System.out.println(String.format(
                    "  [DECL] Jinja2 loop variable '%s' declared at line %d (scope: %d)",
                    variableName, line, symbolTable.getCurrentScopeLevel()
                ));
            }
        }

        // Enter new scope for Jinja2 loop body
        symbolTable.enterScope();
        System.out.println(String.format(
            "  → Entering Jinja2 for-loop scope (level %d)",
            symbolTable.getCurrentScopeLevel()
        ));

        // Process loop body
        for (ASTNode bodyNode : node.getBody()) {
            traverse(bodyNode);
        }

        // Exit loop scope
        symbolTable.exitScope();
        System.out.println(String.format(
            "  ← Exiting Jinja2 for-loop scope (back to level %d)",
            symbolTable.getCurrentScopeLevel()
        ));
    }

    /**
     * Process identifier (usage context)
     */
    private void processIdentifier(IdentifierNode node) {
        // Skip if we're in a declaration context (handled elsewhere)
        if (isInDeclarationContext) {
            return;
        }

        String name = node.getName();
        int line = node.getLineNumber();

        // Record usage
        boolean success = symbolTable.recordUsage(name, line);
        if (success) {
            System.out.println(String.format(
                "  [USE]  Identifier '%s' used at line %d",
                name, line
            ));
        }
        // If recordUsage returns false, an error was already logged
    }

    /**
     * Infer type from expression node
     */
    private String inferType(ExpressionNode expr) {
        if (expr == null) {
            return "unknown";
        }

        if (expr instanceof NumberLiteralNode) {
            double value = ((NumberLiteralNode) expr).getValue();
            if (value == (int) value) {
                return "int";
            } else {
                return "float";
            }
        }
        else if (expr instanceof StringLiteralNode) {
            return "string";
        }
        else if (expr instanceof BooleanLiteralNode) {
            return "boolean";
        }
        else if (expr instanceof ListLiteralNode) {
            return "list";
        }
        else if (expr instanceof DictionaryLiteralNode) {
            return "dictionary";
        }
        else if (expr instanceof TupleLiteralNode) {
            return "tuple";
        }
        else if (expr instanceof SetLiteralNode) {
            return "set";
        }
        else if (expr instanceof NoneLiteralNode) {
            return "none";
        }
        else if (expr instanceof FunctionCallNode) {
            return "unknown"; // Would need type inference system
        }
        else if (expr instanceof IdentifierNode) {
            // Look up the type of the identifier
            String name = ((IdentifierNode) expr).getName();
            SymbolTableEntry entry = symbolTable.lookup(name);
            if (entry != null) {
                return entry.getType();
            }
            return "unknown";
        }
        else {
            return "unknown";
        }
    }

    /**
     * Get the built symbol table
     */
    public ClassicalSymbolTable getSymbolTable() {
        return symbolTable;
    }
}
