package honeyroasted.pecans.node.instruction;

import honeyroasted.pecans.node.Context;
import org.objectweb.asm.commons.InstructionAdapter;

public class Throw implements Node {
    private TypedNode exception;

    public Throw(TypedNode exception) {
        this.exception = exception;
    }

    @Override
    public void accept(InstructionAdapter adapter, Context context) {
        this.exception.accept(adapter, context);
        adapter.athrow();
    }

    @Override
    public void preprocess(Context context) {
        this.exception.preprocess(context);
    }
}
