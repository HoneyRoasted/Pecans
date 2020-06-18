package honeyroasted.pecans.optimisation;

import org.objectweb.asm.MethodVisitor;

public class Optimizer extends MethodVisitor {
    private boolean changed;

    public Optimizer(int api) {
        super(api);
    }

    public boolean isChanged() {
        return changed;
    }

    protected void markChanged() {
        this.changed = true;
    }

}
