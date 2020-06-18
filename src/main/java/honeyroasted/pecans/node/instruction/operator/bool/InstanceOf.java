package honeyroasted.pecans.node.instruction.operator.bool;

import honeyroasted.pecans.node.Context;
import honeyroasted.pecans.node.instruction.TypedNode;
import honeyroasted.pecans.type.Types;
import honeyroasted.pecans.type.type.TypeInformal;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.InstructionAdapter;

public class InstanceOf implements TypedNode {
    private TypedNode val;
    private TypeInformal type;

    public InstanceOf(TypedNode val, TypeInformal type) {
        this.val = val;
        this.type = type;
    }

    @Override
    public TypeInformal type(Context context) {
        return Types.BOOLEAN;
    }

    @Override
    public void accept(InstructionAdapter adapter, Context context) {
        this.val.accept(adapter, context);
        adapter.visitTypeInsn(Opcodes.INSTANCEOF, this.type.writeInternalName());
    }

    @Override
    public void preprocess(Context context) {
        this.val.preprocess(context);
    }
}
