import honeyroasted.pecans.ast.ClassNode;
import honeyroasted.pecans.ast.instruction.operator.Compare;
import honeyroasted.pecans.ast.instruction.operator.ComparisonOperator;
import honeyroasted.pecans.signature.type.TypeParameterized;
import honeyroasted.pecans.util.ByteArrayClassLoader;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Paths;

import static honeyroasted.pecans.ast.Nodes.*;
import static honeyroasted.pecans.signature.Types.*;

public class Test {

    public static void main(String[] args) throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        TypeParameterized clsType = parameterized("Ltests/Generated;");

        ClassNode node = classDef(ACC_PUBLIC, classSignature(clsType));

        node.add(field(ACC_PUBLIC, "myString", type(String.class)))
                .add(method(ACC_PUBLIC, "<init>", methodSignature(VOID))
                        .param("myString", type(String.class))
                        .body(sequence(
                                invokeSpecial(type(Object.class), loadThis(clsType), "<init>", methodSignature(VOID)),
                                set(loadThis(clsType), "myString", get(1, type(String.class))),
                                set(2, new Compare(ComparisonOperator.LESS_THAN, constant(12), constant(31))),
                                ret())))
                .add(method(ACC_PUBLIC, "getValue", methodSignature(OBJECT), ret(get(loadThis(clsType), "myString", type(String.class)))));

        node.writeIn(Paths.get(""));

        ByteArrayClassLoader loader = new ByteArrayClassLoader();
        Class<?> cls = loader.defineClass(clsType.writeExternalName(), node.toByteArray());
        Object val = cls.getConstructor(String.class).newInstance("Hello world");
        System.out.println(cls.getMethod("getValue").invoke(val));
    }

}
