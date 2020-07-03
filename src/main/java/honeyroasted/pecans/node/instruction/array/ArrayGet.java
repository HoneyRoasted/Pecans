package honeyroasted.pecans.node.instruction.array;

import honeyroasted.pecans.node.Context;
import honeyroasted.pecans.node.instruction.TypedNode;
import honeyroasted.pecans.type.type.TypeArray;
import honeyroasted.pecans.type.type.TypeInformal;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.InstructionAdapter;

public class ArrayGet implements TypedNode {
    private TypedNode array;
    private TypedNode index;

    public ArrayGet(TypedNode array, TypedNode index) {
        this.array = array;
        this.index = index;
    }

    @Override
    public TypeInformal type(Context context) {
        TypeInformal arr = array.type(context);
        if (arr instanceof TypeArray) {
            return ((TypeArray) arr).element();
        } else {
            throw new IllegalArgumentException("Array node must have array type");
        }
    }

    @Override
    public void accept(InstructionAdapter adapter, Context context) {
        this.array.accept(adapter, context);
        this.index.accept(adapter, context);
        adapter.aload(Type.getType(((TypeArray) this.array.type(context)).element().writeDesc()));
    }

    @Override
    public void preprocess(Context context) {
        this.array.type(context);
        this.index.type(context);
    }
}
