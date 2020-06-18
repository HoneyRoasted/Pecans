package honeyroasted.pecans.node.instruction.util;

import honeyroasted.pecans.node.Context;
import honeyroasted.pecans.node.instruction.Node;
import org.objectweb.asm.commons.InstructionAdapter;

import java.util.function.Function;

public class LazyNode implements Node {
    private Function<Context, Node> nodeFunction;
    private Node node;

    public LazyNode(Function<Context, Node> nodeFunction) {
        this.nodeFunction = nodeFunction;
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
