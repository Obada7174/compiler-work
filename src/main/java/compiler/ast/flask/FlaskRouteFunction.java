package compiler.ast.flask;

import compiler.ast.core.ParameterNode;
import compiler.ast.python.FunctionDefNode;
import compiler.ast.python.StatementNode;

import java.util.List;
import java.util.ArrayList;

public class FlaskRouteFunction extends StatementNode {
    private final FunctionDefNode functionDef;
    private final List<RouteDecoratorNode> routeDecorators;
    private final String endpoint;  // Flask endpoint name (defaults to function name)

    public FlaskRouteFunction(FunctionDefNode functionDef,
                              List<RouteDecoratorNode> routeDecorators,
                              int lineNumber) {
        super(lineNumber, "FlaskRouteFunction");
        this.functionDef = functionDef;
        this.routeDecorators = routeDecorators != null ? routeDecorators : new ArrayList<>();
        this.endpoint = functionDef.getFunctionName();
        this.name = functionDef.getFunctionName();

        // Add route decorators as children
        for (RouteDecoratorNode decorator : this.routeDecorators) {
            addChild(decorator);
        }
        addChild(functionDef);
    }

    public FunctionDefNode getFunctionDef() {
        return functionDef;
    }

    public List<RouteDecoratorNode> getRouteDecorators() {
        return new ArrayList<>(routeDecorators);
    }

    public String getEndpoint() {
        return endpoint;
    }

    public String getFunctionName() {
        return functionDef.getFunctionName();
    }

    public List<ParameterNode> getParameters() {
        return functionDef.getParameters();
    }

    /**
     * Get all route paths from decorators.
     */
    public List<String> getRoutePaths() {
        List<String> paths = new ArrayList<>();
        for (RouteDecoratorNode decorator : routeDecorators) {
            if (decorator.getRoutePath() != null) {
                paths.add(decorator.getRoutePath());
            }
        }
        return paths;
    }

    /**
     * Get all HTTP methods across all route decorators.
     */
    public List<String> getAllHttpMethods() {
        List<String> methods = new ArrayList<>();
        for (RouteDecoratorNode decorator : routeDecorators) {
            for (String method : decorator.getHttpMethods()) {
                if (!methods.contains(method)) {
                    methods.add(method);
                }
            }
        }
        return methods;
    }

    @Override
    public String getNodeType() {
        return "FlaskRouteFunction";
    }

    @Override
    public String getNodeDetails() {
        return String.format("FlaskRouteFunction: %s (routes: %d, methods: %s) (line %d)",
                           endpoint, routeDecorators.size(), getAllHttpMethods(), lineNumber);
    }
}
