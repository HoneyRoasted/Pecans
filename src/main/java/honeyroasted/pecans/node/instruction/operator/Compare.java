package honeyroasted.pecans.node.instruction.operator;

import honeyroasted.pecans.node.Context;
import honeyroasted.pecans.node.Nodes;
import honeyroasted.pecans.node.instruction.TypedNode;
import honeyroasted.pecans.node.instruction.util.SkipPreprocTypedNode;
import honeyroasted.pecans.type.Types;
import honeyroasted.pecans.type.type.TypeInformal;
import org.objectweb.asm.Label;
import org.objectweb.asm.commons.InstructionAdapter;

import static honeyroasted.pecans.type.Types.*;

public class Compare implements BooleanOperator {
    private ComparisonOperator operator;
    private TypedNode left;
    private TypedNode right;

    public Compare(ComparisonOperator operator, TypedNode left, TypedNode right) {
        this.operator = operator;
        this.left = left;
        this.right = right;
    }

    @Override
    public void acceptTrueJump(InstructionAdapter adapter, Context context, Label ifTrue) {
        TypeInformal wide = this.left.type(context);

        boolean compare = wide.equals(Types.DOUBLE) ||
                wide.equals(Types.FLOAT) ||
                wide.equals(Types.LONG);

        this.left.accept(adapter, context);
        this.right.accept(adapter, context);

        if (compare) {
            if (wide.equals(Types.DOUBLE)) {
                this.operator.visitDoubleCompare(adapter);
            } else if (wide.equals(Types.FLOAT)) {
                this.operator.visitFloatCompare(adapter);
            } else if (wide.equals(Types.LONG)) {
                adapter.visitInsn(LCMP);
            }

            this.operator.visitCompareJump(adapter, ifTrue);
        } else {
            this.operator.visitIntCompare(adapter, ifTrue);
        }
    }

    @Override
    public void acceptFalseJump(InstructionAdapter adapter, Context context, Label ifFalse) {
        TypeInformal wide = this.left.type(context);

        boolean compare = wide.equals(Types.DOUBLE) ||
                wide.equals(Types.FLOAT) ||
                wide.equals(Types.LONG);

        this.left.accept(adapter, context);
        this.right.accept(adapter, context);

        if (compare) {
            if (wide.equals(Types.DOUBLE)) {
                this.operator.visitDoubleInverseCompare(adapter);
            } else if (wide.equals(Types.FLOAT)) {
                this.operator.visitFloatInverseCompare(adapter);
            } else if (wide.equals(Types.LONG)) {
                adapter.visitInsn(LCMP);
            }

            this.operator.visitInverseCompareJump(adapter, ifFalse);
        } else {
            this.operator.visitInverseIntCompare(adapter, ifFalse);
        }
    }

    @Override
    public void acceptJump(InstructionAdapter adapter, Context context, Label ifTrue, Label ifFalse) {
        TypeInformal wide = this.left.type(context);

        boolean compare = wide.equals(Types.DOUBLE) ||
                wide.equals(Types.FLOAT) ||
                wide.equals(Types.LONG);

        this.left.accept(adapter, context);
        this.right.accept(adapter, context);

        if (compare) {
            if (wide.equals(Types.DOUBLE)) {
                this.operator.visitDoubleCompare(adapter);
            } else if (wide.equals(Types.FLOAT)) {
                this.operator.visitFloatCompare(adapter);
            } else if (wide.equals(Types.LONG)) {
                adapter.visitInsn(LCMP);
            }

            this.operator.visitCompareJump(adapter, ifTrue);
            adapter.goTo(ifFalse);
        } else {
            this.operator.visitIntCompare(adapter, ifTrue);
            adapter.goTo(ifFalse);
        }
    }

    @Override
    public void accept(InstructionAdapter adapter, Context context) {
        TypeInformal wide = this.left.type(context);

        boolean compare = wide.equals(Types.DOUBLE) ||
                wide.equals(Types.FLOAT) ||
                wide.equals(Types.LONG);

        this.left.accept(adapter, context);
        this.right.accept(adapter, context);

        if (compare) {
            if (wide.equals(Types.DOUBLE)) {
                this.operator.visitDoubleInverseCompare(adapter);
            } else if (wide.equals(Types.FLOAT)) {
                this.operator.visitFloatInverseCompare(adapter);
            } else if (wide.equals(Types.LONG)) {
                adapter.visitInsn(LCMP);
            }

            Label a = new Label();
            Label b = new Label();
            this.operator.visitInverseCompareJump(adapter, a);
            Nodes.constant(true).accept(adapter, context);
            adapter.goTo(b);
            adapter.mark(a);
            Nodes.constant(false).accept(adapter, context);
            adapter.mark(b);
        } else {
            Label a = new Label();
            Label b = new Label();
            this.operator.visitInverseIntCompare(adapter, a);
            Nodes.constant(true).accept(adapter, context);
            adapter.goTo(b);
            adapter.mark(a);
            Nodes.constant(false).accept(adapter, context);
            adapter.mark(b);
        }
    }

    @Override
    public void preprocess(Context context) {
        this.left.preprocess(context);
        this.right.preprocess(context);

        TypeInformal wide = Types.widest(left.type(context), right.type(context));
        this.left = Nodes.convert(wide, new SkipPreprocTypedNode(this.left));
        this.right = Nodes.convert(wide, new SkipPreprocTypedNode(this.right));

        this.left.preprocess(context);
        this.right.preprocess(context);
    }
}
