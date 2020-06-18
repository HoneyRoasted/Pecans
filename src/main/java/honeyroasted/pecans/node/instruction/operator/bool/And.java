package honeyroasted.pecans.node.instruction.operator.bool;

import honeyroasted.pecans.node.Context;
import honeyroasted.pecans.node.Nodes;
import honeyroasted.pecans.node.instruction.TypedNode;
import org.objectweb.asm.Label;
import org.objectweb.asm.commons.InstructionAdapter;

public class And implements BooleanOperator {
    private TypedNode left;
    private TypedNode right;

    public And(TypedNode left, TypedNode right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public void acceptFalseJump(InstructionAdapter adapter, Context context, Label ifFalse) {
        if (this.left instanceof BooleanOperator) {
            ((BooleanOperator) this.left).acceptFalseJump(adapter, context, ifFalse);
        } else {
            this.left.accept(adapter, context);
            adapter.ifeq(ifFalse);
        }
    }

    @Override
    public void acceptTrueJump(InstructionAdapter adapter, Context context, Label ifTrue) {
        Label a = new Label();

        acceptFalseJump(adapter, context, a);

        adapter.goTo(ifTrue);
        adapter.mark(a);
    }

    @Override
    public void acceptJump(InstructionAdapter adapter, Context context, Label ifTrue, Label ifFalse) {
        acceptFalseJump(adapter, context, ifFalse);
        adapter.goTo(ifTrue);
    }

    @Override
    public void accept(InstructionAdapter adapter, Context context) {
        Label a = new Label();
        Label b = new Label();

        acceptFalseJump(adapter, context, a);

        Nodes.constant(true).accept(adapter, context);
        adapter.goTo(b);
        adapter.mark(a);
        Nodes.constant(false).accept(adapter, context);
        adapter.mark(b);
    }

    @Override
    public void preprocess(Context context) {
        this.left.preprocess(context);
        this.right.preprocess(context);
    }

}
