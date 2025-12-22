package compiler.symboltable;

import java.util.*;

/**
 * Symbol Table with nested scope support
 * Demonstrates data structure design for compiler symbol management
 */
public class SymbolTable {

    /**
     * Symbol class representing a variable, function, or other identifier
     */
    public static class Symbol {
        private String name;
        private String type;
        private Object value;
        private int scopeLevel;

        public Symbol(String name, String type, Object value) {
            this.name = name;
            this.type = type;
            this.value = value;
            this.scopeLevel = 0;
        }

        public Symbol(String name, String type, Object value, int scopeLevel) {
            this.name = name;
            this.type = type;
            this.value = value;
            this.scopeLevel = scopeLevel;
        }

        public String getName() {
            return name;
        }

        public String getType() {
            return type;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        public int getScopeLevel() {
            return scopeLevel;
        }

        @Override
        public String toString() {
            return String.format("Symbol{name='%s', type='%s', value=%s, scope=%d}",
                name, type, value, scopeLevel);
        }
    }

    /**
     * Scope class representing a single scope level
     */
    private static class Scope {
        private Map<String, Symbol> symbols;
        private int level;

        public Scope(int level) {
            this.symbols = new HashMap<>();
            this.level = level;
        }

        public void insert(String name, Symbol symbol) {
            symbols.put(name, symbol);
        }

        public Symbol lookup(String name) {
            return symbols.get(name);
        }

        public boolean contains(String name) {
            return symbols.containsKey(name);
        }

        public Map<String, Symbol> getSymbols() {
            return symbols;
        }

        public int getLevel() {
            return level;
        }
    }

    // Stack of scopes (most recent scope at the top)
    private Deque<Scope> scopeStack;
    private int currentLevel;

    /**
     * Constructor - initializes with global scope
     */
    public SymbolTable() {
        scopeStack = new ArrayDeque<>();
        currentLevel = 0;
        // Create global scope
        scopeStack.push(new Scope(currentLevel));
    }

    /**
     * Enter a new scope (e.g., function body, block)
     */
    public void enterScope() {
        currentLevel++;
        scopeStack.push(new Scope(currentLevel));
        System.out.println("→ Entered scope level " + currentLevel);
    }

    /**
     * Exit current scope and return to parent
     */
    public void exitScope() {
        if (scopeStack.size() > 1) {
            Scope exitedScope = scopeStack.pop();
            currentLevel--;
            System.out.println("← Exited scope level " + (currentLevel + 1));
        } else {
            System.out.println("Warning: Cannot exit global scope!");
        }
    }

    /**
     * Insert a symbol into current scope
     */
    public void insert(String name, Symbol symbol) {
        if (scopeStack.isEmpty()) {
            throw new IllegalStateException("No scope available!");
        }

        Scope currentScope = scopeStack.peek();

        // Check for redeclaration in current scope
        if (currentScope.contains(name)) {
            System.out.println("Warning: Symbol '" + name + "' already declared in current scope");
        }

        // Set the correct scope level
        symbol.scopeLevel = currentLevel;
        currentScope.insert(name, symbol);

        System.out.println("  + Inserted: " + symbol);
    }

    /**
     * Lookup symbol in current scope only
     */
    public Symbol lookupCurrent(String name) {
        if (scopeStack.isEmpty()) {
            return null;
        }
        return scopeStack.peek().lookup(name);
    }

    /**
     * Lookup symbol in current and all parent scopes
     */
    public Symbol lookup(String name) {
        // Search from current scope up to global scope
        for (Scope scope : scopeStack) {
            Symbol symbol = scope.lookup(name);
            if (symbol != null) {
                return symbol;
            }
        }
        return null; // Symbol not found
    }

    /**
     * Update an existing symbol's value
     */
    public boolean update(String name, Object newValue) {
        Symbol symbol = lookup(name);
        if (symbol != null) {
            symbol.setValue(newValue);
            System.out.println("  ✓ Updated: " + symbol);
            return true;
        }
        System.out.println("  ✗ Symbol '" + name + "' not found for update");
        return false;
    }

    /**
     * Check if symbol exists in any scope
     */
    public boolean contains(String name) {
        return lookup(name) != null;
    }

    /**
     * Get current scope level
     */
    public int getCurrentLevel() {
        return currentLevel;
    }

