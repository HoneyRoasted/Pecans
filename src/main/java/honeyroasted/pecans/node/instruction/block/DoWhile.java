package honeyroasted.pecans.node.instruction.block;

import honeyroasted.pecans.node.Context;
import honeyroasted.pecans.node.instruction.Node;
import honeyroasted.pecans.node.instruction.TypedNode;
import org.objectweb.asm.Label;
import org.objectweb.asm.commons.InstructionAdapter;

public class DoWhile implements Node {
    private TypedNode condition;
    private Node body;

    public DoWhile(TypedNode condition, Node body) {
        this.condition = condition;
        this.body = body;
    }

    @Override
    public void accept(InstructionAdapter adapter, Context context) {
        Label start = new Label();
        adapter.mark(start);
        this.body.accept(adapter, context);
        this.condition.accept(adapter, context);
        adapter.ifne(start);
    }

    @Override
    public void preprocess(Context context) {
        this.body.preprocess(context);
        this.condition.preprocess(context);
    }
}
