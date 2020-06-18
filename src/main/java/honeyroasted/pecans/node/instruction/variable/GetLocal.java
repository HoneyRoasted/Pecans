package honeyroasted.pecans.node.instruction.variable;

import honeyroasted.pecans.node.Context;
import honeyroasted.pecans.node.instruction.TypedNode;
import honeyroasted.pecans.type.type.TypeInformal;
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
    public TypeInformal type(Context context) {
        return this.type;
    }

    @Override
    public void accept(InstructionAdapter adapter, Context context) {
        adapter.load(index, Type.getType(this.type.writeDesc()));
    }

    @Override
    public void preprocess(Context context) {

    }

}
