package compiler.symboltable;

import compiler.ast.*;
import compiler.ast.flask.*;
import java.util.*;


/**
 * Symbol Table Builder with Flask framework support.
 *
 * This builder traverses the AST and populates the symbol table with:
 * - Standard Python symbols (variables, functions, classes, parameters)
 * - Flask-specific symbols (app instances, route endpoints, Flask imports)
 * - Framework-injected globals (request, g, session)
 */
public class SymbolTableBuilder {

    private ClassicalSymbolTable symbolTable;
    private boolean isInDeclarationContext;

    // Flask-specific tracking
    private final Set<String> flaskApps = new HashSet<>();
    private final Map<String, RouteInfo> routeEndpoints = new LinkedHashMap<>();
    private final Set<String> flaskImports = new HashSet<>();

    /**
     * Route information for Flask endpoints.
     */
    public static class RouteInfo {
        public final String functionName;
        public final String path;
        public final List<String> methods;
        public final int lineNumber;

        public RouteInfo(String functionName, String path, List<String> methods, int lineNumber) {
            this.functionName = functionName;
            this.path = path;
            this.methods = methods;
            this.lineNumber = lineNumber;
        }

        @Override
        public String toString() {
            return String.format("%s -> %s %s", functionName, methods, path);
        }
    }

    public SymbolTableBuilder(ClassicalSymbolTable symbolTable) {
        this.symbolTable = symbolTable;
        this.isInDeclarationContext = false;
    }


    public void build(ASTNode root) {
        if (root == null) {
            return;
        }

        System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║         BUILDING SYMBOL TABLE FROM AST                    ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝\n");

        // Initialize with Python built-ins
        initializePythonBuiltins();

        traverse(root);

        System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║         SYMBOL TABLE BUILD COMPLETED                      ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝\n");

        // Print any errors or warnings
        symbolTable.printErrors();
        symbolTable.printWarnings();
    }

