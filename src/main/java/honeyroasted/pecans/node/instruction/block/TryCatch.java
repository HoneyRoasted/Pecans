package honeyroasted.pecans.node.instruction.block;

import honeyroasted.pecans.node.Context;
import honeyroasted.pecans.node.Nodes;
import honeyroasted.pecans.node.instruction.Node;
import honeyroasted.pecans.node.instruction.util.NoopNode;
import honeyroasted.pecans.type.type.TypeInformal;
import org.objectweb.asm.Label;
import org.objectweb.asm.commons.InstructionAdapter;

public class TryCatch implements Node {
    private Node body;
    private TypeInformal exception;
    private String var;
    private Node handler;

    public TryCatch(Node body, TypeInformal exception, String var, Node handler) {
        this.body = Nodes.scope(body);

        this.exception = exception;
        this.var = var;

        this.handler = Nodes.scope(Nodes.sequence(Nodes.def(this.exception, this.var, new NoopNode().type(this.exception)), handler));
    }

    @Override
    public void accept(InstructionAdapter adapter, Context context) {
        Label startBody = new Label();
        Label endBody = new Label();
        Label handler = new Label();
        Label end = new Label();

        adapter.mark(startBody);
        this.body.accept(adapter, context);
        adapter.mark(endBody);
        adapter.goTo(end);
        adapter.mark(handler);
        this.handler.accept(adapter, context);
        adapter.mark(end);

        adapter.visitTryCatchBlock(startBody, endBody, handler, this.exception.writeInternalName());
    }

    @Override
    public void preprocess(Context context) {
        this.body.preprocess(context);
        this.handler.preprocess(context);
    }
}
