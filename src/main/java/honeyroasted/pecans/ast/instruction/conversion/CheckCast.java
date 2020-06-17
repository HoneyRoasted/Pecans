package honeyroasted.pecans.ast.instruction.conversion;

import honeyroasted.pecans.ast.instruction.TypedNode;
import honeyroasted.pecans.signature.type.TypeInformal;
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
    public TypeInformal type() {
        return this.type;
    }

    @Override
    public void accept(InstructionAdapter adapter) {
        this.value.accept(adapter);
        adapter.visitTypeInsn(Opcodes.CHECKCAST, this.type.writeInternalName());
    }
}
