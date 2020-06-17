package honeyroasted.pecans.ast.instruction.variable;

import honeyroasted.pecans.ast.instruction.Node;
import honeyroasted.pecans.ast.instruction.TypedNode;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.InstructionAdapter;

public class PutLocal implements Node {
    private int index;
    private TypedNode value;

    public PutLocal(int index, TypedNode value) {
        this.index = index;
        this.value = value;
    }

    @Override
    public void accept(InstructionAdapter adapter) {
        this.value.accept(adapter);
        adapter.store(this.index, Type.getType(this.value.type().writeDesc()));
    }
}
