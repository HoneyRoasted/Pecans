package honeyroasted.pecans.node.instruction.operator;

import honeyroasted.pecans.node.Context;
import honeyroasted.pecans.node.instruction.TypedNode;
import honeyroasted.pecans.type.Types;
import honeyroasted.pecans.type.type.TypeInformal;
import org.objectweb.asm.commons.InstructionAdapter;

import static org.objectweb.asm.Opcodes.*;

public class LeftShift implements TypedNode {
    private TypedNode left;
    private TypedNode right;

    public LeftShift(TypedNode left, TypedNode right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public TypeInformal type(Context context) {
        return this.left.type(context);
    }

    @Override
    public void accept(InstructionAdapter adapter, Context context) {
        this.left.accept(adapter, context);
        this.right.accept(adapter, context);

        if (this.left.type(context).equals(Types.LONG)) {
            adapter.visitInsn(LSHL);
        } else {
            adapter.visitInsn(ISHL);
        }
    }

    @Override
    public void preprocess(Context context) {
        this.left.preprocess(context);
        this.right.preprocess(context);
    }
}
