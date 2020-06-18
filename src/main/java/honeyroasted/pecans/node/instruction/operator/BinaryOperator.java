package honeyroasted.pecans.node.instruction.operator;

import honeyroasted.pecans.node.Context;
import honeyroasted.pecans.node.Nodes;
import honeyroasted.pecans.node.instruction.TypedNode;
import honeyroasted.pecans.node.instruction.util.SkipPreprocTypedNode;
import honeyroasted.pecans.type.Types;
import honeyroasted.pecans.type.type.TypeInformal;

public abstract class BinaryOperator implements TypedNode {
    protected TypedNode left;
    protected TypedNode right;
    protected TypeInformal wide;

    public BinaryOperator(TypedNode left, TypedNode right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public void preprocess(Context context) {
        this.left.preprocess(context);
        this.right.preprocess(context);

        TypeInformal wide = Types.widest(left.type(context), right.type(context));
        this.wide = wide;
        this.left = Nodes.convert(wide, new SkipPreprocTypedNode(this.left));
        this.right = Nodes.convert(wide, new SkipPreprocTypedNode(this.right));

        this.left.preprocess(context);
        this.right.preprocess(context);
    }

}
