package honeyroasted.pecans.node.instruction.operator;

import honeyroasted.pecans.node.Context;
import honeyroasted.pecans.node.instruction.TypedNode;
import honeyroasted.pecans.type.Types;
import honeyroasted.pecans.type.type.TypeInformal;
import org.objectweb.asm.commons.InstructionAdapter;

import static org.objectweb.asm.Opcodes.*;

public class Negate implements TypedNode {
    private TypedNode val;

    public Negate(TypedNode val) {
        this.val = val;
    }

    @Override
    public TypeInformal type(Context context) {
        return this.val.type(context);
    }

    @Override
    public void accept(InstructionAdapter adapter, Context context) {
        TypeInformal type = this.type(context);

        this.val.accept(adapter, context);

        if (type.equals(Types.DOUBLE)) {
            adapter.visitInsn(DNEG);
        } else if (type.equals(Types.LONG)) {
            adapter.visitInsn(LNEG);
        } else if (type.equals(Types.FLOAT)) {
            adapter.visitInsn(FNEG);
        } else {
            adapter.visitInsn(INEG);
        }
    }

    @Override
    public void preprocess(Context context) {
        this.val.preprocess(context);
    }
}
