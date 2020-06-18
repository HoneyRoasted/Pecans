package honeyroasted.pecans.node.instruction.block;

import honeyroasted.pecans.node.Context;
import honeyroasted.pecans.node.instruction.Node;
import honeyroasted.pecans.node.instruction.TypedNode;
import org.objectweb.asm.Label;
import org.objectweb.asm.commons.InstructionAdapter;

public class While implements Node {
    private TypedNode condition;
    private Node body;

    public While(TypedNode condition, Node body) {
        this.condition = condition;
        this.body = body;
    }

    @Override
    public void accept(InstructionAdapter adapter, Context context) {
        Label start = new Label();
        Label end = new Label();

        adapter.mark(start);
        this.condition.accept(adapter, context);
        adapter.ifeq(end);
        this.body.accept(adapter, context);
        adapter.goTo(start);
        adapter.mark(end);
    }

    @Override
    public void preprocess(Context context) {
        this.condition.preprocess(context);
        this.body.preprocess(context);
    }
}
