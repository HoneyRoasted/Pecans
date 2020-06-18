package honeyroasted.pecans.node.instruction.util;

import honeyroasted.pecans.node.Context;
import honeyroasted.pecans.node.instruction.TypedNode;
import honeyroasted.pecans.type.type.TypeInformal;
import org.objectweb.asm.commons.InstructionAdapter;

public class SkipPreprocTypedNode implements TypedNode {
    private TypedNode node;

    public SkipPreprocTypedNode(TypedNode node) {
        this.node = node;
    }

    @Override
    public TypeInformal type(Context context) {
        return this.node.type(context);
    }

    @Override
    public void accept(InstructionAdapter adapter, Context context) {
        this.node.accept(adapter, context);
    }

    @Override
    public void preprocess(Context context) {

    }
}
