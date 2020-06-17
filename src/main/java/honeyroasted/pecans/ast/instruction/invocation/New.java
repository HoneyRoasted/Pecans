package honeyroasted.pecans.ast.instruction.invocation;

import honeyroasted.pecans.ast.instruction.TypedNode;
import honeyroasted.pecans.signature.MethodSignature;
import honeyroasted.pecans.signature.type.TypeInformal;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.InstructionAdapter;

import java.util.Collection;
import java.util.Collections;

public class New implements TypedNode {
    private TypeInformal type;
    private Collection<TypedNode> params;

    private MethodSignature signature;

    public New(TypeInformal type, Collection<TypedNode> params, MethodSignature signature) {
        this.type = type;
        this.params = params;
        this.signature = signature;
    }

    public New arg(TypedNode param) {
        this.params.add(param);
        return this;
    }

    public New args(TypedNode... params) {
        Collections.addAll(this.params, params);
        return this;
    }

    @Override
    public TypeInformal type() {
        return this.type;
    }

    @Override
    public void accept(InstructionAdapter adapter) {
        adapter.anew(Type.getType(type.writeDesc()));
        adapter.dup();

        this.params.forEach(t -> t.accept(adapter));

        adapter.invokespecial(this.type.writeInternalName(), "<init>", this.signature.writeDesc(), false);
    }

}
