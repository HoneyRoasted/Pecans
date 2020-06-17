package honeyroasted.pecans.ast.instruction.variable;

import honeyroasted.pecans.ast.instruction.TypedNode;
import honeyroasted.pecans.signature.type.TypeInformal;
import org.objectweb.asm.commons.InstructionAdapter;

public class GetField implements TypedNode {
    private TypedNode target;
    private String name;
    private TypeInformal type;

    public GetField(TypedNode target, String name, TypeInformal type) {
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
        this.target.accept(adapter);
        adapter.getfield(this.target.type().writeInternalName(), this.name, this.type.writeDesc());
    }
}
