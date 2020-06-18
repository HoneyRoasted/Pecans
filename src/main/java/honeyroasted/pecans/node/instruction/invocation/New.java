package honeyroasted.pecans.node.instruction.invocation;

import honeyroasted.pecans.node.Context;
import honeyroasted.pecans.node.instruction.TypedNode;
import honeyroasted.pecans.type.MethodSignature;
import honeyroasted.pecans.type.type.TypeInformal;
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
    public TypeInformal type(Context context) {
        return this.type;
    }

    @Override
    public void accept(InstructionAdapter adapter, Context context) {
        adapter.anew(Type.getType(type.writeDesc()));
        adapter.dup();

        this.params.forEach(t -> t.accept(adapter, context));

        adapter.invokespecial(this.type.writeInternalName(), "<init>", this.signature.writeDesc(), false);
    }

    @Override
    public void preprocess(Context context) {
        this.params.forEach(n -> n.preprocess(context));
    }

}
