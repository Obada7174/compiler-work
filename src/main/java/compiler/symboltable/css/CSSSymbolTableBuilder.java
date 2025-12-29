package compiler.symboltable.css;

import compiler.ast.css.*;

import java.util.Stack;


public class CSSSymbolTableBuilder {
    private CSSSymbolTable symbolTable;
    private Stack<String> scopeStack;
    private Stack<CSSSymbolTableEntry> parentStack;

    public CSSSymbolTableBuilder() {
        this.symbolTable = new CSSSymbolTable();
        this.scopeStack = new Stack<>();
        this.parentStack = new Stack<>();
        this.scopeStack.push("global");
    }


     // Build symbol table from CSS AST root.

    public CSSSymbolTable build(CSSStylesheetNode stylesheet) {
        processStylesheet(stylesheet);
        return symbolTable;
    }


     // Process the stylesheet root.

    private void processStylesheet(CSSStylesheetNode stylesheet) {
        // Process rule sets
        for (CSSRuleSetNode ruleSet : stylesheet.getRuleSets()) {
            processRuleSet(ruleSet);
        }

        // Process at-rules
        for (CSSAtRuleNode atRule : stylesheet.getAtRules()) {
            processAtRule(atRule);
        }
    }


    //  Process a rule set (selector + declarations).

    private void processRuleSet(CSSRuleSetNode ruleSet) {
        CSSSelectorListNode selectorList = ruleSet.getSelectorList();
        if (selectorList == null) return;

        // Process each selector in the list
        for (CSSSelectorNode selector : selectorList.getSelectors()) {
            String selectorText = selector.getSelectorText();
            String currentScope = getCurrentScope();

            // Extract individual selector parts
            for (CSSSimpleSelectorNode simpleSelector : selector.getSimpleSelectors()) {
                processSelectorParts(simpleSelector, selectorText, currentScope, ruleSet);
            }
        }
    }

   // Process selector parts to extract individual symbols.

    private void processSelectorParts(CSSSimpleSelectorNode simpleSelector,
                                      String fullSelector,
                                      String scope,
                                      CSSRuleSetNode ruleSet) {
        for (CSSSelectorPartNode part : simpleSelector.getSelectorParts()) {
            CSSSymbolTableEntry entry = null;

            if (part instanceof CSSClassSelectorNode) {
                CSSClassSelectorNode classNode = (CSSClassSelectorNode) part;
                entry = new CSSSymbolTableEntry(
                        classNode.getClassName(),
                        CSSSymbolType.CLASS_SELECTOR,
                        scope,
                        part.getLineNumber()
                );
                entry.setFullSelector(fullSelector);

            } else if (part instanceof CSSIdSelectorNode) {
                CSSIdSelectorNode idNode = (CSSIdSelectorNode) part;
                entry = new CSSSymbolTableEntry(
                        idNode.getIdName(),
                        CSSSymbolType.ID_SELECTOR,
                        scope,
                        part.getLineNumber()
                );
                entry.setFullSelector(fullSelector);

            } else if (part instanceof CSSElementSelectorNode) {
                CSSElementSelectorNode elemNode = (CSSElementSelectorNode) part;
                entry = new CSSSymbolTableEntry(
                        elemNode.getElementName(),
                        CSSSymbolType.ELEMENT_SELECTOR,
                        scope,
                        part.getLineNumber()
                );
                entry.setFullSelector(fullSelector);

            } else if (part instanceof CSSPseudoClassSelectorNode) {
                CSSPseudoClassSelectorNode pseudoClass = (CSSPseudoClassSelectorNode) part;
                entry = new CSSSymbolTableEntry(
                        pseudoClass.getPseudoClassName(),
                        CSSSymbolType.PSEUDO_CLASS,
                        scope,
                        part.getLineNumber()
                );
                entry.setFullSelector(fullSelector);

            } else if (part instanceof CSSPseudoElementSelectorNode) {
                CSSPseudoElementSelectorNode pseudoElem = (CSSPseudoElementSelectorNode) part;
                entry = new CSSSymbolTableEntry(
                        pseudoElem.getPseudoElementName(),
                        CSSSymbolType.PSEUDO_ELEMENT,
                        scope,
                        part.getLineNumber()
                );
                entry.setFullSelector(fullSelector);

            } else if (part instanceof CSSAttributeSelectorNode) {
                CSSAttributeSelectorNode attrNode = (CSSAttributeSelectorNode) part;
                entry = new CSSSymbolTableEntry(
                        attrNode.getAttributeName(),
                        CSSSymbolType.ATTRIBUTE_SELECTOR,
                        scope,
                        part.getLineNumber()
                );
                entry.setFullSelector(fullSelector);
                if (attrNode.hasValue()) {
                    entry.setValue(attrNode.getValue().getName());
                }
            }

            // Add entry and process declarations
            if (entry != null) {
                // Add properties from declarations
                for (CSSDeclarationNode decl : ruleSet.getDeclarations()) {
                    entry.addProperty(decl.getProperty());

                    // Check if this is a CSS variable declaration
                    if (decl.getProperty().startsWith("--")) {
                        CSSSymbolTableEntry varEntry = new CSSSymbolTableEntry(
                                decl.getProperty(),
                                CSSSymbolType.VARIABLE,
                                fullSelector,  // Scope is the selector
                                decl.getLineNumber()
                        );
                        if (decl.getValue() != null) {
                            varEntry.setValue(decl.getValue().getValueText());
                        }
                        symbolTable.addSymbol(varEntry);
                        entry.addChild(varEntry);
                    }

                    // Check for animation property referencing keyframes
                    if (decl.getProperty().equals("animation") ||
                        decl.getProperty().equals("animation-name")) {
                        // Extract keyframe name from value
                        // This is a reference, not a definition
                    }
                }

                symbolTable.addSymbol(entry);

                // Handle parent-child relationship
                if (!parentStack.isEmpty()) {
                    parentStack.peek().addChild(entry);
                }
            }
        }
    }


