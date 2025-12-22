package compiler.symboltable;

import java.util.*;

/**
 * Symbol Table Entry
 * Represents a single entry in the symbol table following classical Compiler Design principles.
 *
 * Each entry contains all attributes necessary for identifier management during compilation.
 */
public class SymbolTableEntry {

    // ========== Core Attributes (Required by Compiler Design Theory) ==========

    /** Name: identifier name (variable, function, class, etc.) */
    private String name;

    /** Type: data type (int, char, float, string, boolean, array, function, class, etc.) */
    private String type;

    /** Size: storage size in bytes */
    private int size;

    /** Dimension: number of dimensions (0 for scalar, 1+ for arrays) */
    private int dimension;

    /** Line of Declaration: source code line where identifier is first declared */
    private int lineOfDeclaration;

    /** Lines of Usage: all source code lines where identifier is used/referenced */
    private List<Integer> linesOfUsage;

    /** Address: virtual memory address or offset (for code generation phase) */
    private int address;

    // ========== Additional Attributes for Enhanced Tracking ==========

    /** Scope Level: nesting depth (0 = global, 1+ = nested scopes) */
    private int scopeLevel;

    /** Is Initialized: whether the identifier has been assigned a value */
    private boolean isInitialized;

    /** Array Dimensions: specific size of each dimension (e.g., [10][20] for 2D array) */
    private List<Integer> arrayDimensions;


    // ========== Constructors ==========

    /**
     * Default constructor - creates entry with minimal information
     */
    public SymbolTableEntry(String name, String type, int lineOfDeclaration) {
        this.name = name;
        this.type = type;
        this.lineOfDeclaration = lineOfDeclaration;
        this.linesOfUsage = new ArrayList<>();
        this.arrayDimensions = new ArrayList<>();

        // Initialize with default values
        this.size = calculateDefaultSize(type);
        this.dimension = 0;
        this.address = -1; // -1 indicates address not yet assigned
        this.scopeLevel = 0;
        this.isInitialized = false;
    }

    /**
     * Full constructor - creates entry with all attributes specified
     */
    public SymbolTableEntry(String name, String type, int size, int dimension,
                           int lineOfDeclaration, int address) {
        this.name = name;
        this.type = type;
        this.size = size;
        this.dimension = dimension;
        this.lineOfDeclaration = lineOfDeclaration;
        this.address = address;
        this.linesOfUsage = new ArrayList<>();
        this.arrayDimensions = new ArrayList<>();
        this.scopeLevel = 0;
        this.isInitialized = false;
    }


    // ========== Core Getters and Setters ==========

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
        // Recalculate size if type changes
        if (this.size == 0) {
            this.size = calculateDefaultSize(type);
        }
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getDimension() {
        return dimension;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

    public int getLineOfDeclaration() {
        return lineOfDeclaration;
    }

    public void setLineOfDeclaration(int lineOfDeclaration) {
        this.lineOfDeclaration = lineOfDeclaration;
    }

    public List<Integer> getLinesOfUsage() {
        return new ArrayList<>(linesOfUsage); // Return copy to prevent external modification
    }

    public void addLineOfUsage(int line) {
        if (!linesOfUsage.contains(line)) {
            linesOfUsage.add(line);
        }
    }

    public void setLinesOfUsage(List<Integer> lines) {
        this.linesOfUsage = new ArrayList<>(lines);
    }

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }


    // ========== Additional Getters and Setters ==========

    public int getScopeLevel() {
        return scopeLevel;
    }

    public void setScopeLevel(int scopeLevel) {
        this.scopeLevel = scopeLevel;
    }

    public boolean isInitialized() {
        return isInitialized;
    }

    public void setInitialized(boolean initialized) {
        this.isInitialized = initialized;
    }

    public List<Integer> getArrayDimensions() {
        return new ArrayList<>(arrayDimensions);
    }

    public void setArrayDimensions(List<Integer> dimensions) {
        this.arrayDimensions = new ArrayList<>(dimensions);
        this.dimension = dimensions.size();
        // Recalculate size based on dimensions
        if (this.dimension > 0) {
            int totalElements = 1;
            for (int dim : dimensions) {
                totalElements *= dim;
            }
            this.size = totalElements * calculateDefaultSize(type);
        }
    }

