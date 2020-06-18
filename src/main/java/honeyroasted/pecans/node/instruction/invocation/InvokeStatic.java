package honeyroasted.pecans.node.instruction.invocation;

import honeyroasted.pecans.node.Context;
import honeyroasted.pecans.node.instruction.TypedNode;
import honeyroasted.pecans.type.MethodSignature;
import honeyroasted.pecans.type.type.TypeFill;
import honeyroasted.pecans.type.type.TypeInformal;
import org.objectweb.asm.commons.InstructionAdapter;

import java.util.Collection;
import java.util.Collections;

public class InvokeStatic implements TypedNode {
    private TypeFill target;
    private Collection<TypedNode> params;

    private String name;
    private MethodSignature signature;
    private boolean isInterface;

    public InvokeStatic(TypeFill target, Collection<TypedNode> params, String name, MethodSignature signature, boolean isInterface) {
        this.target = target;
        this.params = params;
        this.name = name;
        this.signature = signature;
        this.isInterface = isInterface;
    }

    public InvokeStatic arg(TypedNode param) {
        this.params.add(param);
        return this;
    }

    public InvokeStatic args(TypedNode... params) {
        Collections.addAll(this.params, params);
        return this;
    }

    @Override
    public TypeInformal type(Context context) {
        return this.signature.getReturn();
    }

    @Override
    public void accept(InstructionAdapter adapter, Context context) {
        this.params.forEach(t -> t.accept(adapter, context));
        adapter.invokestatic(this.target.writeInternalName(), this.name, this.signature.writeDesc(), this.isInterface);
    }

    @Override
    public void preprocess(Context context) {
        this.params.forEach(n -> n.preprocess(context));
    }

}
