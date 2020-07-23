package honeyroasted.pecans.type;

import honeyroasted.pecans.type.type.TypeArray;
import honeyroasted.pecans.type.type.TypeFill;
import honeyroasted.pecans.type.type.TypeInformal;
import honeyroasted.pecans.type.type.TypeInner;
import honeyroasted.pecans.type.type.TypeParameterized;
import honeyroasted.pecans.type.type.TypeVar;
import honeyroasted.pecans.type.type.TypeVarRef;
import honeyroasted.pecans.type.type.TypeWild;
import honeyroasted.pecans.type.type.TypeNull;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public interface Types extends Opcodes {

    TypeInformal OBJECT = type(Object.class);

    TypeInformal BOOLEAN = type(boolean.class);
    TypeInformal BOOLEAN_BOX = type(Boolean.class);

    TypeInformal BYTE = type(byte.class);
    TypeInformal BYTE_BOX = type(Byte.class);

    TypeInformal SHORT = type(short.class);
    TypeInformal SHORT_BOX = type(Short.class);

    TypeInformal INT = type(int.class);
    TypeInformal INT_BOX = type(Integer.class);

    TypeInformal LONG = type(long.class);
    TypeInformal LONG_BOX = type(Long.class);

    TypeInformal FLOAT = type(float.class);
    TypeInformal FLOAT_BOX = type(Float.class);

    TypeInformal DOUBLE = type(double.class);
    TypeInformal DOUBLE_BOX = type(Double.class);

    TypeInformal CHAR = type(char.class);
    TypeInformal CHAR_BOX = type(Character.class);

    TypeInformal STRING = type(String.class);

    TypeInformal VOID = type(void.class);

    Map<TypeInformal, TypeInformal> PRIMITIVE_TO_BOX = Collections.unmodifiableMap(new LinkedHashMap<>() {{
        put(BOOLEAN, BOOLEAN_BOX);
        put(BYTE, BYTE_BOX);
        put(SHORT, SHORT_BOX);
        put(INT, INT_BOX);
        put(LONG, LONG_BOX);
        put(FLOAT, FLOAT_BOX);
        put(DOUBLE, DOUBLE_BOX);
        put(CHAR, CHAR_BOX);
    }});

    Map<TypeInformal, TypeInformal> BOX_TO_PRIMITIVE = Collections.unmodifiableMap(new LinkedHashMap<>() {{
        put(BOOLEAN_BOX, BOOLEAN);
        put(BYTE_BOX, BYTE);
        put(SHORT_BOX, SHORT);
        put(INT_BOX, INT);
        put(LONG_BOX, LONG);
        put(FLOAT_BOX, FLOAT);
        put(DOUBLE_BOX, DOUBLE);
        put(CHAR_BOX, CHAR);
    }});

    static TypeInformal widest(TypeInformal a, TypeInformal b) {
        if (a.isPrimitive() && b.isPrimitive()) {
            if (a.equals(DOUBLE) || b.equals(DOUBLE)) {
                return DOUBLE;
            }  else if (a.equals(FLOAT) || b.equals(FLOAT)) {
                return FLOAT;
            } else if (a.equals(LONG) || b.equals(LONG)) {
                return LONG;
            } else if (a.equals(INT) || b.equals(INT)) {
                return INT;
            } else if (a.equals(SHORT) || b.equals(SHORT)) {
                return SHORT;
            } else if (a.equals(CHAR) || b.equals(CHAR)) {
                return CHAR;
            } else if (a.equals(BYTE) || b.equals(BYTE)) {
                return BYTE;
            } else if (a.equals(BOOLEAN) || b.equals(BOOLEAN)) {
                return BOOLEAN;
            }
        }
        return a;
    }

    static TypeFill type(Type asmType) {
        return new TypeFill(asmType);
    }

    static TypeFill type(Class cls) {
        return type(Type.getType(cls));
    }

    static TypeFill type(String desc) {
        return type(Type.getType(desc));
    }

    static TypeArray array(TypeInformal type) {
        return TypeArray.create(type, 1);
    }

    static TypeArray array(TypeInformal type, int dimensions) {
        return TypeArray.create(type, dimensions);
    }

    static TypeInner inner(Type type, TypeFill outer) {
        return new TypeInner(type, outer);
    }

    static TypeInner inner(Type type, TypeFill outer, String desc) {
        return new TypeInner(type, outer).setDesc(desc);
    }

    static TypeNull nullType() {
        return new TypeNull();
    }

    static TypeParameterized parameterized(Type type) {
        return new TypeParameterized(type);
    }

    static TypeParameterized parameterized(Class cls) {
        return parameterized(Type.getType(cls));
    }

    static TypeParameterized parameterized(String desc) {
        return parameterized(Type.getType(desc));
    }

    static TypeVar var(String name) {
        return new TypeVar(name);
    }

    static TypeVarRef varRef(String name) {
        return new TypeVarRef(name);
    }

    static TypeWild wildcard() {
        return new TypeWild(OBJECT, null);
    }

    static TypeWild wildcard(TypeInformal upper) {
        return new TypeWild(upper, null);
    }

    static TypeWild wildcard(TypeInformal upper, TypeInformal lower) {
        return new TypeWild(upper, lower);
    }

    static ClassSignature classSignature(TypeParameterized type) {
        return ClassSignature.of(type);
    }

    static ClassSignature classSignature(TypeParameterized type, TypeFill superclass) {
        return ClassSignature.of(type, superclass);
    }

    static MethodSignature methodSignature(TypeInformal ret) {
        return MethodSignature.of(ret);
    }

    static MethodSignature methodSignature(TypeInformal ret, TypeInformal... params) {
        MethodSignature sig = MethodSignature.of(ret);
        for (TypeInformal type : params) {
            sig.addParameter(type);
        }
        return sig;
    }

    static MethodSignature methodSignature(Method method) {
        MethodSignature sig = methodSignature(type(method.getReturnType()));
        for (Class<?> param : method.getParameterTypes()) {
            sig.addParameter(type(param));
        }
        return sig;
    }

}
