package honeyroasted.pecans.node.instruction;

import honeyroasted.pecans.node.Context;
import honeyroasted.pecans.type.Types;
import honeyroasted.pecans.type.type.TypeInformal;
import org.objectweb.asm.commons.InstructionAdapter;

public interface TypedNode extends Node {

    TypeInformal type(Context context);

    default Node pop() {
        TypedNode self = this;
        return new Node() {
            @Override
            public void accept(InstructionAdapter adapter, Context context) {
                self.accept(adapter, context);


                if (!self.type(context).equals(Types.VOID)) {
                    if (self.type(context).equals(Types.LONG) || self.type(context).equals(Types.DOUBLE)) {
                        adapter.pop2();
                    } else {
                        adapter.pop();
                    }
                }
            }

            @Override
            public void preprocess(Context context) {
                self.preprocess(context);
            }
        };
    }

}
