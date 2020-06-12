package io.github.honeyroasted.pecans.ast.instruction;

import io.github.honeyroasted.pecans.signature.type.TypeInformal;
import org.objectweb.asm.commons.InstructionAdapter;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnList;

public interface Node {

    void accept(InstructionAdapter adapter);

    default TypedNode type(TypeInformal type) {
        return new DelegateTypedNode(type, this);
    }

    static Node of(AbstractInsnNode node) {
        return node::accept;
    }

    static Node of(InsnList list) {
        return list::accept;
    }

}
