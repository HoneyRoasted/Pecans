package honeyroasted.pecans.node.instruction.operator.bool;

import honeyroasted.pecans.node.Context;
import honeyroasted.pecans.node.Nodes;
import honeyroasted.pecans.node.instruction.TypedNode;
import org.objectweb.asm.Label;
import org.objectweb.asm.commons.InstructionAdapter;

public class EqualNull implements BooleanOperator {
    private TypedNode val;

    public EqualNull(TypedNode val) {
        this.val = val;
    }

    @Override
    public void acceptTrueJump(InstructionAdapter adapter, Context context, Label ifTrue) {
        this.val.accept(adapter, context);
        adapter.ifnull(ifTrue);
    }

    @Override
    public void acceptFalseJump(InstructionAdapter adapter, Context context, Label ifFalse) {
        this.val.accept(adapter, context);
        adapter.ifnonnull(ifFalse);
    }

    @Override
    public void acceptJump(InstructionAdapter adapter, Context context, Label ifTrue, Label ifFalse) {
        this.val.accept(adapter, context);
        adapter.ifnull(ifTrue);
        adapter.goTo(ifFalse);
    }

    @Override
    public void accept(InstructionAdapter adapter, Context context) {
        this.val.accept(adapter, context);

        Label a = new Label();
        Label b = new Label();
        adapter.ifnonnull(a);
        Nodes.constant(true).accept(adapter, context);
        adapter.goTo(b);
        adapter.mark(a);
        Nodes.constant(false).accept(adapter, context);
        adapter.mark(b);
    }

    @Override
    public void preprocess(Context context) {
        this.val.preprocess(context);
    }

}
