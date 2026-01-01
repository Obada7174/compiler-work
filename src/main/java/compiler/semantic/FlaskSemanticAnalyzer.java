package compiler.semantic;

import compiler.ast.*;
import compiler.ast.flask.*;
import compiler.symboltable.*;

import java.util.*;


public class FlaskSemanticAnalyzer {

    // Valid HTTP methods according to HTTP/1.1 and common extensions
    private static final Set<String> VALID_HTTP_METHODS = Set.of(
        "GET", "POST", "PUT", "DELETE", "PATCH", "HEAD", "OPTIONS", "TRACE", "CONNECT"
    );

    // Flask-specific context objects
    private static final Set<String> FLASK_CONTEXT_OBJECTS = Set.of(
        "request", "session", "g", "current_app"
    );

    private final List<SemanticError> errors = new ArrayList<>();
    private final List<SemanticWarning> warnings = new ArrayList<>();
    private final Set<String> registeredEndpoints = new HashSet<>();
    private final Map<String, Set<String>> routeMethodMap = new HashMap<>();

    public static class SemanticError {
        public final String message;
        public final int lineNumber;
        public final String errorCode;

        public SemanticError(String errorCode, String message, int lineNumber) {
            this.errorCode = errorCode;
            this.message = message;
            this.lineNumber = lineNumber;
        }

        @Override
        public String toString() {
            return String.format("[%s] Line %d: %s", errorCode, lineNumber, message);
        }
    }

    public static class SemanticWarning {
        public final String message;
        public final int lineNumber;
        public final String warningCode;

        public SemanticWarning(String warningCode, String message, int lineNumber) {
            this.warningCode = warningCode;
            this.message = message;
            this.lineNumber = lineNumber;
        }

        @Override
        public String toString() {
            return String.format("[%s] Line %d: %s", warningCode, lineNumber, message);
        }
    }

    public void analyze(ASTNode root) {
        if (root == null) return;

            System.out.println("FLASK SEMANTIC ANALYSIS");

        traverseAndAnalyze(root);

        printAnalysisResults();
    }

    private void traverseAndAnalyze(ASTNode node) {
        if (node == null) return;

        // Analyze current node
        if (node instanceof FlaskRouteFunction) {
            analyzeRouteFunction((FlaskRouteFunction) node);
        } else if (node instanceof RouteDecoratorNode) {
            analyzeRouteDecorator((RouteDecoratorNode) node);
        } else if (node instanceof FlaskAppNode) {
            analyzeFlaskApp((FlaskAppNode) node);
        } else if (node instanceof FlaskImportNode) {
            analyzeFlaskImport((FlaskImportNode) node);
        } else if (node instanceof FlaskRequestNode) {
            analyzeFlaskRequest((FlaskRequestNode) node);
        }

        // Recurse into children
        for (ASTNode child : node.getChildren()) {
            traverseAndAnalyze(child);
        }
    }

    private void analyzeRouteFunction(FlaskRouteFunction node) {
        String funcName = node.getFunctionName();
        int line = node.getLineNumber();

        // Check for duplicate endpoint names
        if (registeredEndpoints.contains(funcName)) {
            addError("FLASK001", String.format(
                "Duplicate endpoint name '%s'. Each route handler must have a unique name.",
                funcName), line);
        } else {
            registeredEndpoints.add(funcName);
        }

        if (node.getRouteDecorators().isEmpty()) {
            addWarning("FLASK101", String.format(
                "Function '%s' appears to be a route handler but has no @app.route decorator.",
                funcName), line);
        }

        // Check if function has parameters matching URL path parameters
        FunctionDefNode funcDef = node.getFunctionDef();
        Set<String> funcParams = new HashSet<>();
        for (ParameterNode param : funcDef.getParameters()) {
            funcParams.add(param.getParameterName());
        }

        for (RouteDecoratorNode decorator : node.getRouteDecorators()) {
            List<String> pathParams = decorator.getPathParameterNames();
            for (String pathParam : pathParams) {
                if (!funcParams.contains(pathParam)) {
                    addError("FLASK002", String.format(
                        "URL path parameter '<%s>' not found in function '%s' parameters.",
                        pathParam, funcName), line);
                }
            }
        }

           for (RouteDecoratorNode decorator : node.getRouteDecorators()) {
            analyzeRouteDecorator(decorator);
        }
    }

     private void analyzeRouteDecorator(RouteDecoratorNode node) {
        String path = node.getRoutePath();
        int line = node.getLineNumber();

        // Validate route path syntax
        validateRoutePath(path, line);

        // Validate HTTP methods
        for (String method : node.getHttpMethods()) {
            if (!VALID_HTTP_METHODS.contains(method.toUpperCase())) {
                addError("FLASK003", String.format(
                    "Invalid HTTP method '%s'. Valid methods: %s",
                    method, VALID_HTTP_METHODS), line);
            }
        }
        String normalizedPath = normalizePath(path);
        Set<String> existingMethods = routeMethodMap.getOrDefault(normalizedPath, new HashSet<>());

        for (String method : node.getHttpMethods()) {
            if (existingMethods.contains(method)) {
                addWarning("FLASK102", String.format(
                    "Route '%s' with method '%s' may conflict with an existing route.",
                    path, method), line);
            }
        }

        // Track route for conflict detection
        routeMethodMap.computeIfAbsent(normalizedPath, k -> new HashSet<>())
                      .addAll(node.getHttpMethods());
    }

