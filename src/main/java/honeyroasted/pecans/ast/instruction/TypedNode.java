package honeyroasted.pecans.ast.instruction;

import honeyroasted.pecans.signature.Types;
import honeyroasted.pecans.signature.type.TypeInformal;

public interface TypedNode extends Node {

    TypeInformal type();

    default Node pop() {
        return adapter -> {
            accept(adapter);

            if (type().equals(Types.LONG) || type().equals(Types.DOUBLE)) {
                adapter.pop2();
            } else {
                adapter.pop();
            }
        };
    }

}
