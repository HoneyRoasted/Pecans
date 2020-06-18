package honeyroasted.pecans.node.instruction.variable;

import honeyroasted.pecans.node.Context;
import honeyroasted.pecans.node.instruction.TypedNode;
import honeyroasted.pecans.type.type.TypeInformal;
import org.objectweb.asm.commons.InstructionAdapter;

public class GetField implements TypedNode {
    private TypedNode target;
    private String name;
    private TypeInformal type;

    public GetField(TypedNode target, String name, TypeInformal type) {
        this.target = target;
        this.name = name;
        this.type = type;
    }

    @Override
    public TypeInformal type(Context context) {
        return this.type;
    }

    @Override
    public void accept(InstructionAdapter adapter, Context context) {
        this.target.accept(adapter, context);
        adapter.getfield(this.target.type(context).writeInternalName(), this.name, this.type.writeDesc());
    }

    @Override
    public void preprocess(Context context) {
        this.target.preprocess(context);
    }
}
