package compiler.symboltable.css;


public enum CSSSymbolType {
    VARIABLE("CSS Variable"),           // --custom-property
    CLASS_SELECTOR("Class Selector"),   // .class-name
    ID_SELECTOR("ID Selector"),         // #id-name
    ELEMENT_SELECTOR("Element Selector"), // div, p, etc.
    PSEUDO_CLASS("Pseudo-class"
    ),       // :hover, :focus
    PSEUDO_ELEMENT("Pseudo-element"),   // ::before, ::after
    ATTRIBUTE_SELECTOR("Attribute Selector"), // [type="text"]
    KEYFRAME("Keyframe"),               // @keyframes name
    MEDIA_QUERY("Media Query"),         // @media
    AT_RULE("At-Rule");                 // @import, @font-face, etc.

    private final String displayName;

    CSSSymbolType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
