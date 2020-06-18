package honeyroasted.pecans.node.instruction.block;

import honeyroasted.pecans.node.ClassNode;
import honeyroasted.pecans.node.Context;
import honeyroasted.pecans.node.MethodNode;
import honeyroasted.pecans.node.instruction.Node;
import honeyroasted.pecans.node.instruction.TypedNode;
import honeyroasted.pecans.node.instruction.operator.BooleanOperator;
import org.objectweb.asm.Label;
import org.objectweb.asm.commons.InstructionAdapter;

import java.util.Collection;
import java.util.Iterator;

public class If implements Node {
    private TypedNode condition;
    private Node ifTrue;
    private Collection<ElseIf> elseIfs;

    public If(TypedNode condition, Node ifTrue, Collection<ElseIf> elseIfs) {
        this.condition = condition;
        this.ifTrue = ifTrue;
        this.elseIfs = elseIfs;
    }

    @Override
    public void accept(InstructionAdapter adapter, Context context) {
        Label end = new Label();
        Label start = new Label();
        Label startElse = new Label();


        if (!this.elseIfs.isEmpty()) {
            apply(adapter, context, this.condition, start, startElse);
        } else {
            apply(adapter, context, this.condition, start, end);
        }

        adapter.mark(start);
        this.ifTrue.accept(adapter, context);
        adapter.goTo(end);

        Iterator<ElseIf> elseIfIterator = this.elseIfs.iterator();
        while (elseIfIterator.hasNext()) {
            ElseIf elseIf = elseIfIterator.next();

            adapter.mark(startElse);
            start = startElse;
            startElse = new Label();

            if (elseIf.condition != null) {
                if (elseIfIterator.hasNext()) {
                    apply(adapter, context, elseIf.condition, start, startElse);
                } else {
                    apply(adapter, context, elseIf.condition, start, end);
                }
            }

            elseIf.ifTrue.accept(adapter, context);

            if (elseIfIterator.hasNext()) {
                adapter.goTo(end);
            }
        }

        adapter.mark(end);
    }

    @Override
    public void preprocess(Context context) {
        this.condition.preprocess(context);
        this.ifTrue.preprocess(context);

        this.elseIfs.forEach(e -> {
            e.condition.preprocess(context);
            e.ifTrue.preprocess(context);
        });
    }

    private void apply(InstructionAdapter adapter, Context context, TypedNode condition, Label start, Label end) {
        if (condition instanceof BooleanOperator) {
            ((BooleanOperator) condition).acceptJump(adapter, context, start, end);
        } else {
            condition.accept(adapter, context);
            adapter.ifeq(end);
        }
    }

    public If elseBlock(Node el) {
        this.elseIfs.add(new ElseIf(null, el));
        return this;
    }

    public If elseIfBlock(TypedNode condition, Node ifTrue) {
        this.elseIfs.add(new ElseIf(condition, ifTrue));
        return this;
    }

    public static class ElseIf {
        private TypedNode condition;
        private Node ifTrue;

        public ElseIf(TypedNode condition, Node ifTrue) {
            this.condition = condition;
            this.ifTrue = ifTrue;
        }

    }

}
