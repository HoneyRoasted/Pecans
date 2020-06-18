package honeyroasted.pecans.node.instruction;

import honeyroasted.pecans.node.Context;
import honeyroasted.pecans.type.type.TypeInformal;
import org.objectweb.asm.commons.InstructionAdapter;

public interface Node {

    void accept(InstructionAdapter adapter, Context context);

    void preprocess(Context context);

    default TypedNode type(TypeInformal type) {
        return new DelegateTypedNode(type, this);
    }

}
