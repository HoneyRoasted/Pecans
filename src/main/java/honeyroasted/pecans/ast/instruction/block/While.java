package honeyroasted.pecans.ast.instruction.block;

import honeyroasted.pecans.ast.instruction.Node;
import honeyroasted.pecans.ast.instruction.TypedNode;
import org.objectweb.asm.Label;
import org.objectweb.asm.commons.InstructionAdapter;

public class While implements Node {
    private TypedNode condition;
    private Node body;

    public While(TypedNode condition, Node body) {
        this.condition = condition;
        this.body = body;
    }

    @Override
    public void accept(InstructionAdapter adapter) {
        Label start = new Label();
        Label end = new Label();

        adapter.mark(start);
        this.condition.accept(adapter);
        adapter.ifeq(end);
        this.body.accept(adapter);
        adapter.goTo(start);
        adapter.mark(end);
    }
}
