package honeyroasted.pecans.signature.type;

import org.objectweb.asm.Type;

public class TypeNull implements TypeInformal {

    @Override
    public String write() {
        throw new UnsupportedOperationException("TypeNull may not be part of a signature");
    }

    @Override
    public String writeDesc() {
        return Type.getType(Object.class).getDescriptor();
    }
}
