package honeyroasted.pecans.ast.instruction;

import org.objectweb.asm.Type;
import org.objectweb.asm.commons.InstructionAdapter;

import static org.objectweb.asm.Opcodes.*;

public class Return implements Node {
    private TypedNode value;

    public Return(TypedNode value) {
        this.value = value;
    }

    public static Return of(TypedNode value) {
        return new Return(value);
    }

    @Override
    public void accept(InstructionAdapter adapter) {
        if (this.value == null) {
            adapter.visitInsn(RETURN);
        } else {
            this.value.accept(adapter);
            adapter.areturn(Type.getType(this.value.type().writeDesc()));
        }
    }
}
