package honeyroasted.pecans.ast;

import honeyroasted.pecans.signature.type.TypeFill;

public class AnnotationEnumNode {
    private TypeFill enumType;
    private String enumValue;

    public AnnotationEnumNode(TypeFill enumType, String enumValue) {
        this.enumType = enumType;
        this.enumValue = enumValue;
    }

    public TypeFill getEnumType() {
        return enumType;
    }

    public String getEnumValue() {
        return enumValue;
    }
}
