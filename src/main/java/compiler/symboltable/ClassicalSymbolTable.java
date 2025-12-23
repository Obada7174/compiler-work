package compiler.symboltable;

import java.util.*;

/**
 * Classical Symbol Table Implementation
 * Following standard Compiler Design principles and theory.
 *
 * This implementation provides all core operations required for a compiler symbol table:
 * - allocate: create a new empty symbol table
 * - free: remove all entries and release resources
 * - lookup: search for an identifier and return its entry
 * - insert: insert a new identifier into the table
 * - set_attribute: associate a specific attribute with an entry
 * - get_attribute: retrieve a specific attribute from an entry
 *
 * The symbol table supports nested scopes and maintains proper scope hierarchy.
 */
public class ClassicalSymbolTable {

    // ========== Internal Data Structures ==========

    /**
     * Scope - represents a single scope level
     * Each scope contains a mapping of identifier names to their entries
     */
    private static class Scope {
        private final int level;
        private final Map<String, SymbolTableEntry> entries;

        public Scope(int level) {
            this.level = level;
            this.entries = new HashMap<>();
        }

        public int getLevel() {
            return level;
        }

        public Map<String, SymbolTableEntry> getEntries() {
            return entries;
        }

        public boolean contains(String name) {
            return entries.containsKey(name);
        }

        public SymbolTableEntry get(String name) {
            return entries.get(name);
        }

        public void put(String name, SymbolTableEntry entry) {
            entries.put(name, entry);
        }
    }


    // ========== Symbol Table State ==========

    /** Stack of scopes (top = current scope, bottom = global scope) */
    private Deque<Scope> scopeStack;

    /** Current scope level (0 = global) */
    private int currentScopeLevel;

    /** Next available memory address for allocation */
    private int nextAddress;

    /** Error tracking */
    private List<String> errors;

    /** Warning tracking */
    private List<String> warnings;


    // ========== Constructor ==========

    /**
     * Private constructor - use allocate() to create instances
     */
    private ClassicalSymbolTable() {
        this.scopeStack = new ArrayDeque<>();
        this.currentScopeLevel = 0;
        this.nextAddress = 0;
        this.errors = new ArrayList<>();
        this.warnings = new ArrayList<>();

        // Create global scope (level 0)
        scopeStack.push(new Scope(currentScopeLevel));
    }


    // ========== Core Operations (Required by Classical Compiler Design) ==========

    /**
     * OPERATION 1: allocate
     * Creates a new empty symbol table with global scope initialized.
     *
     * @return a new symbol table instance
     */
    public static ClassicalSymbolTable allocate() {
        return new ClassicalSymbolTable();
    }

    /**
     * OPERATION 2: free
     * Removes all entries and releases the symbol table.
     * Clears all scopes and resets internal state.
     */
    public void free() {
        scopeStack.clear();
        errors.clear();
        warnings.clear();
        currentScopeLevel = 0;
        nextAddress = 0;
    }

    /**
     * OPERATION 3: lookup
     * Searches for an identifier name and returns its entry.
     * Searches from current scope up to global scope (following scope rules).
     *
     * @param name the identifier name to search for
     * @return the symbol table entry if found, null otherwise
     */
    public SymbolTableEntry lookup(String name) {
        // Search from current scope upward to global scope
        for (Scope scope : scopeStack) {
            if (scope.contains(name)) {
                return scope.get(name);
            }
        }
        return null; // Not found in any scope
    }

    /**
     * OPERATION 4: insert
     * Inserts a new identifier into the symbol table in the current scope.
     * Reports error if identifier already exists in current scope (redeclaration).
     *
     * @param name the identifier name
     * @param entry the symbol table entry to insert
     * @return true if insertion succeeded, false if redeclaration error
     */
    public boolean insert(String name, SymbolTableEntry entry) {
        if (scopeStack.isEmpty()) {
            errors.add("ERROR: Cannot insert - no scope available");
            return false;
        }

        Scope currentScope = scopeStack.peek();

        // Check for redeclaration in current scope
        if (currentScope.contains(name)) {
            int existingLine = currentScope.get(name).getLineOfDeclaration();
            errors.add(String.format(
                "ERROR: Redeclaration of identifier '%s' at line %d (previously declared at line %d)",
                name, entry.getLineOfDeclaration(), existingLine
            ));
            return false;
        }

        // Set scope level and address
        entry.setScopeLevel(currentScopeLevel);

        // Allocate memory address if not already set
        if (entry.getAddress() == -1) {
            entry.setAddress(nextAddress);
            nextAddress += entry.getSize();
        }

        // Insert into current scope
        currentScope.put(name, entry);

        return true;
    }

