package io.github.honeyroasted.pecans.ast;

import io.github.honeyroasted.pecans.ast.instruction.Node;
import io.github.honeyroasted.pecans.signature.MethodSignature;
import io.github.honeyroasted.pecans.signature.type.TypeInformal;
import io.github.honeyroasted.pecans.signature.type.TypeSignaturePart;
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

    public MethodNode addAnnotation(String parameter, AnnotationNode annotation) {
        this.parameterAnnotations.computeIfAbsent(parameter, i -> new ArrayList<>()).add(annotation);
        return this;
    }

    public MethodNode body(Node node) {
        this.node = node;
        return this;
    }

    public InstructionAdapter accept(ClassVisitor writer, ClassNode node) {
        InstructionAdapter method = new InstructionAdapter(writer.visitMethod(this.modifiers, this.name, this.signature.writeDesc(), this.signature.write(), this.signature.getExceptions().stream().map(TypeSignaturePart::writeDesc).toArray(java.lang.String[]::new)));

        Label start = new Label();
        method.visitLabel(start);

        Label end = new Label();

        for (AnnotationNode annotation : annotations) {
            annotation.accept(method.visitAnnotation(annotation.getAnnotation().getDescriptor(), annotation.isVisible()));
        }

        this.params.forEach(method::visitParameter);

        this.parameterAnnotations.forEach((p, annotations) -> annotations.forEach(annotation -> {
            annotation.accept(method.visitParameterAnnotation(this.paramIndices.get(p), annotation.getAnnotation().getDescriptor(), annotation.isVisible()));
        }));

        if (this.node != null) {
            this.node.accept(method);
        }
        onAccept.accept(method);
        method.visitLabel(end);

        AtomicInteger index = new AtomicInteger(0);

        if (!Modifier.isStatic(this.modifiers)) {
            method.visitLocalVariable("this", node.getSignature().writeDesc(), null, start, end, index.getAndIncrement());
        }
        this.params.forEach((name, mods) -> method.visitLocalVariable(name, this.paramTypes.get(name).writeDesc(), this.paramTypes.get(name).write(), start, end, index.getAndIncrement()));
        return method;
    }

}
