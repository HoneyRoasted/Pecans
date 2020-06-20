package honeyroasted.pecans.node.instruction.block;

import honeyroasted.pecans.node.Context;
import honeyroasted.pecans.node.instruction.Node;
import org.objectweb.asm.commons.InstructionAdapter;

public class Goto implements Node {
    private String label;

    public Goto(String label) {
        this.label = label;
    }

    @Override
    public void accept(InstructionAdapter adapter, Context context) {
        adapter.goTo(context.label(this.label));
    }

    @Override
    public void preprocess(Context context) {

    }
}
