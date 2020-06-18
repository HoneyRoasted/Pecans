package honeyroasted.pecans.node.instruction.operator;

import honeyroasted.pecans.node.Context;
import honeyroasted.pecans.node.instruction.TypedNode;
import honeyroasted.pecans.node.instruction.operator.bool.BooleanOperator;
import honeyroasted.pecans.type.type.TypeInformal;
import org.objectweb.asm.Label;
import org.objectweb.asm.commons.InstructionAdapter;

public class Ternary implements TypedNode {
    private TypedNode cond;
    private TypedNode left;
    private TypedNode right;

    public Ternary(TypedNode cond, TypedNode left, TypedNode right) {
        this.cond = cond;
        this.left = left;
        this.right = right;
    }

    @Override
    public TypeInformal type(Context context) {
        return this.left.type(context);
    }

    @Override
    public void accept(InstructionAdapter adapter, Context context) {
        if (this.cond instanceof BooleanOperator) {
            BooleanOperator op = (BooleanOperator) this.cond;

            Label a = new Label();
            Label b = new Label();

            op.acceptFalseJump(adapter, context, a);
            this.left.accept(adapter, context);
            adapter.goTo(b);
            adapter.mark(a);
            this.right.accept(adapter, context);
            adapter.mark(b);

        }
    }

    @Override
    public void preprocess(Context context) {
        this.cond.preprocess(context);
        this.left.preprocess(context);
        this.right.preprocess(context);
    }
}
