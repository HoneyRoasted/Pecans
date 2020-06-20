package honeyroasted.pecans.node.instruction.block;

import honeyroasted.pecans.node.Context;
import honeyroasted.pecans.node.instruction.Node;
import honeyroasted.pecans.node.instruction.TypedNode;
import org.objectweb.asm.Label;
import org.objectweb.asm.commons.InstructionAdapter;

public class For implements Node {
    private String name;
    private Node begin;
    private TypedNode condition;
    private Node action;

    private Node body;

    public For(String name, Node begin, TypedNode condition, Node action, Node body) {
        this.name = name;
        this.begin = begin;
        this.condition = condition;
        this.action = action;
        this.body = body;
    }

    @Override
    public void accept(InstructionAdapter adapter, Context context) {
        Label start = new Label();
        Label end = new Label();

        context = context.withBreak(end).withContinue(end);
        context.setName(this.name);

        if (this.begin != null) {
            this.begin.accept(adapter, context);
        }
        adapter.mark(start);

        if (this.condition != null) {
            this.condition.accept(adapter, context);
            adapter.ifeq(end);
        }

        this.body.accept(adapter, context);

        if (this.action != null) {
            this.action.accept(adapter, context);
        }

        adapter.goTo(start);

        adapter.mark(end);
    }

    @Override
    public void preprocess(Context context) {
        this.begin.preprocess(context);
        this.condition.preprocess(context);
        this.body.preprocess(context);
        this.action.preprocess(context);
    }

}