    public void addArrayDimension(int size) {
        this.arrayDimensions.add(size);
        this.dimension = this.arrayDimensions.size();
    }


    // ========== Helper Methods ==========

    /**
     * Calculate default storage size based on type
     * Following typical compiler conventions (in bytes)
     */
    private int calculateDefaultSize(String type) {
        if (type == null) {
            return 0;
        }

        switch (type.toLowerCase()) {
            case "char":
            case "boolean":
            case "bool":
                return 1;

            case "short":
                return 2;

            case "int":
            case "integer":
            case "float":
                return 4;

            case "long":
            case "double":
                return 8;

            case "string":
                return 8; // Pointer size (assuming 64-bit system)

            case "void":
                return 0;

            default:
                // For user-defined types, arrays, or unknown types
                return 4; // Default pointer size
        }
    }

    /**
     * Check if this entry represents an array
     */
    public boolean isArray() {
        return dimension > 0;
    }

    /**
     * Check if this entry represents a scalar variable
     */
    public boolean isScalar() {
        return dimension == 0;
    }

    /**
     * Get total number of usages
     */
    public int getUsageCount() {
        return linesOfUsage.size();
    }

    /**
     * Get formatted string representation of array dimensions
     */
    public String getArrayDimensionsString() {
        if (arrayDimensions.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int dim : arrayDimensions) {
            sb.append("[").append(dim).append("]");
        }
        return sb.toString();
    }


    // ========== Display Methods ==========

    /**
     * Get detailed string representation of the entry
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-15s | ", name));
        sb.append(String.format("%-10s | ", type));
        sb.append(String.format("Size: %-4d | ", size));
        sb.append(String.format("Dim: %-2d | ", dimension));
        sb.append(String.format("Decl: %-4d | ", lineOfDeclaration));
        sb.append(String.format("Addr: %-6d | ", address));
        sb.append(String.format("Scope: %-2d", scopeLevel));

        if (!linesOfUsage.isEmpty()) {
            sb.append(" | Usage: ").append(linesOfUsage);
        }

        return sb.toString();
    }

    /**
     * Get compact string representation
     */
    public String toCompactString() {
        return String.format("%s (%s) @ line %d", name, type, lineOfDeclaration);
    }

    /**
     * Get detailed multi-line representation
     */
    public String toDetailedString() {
        StringBuilder sb = new StringBuilder();
        sb.append("┌─ Symbol Entry ─────────────────────────\n");
        sb.append(String.format("│ Name:             %s\n", name));
        sb.append(String.format("│ Type:             %s\n", type));
        sb.append(String.format("│ Size:             %d bytes\n", size));
        sb.append(String.format("│ Dimension:        %d %s\n",
            dimension, dimension == 0 ? "(scalar)" : "(array)"));

        if (dimension > 0 && !arrayDimensions.isEmpty()) {
            sb.append(String.format("│ Array Dimensions: %s\n", getArrayDimensionsString()));
        }

        sb.append(String.format("│ Line of Decl:     %d\n", lineOfDeclaration));
        sb.append(String.format("│ Memory Address:   %d (0x%X)\n", address, address));
        sb.append(String.format("│ Scope Level:      %d\n", scopeLevel));
        sb.append(String.format("│ Initialized:      %s\n", isInitialized ? "Yes" : "No"));

        if (!linesOfUsage.isEmpty()) {
            sb.append(String.format("│ Lines of Usage:   %s\n", linesOfUsage));
            sb.append(String.format("│ Usage Count:      %d\n", linesOfUsage.size()));
        } else {
            sb.append("│ Lines of Usage:   (none)\n");
        }

        sb.append("└────────────────────────────────────────");
        return sb.toString();
    }


    // ========== Equality and Hashing ==========

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        SymbolTableEntry other = (SymbolTableEntry) obj;
        return name.equals(other.name) &&
               scopeLevel == other.scopeLevel;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, scopeLevel);
    }
}
