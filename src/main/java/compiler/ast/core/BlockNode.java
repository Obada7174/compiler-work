package compiler.ast.core;

import compiler.ast.core.ASTNode;

import java.util.List;
import java.util.ArrayList;


public class BlockNode extends ASTNode {
    private String blockName;
    private List<ASTNode> content;

    public BlockNode(String blockName, List<ASTNode> content, int lineNumber) {
        super(lineNumber);
        this.blockName = blockName;
        this.content = content != null ? content : new ArrayList<>();

        for (ASTNode node : this.content) {
            addChild(node);
        }
    }

    public String getBlockName() {
        return blockName;
    }

    public List<ASTNode> getContent() {
        return content;
    }

    @Override
    public String getNodeType() {
        return "JinjaBlock";
    }

    @Override
    public String getNodeDetails() {
        return String.format("JinjaBlock: {%% block %s %%} (%d items) (line %d)",
            blockName, content.size(), lineNumber);
    }
}
