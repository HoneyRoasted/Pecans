package honeyroasted.pecans.node.instruction.util;

import honeyroasted.pecans.node.Context;
import honeyroasted.pecans.node.instruction.Node;
import org.objectweb.asm.commons.InstructionAdapter;

public class SkipPreprocNode implements Node {
    private Node node;

    public SkipPreprocNode(Node node) {
        this.node = node;
    }

    @Override
    public void accept(InstructionAdapter adapter, Context context) {
        this.node.accept(adapter, context);
    }

    @Override
    public void preprocess(Context context) {

    }
}
