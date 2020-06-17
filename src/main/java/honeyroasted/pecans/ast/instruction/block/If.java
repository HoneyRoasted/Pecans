package honeyroasted.pecans.ast.instruction.block;

import honeyroasted.pecans.ast.instruction.Node;
import honeyroasted.pecans.ast.instruction.TypedNode;
import honeyroasted.pecans.ast.instruction.operator.BooleanOperator;
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
    public void accept(InstructionAdapter adapter) {
        Label end = new Label();
        Label start = new Label();
        Label startElse = new Label();


        if (!this.elseIfs.isEmpty()) {
            apply(adapter, this.condition, start, startElse);
        } else {
            apply(adapter, this.condition, start, end);
        }

        adapter.mark(start);
        this.ifTrue.accept(adapter);
        adapter.goTo(end);

        Iterator<ElseIf> elseIfIterator = this.elseIfs.iterator();
        while (elseIfIterator.hasNext()) {
            ElseIf elseIf = elseIfIterator.next();

            adapter.mark(startElse);
            start = startElse;
            startElse = new Label();

            if (elseIf.condition != null) {
                if (elseIfIterator.hasNext()) {
                    apply(adapter, elseIf.condition, start, startElse);
                } else {
                    apply(adapter, elseIf.condition, start, end);
                }
            }

            elseIf.ifTrue.accept(adapter);

            if (elseIfIterator.hasNext()) {
                adapter.goTo(end);
            }
        }

        adapter.mark(end);
    }

    private void apply(InstructionAdapter adapter, TypedNode condition, Label start, Label end) {
        if (condition instanceof BooleanOperator) {
            ((BooleanOperator) condition).acceptJump(adapter, start, end);
        } else {
            condition.accept(adapter);
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