     // Process at-rules (@media, @keyframes, etc.).

    private void processAtRule(CSSAtRuleNode atRule) {
        String keyword = atRule.getKeyword();

        if (keyword.equals("@keyframes")) {
            processKeyframes(atRule);
        } else if (keyword.equals("@media")) {
            processMediaQuery(atRule);
        } else {
            // Generic at-rule
            CSSSymbolTableEntry entry = new CSSSymbolTableEntry(
                    keyword,
                    CSSSymbolType.AT_RULE,
                    getCurrentScope(),
                    atRule.getLineNumber()
            );
            if (atRule.getPrelude() != null) {
                entry.setValue(atRule.getPrelude().getPreludeText());
            }
            symbolTable.addSymbol(entry);
        }
    }


    //  Process @keyframes rule.

    private void processKeyframes(CSSAtRuleNode atRule) {
        // Extract keyframe name from prelude
        String keyframeName = "unknown";
        if (atRule.getPrelude() != null) {
            String preludeText = atRule.getPrelude().getPreludeText();
            // The prelude text should contain the animation name
            keyframeName = preludeText.trim();
        }

        CSSSymbolTableEntry entry = new CSSSymbolTableEntry(
                keyframeName,
                CSSSymbolType.KEYFRAME,
                getCurrentScope(),
                atRule.getLineNumber()
        );

        symbolTable.addSymbol(entry);

        // Process nested rules within keyframes (from, to, percentage stops)
        parentStack.push(entry);
        scopeStack.push("@keyframes " + keyframeName);

        for (CSSRuleSetNode ruleSet : atRule.getRuleSets()) {
            // These are keyframe stops (from, to, 0%, 50%, etc.)
            if (ruleSet.getSelectorList() != null) {
                String stopName = ruleSet.getSelectorList().toString();
                CSSSymbolTableEntry stopEntry = new CSSSymbolTableEntry(
                        stopName,
                        CSSSymbolType.ELEMENT_SELECTOR,
                        getCurrentScope(),
                        ruleSet.getLineNumber()
                );

                // Add properties from this keyframe stop
                for (CSSDeclarationNode decl : ruleSet.getDeclarations()) {
                    stopEntry.addProperty(decl.getProperty());
                }

                entry.addChild(stopEntry);
            }
        }

        scopeStack.pop();
        parentStack.pop();
    }


     // Process @media query.

    private void processMediaQuery(CSSAtRuleNode atRule) {
        String mediaQuery = "unknown";
        if (atRule.getPrelude() != null) {
            mediaQuery = atRule.getPrelude().getPreludeText();
        }

        CSSSymbolTableEntry entry = new CSSSymbolTableEntry(
                "@media",
                CSSSymbolType.MEDIA_QUERY,
                getCurrentScope(),
                atRule.getLineNumber()
        );
        entry.setValue(mediaQuery);

        symbolTable.addSymbol(entry);

        // Process nested rules within media query
        parentStack.push(entry);
        scopeStack.push("@media " + mediaQuery);

        for (CSSRuleSetNode ruleSet : atRule.getRuleSets()) {
            processRuleSet(ruleSet);
        }

        scopeStack.pop();
        parentStack.pop();
    }


    //  Get current scope as a string.

    private String getCurrentScope() {
        return scopeStack.peek();
    }

    public CSSSymbolTable getSymbolTable() {
        return symbolTable;
    }
}
