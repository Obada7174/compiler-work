package compiler.symboltable;

import java.util.*;


public class SymbolTable {

    public static class Symbol {
        private final String name;
        private final String type;
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


    private static class Scope {
        private final Map<String, Symbol> symbols;
        private final int level;

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
    private final Deque<Scope> scopeStack;
    private int currentLevel;

    public SymbolTable() {
        scopeStack = new ArrayDeque<>();
        currentLevel = 0;
        // Create global scope
        scopeStack.push(new Scope(currentLevel));
    }

    public void enterScope() {
        currentLevel++;
        scopeStack.push(new Scope(currentLevel));
        System.out.println("→ Entered scope level " + currentLevel);
    }


    public void exitScope() {
        if (scopeStack.size() > 1) {
            Scope exitedScope = scopeStack.pop();
            currentLevel--;
            System.out.println("← Exited scope level " + (currentLevel + 1));
        } else {
            System.out.println("Warning: Cannot exit global scope!");
        }
    }


    public void insert(String name, Symbol symbol) {
        if (scopeStack.isEmpty()) {
            throw new IllegalStateException("No scope available!");
        }

        Scope currentScope = scopeStack.peek();

        if (currentScope.contains(name)) {
            System.out.println("Warning: Symbol '" + name + "' already declared in current scope");
        }

        // Set the correct scope level
        symbol.scopeLevel = currentLevel;
        currentScope.insert(name, symbol);

        System.out.println("  + Inserted: " + symbol);
    }


    public Symbol lookupCurrent(String name) {
        if (scopeStack.isEmpty()) {
            return null;
        }
        return scopeStack.peek().lookup(name);
    }

    public Symbol lookup(String name) {

        for (Scope scope : scopeStack) {
            Symbol symbol = scope.lookup(name);
            if (symbol != null) {
                return symbol;
            }
        }
        return null; // Symbol not found
    }


    public void update(String name, Object newValue) {
        Symbol symbol = lookup(name);
        if (symbol != null) {
            symbol.setValue(newValue);
            System.out.println("  ✓ Updated: " + symbol);
            return;
        }
        System.out.println("  ✗ Symbol '" + name + "' not found for update");
    }


    public boolean contains(String name) {
        return lookup(name) != null;
    }


    public int getCurrentLevel() {
        return currentLevel;
    }


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


    public List<Symbol> getAllSymbols() {
        List<Symbol> allSymbols = new ArrayList<>();
        for (Scope scope : scopeStack) {
            allSymbols.addAll(scope.getSymbols().values());
        }
        return allSymbols;
    }

//
//    public void reset() {
//        scopeStack.clear();
//        currentLevel = 0;
//        scopeStack.push(new Scope(currentLevel));
//        System.out.println("Symbol table reset to initial state");
//    }


    public void define(String name, String type, Object value) {
        Symbol symbol = new Symbol(name, type, value, currentLevel);
        insert(name, symbol);
    }


//    public void define(String name, String type) {
//        define(name, type, null);
//    }
//
//    public Symbol resolve(String name) {
//        Symbol symbol = lookup(name);
//        if (symbol == null) {
//            System.out.println("  ⚠ Warning: Symbol '" + name + "' not defined");
//        }
//        return symbol;
//    }

//
//    public String getType(String name) {
//        Symbol symbol = lookup(name);
//        return symbol != null ? symbol.getType() : null;
//    }

    public Object getValue(String name) {
        Symbol symbol = lookup(name);
        return symbol != null ? symbol.getValue() : null;
    }


//    public boolean isDeclaredInCurrentScope(String name) {
//        return lookupCurrent(name) != null;
//    }
//
//
//    public List<Symbol> getCurrentScopeSymbols() {
//        if (scopeStack.isEmpty()) {
//            return new ArrayList<>();
//        }
//        return new ArrayList<>(scopeStack.peek().getSymbols().values());
//    }
//
    public void printCompact() {
        System.out.println("\n Symbol Table ");
        for (Symbol symbol : getAllSymbols()) {
            System.out.println("  " + symbol);
        }
        System.out.println("═══════════════════\n");
    }


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
