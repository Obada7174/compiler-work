package compiler;

import compiler.symboltable.*;
import compiler.ast.*;

import java.util.*;

/**
 * Test Class for Classical Symbol Table Implementation
 *
 * This class demonstrates all symbol table operations following classical Compiler Design principles:
 * 1. allocate() - create new symbol table
 * 2. free() - release symbol table
 * 3. lookup() - search for identifiers
 * 4. insert() - add new identifiers
 * 5. set_attribute() - modify entry attributes
 * 6. get_attribute() - retrieve entry attributes
 *
 * Also demonstrates:
 * - Nested scope management
 * - Redeclaration error detection
 * - Undeclared identifier error detection
 * - Usage tracking
 */
public class TestSymbolTable {

    public static void main(String[] args) {
        System.out.println("CLASSICAL SYMBOL TABLE - COMPILER DESIGN IMPLEMENTATION");
        System.out.println("Academic Test Suite");

        // Run all tests
        test1_BasicOperations();
        test2_ScopeManagement();
        test3_ErrorDetection();
        test4_AttributeOperations();
        test5_UsageTracking();
        test6_ArrayDeclarations();
        test7_CompleteProgram();

        System.out.println("ALL TESTS COMPLETED!!!!");

    }

    /* TEST 1: Basic Operations
     Demonstrates: allocate, insert, lookup, free
     */
    private static void test1_BasicOperations() {
        printTestHeader("TEST 1: Basic Symbol Table Operations");

        // OPERATION 1: allocate
        ClassicalSymbolTable symbolTable = ClassicalSymbolTable.allocate();
        System.out.println("✓ Symbol table allocated\n");

        // OPERATION 4: insert
        System.out.println("Inserting identifiers:");
        SymbolTableEntry entry1 = new SymbolTableEntry("x", "int", 1);
        symbolTable.insert("x", entry1);

        SymbolTableEntry entry2 = new SymbolTableEntry("y", "float", 2);
        symbolTable.insert("y", entry2);

        SymbolTableEntry entry3 = new SymbolTableEntry("name", "string", 3);
        symbolTable.insert("name", entry3);

        System.out.println("\n✓ Three identifiers inserted\n");

        // OPERATION 3: lookup
        System.out.println("Looking up identifiers:");
        SymbolTableEntry found = symbolTable.lookup("x");
        System.out.println("  lookup('x'): " + (found != null ? found.toCompactString() : "NOT FOUND"));

        found = symbolTable.lookup("y");
        System.out.println("  lookup('y'): " + (found != null ? found.toCompactString() : "NOT FOUND"));

        found = symbolTable.lookup("undefined");
        System.out.println("  lookup('undefined'): " + (found != null ? found.toCompactString() : "NOT FOUND"));

        System.out.println("\n✓ Lookup operations completed\n");

        // Display table
        symbolTable.printTable();

        // OPERATION 2: free
        symbolTable.free();
        System.out.println("✓ Symbol table freed\n");

        printTestFooter();
    }

