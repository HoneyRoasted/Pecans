package honeyroasted.pecans.node.instruction;

import honeyroasted.pecans.node.Context;
import honeyroasted.pecans.type.type.TypeInformal;
import org.objectweb.asm.commons.InstructionAdapter;

public class DelegateTypedNode extends AbstractTypedNode {
    private Node node;

    public DelegateTypedNode(TypeInformal type, Node node) {
        super(type);
        this.node = node;
    }

    @Override
    public void accept(InstructionAdapter adapter, Context context) {
        this.node.accept(adapter, context);
    }

    @Override
    public void preprocess(Context context) {
        this.node.preprocess(context);
    }
}
