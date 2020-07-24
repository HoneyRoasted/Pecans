package honeyroasted.pecans.node;

import honeyroasted.pecans.type.type.TypeFill;
import honeyroasted.pecans.type.type.TypeInformal;
import org.objectweb.asm.AnnotationVisitor;

import java.lang.reflect.Array;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

public class AnnotationNode {
    private TypeInformal annotation;
    private boolean visible;
    private Map<String, Object> vals;
    private Consumer<AnnotationVisitor> onAccept = v -> {};

    public AnnotationNode(TypeInformal annotation, boolean visible) {
        this.annotation = annotation;
        this.visible = visible;
        this.vals = new LinkedHashMap<>();
    }

    public static AnnotationNode of(TypeInformal annotation) {
        return new AnnotationNode(annotation, true);
    }

    public static AnnotationNode of(TypeInformal annotation, boolean visible) {
        return new AnnotationNode(annotation, visible);
    }

    public AnnotationNode onAccept(Consumer<AnnotationVisitor> onAccept) {
        this.onAccept = onAccept;
        return this;
    }

    public boolean isVisible() {
        return visible;
    }

    public TypeInformal getAnnotation() {
        return annotation;
    }

    public AnnotationNode put(String name, Object obj) {
        this.vals.put(name, obj);
        return this;
    }

    public AnnotationNode putEnum(String name, TypeFill enumType, String enumVal) {
        this.vals.put(name, new AnnotationEnumNode(enumType, enumVal));
        return this;
    }

    public AnnotationNode putEnumArray(String name, TypeFill enumType, String... enumVals) {
        AnnotationEnumNode[] arr = new AnnotationEnumNode[enumVals.length];
        for (int i = 0; i < enumVals.length; i++) {
            arr[i] = new AnnotationEnumNode(enumType, enumVals[i]);
        }
        this.vals.put(name, arr);
        return this;
    }

    public void accept(AnnotationVisitor visitor) {
        vals.forEach((k, v) -> {
            if (v != null && v.getClass().isArray()) {
                AnnotationVisitor array = visitor.visitArray(k);
                for (int i = 0; i < Array.getLength(v); i++) {
                    Object val = Array.get(v, i);
                    visitVal(array, null, val);
                }
                array.visitEnd();
            } else {
                visitVal(visitor, k, v);
            }
        });
        onAccept.accept(visitor);
        visitor.visitEnd();
    }

    private void visitVal(AnnotationVisitor visitor, String key, Object val) {
        if (val instanceof AnnotationEnumNode) {
            AnnotationEnumNode node = (AnnotationEnumNode) val;
            visitor.visitEnum(key, node.getEnumType().writeDesc(), node.getEnumValue());
        } else {
            visitor.visit(key, val);
        }
    }

}
