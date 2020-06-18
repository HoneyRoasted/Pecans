package honeyroasted.pecans.node.instruction.conversion;

import honeyroasted.pecans.node.Context;
import honeyroasted.pecans.node.instruction.TypedNode;
import honeyroasted.pecans.type.type.TypeInformal;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.InstructionAdapter;

public class PrimitiveCast implements TypedNode {
    private TypeInformal type;
    private TypedNode value;

    public PrimitiveCast(TypeInformal type, TypedNode value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public TypeInformal type(Context context) {
        return this.type;
    }

    @Override
    public void accept(InstructionAdapter adapter, Context context) {
        this.value.accept(adapter, context);
        adapter.cast(Type.getType(this.value.type(context).writeDesc()), Type.getType(this.type.writeDesc()));
    }

    @Override
    public void preprocess(Context context) {
        this.value.preprocess(context);
    }
}
