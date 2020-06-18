package honeyroasted.pecans.node.instruction.operator.bool;

import honeyroasted.pecans.node.Context;
import honeyroasted.pecans.node.Nodes;
import honeyroasted.pecans.node.instruction.TypedNode;
import org.objectweb.asm.Label;
import org.objectweb.asm.commons.InstructionAdapter;

public class Or implements BooleanOperator {
    private TypedNode left;
    private TypedNode right;

    public Or(TypedNode left, TypedNode right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public void acceptTrueJump(InstructionAdapter adapter, Context context, Label ifTrue) {
        if (this.left instanceof BooleanOperator) {
            ((BooleanOperator) this.left).acceptTrueJump(adapter, context, ifTrue);
        } else {
            this.left.accept(adapter, context);
            adapter.ifne(ifTrue);
        }

        if (this.right instanceof BooleanOperator) {
            ((BooleanOperator) this.right).acceptTrueJump(adapter, context, ifTrue);
        } else {
            this.right.accept(adapter, context);
            adapter.ifne(ifTrue);
        }
    }

    @Override
    public void acceptFalseJump(InstructionAdapter adapter, Context context, Label ifFalse) {
        Label a = new Label();

        acceptTrueJump(adapter, context, a);

        adapter.goTo(ifFalse);
        adapter.mark(a);
    }

    @Override
    public void acceptJump(InstructionAdapter adapter, Context context, Label ifTrue, Label ifFalse) {
        acceptTrueJump(adapter, context, ifTrue);
        adapter.goTo(ifFalse);
    }

    @Override
    public void accept(InstructionAdapter adapter, Context context) {
        Label a = new Label();
        Label b = new Label();

        acceptTrueJump(adapter, context, a);

        Nodes.constant(false).accept(adapter, context);
        adapter.goTo(b);
        adapter.mark(a);
        Nodes.constant(true).accept(adapter, context);
        adapter.mark(b);
    }

    @Override
    public void preprocess(Context context) {
        this.left.preprocess(context);
        this.right.preprocess(context);
    }

}