    /*
      TEST 2: Scope Management
      Demonstrates: enterScope, exitScope, nested scopes
     */
    private static void test2_ScopeManagement() {
        printTestHeader("TEST 2: Nested Scope Management");

        ClassicalSymbolTable symbolTable = ClassicalSymbolTable.allocate();

        // Global scope (level 0)
        System.out.println("GLOBAL SCOPE (Level 0):");
        symbolTable.insert("globalVar", new SymbolTableEntry("globalVar", "int", 10));
        symbolTable.insert("x", new SymbolTableEntry("x", "int", 11));

        // Enter scope level 1 (function)
        symbolTable.enterScope();
        System.out.println("\nFUNCTION SCOPE (Level 1):");
        symbolTable.insert("param1", new SymbolTableEntry("param1", "int", 15));
        symbolTable.insert("localVar", new SymbolTableEntry("localVar", "float", 16));
        symbolTable.insert("x", new SymbolTableEntry("x", "int", 17)); // Shadow global x

        // Enter scope level 2 (nested block)
        symbolTable.enterScope();
        System.out.println("\nBLOCK SCOPE (Level 2):");
        symbolTable.insert("i", new SymbolTableEntry("i", "int", 20));
        symbolTable.insert("temp", new SymbolTableEntry("temp", "int", 21));

        System.out.println("\n");
        symbolTable.printTable();

        // Lookup from nested scope - should find shadowed variables
        System.out.println("Lookup tests from innermost scope:");
        System.out.println("  lookup('x'): " + symbolTable.lookup("x").toCompactString());
        System.out.println("  lookup('globalVar'): " + symbolTable.lookup("globalVar").toCompactString());
        System.out.println("  lookup('i'): " + symbolTable.lookup("i").toCompactString());

        // Exit scopes
        symbolTable.exitScope();
        System.out.println("\nExited block scope (back to level 1)");

        symbolTable.exitScope();
        System.out.println("Exited function scope (back to level 0)\n");

        symbolTable.printTable();
        symbolTable.free();

        printTestFooter();
    }

    /*
     TEST 3: Error Detection
     Demonstrates: redeclaration errors, undeclared identifier errors
     */
    private static void test3_ErrorDetection() {
        printTestHeader("TEST 3: Error Detection");

        ClassicalSymbolTable symbolTable = ClassicalSymbolTable.allocate();

        System.out.println("Testing REDECLARATION error:");
        symbolTable.insert("x", new SymbolTableEntry("x", "int", 30));
        symbolTable.insert("x", new SymbolTableEntry("x", "float", 31)); // Error: redeclaration
        System.out.println();

        System.out.println("Testing UNDECLARED IDENTIFIER error:");
        symbolTable.recordUsage("undeclaredVar", 35); // Error: undeclared
        System.out.println();

        // Display errors
        symbolTable.printErrors();
        symbolTable.printWarnings();

        System.out.println("Statistics: " + symbolTable.getStatistics());

        symbolTable.free();
        printTestFooter();
    }

    /*
     TEST 4: Attribute Operations
     Demonstrates: set_attribute, get_attribute
     */
    private static void test4_AttributeOperations() {
        printTestHeader("TEST 4: Attribute Get/Set Operations");

        ClassicalSymbolTable symbolTable = ClassicalSymbolTable.allocate();

        // Insert an entry
        SymbolTableEntry entry = new SymbolTableEntry("myVar", "int", 40);
        symbolTable.insert("myVar", entry);

        System.out.println("Initial entry:");
        symbolTable.printEntry("myVar");

        // OPERATION 5: set_attribute
        System.out.println("\nModifying attributes:");
        symbolTable.set_attribute("myVar", "type", "float");
        System.out.println("  ✓ Changed type to 'float'");

        symbolTable.set_attribute("myVar", "size", 8);
        System.out.println("  ✓ Changed size to 8 bytes");

        symbolTable.set_attribute("myVar", "address", 1024);
        System.out.println("  ✓ Set address to 1024");

        symbolTable.set_attribute("myVar", "initialized", true);
        System.out.println("  ✓ Marked as initialized");

        // OPERATION 6: get_attribute
        System.out.println("\nRetrieving attributes:");
        System.out.println("  get_attribute('type'): " + symbolTable.get_attribute("myVar", "type"));
        System.out.println("  get_attribute('size'): " + symbolTable.get_attribute("myVar", "size"));
        System.out.println("  get_attribute('address'): " + symbolTable.get_attribute("myVar", "address"));
        System.out.println("  get_attribute('initialized'): " + symbolTable.get_attribute("myVar", "initialized"));

        System.out.println("\nModified entry:");
        symbolTable.printEntry("myVar");

        symbolTable.free();
        printTestFooter();
    }

