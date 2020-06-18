package honeyroasted.pecans.node.instruction.util;

import honeyroasted.pecans.node.Context;
import honeyroasted.pecans.node.instruction.TypedNode;
import honeyroasted.pecans.type.type.TypeInformal;
import org.objectweb.asm.commons.InstructionAdapter;

import java.util.function.Function;

public class LazyTypedNode implements TypedNode {
    private Function<Context, TypedNode> nodeFunction;
    private TypedNode node;

    public LazyTypedNode(Function<Context, TypedNode> nodeFunction) {
        this.nodeFunction = nodeFunction;
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
        this.node = this.nodeFunction.apply(context);
        this.node.preprocess(context);
    }
}
