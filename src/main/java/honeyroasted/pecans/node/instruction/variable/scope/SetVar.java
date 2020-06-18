package honeyroasted.pecans.node.instruction.variable.scope;

import honeyroasted.pecans.node.Context;
import honeyroasted.pecans.node.instruction.Node;
import honeyroasted.pecans.node.instruction.TypedNode;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.InstructionAdapter;

public class SetVar implements Node {
    private String name;
    private TypedNode value;

    public SetVar(String name, TypedNode value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public void accept(InstructionAdapter adapter, Context context) {
        VariableScope.Var var = context.scope().getVar(name);

        this.value.accept(adapter, context);
        adapter.store(var.getIndex(), Type.getType(var.getType().writeDesc()));

        var.accept(this, adapter);
    }

    @Override
    public void preprocess(Context context) {
        this.value.preprocess(context);
    }

}
