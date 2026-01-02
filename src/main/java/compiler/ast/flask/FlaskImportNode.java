package compiler.ast.flask;

import compiler.ast.python.StatementNode;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

/**
 * AST node representing Flask-specific imports.
 *
 * Patterns:
 * - from flask import Flask, request, render_template, jsonify
 * - from flask import redirect, url_for, flash
 *
 * This node tracks which Flask components are imported for semantic analysis.
 */
public class FlaskImportNode extends StatementNode {

    public enum FlaskComponent {
        FLASK("Flask", "Application class"),
        REQUEST("request", "Request context proxy"),
        RESPONSE("Response", "Response class"),
        RENDER_TEMPLATE("render_template", "Template rendering"),
        REDIRECT("redirect", "URL redirection"),
        URL_FOR("url_for", "URL building"),
        JSONIFY("jsonify", "JSON response helper"),
        FLASH("flash", "Flash messaging"),
        SESSION("session", "Session object"),
        G("g", "Application context globals"),
        CURRENT_APP("current_app", "Current application proxy"),
        ABORT("abort", "HTTP error abort"),
        MAKE_RESPONSE("make_response", "Response factory"),
        SEND_FILE("send_file", "File sending"),
        SEND_FROM_DIRECTORY("send_from_directory", "Directory file serving");

        private final String name;
        private final String description;

        FlaskComponent(String name, String description) {
            this.name = name;
            this.description = description;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public static FlaskComponent fromName(String name) {
            for (FlaskComponent component : values()) {
                if (component.name.equals(name)) {
                    return component;
                }
            }
            return null;
        }
    }

    private final List<String> importedNames;
    private final Set<FlaskComponent> importedComponents;
    private final String moduleName;  // "flask" or "flask.xxx"

    public FlaskImportNode(String moduleName, List<String> importedNames, int lineNumber) {
        super(lineNumber, "FlaskImport");
        this.moduleName = moduleName;
        this.importedNames = importedNames != null ? importedNames : new ArrayList<>();
        this.importedComponents = new HashSet<>();

        // Identify Flask components
        for (String name : this.importedNames) {
            FlaskComponent component = FlaskComponent.fromName(name);
            if (component != null) {
                importedComponents.add(component);
            }
        }
    }

    public String getModuleName() {
        return moduleName;
    }

    public List<String> getImportedNames() {
        return new ArrayList<>(importedNames);
    }

    public Set<FlaskComponent> getImportedComponents() {
        return new HashSet<>(importedComponents);
    }

    public boolean hasComponent(FlaskComponent component) {
        return importedComponents.contains(component);
    }

    public boolean hasFlaskClass() {
        return hasComponent(FlaskComponent.FLASK);
    }

    public boolean hasRequest() {
        return hasComponent(FlaskComponent.REQUEST);
    }

    public boolean hasRenderTemplate() {
        return hasComponent(FlaskComponent.RENDER_TEMPLATE);
    }

    @Override
    public String getNodeType() {
        return "FlaskImport";
    }

    @Override
    public String getNodeDetails() {
        return String.format("FlaskImport: from %s import %s (line %d)",
                           moduleName, importedNames, lineNumber);
    }
}
