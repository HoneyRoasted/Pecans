package honeyroasted.pecans.ast;

import honeyroasted.pecans.signature.type.TypeInformal;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;

public class FieldNode {
    private int modifiers;
    private String name;
    private TypeInformal type;
    private Object value;
    private Collection<AnnotationNode> annotations;

    private Consumer<FieldVisitor> onAccept = v -> {};

    public FieldNode(int modifiers, String name, TypeInformal type, Object value, Collection<AnnotationNode> annotations) {
        this.modifiers = modifiers;
        this.name = name;
        this.type = type;
        this.value = value;
        this.annotations = annotations;
    }

    public static FieldNode of(int modifiers, String name, TypeInformal type) {
        return new FieldNode(modifiers, name, type, null, new ArrayList<>());
    }

    public static FieldNode of(int modifiers, String name, TypeInformal type, Object value) {
        return new FieldNode(modifiers, name, type, value, new ArrayList<>());
    }

    public FieldNode add(AnnotationNode annotation) {
        this.annotations.add(annotation);
        return this;
    }

    public FieldNode onAccept(Consumer<FieldVisitor> onAccept) {
        this.onAccept = onAccept;
        return this;
    }

    public FieldVisitor accept(ClassVisitor writer) {
        FieldVisitor visitor = writer.visitField(this.modifiers, this.name, this.type.writeDesc(), this.type.write(), this.value);
        for (AnnotationNode annotation : annotations) {
            annotation.accept(visitor.visitAnnotation(annotation.getAnnotation().writeDesc(), annotation.isVisible()));
        }
        onAccept.accept(visitor);
        return visitor;
    }

}
