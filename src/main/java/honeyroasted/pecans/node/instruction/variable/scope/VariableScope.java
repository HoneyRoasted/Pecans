package honeyroasted.pecans.node.instruction.variable.scope;

import honeyroasted.pecans.node.Context;
import honeyroasted.pecans.node.instruction.Node;
import honeyroasted.pecans.type.type.TypeInformal;
import org.objectweb.asm.Label;
import org.objectweb.asm.commons.InstructionAdapter;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

public class VariableScope {
    private IndexTracker tracker;
    private Map<String, Var> variables;
    private VariableScope parent;

    private List<VariableScope> children = new ArrayList<>();

    public VariableScope() {
        this.tracker = new IndexTracker();
        this.variables = new LinkedHashMap<>();
    }

    private VariableScope(VariableScope parent, IndexTracker tracker) {
        this.tracker = tracker;
        this.variables = new LinkedHashMap<>();
        this.parent = parent;
    }

    public VariableScope newChild() {
        VariableScope s = new VariableScope(this, this.tracker);
        this.children.add(s);
        return s;
    }

    public void forEach(Consumer<Var> fn) {
        this.variables.values().forEach(fn);
        this.children.forEach(v -> v.forEach(fn));
    }

    public Var getVar(String name) {
        return getVarOpt(name).orElseThrow(() -> new IllegalArgumentException("No variable with name: " + name));
    }

    public Optional<Var> getVarOpt(String name) {
        if (this.variables.containsKey(name)) {
            return Optional.of(this.variables.get(name));
        } else if (this.parent != null) {
            return this.parent.getVarOpt(name);
        } else {
            return Optional.empty();
        }
    }

    public Var defVar(String name, TypeInformal type) {
        Var var = new Var(name, type, type.isTwoWords() ? this.tracker.pop2() : this.tracker.pop(), null);
        this.variables.put(name, var);
        return var;
    }

    public void markVar(String name, Node node) {
        getVarOpt(name).ifPresent(v -> v.setLastRef(node));
    }

    public void markVar(String name, Label label) {
        getVarOpt(name).ifPresent(v -> v.setStart(label));
    }

    public static class Var {
        private boolean visited = false;

        private String name;
        private TypeInformal type;
        private int index;

        private Label start;
        private Node lastRef;

        private Var(String name, TypeInformal type, int index, Node lastRef) {
            this.name = name;
            this.type = type;
            this.index = index;
            this.lastRef = lastRef;
        }

        public String getName() {
            return name;
        }

        public TypeInformal getType() {
            return type;
        }

        public int getIndex() {
            return index;
        }

        public Label getStart() {
            return start;
        }

        public void setStart(Label start) {
            this.start = start;
        }

        public Node getLastRef() {
            return lastRef;
        }

        public void setLastRef(Node lastRef) {
            this.lastRef = lastRef;
        }

        public void accept(Node node, InstructionAdapter adapter) {
            if (node == this.lastRef) {
                Label end = new Label();
                adapter.mark(end);

                accept(adapter, end);
            }
        }

        public void accept(Node node, InstructionAdapter adapter, Label end) {
            if (node == this.lastRef) {
                accept(adapter, end);
            }
        }

        public void accept(InstructionAdapter adapter, Label end) {
            if (!visited) {
                adapter.visitLocalVariable(this.name, this.type.writeDesc(), this.type.write(), this.start, end, this.index);
                visited = true;
            }
        }
    }

    private static class IndexTracker {
        private int index = 0;

        public int pop() {
            int res = this.index;
            this.index += 1;
            return res;
        }

        public int pop2() {
            int res = this.index;
            this.index += 2;
            return res;
        }

        public int getIndex() {
            return this.index;
        }
    }

}
