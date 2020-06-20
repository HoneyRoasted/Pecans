package honeyroasted.pecans.node;

import honeyroasted.pecans.node.instruction.variable.scope.VariableScope;
import org.objectweb.asm.Label;

import java.util.LinkedHashMap;
import java.util.Map;

public class Context {
    private String name;
    private Context parent;

    private ClassNode classNode;
    private MethodNode methodNode;
    private VariableScope scope;

    private Label breakLabel;
    private Label continueLabel;

    private Map<String, Label> labels;

    private Context(Context parent, String name, Label breakLabel, Label continueLabel, ClassNode classNode, MethodNode methodNode, VariableScope scope) {
        this.name = name;
        this.parent = parent;
        this.classNode = classNode;
        this.methodNode = methodNode;
        this.scope = scope;
        this.breakLabel = breakLabel;
        this.continueLabel = continueLabel;
        this.labels = new LinkedHashMap<>();
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

    public Label breakLabel() {
        return this.breakLabel;
    }

    public Label continueLabel() {
        return this.continueLabel;
    }

    public Label label(String name) {
        return this.labels.computeIfAbsent(name, s -> new Label());
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBreakLabel(Label breakLabel) {
        this.breakLabel = breakLabel;
    }

    public void setContinueLabel(Label continueLabel) {
        this.continueLabel = continueLabel;
    }

    public Context findNamed(String name) {
        if (this.name.equals(name)) {
            return this;
        } else if (this.parent != null) {
            return this.parent.findNamed(name);
        } else {
            return null;
        }
    }

    public Context withBreak(Label breakLabel) {
        Context context = new Context(this, null, this.breakLabel, this.continueLabel, this.classNode, this.methodNode, this.scope);
        context.breakLabel = breakLabel;
        return context;
    }

    public Context withContinue(Label continueLabel) {
        Context context = new Context(this, null, this.breakLabel, this.continueLabel, this.classNode, this.methodNode, this.scope);
        context.continueLabel = continueLabel;
        return context;
    }

    public Context withChildScope() {
        return new Context(this, null, this.breakLabel, this.continueLabel, this.classNode, this.methodNode, this.scope.newChild());
    }

}
