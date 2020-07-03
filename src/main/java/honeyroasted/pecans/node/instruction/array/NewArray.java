package honeyroasted.pecans.node.instruction.array;

import honeyroasted.pecans.node.Context;
import honeyroasted.pecans.node.instruction.TypedNode;
import honeyroasted.pecans.type.type.TypeArray;
import honeyroasted.pecans.type.type.TypeInformal;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.InstructionAdapter;

import java.util.List;

public class NewArray implements TypedNode {
    private TypeInformal elementType;
    private List<TypedNode> elements;
    private TypedNode size;

    public NewArray(TypeInformal elementType, List<TypedNode> elements, TypedNode size) {
        this.elementType = elementType;
        this.elements = elements;
        this.size = size;
    }

    @Override
    public TypeInformal type(Context context) {
        return TypeArray.create(elementType, 1);
    }

    @Override
    public void accept(InstructionAdapter adapter, Context context) {
        Type type = Type.getType(this.elementType.writeDesc());

        this.size.accept(adapter, context);
        adapter.newarray(type);

        if (this.elements != null) {
            for (int i = 0; i < elements.size(); i++) {
                adapter.dup();
                adapter.iconst(i);
                adapter.astore(type);
            }
        }
    }

    @Override
    public void preprocess(Context context) {
        this.elements.forEach(n -> n.preprocess(context));
    }
}
