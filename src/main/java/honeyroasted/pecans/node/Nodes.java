package honeyroasted.pecans.node;

import honeyroasted.pecans.node.instruction.Composition;
import honeyroasted.pecans.node.instruction.operator.Not;
import honeyroasted.pecans.node.instruction.util.LazyTypedNode;
import honeyroasted.pecans.node.instruction.Return;
import honeyroasted.pecans.node.instruction.TypedNode;
import honeyroasted.pecans.node.instruction.block.If;
import honeyroasted.pecans.node.instruction.invocation.Invoke;
import honeyroasted.pecans.node.instruction.invocation.InvokeSpecial;
import honeyroasted.pecans.node.instruction.util.SkipPreprocTypedNode;
import honeyroasted.pecans.node.instruction.variable.scope.DefVar;
import honeyroasted.pecans.node.instruction.variable.scope.GetVar;
import honeyroasted.pecans.node.instruction.variable.scope.SetVar;
import honeyroasted.pecans.type.ClassSignature;
import honeyroasted.pecans.type.MethodSignature;
import honeyroasted.pecans.type.Types;
import honeyroasted.pecans.type.type.TypeFill;
import honeyroasted.pecans.type.type.TypeInformal;
import honeyroasted.pecans.node.instruction.Constant;
import honeyroasted.pecans.node.instruction.Line;
import honeyroasted.pecans.node.instruction.Node;
import honeyroasted.pecans.node.instruction.Sequence;
import honeyroasted.pecans.node.instruction.block.DoWhile;
import honeyroasted.pecans.node.instruction.block.For;
import honeyroasted.pecans.node.instruction.block.While;
import honeyroasted.pecans.node.instruction.conversion.CheckCast;
import honeyroasted.pecans.node.instruction.conversion.PrimitiveCast;
import honeyroasted.pecans.node.instruction.invocation.InvokeStatic;
import honeyroasted.pecans.node.instruction.invocation.New;
import honeyroasted.pecans.node.instruction.operator.Compare;
import honeyroasted.pecans.node.instruction.operator.ComparisonOperator;
import honeyroasted.pecans.node.instruction.variable.GetField;
import honeyroasted.pecans.node.instruction.variable.GetLocal;
import honeyroasted.pecans.node.instruction.variable.GetStatic;
import honeyroasted.pecans.node.instruction.variable.PutField;
import honeyroasted.pecans.node.instruction.variable.PutLocal;
import honeyroasted.pecans.node.instruction.variable.PutStatic;
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

    static GetVar loadThis() {
        return get("this");
    }

    static DefVar def(TypeInformal type, String name, TypedNode val) {
        return new DefVar(name, type, val);
    }

    static DefVar def(String name, TypedNode val) {
        return new DefVar(name, null, val);
    }

    static SetVar set(String name, TypedNode val) {
        return new SetVar(name, val);
    }

    static GetVar get(String name) {
        return new GetVar(name);
    }

    static GetField get(TypedNode target, String name, TypeInformal type) {
        return new GetField(target, name, type);
    }

    static GetStatic get(TypeFill target, String name, TypeInformal type) {
        return new GetStatic(target, name, type);
    }

    static GetLocal getraw(int index, TypeInformal type) {
        return new GetLocal(index, type);
    }

    static PutField set(TypedNode target, String name, TypedNode value) {
        return new PutField(target, name, value);
    }

    static PutStatic set(TypeFill target, String name, TypedNode value) {
        return new PutStatic(target, name, value);
    }

    static PutLocal setraw(int index, TypedNode value) {
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

    static TypedNode box(TypedNode val) {
        return new LazyTypedNode(context -> {
            val.preprocess(context);

            if (!val.type(context).isPrimitive()) {
                throw new IllegalArgumentException(val.type(context).write() + " is not primitive");
            }

            return newObj(Types.PRIMITIVE_TO_BOX.get(val.type(context)), Types.methodSignature(Types.VOID, val.type(context)))
                    .arg(new SkipPreprocTypedNode(val));
        });
    }

    static TypedNode unbox(TypedNode val) {
        return new LazyTypedNode(context -> {
            val.preprocess(context);

            if (!Types.BOX_TO_PRIMITIVE.containsKey(val.type(context))) {
                throw new IllegalArgumentException(val.type(context).write() + " is not a primitive box");
            }

            TypeInformal prim = Types.BOX_TO_PRIMITIVE.get(val.type(context));

            return invokeVirtual(new SkipPreprocTypedNode(val), Type.getType(prim.writeDesc()).getClassName() + "Value", Types.methodSignature(prim));
        });
    }
    
    static TypedNode convert(TypeInformal target, TypedNode node) {
        return new LazyTypedNode(context -> {
            node.preprocess(context);
            TypedNode val = new SkipPreprocTypedNode(node);


            if (!target.isPrimitive() && !val.type(context).isPrimitive()) {
                return checkcast(target, val);
            } else if (target.isPrimitive() && val.type(context).isPrimitive()) {
                return primcast(target, val);
            } else if (target.isPrimitive()) {
                return convert(target, unbox(val));
            } else if (val.type(context).isPrimitive()) {
                return convert(target, box(val));
            }

            return val;
        });
    }

    static TypedNode equal(TypedNode left, TypedNode right) {
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

    static TypedNode not(TypedNode val) {
        return new Not(val);
    }

}
