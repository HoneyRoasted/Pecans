package io.github.honeyroasted.pecans.ast.instruction;

import org.objectweb.asm.Label;
import org.objectweb.asm.commons.InstructionAdapter;

public class Line implements Node {
    private int line;

    public Line(int line) {
        this.line = line;
    }

    @Override
    public void accept(InstructionAdapter adapter) {
        Label label = new Label();
        adapter.visitLabel(label);
        adapter.visitLineNumber(line, label);
    }
}