    private void validateRoutePath(String path, int line) {
        if (path == null || path.isEmpty()) {
            addError("FLASK004", "Route path cannot be empty.", line);
            return;
        }

        if (!path.startsWith("/")) {
            addError("FLASK005", String.format(
                "Route path '%s' must start with '/'.", path), line);
        }

        // Check for malformed path parameters
        int openBrackets = 0;
        for (int i = 0; i < path.length(); i++) {
            char c = path.charAt(i);
            if (c == '<') {
                openBrackets++;
                if (openBrackets > 1) {
                    addError("FLASK006", String.format(
                        "Nested angle brackets in route path '%s' at position %d.",
                        path, i), line);
                }
            } else if (c == '>') {
                openBrackets--;
                if (openBrackets < 0) {
                    addError("FLASK006", String.format(
                        "Unmatched '>' in route path '%s' at position %d.",
                        path, i), line);
                }
            }
        }

        if (openBrackets != 0) {
            addError("FLASK006", String.format(
                "Unmatched '<' in route path '%s'.", path), line);
        }

        // Check for valid path parameter types
        java.util.regex.Pattern paramPattern = java.util.regex.Pattern.compile("<([^:>]+):([^>]+)>");
        java.util.regex.Matcher matcher = paramPattern.matcher(path);

        Set<String> validConverters = Set.of("int", "float", "path", "string", "uuid", "any");
        while (matcher.find()) {
            String converter = matcher.group(1);
            if (!validConverters.contains(converter)) {
                addWarning("FLASK103", String.format(
                    "Unknown URL converter '%s' in path '%s'. Standard converters: %s",
                    converter, path, validConverters), line);
            }
        }
    }


    private String normalizePath(String path) {
        // Replace path parameters with placeholder for comparison
        return path.replaceAll("<[^>]+>", "<param>");
    }

    private void analyzeFlaskApp(FlaskAppNode node) {
        String moduleName = node.getModuleName();
        int line = node.getLineNumber();


        if (!"__name__".equals(moduleName)) {
            addWarning("FLASK104", String.format(
                "Flask app initialized with '%s' instead of '__name__'. " +
                "Using __name__ is recommended for proper module resolution.",
                moduleName), line);
        }
    }


    private void analyzeFlaskImport(FlaskImportNode node) {
        // Check for commonly needed but missing imports
        Set<FlaskImportNode.FlaskComponent> components = node.getImportedComponents();

        if (components.contains(FlaskImportNode.FlaskComponent.FLASK)) {
            // Flask class imported - good
        }

        if (!components.contains(FlaskImportNode.FlaskComponent.REQUEST)) {
            // Note: This is just informational, not an error
        }
    }

    private void analyzeFlaskRequest(FlaskRequestNode node) {
        int line = node.getLineNumber();

        if (node.getProperty() == FlaskRequestNode.RequestProperty.UNKNOWN) {
            addWarning("FLASK105", String.format(
                "Accessing unknown request property '%s'. " +
                "Common properties: args, form, json, method, path, headers.",
                node.getPropertyName()), line);
        }
    }

    private void addError(String code, String message, int line) {
        errors.add(new SemanticError(code, message, line));
    }

    private void addWarning(String code, String message, int line) {
        warnings.add(new SemanticWarning(code, message, line));
    }

    public List<SemanticError> getErrors() {
        return new ArrayList<>(errors);
    }

    public List<SemanticWarning> getWarnings() {
        return new ArrayList<>(warnings);
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    private void printAnalysisResults() {
        if (errors.isEmpty() && warnings.isEmpty()) {
            System.out.println("  ✓ No Flask semantic issues found.\n");
            return;
        }

        if (!errors.isEmpty()) {
            System.out.println("ERRORS (" + errors.size() + "):");
            System.out.println("─────────────────────────────────────────");
            for (SemanticError error : errors) {
                System.out.println("  ✗ " + error);
            }
            System.out.println();
        }

        if (!warnings.isEmpty()) {
            System.out.println("WARNINGS (" + warnings.size() + "):");
            System.out.println("─────────────────────────────────────────");
            for (SemanticWarning warning : warnings) {
                System.out.println("  ⚠ " + warning);
            }
            System.out.println();
        }

        System.out.println("─────────────────────────────────────────");
        System.out.println(String.format("Summary: %d error(s), %d warning(s)",
            errors.size(), warnings.size()));
    }


    public String getErrorReport() {
        StringBuilder sb = new StringBuilder();
        sb.append("Flask Semantic Analysis Report\n");

        if (errors.isEmpty() && warnings.isEmpty()) {
            sb.append("No issues found.\n");
            return sb.toString();
        }

        if (!errors.isEmpty()) {
            sb.append("Errors:\n");
            for (SemanticError error : errors) {
                sb.append("  - ").append(error).append("\n");
            }
            sb.append("\n");
        }

        if (!warnings.isEmpty()) {
            sb.append("Warnings:\n");
            for (SemanticWarning warning : warnings) {
                sb.append("  - ").append(warning).append("\n");
            }
        }

        return sb.toString();
    }
}
