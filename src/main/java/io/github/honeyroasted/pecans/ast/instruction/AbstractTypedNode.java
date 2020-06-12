package io.github.honeyroasted.pecans.ast.instruction;

import io.github.honeyroasted.pecans.signature.type.TypeInformal;

public abstract class AbstractTypedNode implements TypedNode {
    private TypeInformal type;

    public AbstractTypedNode(TypeInformal type) {
        this.type = type;
    }

    @Override
    public TypeInformal type() {
        return this.type;
    }
}
