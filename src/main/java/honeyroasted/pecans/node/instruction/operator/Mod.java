package honeyroasted.pecans.node.instruction.operator;

import honeyroasted.pecans.node.Context;
import honeyroasted.pecans.node.instruction.TypedNode;
import honeyroasted.pecans.type.Types;
import honeyroasted.pecans.type.type.TypeInformal;
import org.objectweb.asm.commons.InstructionAdapter;

import static org.objectweb.asm.Opcodes.*;

public class Mod extends BinaryOperator {

    public Mod(TypedNode left, TypedNode right) {
        super(left, right);
    }

    @Override
    public TypeInformal type(Context context) {
        return this.wide;
    }

    @Override
    public void accept(InstructionAdapter adapter, Context context) {
        this.left.accept(adapter, context);
        this.right.accept(adapter, context);

        if (this.wide.equals(Types.DOUBLE)) {
            adapter.visitInsn(DREM);
        } else if (this.wide.equals(Types.LONG)) {
            adapter.visitInsn(LREM);
        } else if (this.wide.equals(Types.FLOAT)) {
            adapter.visitInsn(FREM);
        } else {
            adapter.visitInsn(IREM);
        }
    }

}