    /*
      TEST 5: Usage Tracking
      Demonstrates: recording and tracking identifier usage across multiple lines
     */
    private static void test5_UsageTracking() {
        printTestHeader("TEST 5: Usage Tracking");

        ClassicalSymbolTable symbolTable = ClassicalSymbolTable.allocate();

        // Declare variables
        symbolTable.insert("count", new SymbolTableEntry("count", "int", 50));
        symbolTable.insert("total", new SymbolTableEntry("total", "float", 51));

        // Record usage at various lines
        System.out.println("Recording usage:");
        symbolTable.recordUsage("count", 55);
        symbolTable.recordUsage("count", 58);
        symbolTable.recordUsage("count", 62);
        symbolTable.recordUsage("total", 56);
        symbolTable.recordUsage("total", 60);

        System.out.println();
        symbolTable.printTable();

        // Display usage details
        System.out.println("Usage Details:");
        SymbolTableEntry countEntry = symbolTable.lookup("count");
        System.out.println("  'count' declared at line " + countEntry.getLineOfDeclaration());
        System.out.println("  'count' used at lines: " + countEntry.getLinesOfUsage());
        System.out.println("  'count' usage count: " + countEntry.getUsageCount());

        symbolTable.free();
        printTestFooter();
    }

    /*
     TEST 6: Array Declarations
     Demonstrates: dimension and array size tracking
     */
    private static void test6_ArrayDeclarations() {
        printTestHeader("TEST 6: Array Declarations");

        ClassicalSymbolTable symbolTable = ClassicalSymbolTable.allocate();

        // Scalar variable
        System.out.println("Declaring scalar variable:");
        SymbolTableEntry scalar = new SymbolTableEntry("x", "int", 70);
        symbolTable.insert("x", scalar);
        System.out.println("  x: dimension = " + scalar.getDimension() + " (scalar)");

        // 1D array: int arr[10]
        System.out.println("\nDeclaring 1D array:");
        SymbolTableEntry array1D = new SymbolTableEntry("arr", "int", 71);
        array1D.setDimension(1);
        array1D.setArrayDimensions(Arrays.asList(10));
        symbolTable.insert("arr", array1D);
        System.out.println("  arr[10]: dimension = " + array1D.getDimension());
        System.out.println("  arr dimensions: " + array1D.getArrayDimensionsString());
        System.out.println("  arr size: " + array1D.getSize() + " bytes");

        // 2D array: int matrix[5][10]
        System.out.println("\nDeclaring 2D array:");
        SymbolTableEntry array2D = new SymbolTableEntry("matrix", "int", 72);
        array2D.setDimension(2);
        array2D.setArrayDimensions(Arrays.asList(5, 10));
        symbolTable.insert("matrix", array2D);
        System.out.println("  matrix[5][10]: dimension = " + array2D.getDimension());
        System.out.println("  matrix dimensions: " + array2D.getArrayDimensionsString());
        System.out.println("  matrix size: " + array2D.getSize() + " bytes");

        // 3D array: int cube[3][4][5]
        System.out.println("\nDeclaring 3D array:");
        SymbolTableEntry array3D = new SymbolTableEntry("cube", "int", 73);
        array3D.setDimension(3);
        array3D.setArrayDimensions(Arrays.asList(3, 4, 5));
        symbolTable.insert("cube", array3D);
        System.out.println("  cube[3][4][5]: dimension = " + array3D.getDimension());
        System.out.println("  cube dimensions: " + array3D.getArrayDimensionsString());
        System.out.println("  cube size: " + array3D.getSize() + " bytes");

        System.out.println();
        symbolTable.printTable();

        symbolTable.free();
        printTestFooter();
    }

