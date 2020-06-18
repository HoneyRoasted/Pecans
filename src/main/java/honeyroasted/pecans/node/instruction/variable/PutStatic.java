package honeyroasted.pecans.node.instruction.variable;

import honeyroasted.pecans.node.Context;
import honeyroasted.pecans.node.instruction.Node;
import honeyroasted.pecans.node.instruction.TypedNode;
import honeyroasted.pecans.type.type.TypeFill;
import org.objectweb.asm.commons.InstructionAdapter;

public class PutStatic implements Node {
    private TypeFill target;
    private String name;
    private TypedNode value;

    public PutStatic(TypeFill target, String name, TypedNode value) {
        this.target = target;
        this.name = name;
        this.value = value;
    }

    @Override
    public void accept(InstructionAdapter adapter, Context context) {
        this.value.accept(adapter, context);
        adapter.putstatic(target.writeInternalName(), name, value.type(context).writeDesc());
    }

    @Override
    public void preprocess(Context context) {
        this.value.preprocess(context);
    }
}
