package honeyroasted.pecans.node.instruction;

import honeyroasted.pecans.node.Context;
import honeyroasted.pecans.type.type.TypeInformal;
import org.objectweb.asm.commons.InstructionAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class Composition implements TypedNode {
    private Collection<Node> sequence;
    private TypedNode result;

    public Composition(Collection<Node> sequence, TypedNode result) {
        this.sequence = sequence;
        this.result = result;
    }

    public static Composition of(TypedNode result, Node... nodes) {
        return new Composition(new ArrayList<>(Arrays.asList(nodes)), result);
    }

    public static Composition of(TypedNode result, Collection<Node> sequence) {
        return new Composition(sequence, result);
    }

    public Composition add(Node node) {
        this.sequence.add(node);
        return this;
    }

    public Composition setResult(TypedNode node) {
        this.result = node;
        return this;
    }

    public Composition addResult(TypedNode node) {
        add(this.result);
        setResult(node);
        return this;
    }

    @Override
    public TypeInformal type(Context context) {
        return this.result.type(context);
    }

    @Override
    public void accept(InstructionAdapter adapter, Context context) {
        this.sequence.forEach(n -> n.accept(adapter, context));
        this.result.accept(adapter, context);
    }

    @Override
    public void preprocess(Context context) {
        this.sequence.forEach(n -> n.preprocess(context));
        this.result.preprocess(context);
    }
}
