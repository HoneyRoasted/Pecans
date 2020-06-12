package io.github.honeyroasted.pecans.signature;

import io.github.honeyroasted.pecans.signature.type.TypeArray;
import io.github.honeyroasted.pecans.signature.type.TypeFill;
import io.github.honeyroasted.pecans.signature.type.TypeInformal;
import io.github.honeyroasted.pecans.signature.type.TypeInner;
import io.github.honeyroasted.pecans.signature.type.TypeNull;
import io.github.honeyroasted.pecans.signature.type.TypeParameterized;
import io.github.honeyroasted.pecans.signature.type.TypeVar;
import io.github.honeyroasted.pecans.signature.type.TypeVarRef;
import io.github.honeyroasted.pecans.signature.type.TypeWild;
import org.objectweb.asm.Type;

public interface Types {

    TypeInformal OBJECT = type(Object.class);

    TypeInformal BOOLEAN = type(boolean.class);

    TypeInformal BYTE = type(byte.class);
    TypeInformal SHORT = type(short.class);
    TypeInformal INT = type(int.class);
    TypeInformal LONG = type(long.class);

    TypeInformal FLOAT = type(float.class);
    TypeInformal DOUBLE = type(double.class);

    TypeInformal CHAR = type(char.class);

    TypeInformal VOID = type(void.class);

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

}
