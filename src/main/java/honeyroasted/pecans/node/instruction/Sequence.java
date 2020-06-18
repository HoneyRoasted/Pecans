package honeyroasted.pecans.node.instruction;

import honeyroasted.pecans.node.Context;
import org.objectweb.asm.commons.InstructionAdapter;

import java.util.Arrays;
import java.util.Collection;

public class Sequence implements Node {
    private Collection<Node> sequence;

    public Sequence(Collection<Node> sequence) {
        this.sequence = sequence;
    }

    public static Sequence of(Node... nodes) {
        return new Sequence(Arrays.asList(nodes));
    }

    public static Sequence of(Collection<Node> nodes) {
        return new Sequence(nodes);
    }

    public Sequence add(Node node) {
        this.sequence.add(node);
        return this;
    }

    @Override
    public void accept(InstructionAdapter adapter, Context context) {
        sequence.forEach(n -> n.accept(adapter, context));
    }

    @Override
    public void preprocess(Context context) {
        this.sequence.forEach(n -> n.preprocess(context));
    }

}
