package compiler.ast.flask;

import compiler.ast.*;
import java.util.Map;
import java.util.HashMap;

/**
 * AST node representing Flask application run call.
 *
 * Pattern: app.run(debug=True, host='0.0.0.0', port=5000)
 *
 * Typically found in:
 * if __name__ == "__main__":
 *     app.run(debug=True)
 */
public class FlaskRunNode extends StatementNode {
    private final String appReference;
    private final Map<String, Object> runConfig;
    private final boolean isInMainGuard;  // Inside if __name__ == "__main__"

    public FlaskRunNode(String appReference, Map<String, Object> runConfig,
                        boolean isInMainGuard, int lineNumber) {
        super(lineNumber, "FlaskRun");
        this.appReference = appReference;
        this.runConfig = runConfig != null ? runConfig : new HashMap<>();
        this.isInMainGuard = isInMainGuard;
    }

    public String getAppReference() {
        return appReference;
    }

    public Map<String, Object> getRunConfig() {
        return new HashMap<>(runConfig);
    }

    public boolean isDebugEnabled() {
        Object debug = runConfig.get("debug");
        return debug != null && Boolean.TRUE.equals(debug);
    }

    public String getHost() {
        Object host = runConfig.get("host");
        return host != null ? host.toString() : "127.0.0.1";
    }

    public int getPort() {
        Object port = runConfig.get("port");
        if (port instanceof Number) {
            return ((Number) port).intValue();
        }
        return 5000;  // Flask default
    }

    public boolean isInMainGuard() {
        return isInMainGuard;
    }

    @Override
    public String getNodeType() {
        return "FlaskRun";
    }

    @Override
    public String getNodeDetails() {
        return String.format("FlaskRun: %s.run(debug=%s, host=%s, port=%d) (line %d)",
                           appReference, isDebugEnabled(), getHost(), getPort(), lineNumber);
    }
}
