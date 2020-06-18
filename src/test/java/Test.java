import honeyroasted.pecans.node.ClassNode;
import honeyroasted.pecans.type.type.TypeParameterized;
import honeyroasted.pecans.util.ByteArrayClassLoader;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.util.Textifier;
import org.objectweb.asm.util.TraceClassVisitor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Paths;

import static honeyroasted.pecans.node.Nodes.*;
import static honeyroasted.pecans.type.Types.*;

public class Test {

    public static void main(String[] args) throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        TypeParameterized clsType = parameterized("LGenerated;");

        ClassNode node = classDef(ACC_PUBLIC, classSignature(clsType));

        node.add(field(ACC_PUBLIC, "myString", type(String.class)))
                .add(method(ACC_PUBLIC, "<init>", methodSignature(VOID))
                        .param("myString", type(String.class))
                        .body(sequence(
                                invokeSpecial(type(Object.class), loadThis(), "<init>", methodSignature(VOID)),
                                set(loadThis(), "myString", get("myString")),
                                def("a", constant(true)),
                                def("probs_broke", or(get("a"), get("a"))),
                                ret())))
                .add(method(ACC_PUBLIC, "getValue", methodSignature(OBJECT), ret(get(loadThis(), "myString", type(String.class)))));

        node.writeIn(Paths.get("bytecode_tests"));

        byte[] clazz = node.toByteArray();

        ByteArrayClassLoader loader = new ByteArrayClassLoader();
        Class<?> cls = loader.defineClass(clsType.writeExternalName(), clazz);
        Object val = cls.getConstructor(String.class).newInstance("Hello world");
        System.out.println(cls.getMethod("getValue").invoke(val));

        ClassReader reader = new ClassReader(clazz);

        Textifier textifier = new Textifier();
        TraceClassVisitor visitor = new TraceClassVisitor(null, textifier, new PrintWriter(new File("bytecode_tests/Generated.txt")));

        reader.accept(visitor, ClassReader.EXPAND_FRAMES);
    }

}
