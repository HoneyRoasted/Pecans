package honeyroasted.pecans.ast.instruction.operator;

import honeyroasted.pecans.ast.Nodes;
import honeyroasted.pecans.ast.instruction.TypedNode;
import honeyroasted.pecans.signature.Types;
import honeyroasted.pecans.signature.type.TypeInformal;
import org.objectweb.asm.Label;
import org.objectweb.asm.commons.InstructionAdapter;

import static honeyroasted.pecans.signature.Types.*;

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
    public void acceptTrueJump(InstructionAdapter adapter, Label ifTrue) {
        if (left.type().isPrimitive() && right.type().isPrimitive()) {
            TypeInformal wide = Types.widest(left.type(), right.type());

            Nodes.convert(wide, left).accept(adapter);
            Nodes.convert(wide, right).accept(adapter);

            boolean compare = wide.equals(Types.DOUBLE) ||
                    wide.equals(Types.FLOAT) ||
                    wide.equals(Types.LONG);

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
    }

    @Override
    public void acceptFalseJump(InstructionAdapter adapter, Label ifFalse) {
        if (left.type().isPrimitive() && right.type().isPrimitive()) {
            TypeInformal wide = Types.widest(left.type(), right.type());

            Nodes.convert(wide, left).accept(adapter);
            Nodes.convert(wide, right).accept(adapter);

            boolean compare = wide.equals(Types.DOUBLE) ||
                    wide.equals(Types.FLOAT) ||
                    wide.equals(Types.LONG);

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
    }

    @Override
    public void acceptJump(InstructionAdapter adapter, Label ifTrue, Label ifFalse) {
        if (left.type().isPrimitive() && right.type().isPrimitive()) {
            TypeInformal wide = Types.widest(left.type(), right.type());

            Nodes.convert(wide, left).accept(adapter);
            Nodes.convert(wide, right).accept(adapter);

            boolean compare = wide.equals(Types.DOUBLE) ||
                    wide.equals(Types.FLOAT) ||
                    wide.equals(Types.LONG);

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
    }

    @Override
    public void accept(InstructionAdapter adapter) {
        if (left.type().isPrimitive() && right.type().isPrimitive()) {
            TypeInformal wide = Types.widest(left.type(), right.type());

            Nodes.convert(wide, left).accept(adapter);
            Nodes.convert(wide, right).accept(adapter);

            boolean compare = wide.equals(Types.DOUBLE) ||
                    wide.equals(Types.FLOAT) ||
                    wide.equals(Types.LONG);

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
                Nodes.constant(true).accept(adapter);
                adapter.goTo(b);
                adapter.mark(a);
                Nodes.constant(false).accept(adapter);
                adapter.mark(b);
            } else {
                Label a = new Label();
                Label b = new Label();
                this.operator.visitInverseIntCompare(adapter, a);
                Nodes.constant(true).accept(adapter);
                adapter.goTo(b);
                adapter.mark(a);
                Nodes.constant(false).accept(adapter);
                adapter.mark(b);
            }
        }
    }
}