    /**
     * Print all scopes and their symbols (for debugging)
     */
    public void printScopes() {
        System.out.println("\n╔═══════════════════════════════════════════════╗");
        System.out.println("║          SYMBOL TABLE CONTENTS                ║");
        System.out.println("╚═══════════════════════════════════════════════╝");

        int level = currentLevel;
        for (Scope scope : scopeStack) {
            System.out.println("\n┌─ Scope Level " + scope.getLevel() + " ─────────────────────────");

            if (scope.getSymbols().isEmpty()) {
                System.out.println("│  (empty)");
            } else {
                for (Symbol symbol : scope.getSymbols().values()) {
                    System.out.println("│  " + symbol);
                }
            }

            System.out.println("└─────────────────────────────────────────────");
            level--;
        }
        System.out.println();
    }

    /**
     * Get all symbols from all scopes
     */
    public List<Symbol> getAllSymbols() {
        List<Symbol> allSymbols = new ArrayList<>();
        for (Scope scope : scopeStack) {
            allSymbols.addAll(scope.getSymbols().values());
        }
        return allSymbols;
    }

    /**
     * Clear all scopes and reset to initial state
     */
    public void reset() {
        scopeStack.clear();
        currentLevel = 0;
        scopeStack.push(new Scope(currentLevel));
        System.out.println("Symbol table reset to initial state");
    }

    /**
     * Define a new symbol in current scope
     * Helper method for easy symbol declaration
     */
    public void define(String name, String type, Object value) {
        Symbol symbol = new Symbol(name, type, value, currentLevel);
        insert(name, symbol);
    }

    /**
     * Define a new symbol in current scope with default null value
     */
    public void define(String name, String type) {
        define(name, type, null);
    }

    /**
     * Resolve a symbol (lookup with error message if not found)
     */
    public Symbol resolve(String name) {
        Symbol symbol = lookup(name);
        if (symbol == null) {
            System.out.println("  ⚠ Warning: Symbol '" + name + "' not defined");
        }
        return symbol;
    }

    /**
     * Get type of a symbol
     */
    public String getType(String name) {
        Symbol symbol = lookup(name);
        return symbol != null ? symbol.getType() : null;
    }

    /**
     * Get value of a symbol
     */
    public Object getValue(String name) {
        Symbol symbol = lookup(name);
        return symbol != null ? symbol.getValue() : null;
    }

    /**
     * Check if symbol exists in current scope only
     */
    public boolean isDeclaredInCurrentScope(String name) {
        return lookupCurrent(name) != null;
    }

    /**
     * Get all symbols from current scope only
     */
    public List<Symbol> getCurrentScopeSymbols() {
        if (scopeStack.isEmpty()) {
            return new ArrayList<>();
        }
        return new ArrayList<>(scopeStack.peek().getSymbols().values());
    }

    /**
     * Get symbol count in all scopes
     */
    public int getSymbolCount() {
        return getAllSymbols().size();
    }

    /**
     * Print symbol table in compact format
     */
    public void printCompact() {
        System.out.println("\n═══ Symbol Table ═══");
        for (Symbol symbol : getAllSymbols()) {
            System.out.println("  " + symbol);
        }
        System.out.println("═══════════════════\n");
    }

    /**
     * Export symbol table as JSON-like string (for debugging/serialization)
     */
    public String toJSON() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        sb.append("  \"currentLevel\": ").append(currentLevel).append(",\n");
        sb.append("  \"scopes\": [\n");

        int scopeIndex = 0;
        for (Scope scope : scopeStack) {
            if (scopeIndex > 0) {
                sb.append(",\n");
            }
            sb.append("    {\n");
            sb.append("      \"level\": ").append(scope.getLevel()).append(",\n");
            sb.append("      \"symbols\": [\n");

            int symbolIndex = 0;
            for (Symbol symbol : scope.getSymbols().values()) {
                if (symbolIndex > 0) {
                    sb.append(",\n");
                }
                sb.append("        {");
                sb.append("\"name\": \"").append(symbol.getName()).append("\", ");
                sb.append("\"type\": \"").append(symbol.getType()).append("\", ");
                sb.append("\"value\": \"").append(symbol.getValue()).append("\"");
                sb.append("}");
                symbolIndex++;
            }

            sb.append("\n      ]\n");
            sb.append("    }");
            scopeIndex++;
        }

        sb.append("\n  ]\n");
        sb.append("}");
        return sb.toString();
    }
}