    /**
     * Initialize the symbol table with common Python built-in identifiers.
     * These are available in all Python programs by default.
     */
    private void initializePythonBuiltins() {
        System.out.println("  [INIT] Initializing Python built-in identifiers...\n");

        // Python built-in exceptions
        String[] builtinExceptions = {
            "Exception", "ValueError", "TypeError", "KeyError", "IndexError",
            "AttributeError", "NameError", "RuntimeError", "IOError",
            "FileNotFoundError", "ZeroDivisionError", "ImportError",
            "ModuleNotFoundError", "StopIteration", "AssertionError"
        };

        for (String exceptionName : builtinExceptions) {
            SymbolTableEntry entry = new SymbolTableEntry(exceptionName, "builtin.exception", 0);
            entry.setInitialized(true);
            symbolTable.insert(exceptionName, entry);
            symbolTable.set_attribute(exceptionName, "builtin", true);
        }

        // Python built-in functions
        String[] builtinFunctions = {
            "print", "len", "range", "int", "float", "str", "list", "dict",
            "tuple", "set", "bool", "type", "isinstance", "issubclass",
            "hasattr", "getattr", "setattr", "delattr", "dir", "vars",
            "open", "input", "sum", "min", "max", "abs", "round",
            "enumerate", "zip", "map", "filter", "sorted", "reversed",
            "any", "all", "next", "iter", "chr", "ord", "hex", "oct", "bin"
        };

        for (String funcName : builtinFunctions) {
            SymbolTableEntry entry = new SymbolTableEntry(funcName, "builtin.function", 0);
            entry.setInitialized(true);
            symbolTable.insert(funcName, entry);
            symbolTable.set_attribute(funcName, "builtin", true);
        }

        // Python built-in constants
        String[] builtinConstants = {
            "__name__", "__file__", "__doc__", "__package__", "__loader__",
            "True", "False", "None", "NotImplemented", "Ellipsis"
        };

        for (String constantName : builtinConstants) {
            SymbolTableEntry entry = new SymbolTableEntry(constantName, "builtin.constant", 0);
            entry.setInitialized(true);
            symbolTable.insert(constantName, entry);
            symbolTable.set_attribute(constantName, "builtin", true);
        }

        System.out.println(String.format(
            "  [INIT] Added %d built-in exceptions, %d built-in functions, %d built-in constants\n",
            builtinExceptions.length, builtinFunctions.length, builtinConstants.length
        ));
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
        // Flask-specific nodes (check these first)
        if (node instanceof FlaskAppNode) {
            processFlaskApp((FlaskAppNode) node);
        }
        else if (node instanceof FlaskRouteFunction) {
            processFlaskRouteFunction((FlaskRouteFunction) node);
        }
        else if (node instanceof FlaskImportNode) {
            processFlaskImport((FlaskImportNode) node);
        }
        else if (node instanceof FlaskRunNode) {
            processFlaskRun((FlaskRunNode) node);
        }
        // Standard Python nodes
        else if (node instanceof AssignmentNode) {
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
        else if (node instanceof ImportStatementNode) {
            processImport((ImportStatementNode) node);
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
        String variableName = node.getTargetName();
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

    // ═══════════════════════════════════════════════════════════════════════════
    // FLASK-SPECIFIC PROCESSING
    // ═══════════════════════════════════════════════════════════════════════════

    /**
     * Process Flask import: from flask import Flask, request, ...
     */
    private void processFlaskImport(FlaskImportNode node) {
        int line = node.getLineNumber();

        // Track all Flask imports
        for (String importedName : node.getImportedNames()) {
            flaskImports.add(importedName);

            // Insert Flask components into symbol table
            String type = determineFlaskType(importedName);
            SymbolTableEntry entry = new SymbolTableEntry(importedName, type, line);
            entry.setInitialized(true);

            boolean success = symbolTable.insert(importedName, entry);
            if (success) {
                System.out.println(String.format(
                    "  [FLASK] Imported '%s' (%s) at line %d",
                    importedName, type, line
                ));
            }
        }

        // If 'request' is imported, inject Flask context globals
        if (node.hasRequest()) {
            injectFlaskRequestGlobal(line);
        }
    }

    /**
     * Determine the type for a Flask import.
     */
    private String determineFlaskType(String name) {
        return switch (name) {
            case "Flask" -> "flask.Flask";
            case "request" -> "flask.Request";
            case "Response" -> "flask.Response";
            case "render_template" -> "function";
            case "redirect" -> "function";
            case "url_for" -> "function";
            case "jsonify" -> "function";
            case "flash" -> "function";
            case "session" -> "flask.Session";
            case "g" -> "flask.AppContext";
            case "current_app" -> "flask.Flask";
            case "abort" -> "function";
            case "make_response" -> "function";
            case "send_file", "send_from_directory" -> "function";
            default -> "flask.unknown";
        };
    }

    /**
     * Inject Flask request object as a framework-provided global.
     */
    private void injectFlaskRequestGlobal(int line) {
        // 'request' is a context local proxy - mark as framework-injected
        SymbolTableEntry requestEntry = symbolTable.lookup("request");
        if (requestEntry != null) {
            requestEntry.setType("flask.LocalProxy<Request>");
            symbolTable.set_attribute("request", "framework_injected", true);
        }
    }

    /**
     * Process Flask app instantiation: app = Flask(__name__)
     */
    private void processFlaskApp(FlaskAppNode node) {
        String appName = node.getAppVariableName();
        int line = node.getLineNumber();

        // Register Flask app in symbol table
        SymbolTableEntry entry = new SymbolTableEntry(appName, "flask.Flask", line);
        entry.setInitialized(true);

        boolean success = symbolTable.insert(appName, entry);
        if (success) {
            flaskApps.add(appName);
            symbolTable.set_attribute(appName, "flask_app", true);
            symbolTable.set_attribute(appName, "module_name", node.getModuleName());

            System.out.println(String.format(
                "  [FLASK] App '%s' = Flask(%s) declared at line %d",
                appName, node.getModuleName(), line
            ));
        }
    }

    /**
     * Process Flask route function: @app.route("/path") def handler(): ...
     */
    private void processFlaskRouteFunction(FlaskRouteFunction node) {
        String functionName = node.getFunctionName();
        int line = node.getLineNumber();

        // Insert function into symbol table with route metadata
        SymbolTableEntry entry = new SymbolTableEntry(functionName, "flask.route_handler", line);
        entry.setInitialized(true);

        boolean success = symbolTable.insert(functionName, entry);
        if (success) {
            // Store route metadata
            symbolTable.set_attribute(functionName, "is_route_handler", true);
            symbolTable.set_attribute(functionName, "routes", node.getRoutePaths());
            symbolTable.set_attribute(functionName, "methods", node.getAllHttpMethods());

            // Track route endpoints
            for (RouteDecoratorNode routeDecorator : node.getRouteDecorators()) {
                String path = routeDecorator.getRoutePath();
                List<String> methods = routeDecorator.getHttpMethods();

                RouteInfo routeInfo = new RouteInfo(functionName, path, methods, line);
                routeEndpoints.put(path, routeInfo);

                System.out.println(String.format(
                    "  [FLASK] Route '%s' %s -> %s() at line %d",
                    path, methods, functionName, line
                ));
            }
        }

        // Enter function scope and process parameters
        symbolTable.enterScope();
        System.out.println(String.format(
            "  → Entering route handler '%s' scope (level %d)",
            functionName, symbolTable.getCurrentScopeLevel()
        ));

        // Process URL path parameters as function parameters
        for (RouteDecoratorNode routeDecorator : node.getRouteDecorators()) {
            for (String pathParam : routeDecorator.getPathParameterNames()) {
                SymbolTableEntry paramEntry = new SymbolTableEntry(pathParam, "url_param", line);
                paramEntry.setInitialized(true);
                symbolTable.insert(pathParam, paramEntry);

                System.out.println(String.format(
                    "  [FLASK] URL parameter '%s' injected from route path",
                    pathParam
                ));
            }
        }

        // Process function parameters
        FunctionDefNode funcDef = node.getFunctionDef();
        for (ParameterNode param : funcDef.getParameters()) {
            traverse(param);
        }

        // Process function body
        for (ASTNode bodyNode : funcDef.getBody()) {
            traverse(bodyNode);
        }

        // Exit function scope
        symbolTable.exitScope();
        System.out.println(String.format(
            "  ← Exiting route handler '%s' scope (back to level %d)",
            functionName, symbolTable.getCurrentScopeLevel()
        ));
    }

    /**
     * Process Flask app.run() call.
     */
    private void processFlaskRun(FlaskRunNode node) {
        String appRef = node.getAppReference();
        int line = node.getLineNumber();

        // Record usage of Flask app
        symbolTable.recordUsage(appRef, line);

        System.out.println(String.format(
            "  [FLASK] %s.run(debug=%s, port=%d) at line %d%s",
            appRef, node.isDebugEnabled(), node.getPort(), line,
            node.isInMainGuard() ? " (in __main__ guard)" : ""
        ));
    }

    /**
     * Process standard import statement.
     */
    private void processImport(ImportStatementNode node) {
        int line = node.getLineNumber();

        if (node.isFromImport()) {
            // from module import name1, name2
            for (String importedName : node.getImportedNames()) {
                SymbolTableEntry entry = new SymbolTableEntry(importedName, "imported", line);
                entry.setInitialized(true);

                boolean success = symbolTable.insert(importedName, entry);
                if (success) {
                    System.out.println(String.format(
                        "  [IMPORT] '%s' from '%s' at line %d",
                        importedName, node.getModuleName(), line
                    ));
                }
            }
        } else {
            // import module
            String moduleName = node.getModuleName();
            SymbolTableEntry entry = new SymbolTableEntry(moduleName, "module", line);
            entry.setInitialized(true);

            boolean success = symbolTable.insert(moduleName, entry);
            if (success) {
                System.out.println(String.format(
                    "  [IMPORT] Module '%s' at line %d",
                    moduleName, line
                ));
            }
        }
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // FLASK ANALYSIS RESULTS
    // ═══════════════════════════════════════════════════════════════════════════

    /**
     * Get all registered Flask app names.
     */
    public Set<String> getFlaskApps() {
        return new HashSet<>(flaskApps);
    }

    /**
     * Get all route endpoints.
     */
    public Map<String, RouteInfo> getRouteEndpoints() {
        return new LinkedHashMap<>(routeEndpoints);
    }

    /**
     * Get all Flask imports.
     */
    public Set<String> getFlaskImports() {
        return new HashSet<>(flaskImports);
    }

    /**
     * Check if this is a Flask application.
     */
    public boolean isFlaskApplication() {
        return !flaskApps.isEmpty() || flaskImports.contains("Flask");
    }

    /**
     * Print Flask-specific analysis summary.
     */
    public void printFlaskSummary() {
        if (!isFlaskApplication()) {
            return;
        }

        System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║              FLASK APPLICATION SUMMARY                    ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝\n");

        System.out.println("Flask Imports: " + flaskImports);
        System.out.println("Flask Apps: " + flaskApps);
        System.out.println("\nRoute Endpoints:");
        System.out.println("─────────────────────────────────────────");
        for (Map.Entry<String, RouteInfo> entry : routeEndpoints.entrySet()) {
            RouteInfo info = entry.getValue();
            System.out.println(String.format("  %-20s %s -> %s()",
                info.path, info.methods, info.functionName));
        }
        System.out.println("─────────────────────────────────────────");
        System.out.println("Total Routes: " + routeEndpoints.size());
    }
}
