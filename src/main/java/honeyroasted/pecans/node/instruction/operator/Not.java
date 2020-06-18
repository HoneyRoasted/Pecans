package honeyroasted.pecans.node.instruction.operator;

import honeyroasted.pecans.node.Context;
import honeyroasted.pecans.node.Nodes;
import honeyroasted.pecans.node.instruction.TypedNode;
import org.objectweb.asm.Label;
import org.objectweb.asm.commons.InstructionAdapter;

public class Not implements BooleanOperator {
    private TypedNode val;

    public Not(TypedNode val) {
        this.val = val;
    }

    @Override
    public void acceptTrueJump(InstructionAdapter adapter, Context context, Label ifTrue) {
        if (this.val instanceof BooleanOperator) {
            BooleanOperator op = (BooleanOperator) this.val;
            op.acceptFalseJump(adapter, context, ifTrue);
        } else {
            this.val.accept(adapter, context);
            adapter.ifeq(ifTrue);
        }
    }

    @Override
    public void acceptFalseJump(InstructionAdapter adapter, Context context, Label ifFalse) {
        if (this.val instanceof BooleanOperator) {
            BooleanOperator op = (BooleanOperator) this.val;
            op.acceptTrueJump(adapter, context, ifFalse);
        } else {
            this.val.accept(adapter, context);
            adapter.ifne(ifFalse);
        }
    }

    @Override
    public void acceptJump(InstructionAdapter adapter, Context context, Label ifTrue, Label ifFalse) {
        if (this.val instanceof BooleanOperator) {
            BooleanOperator op = (BooleanOperator) this.val;
            op.acceptJump(adapter, context, ifFalse, ifTrue);
        } else {
            this.val.accept(adapter, context);
            adapter.ifeq(ifTrue);
            adapter.goTo(ifFalse);
        }
    }

    @Override
    public void accept(InstructionAdapter adapter, Context context) {
        if (this.val instanceof BooleanOperator) {
            BooleanOperator op = (BooleanOperator) this.val;

            Label a = new Label();
            Label b = new Label();
            op.acceptTrueJump(adapter, context, a);
            Nodes.constant(true).accept(adapter, context);
            adapter.goTo(b);
            adapter.mark(a);
            Nodes.constant(false).accept(adapter, context);
            adapter.mark(b);
        } else {
            Label a = new Label();
            Label b = new Label();
            this.val.accept(adapter, context);
            adapter.ifne(a);
            Nodes.constant(true).accept(adapter, context);
            adapter.goTo(b);
            adapter.mark(a);
            Nodes.constant(false).accept(adapter, context);
            adapter.mark(b);
        }
    }

    @Override
    public void preprocess(Context context) {
        this.val.preprocess(context);
    }
}
