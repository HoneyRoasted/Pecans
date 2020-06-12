package io.github.honeyroasted.pecans.ast;

import io.github.honeyroasted.pecans.ast.instruction.Composition;
import io.github.honeyroasted.pecans.ast.instruction.Constant;
import io.github.honeyroasted.pecans.ast.instruction.invocation.Invoke;
import io.github.honeyroasted.pecans.ast.instruction.invocation.InvokeSpecial;
import io.github.honeyroasted.pecans.ast.instruction.invocation.InvokeStatic;
import io.github.honeyroasted.pecans.ast.instruction.variable.GetField;
import io.github.honeyroasted.pecans.ast.instruction.variable.GetLocal;
import io.github.honeyroasted.pecans.ast.instruction.variable.GetStatic;
import io.github.honeyroasted.pecans.ast.instruction.Line;
import io.github.honeyroasted.pecans.ast.instruction.Node;
import io.github.honeyroasted.pecans.ast.instruction.variable.PutField;
import io.github.honeyroasted.pecans.ast.instruction.variable.PutLocal;
import io.github.honeyroasted.pecans.ast.instruction.variable.PutStatic;
import io.github.honeyroasted.pecans.ast.instruction.Return;
import io.github.honeyroasted.pecans.ast.instruction.Sequence;
import io.github.honeyroasted.pecans.ast.instruction.This;
import io.github.honeyroasted.pecans.ast.instruction.TypedNode;
import io.github.honeyroasted.pecans.signature.ClassSignature;
import io.github.honeyroasted.pecans.signature.MethodSignature;
import io.github.honeyroasted.pecans.signature.type.TypeFill;
import io.github.honeyroasted.pecans.signature.type.TypeInformal;
import io.github.honeyroasted.pecans.signature.type.TypeParameterized;
import org.objectweb.asm.Type;

import java.util.ArrayList;
import java.util.Collection;

import static org.objectweb.asm.Opcodes.*;

public interface Nodes {

    static FieldNode field(int modifiers, String name, TypeInformal type) {
        return FieldNode.of(modifiers, name, type);
    }

    static FieldNode field(int modifiers, String name, TypeInformal type, Object value) {
        return FieldNode.of(modifiers, name, type, value);
    }

    static MethodNode method(int modifiers, String name, MethodSignature signature) {
        return MethodNode.of(modifiers, name, signature);
    }

    static MethodNode method(int modifiers, String name, MethodSignature signature, Node body) {
        return MethodNode.of(modifiers, name, signature, body);
    }

    static AnnotationNode annotation(Type type) {
        return AnnotationNode.of(type);
    }

    static AnnotationNode annotation(Type type, boolean visible) {
        return AnnotationNode.of(type, visible);
    }

    static ClassNode classDef(int modifiers, ClassSignature signature) {
        return ClassNode.of(modifiers, signature);
    }

    static Sequence sequence(Node... nodes) {
        return Sequence.of(nodes);
    }

    static Sequence sequence(Collection<Node> nodes) {
        return Sequence.of(nodes);
    }

    static Composition composition(TypedNode last, Node... nodes) {
        return Composition.of(last, nodes);
    }

    static Composition composition(TypedNode last, Collection<Node> nodes) {
        return Composition.of(last, nodes);
    }

    static Constant constant(Object val) {
        return Constant.of(val);
    }

    static Return ret(TypedNode val) {
        return Return.of(val);
    }

    static This loadThis(TypeParameterized type) {
        return new This(type);
    }

    static GetField get(TypedNode target, String name, TypeInformal type) {
        return new GetField(target, name, type);
    }

    static GetStatic get(TypeFill target, String name, TypeInformal type) {
        return new GetStatic(target, name, type);
    }

    static GetLocal get(int index, TypeInformal type) {
        return new GetLocal(index, type);
    }

    static PutField set(TypedNode target, String name, TypedNode value) {
        return new PutField(target, name, value);
    }

    static PutStatic set(TypeFill target, String name, TypedNode value) {
        return new PutStatic(target, name, value);
    }

    static PutLocal set(int index, TypedNode value) {
        return new PutLocal(index, value);
    }

    static Line line(int line) {
        return new Line(line);
    }

    static InvokeSpecial invokeSpecial(TypeFill owner, TypedNode target, String name, MethodSignature signature) {
        return new InvokeSpecial(owner, target, new ArrayList<>(), name, signature, false);
    }

    static InvokeSpecial invokeSpecial(TypeFill owner, TypedNode target, String name, MethodSignature signature, boolean isInterface) {
        return new InvokeSpecial(owner, target, new ArrayList<>(), name, signature, isInterface);
    }

    static InvokeStatic invokeStatic(TypeFill target, String name, MethodSignature signature) {
        return new InvokeStatic(target, new ArrayList<>(), name, signature, false);
    }

    static InvokeStatic invokeStatic(TypeFill target, String name, MethodSignature signature, boolean isInterface) {
        return new InvokeStatic(target, new ArrayList<>(), name, signature, isInterface);
    }

    static Invoke invokeVirtual(TypedNode target, String name, MethodSignature signature) {
        return new Invoke(INVOKEVIRTUAL, target, new ArrayList<>(), name, signature, false);
    }

    static Invoke invokeVirtual(TypedNode target, String name, MethodSignature signature, boolean isInterface) {
        return new Invoke(INVOKEVIRTUAL, target, new ArrayList<>(), name, signature, isInterface);
    }

    static Invoke invokeInterface(TypedNode target, String name, MethodSignature signature) {
        return new Invoke(INVOKEINTERFACE, target, new ArrayList<>(), name, signature, true);
    }

}
