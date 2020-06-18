package honeyroasted.pecans.node.instruction.variable;

import honeyroasted.pecans.node.Context;
import honeyroasted.pecans.node.instruction.Node;
import honeyroasted.pecans.node.instruction.TypedNode;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.InstructionAdapter;

public class PutLocal implements Node {
    private int index;
    private TypedNode value;

    public PutLocal(int index, TypedNode value) {
        this.index = index;
        this.value = value;
    }

    @Override
    public void accept(InstructionAdapter adapter, Context context) {
        this.value.accept(adapter, context);
        adapter.store(this.index, Type.getType(this.value.type(context).writeDesc()));
    }

    @Override
    public void preprocess(Context context) {
        this.value.preprocess(context);
    }
}
