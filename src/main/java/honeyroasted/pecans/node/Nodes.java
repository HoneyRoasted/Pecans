package honeyroasted.pecans.node;

import honeyroasted.pecans.node.instruction.Composition;
import honeyroasted.pecans.node.instruction.Throw;
import honeyroasted.pecans.node.instruction.block.Break;
import honeyroasted.pecans.node.instruction.block.Continue;
import honeyroasted.pecans.node.instruction.block.Goto;
import honeyroasted.pecans.node.instruction.block.Mark;
import honeyroasted.pecans.node.instruction.block.TryCatch;
import honeyroasted.pecans.node.instruction.operator.Add;
import honeyroasted.pecans.node.instruction.operator.BitwiseAnd;
import honeyroasted.pecans.node.instruction.operator.BitwiseOr;
import honeyroasted.pecans.node.instruction.operator.BitwiseXor;
import honeyroasted.pecans.node.instruction.operator.Divide;
import honeyroasted.pecans.node.instruction.operator.LeftShift;
import honeyroasted.pecans.node.instruction.operator.Mod;
import honeyroasted.pecans.node.instruction.operator.Multiply;
import honeyroasted.pecans.node.instruction.operator.Negate;
import honeyroasted.pecans.node.instruction.operator.RightShift;
import honeyroasted.pecans.node.instruction.operator.Subtract;
import honeyroasted.pecans.node.instruction.operator.Ternary;
import honeyroasted.pecans.node.instruction.operator.UnsignedRightShift;
import honeyroasted.pecans.node.instruction.operator.bool.And;
import honeyroasted.pecans.node.instruction.operator.bool.InstanceOf;
import honeyroasted.pecans.node.instruction.operator.bool.Not;
import honeyroasted.pecans.node.instruction.operator.bool.Or;
import honeyroasted.pecans.node.instruction.util.LazyTypedNode;
import honeyroasted.pecans.node.instruction.Return;
import honeyroasted.pecans.node.instruction.TypedNode;
import honeyroasted.pecans.node.instruction.block.If;
import honeyroasted.pecans.node.instruction.invocation.InvokeSimple;
import honeyroasted.pecans.node.instruction.invocation.InvokeSpecial;
import honeyroasted.pecans.node.instruction.util.SkipPreprocTypedNode;
import honeyroasted.pecans.node.instruction.variable.scope.DefVar;
import honeyroasted.pecans.node.instruction.variable.scope.GetVar;
import honeyroasted.pecans.node.instruction.variable.scope.Scope;
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
import honeyroasted.pecans.node.instruction.operator.bool.Compare;
import honeyroasted.pecans.node.instruction.operator.bool.ComparisonOperator;
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

    static InvokeSimple invokeVirtual(TypedNode target, String name, MethodSignature signature) {
        return new InvokeSimple(Opcodes.INVOKEVIRTUAL, target, new ArrayList<>(), name, signature, false);
    }

    static InvokeSimple invokeVirtual(TypedNode target, String name, MethodSignature signature, boolean isInterface) {
        return new InvokeSimple(Opcodes.INVOKEVIRTUAL, target, new ArrayList<>(), name, signature, isInterface);
    }

    static InvokeSimple invokeInterface(TypedNode target, String name, MethodSignature signature) {
        return new InvokeSimple(Opcodes.INVOKEINTERFACE, target, new ArrayList<>(), name, signature, true);
    }

    static If ifBlock(TypedNode condition, Node ifTrue) {
        return new If(condition, ifTrue, new ArrayList<>());
    }

    static While whileLoop(TypedNode condition, Node body) {
        return new While(null, condition, body);
    }

    static DoWhile doWhileLoop(TypedNode condition, Node body) {
        return new DoWhile(null, condition, body);
    }

    static For forLoop(Node begin, TypedNode condition, Node action, Node body) {
        return new For(null, begin, condition, action, body);
    }

    static While whileLoop(String name, TypedNode condition, Node body) {
        return new While(name, condition, body);
    }

    static DoWhile doWhileLoop(String name, TypedNode condition, Node body) {
        return new DoWhile(name, condition, body);
    }

    static For forLoop(String name, Node begin, TypedNode condition, Node action, Node body) {
        return new For(name, begin, condition, action, body);
    }

    static TryCatch tryCatch(Node body, TypeInformal type, String var, Node handler) {
        return new TryCatch(body, type, var, handler);
    }

    static Mark mark(String label) {
        return new Mark(label);
    }

    static Goto gotoLabel(String label) {
        return new Goto(label);
    }

    static Break breakLoop(String name) {
        return new Break(name);
    }

    static Break breakLoop() {
        return new Break(null);
    }

    static Continue continueLoop(String name) {
        return new Continue(name);
    }

    static Continue continueLoop() {
        return new Continue(null);
    }

    static Scope scope(Node body) {
        return new Scope(body);
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

            if (!target.equals(node.type(context))) {
                if (!target.isPrimitive() && !val.type(context).isPrimitive()) {
                    return checkcast(target, val);
                } else if (target.isPrimitive() && val.type(context).isPrimitive()) {
                    return primcast(target, val);
                } else if (target.isPrimitive()) {
                    return convert(target, unbox(val));
                } else if (val.type(context).isPrimitive()) {
                    return convert(target, box(val));
                }
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

    static TypedNode instanceOf(TypedNode val, TypeInformal type) {
        return new InstanceOf(val, type);
    }

    static TypedNode and(TypedNode left, TypedNode right) {
        return new And(left, right);
    }

    static TypedNode or(TypedNode left, TypedNode right) {
        return new Or(left, right);
    }

    static TypedNode add(TypedNode left, TypedNode right) {
        return new Add(left, right);
    }

    static TypedNode sub(TypedNode left, TypedNode right) {
        return new Subtract(left, right);
    }

    static TypedNode mul(TypedNode left, TypedNode right) {
        return new Multiply(left, right);
    }

    static TypedNode div(TypedNode left, TypedNode right) {
        return new Divide(left, right);
    }

    static TypedNode mod(TypedNode left, TypedNode right) {
        return new Mod(left, right);
    }

    static TypedNode neg(TypedNode val) {
        return new Negate(val);
    }

    static TypedNode lshift(TypedNode val, TypedNode shift) {
        return new LeftShift(val, shift);
    }

    static TypedNode rshift(TypedNode val, TypedNode shift) {
        return new RightShift(val, shift);
    }

    static TypedNode urshift(TypedNode val, TypedNode shift) {
        return new UnsignedRightShift(val, shift);
    }

    static TypedNode band(TypedNode left, TypedNode right) {
        return new BitwiseAnd(left, right);
    }

    static TypedNode bor(TypedNode left, TypedNode right) {
        return new BitwiseOr(left, right);
    }

    static TypedNode bxor(TypedNode left, TypedNode right) {
        return new BitwiseXor(left, right);
    }

    static TypedNode bnegate(TypedNode val) {
        return bxor(val, constant(-1));
    }

    static TypedNode ternary(TypedNode cond, TypedNode ifTrue, TypedNode ifFalse) {
        return new Ternary(cond, ifTrue, ifFalse);
    }

    static Throw throwEx(TypedNode ex) {
        return new Throw(ex);
    }

}
