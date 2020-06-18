package honeyroasted.pecans.node.instruction;

import honeyroasted.pecans.node.Context;
import honeyroasted.pecans.type.type.TypeInformal;

public abstract class AbstractTypedNode implements TypedNode {
    private TypeInformal type;

    public AbstractTypedNode(TypeInformal type) {
        this.type = type;
    }

    @Override
    public TypeInformal type(Context context) {
        return this.type;
    }
}
