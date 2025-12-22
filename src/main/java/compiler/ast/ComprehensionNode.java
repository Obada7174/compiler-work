package compiler.ast;

import java.util.List;
import java.util.ArrayList;

/**
 * Expression node representing list/set/dict comprehension
 * [expr for target in iterable if condition]
 * {expr for target in iterable if condition}
 * {key: value for target in iterable if condition}
 */
public class ComprehensionNode extends ExpressionNode {
    public enum ComprehensionType {
        LIST,
        SET,
        DICT
    }

    private ComprehensionType type;
    private ExpressionNode element;      // For list/set
    private ExpressionNode keyExpr;      // For dict
    private ExpressionNode valueExpr;    // For dict
    private ExpressionNode target;
    private ExpressionNode iterable;
    private ExpressionNode condition;    // Optional filter

    // Constructor for list/set comprehension
    public ComprehensionNode(ComprehensionType type, ExpressionNode element,
                           ExpressionNode target, ExpressionNode iterable,
                           ExpressionNode condition, int lineNumber) {
        super(lineNumber);
        this.type = type;
        this.element = element;
        this.target = target;
        this.iterable = iterable;
        this.condition = condition;

        addChild(element);
        addChild(target);
        addChild(iterable);
        if (condition != null) {
            addChild(condition);
        }
    }

    // Constructor for dict comprehension
    public ComprehensionNode(ExpressionNode keyExpr, ExpressionNode valueExpr,
                           ExpressionNode target, ExpressionNode iterable,
                           ExpressionNode condition, int lineNumber) {
        super(lineNumber);
        this.type = ComprehensionType.DICT;
        this.keyExpr = keyExpr;
        this.valueExpr = valueExpr;
        this.target = target;
        this.iterable = iterable;
        this.condition = condition;

        addChild(keyExpr);
        addChild(valueExpr);
        addChild(target);
        addChild(iterable);
        if (condition != null) {
            addChild(condition);
        }
    }

    public ComprehensionType getComprehensionType() {
        return type;
    }

    public ExpressionNode getElement() {
        return element;
    }

    public ExpressionNode getKeyExpr() {
        return keyExpr;
    }

    public ExpressionNode getValueExpr() {
        return valueExpr;
    }

    public ExpressionNode getTarget() {
        return target;
    }

    public ExpressionNode getIterable() {
        return iterable;
    }

    public ExpressionNode getCondition() {
        return condition;
    }

    @Override
    public String getNodeType() {
        return type.toString() + "Comprehension";
    }

    @Override
    public String getNodeDetails() {
        String filterInfo = (condition != null) ? " with filter" : "";
        return String.format("%sComprehension%s (line %d)", type, filterInfo, lineNumber);
    }
}
