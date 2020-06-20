package honeyroasted.pecans.node.instruction.block;

import honeyroasted.pecans.node.Context;
import honeyroasted.pecans.node.instruction.Node;
import org.objectweb.asm.commons.InstructionAdapter;

public class Continue implements Node {
    private String name;

    public Continue(String name) {
        this.name = name;
    }

    @Override
    public void accept(InstructionAdapter adapter, Context context) {
        if (this.name != null) {
            adapter.goTo(context.findNamed(this.name).continueLabel());
        } else {
            adapter.goTo(context.continueLabel());
        }
    }

    @Override
    public void preprocess(Context context) {

    }
}
