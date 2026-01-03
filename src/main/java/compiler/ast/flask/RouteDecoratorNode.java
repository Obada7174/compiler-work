package compiler.ast.flask;

import compiler.ast.core.DecoratorNode;
import compiler.ast.core.ExpressionNode;
import compiler.ast.core.expressions.IdentifierNode;
import compiler.ast.core.expressions.MemberAccessNode;

import java.util.List;
import java.util.ArrayList;

public class RouteDecoratorNode extends DecoratorNode {
    private final String appReference;
    private final String routePath;
    private final List<String> httpMethods;
    private final boolean hasPathParameters;

    public RouteDecoratorNode(String appReference, String routePath,
                              List<String> httpMethods,
                              List<ExpressionNode> allArguments,
                              int lineNumber) {
        super(createDecoratorExpr(appReference, lineNumber), allArguments, lineNumber);
        this.appReference = appReference;
        this.routePath = routePath;
        this.httpMethods = httpMethods != null ? httpMethods : List.of("GET");
        this.hasPathParameters = routePath != null && routePath.contains("<");
    }

    private static ExpressionNode createDecoratorExpr(String appReference, int line) {
        return new MemberAccessNode(
            new IdentifierNode(appReference, line),
            "route",
            line
        );
    }

    public String getAppReference() {
        return appReference;
    }

    public String getRoutePath() {
        return routePath;
    }

    public List<String> getHttpMethods() {
        return new ArrayList<>(httpMethods);
    }

    public boolean hasPathParameters() {
        return hasPathParameters;
    }

    /**
     * Extract path parameter names from route path.
     * E.g., "/users/<int:id>" returns ["id"]
     */
    public List<String> getPathParameterNames() {
        List<String> params = new ArrayList<>();
        if (routePath == null) return params;

        int start = 0;
        while ((start = routePath.indexOf('<', start)) != -1) {
            int end = routePath.indexOf('>', start);
            if (end == -1) break;

            String paramDef = routePath.substring(start + 1, end);
            // Handle typed params like "int:id" -> extract "id"
            int colonIndex = paramDef.indexOf(':');
            String paramName = colonIndex >= 0
                ? paramDef.substring(colonIndex + 1)
                : paramDef;
            params.add(paramName);
            start = end + 1;
        }
        return params;
    }

    @Override
    public String getNodeType() {
        return "RouteDecorator";
    }

    @Override
    public String getNodeDetails() {
        return String.format("RouteDecorator: @%s.route(\"%s\", methods=%s) (line %d)",
                           appReference, routePath, httpMethods, lineNumber);
    }
}
