package honeyroasted.pecans.node.instruction.operator.bool;

import honeyroasted.pecans.node.Context;
import honeyroasted.pecans.node.instruction.TypedNode;
import honeyroasted.pecans.type.Types;
import honeyroasted.pecans.type.type.TypeInformal;
import org.objectweb.asm.Label;
import org.objectweb.asm.commons.InstructionAdapter;

public interface BooleanOperator extends TypedNode {

    void acceptTrueJump(InstructionAdapter adapter, Context context, Label ifTrue);

    void acceptFalseJump(InstructionAdapter adapter, Context context, Label ifFalse);

    void acceptJump(InstructionAdapter adapter, Context context, Label ifTrue, Label ifFalse);

    @Override
    default TypeInformal type(Context context) {
        return Types.BOOLEAN;
    }

}