    /**
     * OPERATION 5: set_attribute
     * Associates a specific attribute with an entry.
     * The entry must already exist in the symbol table.
     *
     * @param name the identifier name
     * @param attribute the attribute name to set
     * @param value the attribute value
     * @return true if attribute was set, false if identifier not found
     */
    public boolean set_attribute(String name, String attribute, Object value) {
        SymbolTableEntry entry = lookup(name);

        if (entry == null) {
            errors.add(String.format(
                "ERROR: Cannot set attribute - identifier '%s' not found in symbol table",
                name
            ));
            return false;
        }

        // Set the specified attribute
        switch (attribute.toLowerCase()) {
            case "type":
                entry.setType((String) value);
                break;

            case "size":
                entry.setSize((Integer) value);
                break;

            case "dimension":
                entry.setDimension((Integer) value);
                break;

            case "address":
                entry.setAddress((Integer) value);
                break;

            case "initialized":
                entry.setInitialized((Boolean) value);
                break;

            case "line_of_declaration":
                entry.setLineOfDeclaration((Integer) value);
                break;

            case "array_dimensions":
                @SuppressWarnings("unchecked")
                List<Integer> dims = (List<Integer>) value;
                entry.setArrayDimensions(dims);
                break;

            default:
                warnings.add(String.format(
                    "WARNING: Unknown attribute '%s' for identifier '%s'",
                    attribute, name
                ));
                return false;
        }

        return true;
    }

    /**
     * OPERATION 6: get_attribute
     * Retrieves a specific attribute from an entry.
     *
     * @param name the identifier name
     * @param attribute the attribute name to retrieve
     * @return the attribute value, or null if not found
     */
    public Object get_attribute(String name, String attribute) {
        SymbolTableEntry entry = lookup(name);

        if (entry == null) {
            errors.add(String.format(
                "ERROR: Cannot get attribute - identifier '%s' not found in symbol table",
                name
            ));
            return null;
        }

        // Get the specified attribute
        switch (attribute.toLowerCase()) {
            case "name":
                return entry.getName();

            case "type":
                return entry.getType();

            case "size":
                return entry.getSize();

            case "dimension":
                return entry.getDimension();

            case "address":
                return entry.getAddress();

            case "line_of_declaration":
                return entry.getLineOfDeclaration();

            case "lines_of_usage":
                return entry.getLinesOfUsage();

            case "scope_level":
                return entry.getScopeLevel();

            case "initialized":
                return entry.isInitialized();

            case "array_dimensions":
                return entry.getArrayDimensions();

            default:
                warnings.add(String.format(
                    "WARNING: Unknown attribute '%s' for identifier '%s'",
                    attribute, name
                ));
                return null;
        }
    }


    // ========== Scope Management Operations ==========

    /**
     * Enter a new nested scope
     */
    public void enterScope() {
        currentScopeLevel++;
        scopeStack.push(new Scope(currentScopeLevel));
    }

    /**
     * Exit current scope and return to parent scope
     * Cannot exit global scope.
     */
    public void exitScope() {
        if (scopeStack.size() <= 1) {
            warnings.add("WARNING: Cannot exit global scope");
            return;
        }

        scopeStack.pop();
        currentScopeLevel--;
    }

    /**
     * Get current scope level
     */
    public int getCurrentScopeLevel() {
        return currentScopeLevel;
    }


    // ========== Usage Tracking ==========

    /**
     * Record usage of an identifier at a specific line
     * Reports error if identifier is undeclared.
     *
     * @param name the identifier name
     * @param line the line number where identifier is used
     * @return true if usage recorded, false if identifier undeclared
     */
    public boolean recordUsage(String name, int line) {
        SymbolTableEntry entry = lookup(name);

        if (entry == null) {
            errors.add(String.format(
                "ERROR: Undeclared identifier '%s' used at line %d",
                name, line
            ));
            return false;
        }

        entry.addLineOfUsage(line);
        return true;
    }


    // ========== Lookup Variants ==========

    /**
     * Lookup identifier in current scope only
     */
    public SymbolTableEntry lookupCurrentScope(String name) {
        if (scopeStack.isEmpty()) {
            return null;
        }
        return scopeStack.peek().get(name);
    }

    /**
     * Check if identifier exists in any scope
     */
    public boolean contains(String name) {
        return lookup(name) != null;
    }

    /**
     * Check if identifier exists in current scope
     */
    public boolean containsInCurrentScope(String name) {
        return lookupCurrentScope(name) != null;
    }


    // ========== Retrieval Operations ==========

    /**
     * Get all entries from all scopes
     */
    public List<SymbolTableEntry> getAllEntries() {
        List<SymbolTableEntry> allEntries = new ArrayList<>();
        for (Scope scope : scopeStack) {
            allEntries.addAll(scope.getEntries().values());
        }
        return allEntries;
    }

    /**
     * Get all entries from current scope only
     */
    public List<SymbolTableEntry> getCurrentScopeEntries() {
        if (scopeStack.isEmpty()) {
            return new ArrayList<>();
        }
        return new ArrayList<>(scopeStack.peek().getEntries().values());
    }

    /**
     * Get all entries at a specific scope level
     */
    public List<SymbolTableEntry> getEntriesAtLevel(int level) {
        List<SymbolTableEntry> entries = new ArrayList<>();
        for (Scope scope : scopeStack) {
            if (scope.getLevel() == level) {
                entries.addAll(scope.getEntries().values());
            }
        }
        return entries;
    }


    // ========== Error and Warning Management ==========

