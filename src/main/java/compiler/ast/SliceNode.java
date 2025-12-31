package compiler.ast;


public class SliceNode extends ExpressionNode {
    private final ExpressionNode object;
    private final ExpressionNode start;
    private final ExpressionNode stop;
    private final ExpressionNode step;

    public SliceNode(ExpressionNode object, ExpressionNode start,
                     ExpressionNode stop, ExpressionNode step, int lineNumber) {
        super(lineNumber);
        this.object = object;
        this.start = start;
        this.stop = stop;
        this.step = step;
    }

    public ExpressionNode getObject() {
        return object;
    }

    public ExpressionNode getStart() {
        return start;
    }

    public ExpressionNode getStop() {
        return stop;
    }

    public ExpressionNode getStep() {
        return step;
    }

    @Override
    public String getNodeType() {
        return "Slice";
    }
}
