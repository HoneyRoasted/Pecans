package honeyroasted.pecans.ast.instruction;

import honeyroasted.pecans.signature.type.TypeFill;
import honeyroasted.pecans.signature.type.TypeInformal;
import honeyroasted.pecans.signature.type.TypeNull;
import org.objectweb.asm.ConstantDynamic;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.InstructionAdapter;

public class Constant extends AbstractTypedNode {
    private Object cons;

    public Constant(Object cons) {
        super(getType(cons));
        this.cons = cons;
    }

    public static Constant of(Object cons) {
        if (cons instanceof Class) {
            cons = Type.getType((Class) cons);
        } else if (cons instanceof TypeInformal) {
            cons = Type.getType(((TypeInformal) cons).writeDesc());
        }

        return new Constant(cons);
    }

    @Override
    public void accept(InstructionAdapter adapter) {
        if (this.cons instanceof Double) {
            adapter.dconst((Double) this.cons);
        } else if (this.cons instanceof Float) {
            adapter.fconst((Float) this.cons);
        } else if (this.cons instanceof Long) {
            adapter.lconst((Long) this.cons);
        } else if (this.cons instanceof Number) {
            adapter.iconst(((Number) this.cons).intValue());
        } else if (this.cons instanceof Character) {
            adapter.iconst((Character) this.cons);
        } else if (cons instanceof Boolean) {
            if ((Boolean) cons) {
                adapter.iconst(1);
            } else {
                adapter.iconst(0);
            }
        } else if (this.cons instanceof Type) {
            adapter.tconst((Type) this.cons);
        } else if (this.cons instanceof ConstantDynamic) {
            adapter.cconst((ConstantDynamic) this.cons);
        } else if (this.cons instanceof Handle) {
            adapter.hconst((Handle) this.cons);
        } else {
            adapter.aconst(this.cons);
        }
    }

    private static TypeInformal getType(Object cons) {
        if (cons == null) {
            return new TypeNull();
        }

        Type res = null;

        if (cons instanceof Integer) {
            res = Type.INT_TYPE;
        } else if (cons instanceof Byte) {
            res = Type.BYTE_TYPE;
        } else if (cons instanceof Character) {
            res = Type.CHAR_TYPE;
        } else if (cons instanceof Short) {
            res = Type.SHORT_TYPE;
        } else if (cons instanceof Boolean) {
            res = Type.BOOLEAN_TYPE;
        } else if (cons instanceof Float) {
            res = Type.FLOAT_TYPE;
        } else if (cons instanceof Long) {
            res = Type.LONG_TYPE;
        } else if (cons instanceof Double) {
            res = Type.DOUBLE_TYPE;
        } else if (cons instanceof String) {
            res = Type.getType(String.class);
        } else if (cons instanceof Type) {
            res = Type.getType(Class.class);
        } else if (cons instanceof Handle) {
            res = Type.VOID_TYPE; //TODO fix this
        } else if (cons instanceof ConstantDynamic) {
            ConstantDynamic constantDynamic = (ConstantDynamic) cons;
            res = Type.getType(constantDynamic.getDescriptor());
        }

        if (res == null) {
            throw new IllegalArgumentException("Unknown constant: " + cons.getClass());
        } else {
            return new TypeFill(res);
        }
    }
}