    /*
     TEST 7: Complete Program Simulation
     Demonstrates: complete symbol table usage for a realistic program
     */
    private static void test7_CompleteProgram() {
        printTestHeader("TEST 7: Complete Program Simulation");

        System.out.println("Simulating compilation of the following program:\n");
        System.out.println("1:  int globalVar = 100;");
        System.out.println("2:  float pi = 3.14;");
        System.out.println("3:  ");
        System.out.println("4:  int calculateSum(int a, int b) {");
        System.out.println("5:      int result = a + b;");
        System.out.println("6:      return result;");
        System.out.println("7:  }");
        System.out.println("8:  ");
        System.out.println("9:  int main() {");
        System.out.println("10:     int x = 10;");
        System.out.println("11:     int y = 20;");
        System.out.println("12:     int sum = calculateSum(x, y);");
        System.out.println("13:     for (int i = 0; i < 10; i++) {");
        System.out.println("14:         int temp = sum + i;");
        System.out.println("15:         sum = temp;");
        System.out.println("16:     }");
        System.out.println("17:     return sum;");
        System.out.println("18: }\n");

        ClassicalSymbolTable symbolTable = ClassicalSymbolTable.allocate();

        // Global scope
        System.out.println("\n=== Processing Global Scope ===");
        symbolTable.insert("globalVar", new SymbolTableEntry("globalVar", "int", 1));
        symbolTable.insert("pi", new SymbolTableEntry("pi", "float", 2));

        // Function: calculateSum
        System.out.println("\n=== Processing Function: calculateSum ===");
        symbolTable.insert("calculateSum", new SymbolTableEntry("calculateSum", "function", 4));
        symbolTable.enterScope(); // Enter function scope
        symbolTable.insert("a", new SymbolTableEntry("a", "int", 4));
        symbolTable.insert("b", new SymbolTableEntry("b", "int", 4));
        symbolTable.insert("result", new SymbolTableEntry("result", "int", 5));
        symbolTable.recordUsage("a", 5);
        symbolTable.recordUsage("b", 5);
        symbolTable.recordUsage("result", 6);
        symbolTable.exitScope(); // Exit function scope

        // Function: main
        System.out.println("\n=== Processing Function: main ===");
        symbolTable.insert("main", new SymbolTableEntry("main", "function", 9));
        symbolTable.enterScope(); // Enter main scope
        symbolTable.insert("x", new SymbolTableEntry("x", "int", 10));
        symbolTable.insert("y", new SymbolTableEntry("y", "int", 11));
        symbolTable.insert("sum", new SymbolTableEntry("sum", "int", 12));
        symbolTable.recordUsage("calculateSum", 12);
        symbolTable.recordUsage("x", 12);
        symbolTable.recordUsage("y", 12);

        // For loop scope
        symbolTable.enterScope(); // Enter for-loop scope
        symbolTable.insert("i", new SymbolTableEntry("i", "int", 13));
        symbolTable.recordUsage("i", 13);
        symbolTable.recordUsage("i", 13);
        symbolTable.insert("temp", new SymbolTableEntry("temp", "int", 14));
        symbolTable.recordUsage("sum", 14);
        symbolTable.recordUsage("i", 14);
        symbolTable.recordUsage("sum", 15);
        symbolTable.recordUsage("temp", 15);
        symbolTable.exitScope(); // Exit for-loop scope

        symbolTable.recordUsage("sum", 17);
        symbolTable.exitScope(); // Exit main scope

        System.out.println("\n=== Final Symbol Table ===");
        symbolTable.printTable();

        System.out.println("\nDetailed Entry Examples:");
        symbolTable.printEntry("calculateSum");
        System.out.println();
        symbolTable.printEntry("sum");

        System.out.println("\n" + symbolTable.getStatistics());

        symbolTable.free();
        printTestFooter();
    }

    /*
     Helper method to print test header
     */
    private static void printTestHeader(String testName) {
        System.out.println("\n" + "=".repeat(75));
        System.out.println(testName);
        System.out.println("=".repeat(75) + "\n");
    }

    /*
      Helper method to print test footer
     */
    private static void printTestFooter() {
        System.out.println("─".repeat(75) + "\n");
    }
}
