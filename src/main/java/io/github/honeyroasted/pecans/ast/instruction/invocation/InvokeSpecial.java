package io.github.honeyroasted.pecans.ast.instruction.invocation;

import io.github.honeyroasted.pecans.ast.instruction.TypedNode;
import io.github.honeyroasted.pecans.signature.MethodSignature;
import io.github.honeyroasted.pecans.signature.type.TypeFill;
import io.github.honeyroasted.pecans.signature.type.TypeInformal;
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
    public TypeInformal type() {
        return this.signature.getReturn();
    }

    @Override
    public void accept(InstructionAdapter adapter) {
        this.target.accept(adapter);
        this.params.forEach(t -> t.accept(adapter));

        adapter.invokespecial(this.owner.writeInternalName(), this.name, this.signature.writeDesc(), this.isInterface);
    }
}
