package io.github.honeyroasted.pecans.ast.instruction;

import io.github.honeyroasted.pecans.signature.Types;
import io.github.honeyroasted.pecans.signature.type.TypeInformal;
import io.github.honeyroasted.pecans.signature.type.TypeParameterized;
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
