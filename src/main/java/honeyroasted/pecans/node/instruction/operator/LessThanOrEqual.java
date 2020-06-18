package honeyroasted.pecans.node.instruction.operator;

import org.objectweb.asm.Label;
import org.objectweb.asm.commons.InstructionAdapter;

import static org.objectweb.asm.Opcodes.*;

public class LessThanOrEqual implements ComparisonOperator {

    @Override
    public void visitDoubleCompare(InstructionAdapter adapter) {
        adapter.visitInsn(DCMPL);
    }

    @Override
    public void visitDoubleInverseCompare(InstructionAdapter adapter) {
        adapter.visitInsn(DCMPG);
    }

    @Override
    public void visitFloatCompare(InstructionAdapter adapter) {
        adapter.visitInsn(FCMPL);
    }

    @Override
    public void visitFloatInverseCompare(InstructionAdapter adapter) {
        adapter.visitInsn(FCMPG);
    }

    @Override
    public void visitCompareJump(InstructionAdapter adapter, Label label) {
        adapter.ifle(label);
    }

    @Override
    public void visitInverseCompareJump(InstructionAdapter adapter, Label label) {
        adapter.ifgt(label);
    }

    @Override
    public void visitIntCompare(InstructionAdapter adapter, Label label) {
        adapter.ificmple(label);
    }

    @Override
    public void visitInverseIntCompare(InstructionAdapter adapter, Label label) {
        adapter.ificmpgt(label);
    }
}
