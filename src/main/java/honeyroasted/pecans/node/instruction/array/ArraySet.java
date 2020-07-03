package honeyroasted.pecans.node.instruction.array;

import honeyroasted.pecans.node.Context;
import honeyroasted.pecans.node.instruction.Node;
import honeyroasted.pecans.node.instruction.TypedNode;
import honeyroasted.pecans.type.type.TypeArray;
import honeyroasted.pecans.type.type.TypeInformal;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.InstructionAdapter;

public class ArraySet implements Node {
    private TypedNode array;
    private TypedNode index;
    private TypedNode value;

    public ArraySet(TypedNode array, TypedNode index, TypedNode value) {
        this.array = array;
        this.index = index;
        this.value = value;
    }

    @Override
    public void accept(InstructionAdapter adapter, Context context) {
        this.array.accept(adapter, context);
        this.index.accept(adapter, context);
        this.value.accept(adapter, context);

        TypeInformal arr = array.type(context);
        if (arr instanceof TypeArray) {
            adapter.astore(Type.getType(((TypeArray) arr).element().writeDesc()));
        } else {
            throw new IllegalArgumentException("Array node must have array type");
        }
    }

    @Override
    public void preprocess(Context context) {
        this.array.preprocess(context);
        this.index.preprocess(context);
        this.value.preprocess(context);
    }

}
