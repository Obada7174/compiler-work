package compiler.ast.flask;

import compiler.ast.*;

/**
 * AST node representing Flask request object access.
 *
 * Patterns:
 * - request.args.get("param")
 * - request.form["field"]
 * - request.json
 * - request.method
 *
 * This node captures the semantic meaning of Flask request access,
 * enabling validation and type inference.
 */
public class FlaskRequestNode extends ExpressionNode {

    public enum RequestProperty {
        ARGS,           // request.args - URL query parameters
        FORM,           // request.form - form data
        JSON,           // request.json - JSON body
        DATA,           // request.data - raw body data
        FILES,          // request.files - uploaded files
        METHOD,         // request.method - HTTP method
        PATH,           // request.path - URL path
        HEADERS,        // request.headers - HTTP headers
        COOKIES,        // request.cookies - cookies
        VALUES,         // request.values - combined args + form
        UNKNOWN         // Unknown property
    }

    private final RequestProperty property;
    private final String propertyName;
    private final ExpressionNode chainedAccess;  // For request.args.get("x")

    public FlaskRequestNode(String propertyName, ExpressionNode chainedAccess, int lineNumber) {
        super(lineNumber);
        this.propertyName = propertyName;
        this.property = parseProperty(propertyName);
        this.chainedAccess = chainedAccess;

        if (chainedAccess != null) {
            addChild(chainedAccess);
        }
    }

    private RequestProperty parseProperty(String name) {
        if (name == null) return RequestProperty.UNKNOWN;

        return switch (name.toLowerCase()) {
            case "args" -> RequestProperty.ARGS;
            case "form" -> RequestProperty.FORM;
            case "json" -> RequestProperty.JSON;
            case "data" -> RequestProperty.DATA;
            case "files" -> RequestProperty.FILES;
            case "method" -> RequestProperty.METHOD;
            case "path" -> RequestProperty.PATH;
            case "headers" -> RequestProperty.HEADERS;
            case "cookies" -> RequestProperty.COOKIES;
            case "values" -> RequestProperty.VALUES;
            default -> RequestProperty.UNKNOWN;
        };
    }

    public RequestProperty getProperty() {
        return property;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public ExpressionNode getChainedAccess() {
        return chainedAccess;
    }

    public boolean hasChainedAccess() {
        return chainedAccess != null;
    }

    @Override
    public String getNodeType() {
        return "FlaskRequest";
    }

    @Override
    public String getNodeDetails() {
        String chain = chainedAccess != null ? "..." : "";
        return String.format("FlaskRequest: request.%s%s (line %d)",
                           propertyName, chain, lineNumber);
    }
}
