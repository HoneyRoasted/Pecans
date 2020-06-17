package honeyroasted.pecans.ast;

import honeyroasted.pecans.ast.instruction.Composition;
import honeyroasted.pecans.ast.instruction.Return;
import honeyroasted.pecans.ast.instruction.TypedNode;
import honeyroasted.pecans.ast.instruction.block.If;
import honeyroasted.pecans.ast.instruction.invocation.Invoke;
import honeyroasted.pecans.ast.instruction.invocation.InvokeSpecial;
import honeyroasted.pecans.signature.ClassSignature;
import honeyroasted.pecans.signature.MethodSignature;
import honeyroasted.pecans.signature.Types;
import honeyroasted.pecans.signature.type.TypeFill;
import honeyroasted.pecans.signature.type.TypeInformal;
import honeyroasted.pecans.signature.type.TypeParameterized;
import honeyroasted.pecans.ast.instruction.Constant;
import honeyroasted.pecans.ast.instruction.Line;
import honeyroasted.pecans.ast.instruction.Node;
import honeyroasted.pecans.ast.instruction.Sequence;
import honeyroasted.pecans.ast.instruction.This;
import honeyroasted.pecans.ast.instruction.block.DoWhile;
import honeyroasted.pecans.ast.instruction.block.For;
import honeyroasted.pecans.ast.instruction.block.While;
import honeyroasted.pecans.ast.instruction.conversion.CheckCast;
import honeyroasted.pecans.ast.instruction.conversion.PrimitiveCast;
import honeyroasted.pecans.ast.instruction.invocation.InvokeStatic;
import honeyroasted.pecans.ast.instruction.invocation.New;
import honeyroasted.pecans.ast.instruction.operator.Compare;
import honeyroasted.pecans.ast.instruction.operator.ComparisonOperator;
import honeyroasted.pecans.ast.instruction.variable.GetField;
import honeyroasted.pecans.ast.instruction.variable.GetLocal;
import honeyroasted.pecans.ast.instruction.variable.GetStatic;
import honeyroasted.pecans.ast.instruction.variable.PutField;
import honeyroasted.pecans.ast.instruction.variable.PutLocal;
import honeyroasted.pecans.ast.instruction.variable.PutStatic;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import java.util.ArrayList;
import java.util.Collection;

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

    static AnnotationNode annotation(TypeFill type) {
        return AnnotationNode.of(type);
    }

    static AnnotationNode annotation(TypeFill type, boolean visible) {
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

    static Return ret() {
        return Return.of(null);
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
        return new Invoke(Opcodes.INVOKEVIRTUAL, target, new ArrayList<>(), name, signature, false);
    }

    static Invoke invokeVirtual(TypedNode target, String name, MethodSignature signature, boolean isInterface) {
        return new Invoke(Opcodes.INVOKEVIRTUAL, target, new ArrayList<>(), name, signature, isInterface);
    }

    static Invoke invokeInterface(TypedNode target, String name, MethodSignature signature) {
        return new Invoke(Opcodes.INVOKEINTERFACE, target, new ArrayList<>(), name, signature, true);
    }

    static If ifBlock(TypedNode condition, Node ifTrue) {
        return new If(condition, ifTrue, new ArrayList<>());
    }

    static While whileLoop(TypedNode condition, Node body) {
        return new While(condition, body);
    }

    static DoWhile doWhileLoop(TypedNode condition, Node body) {
        return new DoWhile(condition, body);
    }

    static For forLoop(Node begin, TypedNode condition, Node action, Node body) {
        return new For(begin, condition, action, body);
    }

    static New newObj(TypeInformal type, MethodSignature signature) {
        return new New(type, new ArrayList<>(), signature);
    }

    static PrimitiveCast primcast(TypeInformal type, TypedNode node) {
        return new PrimitiveCast(type, node);
    }

    static CheckCast checkcast(TypeInformal type, TypedNode node) {
        return new CheckCast(type, node);
    }

    static New box(TypedNode val) {
        if (!val.type().isPrimitive()) {
            throw new IllegalArgumentException(val.type().write() + " is not primitive");
        }

        return newObj(Types.PRIMITIVE_TO_BOX.get(val.type()), Types.methodSignature(Types.VOID, val.type()))
                .arg(val);
    }

    static Invoke unbox(TypedNode val) {
        if (!Types.BOX_TO_PRIMITIVE.containsKey(val.type())) {
            throw new IllegalArgumentException(val.type().write() + " is not a primitive box");
        }

        TypeInformal prim = Types.BOX_TO_PRIMITIVE.get(val.type());

        return invokeVirtual(val, Type.getType(prim.writeDesc()).getClassName() + "Value", Types.methodSignature(prim));
    }
    
    static TypedNode convert(TypeInformal target, TypedNode val) {
        if (!target.isPrimitive() && !val.type().isPrimitive()) {
            return checkcast(target, val);
        } else if (target.isPrimitive() && val.type().isPrimitive()) {
            return primcast(target, val);
        } else if (target.isPrimitive()) {
            return convert(target, unbox(val));
        } else if (val.type().isPrimitive()) {
            return convert(target, box(val));
        }

        return val;
    }

    static TypedNode equals(TypedNode left, TypedNode right) {
        return new Compare(ComparisonOperator.EQUALS, left, right);
    }

    static TypedNode lessThan(TypedNode left, TypedNode right) {
        return new Compare(ComparisonOperator.LESS_THAN, left, right);
    }

    static TypedNode lessThanOrEqual(TypedNode left, TypedNode right) {
        return new Compare(ComparisonOperator.LESS_THAN_OR_EQUAL, left, right);
    }

    static TypedNode greaterThan(TypedNode left, TypedNode right) {
        return new Compare(ComparisonOperator.GREATER_THAN, left, right);
    }

    static TypedNode greaterThanOrEqual(TypedNode left, TypedNode right) {
        return new Compare(ComparisonOperator.GREATER_THAN_OR_EQUAL, left, right);
    }

}
