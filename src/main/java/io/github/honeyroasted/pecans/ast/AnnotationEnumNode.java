package io.github.honeyroasted.pecans.ast;

import org.objectweb.asm.Type;

public class AnnotationEnumNode {
    private Type enumType;
    private String enumValue;

    public AnnotationEnumNode(Type enumType, String enumValue) {
        this.enumType = enumType;
        this.enumValue = enumValue;
    }

    public Type getEnumType() {
        return enumType;
    }

    public String getEnumValue() {
        return enumValue;
    }
}
