package honeyroasted.pecans.ast.instruction.operator;

import honeyroasted.pecans.ast.instruction.TypedNode;
import honeyroasted.pecans.signature.Types;
import honeyroasted.pecans.signature.type.TypeInformal;
import org.objectweb.asm.Label;
import org.objectweb.asm.commons.InstructionAdapter;

public interface BooleanOperator extends TypedNode {

    void acceptTrueJump(InstructionAdapter adapter, Label ifTrue);

    void acceptFalseJump(InstructionAdapter adapter, Label ifFalse);

    void acceptJump(InstructionAdapter adapter, Label ifTrue, Label ifFalse);

    @Override
    default TypeInformal type() {
        return Types.BOOLEAN;
    }

}
