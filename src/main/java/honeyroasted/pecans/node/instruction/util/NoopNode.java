package honeyroasted.pecans.node.instruction.util;

import honeyroasted.pecans.node.Context;
import honeyroasted.pecans.node.instruction.Node;
import org.objectweb.asm.commons.InstructionAdapter;

public class NoopNode implements Node {

    @Override
    public void accept(InstructionAdapter adapter, Context context) {

    }

    @Override
    public void preprocess(Context context) {

    }

}
