package honeyroasted.pecans.node.instruction.conversion;

import honeyroasted.pecans.node.Context;
import honeyroasted.pecans.node.instruction.TypedNode;
import honeyroasted.pecans.type.type.TypeInformal;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.InstructionAdapter;

public class CheckCast implements TypedNode {
    private TypeInformal type;
    private TypedNode value;

    public CheckCast(TypeInformal type, TypedNode value) {
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
        adapter.visitTypeInsn(Opcodes.CHECKCAST, this.type.writeInternalName());
    }

    @Override
    public void preprocess(Context context) {
        this.value.preprocess(context);
    }
}
