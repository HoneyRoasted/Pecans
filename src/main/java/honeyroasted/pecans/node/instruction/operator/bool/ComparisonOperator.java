package honeyroasted.pecans.node.instruction.operator.bool;

import org.objectweb.asm.Label;
import org.objectweb.asm.commons.InstructionAdapter;

public interface ComparisonOperator {

    ComparisonOperator EQUALS = new Equal();

    ComparisonOperator LESS_THAN = new LessThan();
    ComparisonOperator LESS_THAN_OR_EQUAL = new LessThanOrEqual();

    ComparisonOperator GREATER_THAN = new GreaterThan();
    ComparisonOperator GREATER_THAN_OR_EQUAL = new GreaterThanOrEqual();

    void visitDoubleCompare(InstructionAdapter adapter);

    void visitDoubleInverseCompare(InstructionAdapter adapter);

    void visitFloatCompare(InstructionAdapter adapter);

    void visitFloatInverseCompare(InstructionAdapter adapter);

    void visitCompareJump(InstructionAdapter adapter, Label label);

    void visitInverseCompareJump(InstructionAdapter adapter, Label label);

    void visitIntCompare(InstructionAdapter adapter, Label label);

    void visitInverseIntCompare(InstructionAdapter adapter, Label label);

    default void visitObjCompare(InstructionAdapter adapter, Label label) {
        throw new UnsupportedOperationException("Cannot compare objects with this operator");
    }

    default void visitInverseObjCompare(InstructionAdapter adapter, Label label) {
        throw new UnsupportedOperationException("Cannot compare objects with this operator");
    }

}
