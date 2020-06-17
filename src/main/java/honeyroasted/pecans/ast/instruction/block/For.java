package honeyroasted.pecans.ast.instruction.block;

import honeyroasted.pecans.ast.instruction.Node;
import honeyroasted.pecans.ast.instruction.TypedNode;
import org.objectweb.asm.Label;
import org.objectweb.asm.commons.InstructionAdapter;

public class For implements Node {
    private Node begin;
    private TypedNode condition;
    private Node action;

    private Node body;

    public For(Node begin, TypedNode condition, Node action, Node body) {
        this.begin = begin;
        this.condition = condition;
        this.action = action;
        this.body = body;
    }

    @Override
    public void accept(InstructionAdapter adapter) {
        if (this.begin != null) {
            this.begin.accept(adapter);
        }

        Label start = new Label();
        Label end = new Label();
        adapter.mark(start);

        if (this.condition != null) {
            this.condition.accept(adapter);
            adapter.ifeq(end);
        }

        this.body.accept(adapter);

        if (this.action != null) {
            this.action.accept(adapter);
        }

        adapter.goTo(start);

        adapter.mark(end);
    }

}
