package honeyroasted.pecans.node.instruction.operator.bool;

import org.objectweb.asm.Label;
import org.objectweb.asm.commons.InstructionAdapter;

import static org.objectweb.asm.Opcodes.*;

public class Equal implements ComparisonOperator {

    @Override
    public void visitDoubleCompare(InstructionAdapter adapter) {
        adapter.visitInsn(DCMPL);
    }

    @Override
    public void visitDoubleInverseCompare(InstructionAdapter adapter) {
        adapter.visitInsn(DCMPL);
    }

    @Override
    public void visitFloatCompare(InstructionAdapter adapter) {
        adapter.visitInsn(FCMPL);
    }

    @Override
    public void visitFloatInverseCompare(InstructionAdapter adapter) {
        adapter.visitInsn(FCMPL);
    }

    @Override
    public void visitCompareJump(InstructionAdapter adapter, Label label) {
        adapter.ifeq(label);
    }

    @Override
    public void visitInverseCompareJump(InstructionAdapter adapter, Label label) {
        adapter.ifne(label);
    }

    @Override
    public void visitIntCompare(InstructionAdapter adapter, Label label) {
        adapter.ificmpeq(label);
    }

    @Override
    public void visitInverseIntCompare(InstructionAdapter adapter, Label label) {
        adapter.ificmpne(label);
    }

}
