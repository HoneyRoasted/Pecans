package honeyroasted.pecans.node;

import honeyroasted.pecans.node.instruction.variable.scope.VariableScope;

public class Context {
    private ClassNode classNode;
    private MethodNode methodNode;
    private VariableScope scope;

    public Context(ClassNode classNode, MethodNode methodNode, VariableScope scope) {
        this.classNode = classNode;
        this.methodNode = methodNode;
        this.scope = scope;
    }

    public ClassNode classNode() {
        return this.classNode;
    }

    public MethodNode methodNode() {
        return this.methodNode;
    }

    public VariableScope scope() {
        return this.scope;
    }

}
