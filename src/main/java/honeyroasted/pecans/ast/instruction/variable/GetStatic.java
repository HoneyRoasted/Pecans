package honeyroasted.pecans.ast.instruction.variable;

import honeyroasted.pecans.ast.instruction.TypedNode;
import honeyroasted.pecans.signature.type.TypeFill;
import honeyroasted.pecans.signature.type.TypeInformal;
import org.objectweb.asm.commons.InstructionAdapter;

public class GetStatic implements TypedNode {
    private TypeFill target;
    private String name;
    private TypeInformal type;

    public GetStatic(TypeFill target, String name, TypeInformal type) {
        this.target = target;
        this.name = name;
        this.type = type;
    }

    @Override
    public TypeInformal type() {
        return this.type;
    }

    @Override
    public void accept(InstructionAdapter adapter) {
        adapter.getstatic(this.target.writeInternalName(), this.name, this.type.writeDesc());
    }
}
