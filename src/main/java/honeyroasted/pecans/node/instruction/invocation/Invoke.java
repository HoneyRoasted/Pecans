package honeyroasted.pecans.node.instruction.invocation;

import honeyroasted.pecans.node.instruction.TypedNode;

public interface Invoke extends TypedNode {

    Invoke arg(TypedNode param);

    default Invoke args(TypedNode... params) {
        for (TypedNode param : params) {
            arg(param);
        }
        return this;
    }
}
