package io.github.honeyroasted.pecans.ast.instruction.variable;

import io.github.honeyroasted.pecans.ast.instruction.Node;
import io.github.honeyroasted.pecans.ast.instruction.TypedNode;
import io.github.honeyroasted.pecans.signature.type.TypeFill;
import org.objectweb.asm.commons.InstructionAdapter;

public class PutStatic implements Node {
    private TypeFill target;
    private String name;
    private TypedNode value;

    public PutStatic(TypeFill target, String name, TypedNode value) {
        this.target = target;
        this.name = name;
        this.value = value;
    }

    @Override
    public void accept(InstructionAdapter adapter) {
        this.value.accept(adapter);
        adapter.putstatic(target.writeInternalName(), name, value.type().writeDesc());
    }
}
