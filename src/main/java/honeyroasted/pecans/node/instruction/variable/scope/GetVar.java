package honeyroasted.pecans.node.instruction.variable.scope;

import honeyroasted.pecans.node.Context;
import honeyroasted.pecans.node.instruction.TypedNode;
import honeyroasted.pecans.type.type.TypeInformal;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.InstructionAdapter;

public class GetVar implements TypedNode {
    private String name;

    public GetVar(String name) {
        this.name = name;
    }

    @Override
    public TypeInformal type(Context context) {
        return context.scope().getVar(this.name).getType();
    }

    @Override
    public void accept(InstructionAdapter adapter, Context context) {
        VariableScope.Var var = context.scope().getVar(this.name);

        adapter.load(var.getIndex(), Type.getType(var.getType().writeDesc()));

        var.accept(this, adapter);
    }

    @Override
    public void preprocess(Context context) {

    }
}
