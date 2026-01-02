package compiler.symboltable.css;

import java.util.*;


public class CSSSymbolTable {
    private List<CSSSymbolTableEntry> entries;
    private Map<String, List<CSSSymbolTableEntry>> indexByName;
    private Map<CSSSymbolType, List<CSSSymbolTableEntry>> indexByType;
    private String sourceFile;

    public CSSSymbolTable() {
        this.entries = new ArrayList<>();
        this.indexByName = new HashMap<>();
        this.indexByType = new HashMap<>();
        this.sourceFile = null;
    }

    public void addSymbol(CSSSymbolTableEntry entry) {
        entries.add(entry);

        // Index by name
        indexByName.computeIfAbsent(entry.getName(), k -> new ArrayList<>()).add(entry);

        // Index by type
        indexByType.computeIfAbsent(entry.getType(), k -> new ArrayList<>()).add(entry);
    }

    // Look up symbols by name.

    public List<CSSSymbolTableEntry> lookup(String name) {
        return indexByName.getOrDefault(name, new ArrayList<>());
    }


     // Look up symbols by type.

    public List<CSSSymbolTableEntry> lookupByType(CSSSymbolType type) {
        return indexByType.getOrDefault(type, new ArrayList<>());
    }


     //Get all entries.

    public List<CSSSymbolTableEntry> getAllEntries() {
        return new ArrayList<>(entries);
    }


     // Get all CSS variables.
    public List<CSSSymbolTableEntry> getVariables() {
        return lookupByType(CSSSymbolType.VARIABLE);
    }


     // Get all selectors (class, id, element).

    public List<CSSSymbolTableEntry> getSelectors() {
        List<CSSSymbolTableEntry> selectors = new ArrayList<>();
        selectors.addAll(lookupByType(CSSSymbolType.CLASS_SELECTOR));
        selectors.addAll(lookupByType(CSSSymbolType.ID_SELECTOR));
        selectors.addAll(lookupByType(CSSSymbolType.ELEMENT_SELECTOR));
        return selectors;
    }


     // Get all keyframes.

    public List<CSSSymbolTableEntry> getKeyframes() {
        return lookupByType(CSSSymbolType.KEYFRAME);
    }


     //Get all media queries.
    public List<CSSSymbolTableEntry> getMediaQueries() {
        return lookupByType(CSSSymbolType.MEDIA_QUERY);
    }


    public void print() {
               System.out.println("CSS SYMBOL TABLE");
        if (sourceFile != null) {
            System.out.println("Source: " + sourceFile);
        }
              System.out.println();

        // Print by category
        printCategory("CSS VARIABLES", getVariables());
        printCategory("CLASS SELECTORS", lookupByType(CSSSymbolType.CLASS_SELECTOR));
        printCategory("ID SELECTORS", lookupByType(CSSSymbolType.ID_SELECTOR));
        printCategory("ELEMENT SELECTORS", lookupByType(CSSSymbolType.ELEMENT_SELECTOR));
        printCategory("KEYFRAMES", getKeyframes());
        printCategory("MEDIA QUERIES", getMediaQueries());
        printCategory("AT-RULES", lookupByType(CSSSymbolType.AT_RULE));

               System.out.println(String.format("Total Symbols: %d", entries.size()));
           }

    private void printCategory(String category, List<CSSSymbolTableEntry> categoryEntries) {
        if (categoryEntries.isEmpty()) return;

           System.out.println("  " + category + " (" + categoryEntries.size() + ")");

        for (CSSSymbolTableEntry entry : categoryEntries) {
            System.out.println("  " + entry.toString());

            if (!entry.getProperties().isEmpty()) {
                System.out.println("    Properties: " + String.join(", ", entry.getProperties()));
            }

            if (!entry.getChildren().isEmpty()) {
                System.out.println("    Nested (" + entry.getChildren().size() + "):");
                for (CSSSymbolTableEntry child : entry.getChildren()) {
                    System.out.println("      " + child.toString());
                }
            }
        }
        System.out.println();
    }

  // Export the symbol table as JSON.

    public String toJSON() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        if (sourceFile != null) {
            sb.append("  \"source\": \"").append(sourceFile).append("\",\n");
        }
        sb.append("  \"totalSymbols\": ").append(entries.size()).append(",\n");
        sb.append("  \"symbols\": [\n");

        for (int i = 0; i < entries.size(); i++) {
            sb.append(entries.get(i).toJSON(2));
            if (i < entries.size() - 1) sb.append(",");
            sb.append("\n");
        }

        sb.append("  ]\n");
        sb.append("}\n");
        return sb.toString();
    }


     // Get statistics about the symbol table.

    public Map<String, Integer> getStatistics() {
        Map<String, Integer> stats = new LinkedHashMap<>();
        stats.put("Total Symbols", entries.size());
        stats.put("CSS Variables", getVariables().size());
        stats.put("Class Selectors", lookupByType(CSSSymbolType.CLASS_SELECTOR).size());
        stats.put("ID Selectors", lookupByType(CSSSymbolType.ID_SELECTOR).size());
        stats.put("Element Selectors", lookupByType(CSSSymbolType.ELEMENT_SELECTOR).size());
        stats.put("Keyframes", getKeyframes().size());
        stats.put("Media Queries", getMediaQueries().size());
        stats.put("At-Rules", lookupByType(CSSSymbolType.AT_RULE).size());
        return stats;
    }

    public String getSourceFile() {
        return sourceFile;
    }

    public void setSourceFile(String sourceFile) {
        this.sourceFile = sourceFile;
    }
}
