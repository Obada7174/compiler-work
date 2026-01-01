package compiler.symboltable;

import java.util.*;


public class SymbolTableEntry {

    private String name;

    private String type;
    private int size;
    private int dimension;
    private int lineOfDeclaration;

    private List<Integer> linesOfUsage;

    private int address;

    private int scopeLevel;
    private boolean isInitialized;

    private List<Integer> arrayDimensions;


    // ========== Constructors ==========

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
        return new ArrayList<>(linesOfUsage);
    }

    public void addLineOfUsage(int line) {
        if (!linesOfUsage.contains(line)) {
            linesOfUsage.add(line);
        }
    }

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }

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
    public boolean isScalar() {
        return dimension == 0;
    }
    public int getUsageCount() {
        return linesOfUsage.size();
    }

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


    // Display Methods
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

    public String toCompactString() {
        return String.format("%s (%s) @ line %d", name, type, lineOfDeclaration);
    }
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


    // Equality and Hashing

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
