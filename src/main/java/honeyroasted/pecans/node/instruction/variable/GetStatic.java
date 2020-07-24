package honeyroasted.pecans.node.instruction.variable;

import honeyroasted.pecans.node.Context;
import honeyroasted.pecans.node.instruction.TypedNode;
import honeyroasted.pecans.type.type.TypeFill;
import honeyroasted.pecans.type.type.TypeInformal;
import org.objectweb.asm.commons.InstructionAdapter;

public class GetStatic implements TypedNode {
    private TypeInformal target;
    private String name;
    private TypeInformal type;

    public GetStatic(TypeInformal target, String name, TypeInformal type) {
        this.target = target;
        this.name = name;
        this.type = type;
    }

    @Override
    public TypeInformal type(Context context) {
        return this.type;
    }

    @Override
    public void accept(InstructionAdapter adapter, Context context) {
        adapter.getstatic(this.target.writeInternalName(), this.name, this.type.writeDesc());
    }

    @Override
    public void preprocess(Context context) {

    }
}
