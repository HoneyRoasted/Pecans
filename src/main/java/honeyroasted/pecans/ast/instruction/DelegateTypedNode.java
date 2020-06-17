package honeyroasted.pecans.ast.instruction;

import honeyroasted.pecans.signature.type.TypeInformal;
import org.objectweb.asm.commons.InstructionAdapter;

public class DelegateTypedNode extends AbstractTypedNode {
    private Node node;

    public DelegateTypedNode(TypeInformal type, Node node) {
        super(type);
        this.node = node;
    }

    @Override
    public void accept(InstructionAdapter adapter) {
        this.node.accept(adapter);
    }
}
