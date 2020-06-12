package io.github.honeyroasted.pecans.ast.instruction.variable;

import io.github.honeyroasted.pecans.ast.instruction.Node;
import io.github.honeyroasted.pecans.ast.instruction.TypedNode;
import org.objectweb.asm.commons.InstructionAdapter;

public class PutField implements Node {
    private TypedNode target;
    private String name;
    private TypedNode value;

    public PutField(TypedNode target, String name, TypedNode value) {
        this.target = target;
        this.name = name;
        this.value = value;
    }

    @Override
    public void accept(InstructionAdapter adapter) {
        this.target.accept(adapter);
        this.value.accept(adapter);
        adapter.putfield(this.target.type().writeInternalName(), this.name, this.value.type().writeDesc());
    }

}
