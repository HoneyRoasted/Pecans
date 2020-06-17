package honeyroasted.pecans.ast.instruction.variable;

import honeyroasted.pecans.ast.instruction.TypedNode;
import honeyroasted.pecans.signature.type.TypeInformal;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.InstructionAdapter;

public class GetLocal implements TypedNode {
    private int index;
    private TypeInformal type;

    public GetLocal(int index, TypeInformal type) {
        this.index = index;
        this.type = type;
    }

    @Override
    public TypeInformal type() {
        return this.type;
    }

    @Override
    public void accept(InstructionAdapter adapter) {
        adapter.load(index, Type.getType(this.type.writeDesc()));
    }

}
