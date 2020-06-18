package honeyroasted.pecans.node.instruction.variable.scope;

import honeyroasted.pecans.node.Context;
import honeyroasted.pecans.node.instruction.Node;
import org.objectweb.asm.Label;
import org.objectweb.asm.commons.InstructionAdapter;

public class Scope implements Node {
    private Node node;
    private VariableScope scope;

    public Scope(Node node) {
        this.node = node;
    }

    @Override
    public void accept(InstructionAdapter adapter, Context context) {
        this.node.accept(adapter, new Context(context.classNode(), context.methodNode(), scope));

        Label end = new Label();
        adapter.mark(end);
        scope.forEach(v -> v.accept(null, adapter, end));
    }

    @Override
    public void preprocess(Context context) {
        this.scope = context.scope().newChild();
        this.node.preprocess(new Context(context.classNode(), context.methodNode(), scope));
    }
}
