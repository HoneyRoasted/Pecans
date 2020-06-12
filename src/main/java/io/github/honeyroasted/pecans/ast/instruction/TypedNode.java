package io.github.honeyroasted.pecans.ast.instruction;

import io.github.honeyroasted.pecans.signature.type.TypeInformal;

public interface TypedNode extends Node {

    TypeInformal type();

}
