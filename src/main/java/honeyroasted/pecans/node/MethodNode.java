package honeyroasted.pecans.node;

import honeyroasted.pecans.node.instruction.variable.scope.VariableScope;
import honeyroasted.pecans.type.MethodSignature;
import honeyroasted.pecans.type.type.TypeInformal;
import honeyroasted.pecans.type.type.TypeSignaturePart;
import honeyroasted.pecans.node.instruction.Node;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.commons.InstructionAdapter;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class MethodNode {
    private int modifiers;
    private String name;
    private MethodSignature signature;
    private Node node;
    private Collection<AnnotationNode> annotations;

    private Map<String, Integer> params;
    private Map<String, TypeInformal> paramTypes;
    private Map<String, List<AnnotationNode>> parameterAnnotations;

    private Map<String, Integer> paramIndices;

    private Consumer<InstructionAdapter> onAccept = v -> {};

    public MethodNode(int modifiers, String name, MethodSignature signature, Collection<AnnotationNode> annotations) {
        this.modifiers = modifiers;
        this.name = name;
        this.signature = signature;
        this.annotations = annotations;
        this.params = new LinkedHashMap<>();
        this.parameterAnnotations = new LinkedHashMap<>();
        this.paramIndices = new HashMap<>();
        this.paramTypes = new HashMap<>();
    }

    public static MethodNode of(int modifiers, String name, MethodSignature signature) {
        return new MethodNode(modifiers, name, signature, new ArrayList<>());
    }

    public static MethodNode of(int modifiers, String name, MethodSignature signature, Node body) {
        return new MethodNode(modifiers, name, signature, new ArrayList<>()).body(body);
    }

    public MethodNode addAnnotation(AnnotationNode annotation) {
        this.annotations.add(annotation);
        return this;
    }

    public MethodNode param(String name, TypeInformal type) {
        return param(name, type, 0);
    }

    public MethodNode param(String name, TypeInformal type, int modifiers) {
        this.paramIndices.put(name, this.params.size());
        this.params.put(name, modifiers);
        this.paramTypes.put(name, type);
        this.signature.addParameter(type);
        return this;
    }

    public MethodNode add(String parameter, AnnotationNode annotation) {
        this.parameterAnnotations.computeIfAbsent(parameter, i -> new ArrayList<>()).add(annotation);
        return this;
    }

    public MethodNode body(Node node) {
        this.node = node;
        return this;
    }

    public InstructionAdapter accept(ClassVisitor writer, ClassNode node) {
        InstructionAdapter method = new InstructionAdapter(writer.visitMethod(this.modifiers, this.name, this.signature.writeDesc(), this.signature.write(), this.signature.getExceptions().stream().map(TypeSignaturePart::writeDesc).toArray(java.lang.String[]::new)));

        for (AnnotationNode annotation : annotations) {
            annotation.accept(method.visitAnnotation(annotation.getAnnotation().writeDesc(), annotation.isVisible()));
        }

        this.params.forEach(method::visitParameter);

        this.parameterAnnotations.forEach((p, annotations) -> annotations.forEach(annotation -> {
            annotation.accept(method.visitParameterAnnotation(this.paramIndices.get(p), annotation.getAnnotation().writeDesc(), annotation.isVisible()));
        }));

        if (this.node != null) {
            Context context = new Context(null, null, null, null, node, this, new VariableScope());

            Label start = new Label();
            method.mark(start);

            if (!Modifier.isStatic(this.modifiers)) {
                context.scope().defVar("this", node.getSignature().getType().asFill());
                context.scope().markVar("this", start);
            }

            this.paramTypes.forEach((name, type) -> {
                context.scope().defVar(name, type);
                context.scope().markVar(name, start);
            });

            this.node.preprocess(context);
            this.node.accept(method, context);

            Label end = new Label();
            method.mark(end);
            context.scope().forEach(v -> v.accept(null, method, end));
        }
        onAccept.accept(method);

        return method;
    }

}