    /**
     * Get all errors
     */
    public List<String> getErrors() {
        return new ArrayList<>(errors);
    }

    /**
     * Get all warnings
     */
    public List<String> getWarnings() {
        return new ArrayList<>(warnings);
    }

    /**
     * Check if there are any errors
     */
    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    /**
     * Check if there are any warnings
     */
    public boolean hasWarnings() {
        return !warnings.isEmpty();
    }

    /**
     * Clear all errors and warnings
     */
    public void clearMessages() {
        errors.clear();
        warnings.clear();
    }

    /**
     * Print all errors to console
     */
    public void printErrors() {
        if (errors.isEmpty()) {
            System.out.println("No errors.");
            return;
        }

        System.out.println("\n╔════════════════════════════════════════════════╗");
        System.out.println("║                   ERRORS                       ║");
        System.out.println("╚════════════════════════════════════════════════╝");
        for (String error : errors) {
            System.out.println("  " + error);
        }
        System.out.println();
    }

    /**
     * Print all warnings to console
     */
    public void printWarnings() {
        if (warnings.isEmpty()) {
            return;
        }

        System.out.println("\n╔════════════════════════════════════════════════╗");
        System.out.println("║                  WARNINGS                      ║");
        System.out.println("╚════════════════════════════════════════════════╝");
        for (String warning : warnings) {
            System.out.println("  " + warning);
        }
        System.out.println();
    }


    // ========== Display and Formatting ==========

    /**
     * Print the entire symbol table with all scopes
     */
    public void printTable() {
        System.out.println("\n╔═══════════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                                    SYMBOL TABLE                                           ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════════════════════════════════╝");

        if (scopeStack.isEmpty()) {
            System.out.println("  (empty)");
            return;
        }

        // Print table header
        System.out.println("\n┌───────────────┬────────────┬─────────┬──────┬────────┬──────────┬────────┬──────────────┐");
        System.out.println("│ Name          │ Type       │ Size    │ Dim  │ Decl   │ Address  │ Scope  │ Usage Lines  │");
        System.out.println("├───────────────┼────────────┼─────────┼──────┼────────┼──────────┼────────┼──────────────┤");

        // Print entries for each scope (from global to current)
        List<Scope> scopeList = new ArrayList<>(scopeStack);
        Collections.reverse(scopeList); // Reverse to print global scope first

        for (Scope scope : scopeList) {
            if (!scope.getEntries().isEmpty()) {
                System.out.println(String.format("│ %-90s │", "SCOPE LEVEL " + scope.getLevel()));
                System.out.println("├───────────────┼────────────┼─────────┼──────┼────────┼──────────┼────────┼──────────────┤");

                for (SymbolTableEntry entry : scope.getEntries().values()) {
                    String usageLines = entry.getLinesOfUsage().isEmpty() ?
                        "-" : entry.getLinesOfUsage().toString();

                    System.out.println(String.format(
                        "│ %-13s │ %-10s │ %-7d │ %-4d │ %-6d │ %-8d │ %-6d │ %-12s │",
                        truncate(entry.getName(), 13),
                        truncate(entry.getType(), 10),
                        entry.getSize(),
                        entry.getDimension(),
                        entry.getLineOfDeclaration(),
                        entry.getAddress(),
                        entry.getScopeLevel(),
                        truncate(usageLines, 12)
                    ));
                }

                System.out.println("├───────────────┼────────────┼─────────┼──────┼────────┼──────────┼────────┼──────────────┤");
            }
        }

        System.out.println("└───────────────┴────────────┴─────────┴──────┴────────┴──────────┴────────┴──────────────┘");
        System.out.println(String.format("\nTotal Entries: %d | Current Scope Level: %d | Next Address: %d",
            getAllEntries().size(), currentScopeLevel, nextAddress));
        System.out.println();
    }

    /**
     * Print compact view of symbol table
     */
    public void printCompact() {
        System.out.println("\n═══════════════════ SYMBOL TABLE (Compact) ═══════════════════");
        for (SymbolTableEntry entry : getAllEntries()) {
            System.out.println("  " + entry.toCompactString());
        }
        System.out.println("═══════════════════════════════════════════════════════════════\n");
    }

    /**
     * Print detailed view of a specific entry
     */
    public void printEntry(String name) {
        SymbolTableEntry entry = lookup(name);
        if (entry == null) {
            System.out.println("Entry '" + name + "' not found.");
            return;
        }
        System.out.println(entry.toDetailedString());
    }

    /**
     * Get statistics about the symbol table
     */
    public String getStatistics() {
        int totalEntries = getAllEntries().size();
        int totalScopes = scopeStack.size();
        int totalErrors = errors.size();
        int totalWarnings = warnings.size();

        return String.format(
            "Statistics: %d entries, %d scopes, %d errors, %d warnings",
            totalEntries, totalScopes, totalErrors, totalWarnings
        );
    }


    // ========== Helper Methods ==========

    /**
     * Truncate string to specified length
     */
    private String truncate(String str, int maxLength) {
        if (str.length() <= maxLength) {
            return str;
        }
        return str.substring(0, maxLength - 2) + "..";
    }
}
