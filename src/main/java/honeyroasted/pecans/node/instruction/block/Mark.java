package honeyroasted.pecans.node.instruction.block;

import honeyroasted.pecans.node.Context;
import honeyroasted.pecans.node.instruction.Node;
import org.objectweb.asm.commons.InstructionAdapter;

public class Mark implements Node {
    private String name;

    public Mark(String name) {
        this.name = name;
    }

    @Override
    public void accept(InstructionAdapter adapter, Context context) {
        adapter.mark(context.label(this.name));
    }

    @Override
    public void preprocess(Context context) {

    }

}
