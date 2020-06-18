package honeyroasted.pecans.node.instruction.invocation;

import honeyroasted.pecans.node.Context;
import honeyroasted.pecans.type.MethodSignature;
import honeyroasted.pecans.type.type.TypeFill;
import honeyroasted.pecans.type.type.TypeInformal;
import honeyroasted.pecans.node.instruction.TypedNode;
import org.objectweb.asm.commons.InstructionAdapter;

import java.util.Collection;
import java.util.Collections;

public class InvokeSpecial implements TypedNode {
    private TypeFill owner;
    private TypedNode target;
    private Collection<TypedNode> params;

    private String name;
    private MethodSignature signature;
    private boolean isInterface;

    public InvokeSpecial(TypeFill owner, TypedNode target, Collection<TypedNode> params, String name, MethodSignature signature, boolean isInterface) {
        this.owner = owner;
        this.target = target;
        this.params = params;
        this.name = name;
        this.signature = signature;
        this.isInterface = isInterface;
    }

    public InvokeSpecial arg(TypedNode param) {
        this.params.add(param);
        return this;
    }

    public InvokeSpecial args(TypedNode... params) {
        Collections.addAll(this.params, params);
        return this;
    }

    @Override
    public TypeInformal type(Context context) {
        return this.signature.getReturn();
    }

    @Override
    public void accept(InstructionAdapter adapter, Context context) {
        this.target.accept(adapter, context);
        this.params.forEach(t -> t.accept(adapter, context));

        adapter.invokespecial(this.owner.writeInternalName(), this.name, this.signature.writeDesc(), this.isInterface);
    }

    @Override
    public void preprocess(Context context) {
        this.target.preprocess(context);
        this.params.forEach(n -> n.preprocess(context));
    }
}
