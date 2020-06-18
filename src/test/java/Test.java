import honeyroasted.pecans.node.ClassNode;
import honeyroasted.pecans.type.type.TypeParameterized;
import honeyroasted.pecans.util.ByteArrayClassLoader;

import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Paths;

import static honeyroasted.pecans.node.Nodes.*;
import static honeyroasted.pecans.type.Types.*;

public class Test {

    public static void main(String[] args) throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        TypeParameterized clsType = parameterized("Ltests/Generated;");

        ClassNode node = classDef(ACC_PUBLIC, classSignature(clsType));

        node.add(field(ACC_PUBLIC, "myString", type(String.class)))
                .add(method(ACC_PUBLIC, "<init>", methodSignature(VOID))
                        .param("myString", type(String.class))
                        .body(sequence(
                                invokeSpecial(type(Object.class), loadThis(), "<init>", methodSignature(VOID)),
                                set(loadThis(), "myString", get("myString")),
                                def("a", constant(2.0d)),
                                def("b", constant(4)),
                                def("c", not(equal(get("a"), get("b")))),
                                def("d", constant("hello")),
                                invokeVirtual(get(type(System.class), "out", type(PrintStream.class)), "println", methodSignature(VOID)),
                                ret())))
                .add(method(ACC_PUBLIC, "getValue", methodSignature(OBJECT), ret(get(loadThis(), "myString", type(String.class)))));

        node.writeIn(Paths.get(""));

        ByteArrayClassLoader loader = new ByteArrayClassLoader();
        Class<?> cls = loader.defineClass(clsType.writeExternalName(), node.toByteArray());
        Object val = cls.getConstructor(String.class).newInstance("Hello world");
        System.out.println(cls.getMethod("getValue").invoke(val));
    }

}
