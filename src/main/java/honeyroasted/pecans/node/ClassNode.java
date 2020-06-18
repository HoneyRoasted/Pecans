package honeyroasted.pecans.node;

import honeyroasted.pecans.type.ClassSignature;
import honeyroasted.pecans.type.type.TypeSignaturePart;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;

public class ClassNode implements Opcodes {
    private int modifiers;
    private ClassSignature signature;
    private Collection<AnnotationNode> annotations;
    private Collection<FieldNode> fields;
    private Collection<MethodNode> methods;

    private Consumer<ClassVisitor> onAccept = v -> {};

    public ClassNode(int modifiers, ClassSignature signature, Collection<AnnotationNode> annotations, Collection<FieldNode> fields, Collection<MethodNode> methods) {
        this.modifiers = modifiers;
        this.signature = signature;
        this.annotations = annotations;
        this.fields = fields;
        this.methods = methods;
    }

    public ClassSignature getSignature() {
        return signature;
    }

    public static ClassNode of(int modifiers, ClassSignature signature) {
        return new ClassNode(modifiers, signature, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    public ClassNode onAccept(Consumer<ClassVisitor> onAccept) {
        this.onAccept = onAccept;
        return this;
    }

    public ClassNode add(AnnotationNode annotation) {
        this.annotations.add(annotation);
        return this;
    }

    public ClassNode add(FieldNode field) {
        this.fields.add(field);
        return this;
    }

    public ClassNode add(MethodNode method) {
        this.methods.add(method);
        return this;
    }

    public void accept(ClassVisitor visitor) {
        visitor.visit(V14, this.modifiers, this.signature.writeInternalName(), this.signature.write(), this.signature.getSuperclass().writeInternalName(), this.signature.getInterfaces().stream().map(TypeSignaturePart::writeInternalName).toArray(String[]::new));

        for (AnnotationNode annotation : annotations) {
            annotation.accept(visitor.visitAnnotation(annotation.getAnnotation().writeDesc(), annotation.isVisible()));
        }

        for (FieldNode field : fields) {
            field.accept(visitor).visitEnd();
        }

        for (MethodNode method : methods) {
            MethodVisitor vis = method.accept(visitor, this);
            vis.visitMaxs(0, 0);
            vis.visitEnd();
        }

        onAccept.accept(visitor);
        visitor.visitEnd();
    }

    public byte[] toByteArray() {
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_FRAMES + ClassWriter.COMPUTE_MAXS);
        accept(writer);
        return writer.toByteArray();
    }

    public void writeTo(Path path) throws IOException {
        Files.createDirectories(path.getParent());
        Files.write(path, toByteArray(), StandardOpenOption.CREATE);
    }

    public void writeIn(Path baseDir) throws IOException {
        String[] parts = signature.writeInternalName().split("/");
        for (int i = 0; i < parts.length - 1; i++) {
            baseDir = baseDir.resolve(parts[i]);
        }
        writeTo(baseDir.resolve(parts[parts.length - 1] + ".class"));
    }

}
