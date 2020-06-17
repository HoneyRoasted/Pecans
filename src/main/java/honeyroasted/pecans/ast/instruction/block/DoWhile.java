package honeyroasted.pecans.ast.instruction.block;

import honeyroasted.pecans.ast.instruction.Node;
import honeyroasted.pecans.ast.instruction.TypedNode;
import org.objectweb.asm.Label;
import org.objectweb.asm.commons.InstructionAdapter;

public class DoWhile implements Node {
    private TypedNode condition;
    private Node body;

    public DoWhile(TypedNode condition, Node body) {
        this.condition = condition;
        this.body = body;
    }

    @Override
    public void accept(InstructionAdapter adapter) {
        Label start = new Label();
        adapter.mark(start);
        this.body.accept(adapter);
        this.condition.accept(adapter);
        adapter.ifne(start);
    }
}
