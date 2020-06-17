package honeyroasted.pecans.ast.instruction;

import honeyroasted.pecans.signature.Types;
import honeyroasted.pecans.signature.type.TypeInformal;
import honeyroasted.pecans.signature.type.TypeParameterized;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.InstructionAdapter;

public class This implements TypedNode, Opcodes {
    private TypeParameterized type;

    public This(TypeParameterized type) {
        this.type = type;
    }

    @Override
    public TypeInformal type() {
        return Types.type(type.getType());
    }

    @Override
    public void accept(InstructionAdapter adapter) {
        adapter.visitVarInsn(ALOAD, 0);
    }
}
