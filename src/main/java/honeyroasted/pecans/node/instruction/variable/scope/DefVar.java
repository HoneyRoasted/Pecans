package honeyroasted.pecans.node.instruction.variable.scope;

import honeyroasted.pecans.node.Context;
import honeyroasted.pecans.node.instruction.Node;
import honeyroasted.pecans.node.instruction.TypedNode;
import honeyroasted.pecans.type.type.TypeInformal;
import org.objectweb.asm.Label;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.InstructionAdapter;

public class DefVar implements Node {
    private String name;
    private TypeInformal type;
    private TypedNode value;

    public DefVar(String name, TypeInformal type, TypedNode value) {
        this.name = name;
        this.type = type;
        this.value = value;
    }

    @Override
    public void accept(InstructionAdapter adapter, Context context) {
        VariableScope.Var var = context.scope().getVar(name);

        Label label = new Label();
        adapter.mark(label);
        var.setStart(label);

        this.value.accept(adapter, context);
        adapter.store(var.getIndex(), Type.getType(var.getType().writeDesc()));

        var.accept(this, adapter);
    }

    @Override
    public void preprocess(Context context) {
        this.value.preprocess(context);
        if (this.type == null) {
            this.type = this.value.type(context);
        }

        context.scope().defVar(name, type);
    }
}
