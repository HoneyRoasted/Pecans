package honeyroasted.pecans.node.instruction.variable;

import honeyroasted.pecans.node.Context;
import honeyroasted.pecans.node.instruction.Node;
import honeyroasted.pecans.node.instruction.TypedNode;
import org.objectweb.asm.commons.InstructionAdapter;

public class PutField implements Node {
    private TypedNode target;
    private String name;
    private TypedNode value;

    public PutField(TypedNode target, String name, TypedNode value) {
        this.target = target;
        this.name = name;
        this.value = value;
    }

    @Override
    public void accept(InstructionAdapter adapter, Context context) {
        this.target.accept(adapter, context);
        this.value.accept(adapter, context);
        adapter.putfield(this.target.type(context).writeInternalName(), this.name, this.value.type(context).writeDesc());
    }

    @Override
    public void preprocess(Context context) {
        this.target.preprocess(context);
        this.value.preprocess(context);
    }

}
