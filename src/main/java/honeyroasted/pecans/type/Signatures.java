/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 socraticphoenix@gmail.com
 * Copyright (c) 2017 contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package honeyroasted.pecans.type;

import honeyroasted.pecans.type.builder.MethodSignatureBuilder;
import honeyroasted.pecans.type.type.TypeInformal;
import honeyroasted.pecans.type.visitor.TypeSignatureVisitor;
import honeyroasted.pecans.type.builder.ClassSignatureBuilder;
import honeyroasted.pecans.type.builder.TypeSignatureBuilder;
import honeyroasted.pecans.type.visitor.ClassSignatureVisitor;
import honeyroasted.pecans.type.visitor.MethodSignatureVisitor;
import org.objectweb.asm.Type;
import org.objectweb.asm.signature.SignatureReader;

/**
 * This class holds utility methods for parsing and writing signatures.
 */
public class Signatures {

    /**
     * Writes the first portion of a type, as it appears in a signature. More specifically, this is the descriptor
     * if the type is primitive, or the descriptor <i>without</i> a semicolon if the type is an object.
     *
     * @param type The type to write.
     * @return The first portion of the type.
     */
    public static String writeStart(Type type) {
        return Signatures.isPrimitive(type) ? type.getDescriptor() : type.getSort() == Type.OBJECT ? "L" + type.getInternalName() : type.getDescriptor().replace(";", "");
    }

    /**
     * Writes the last portion of a type, as it appears in a signature. More specifically, this is a semicolon if the
     * type is an object, ot an empty string if the type is a primitive.
     *
     * @param type The type to write.
     * @return The last portion of the type.
     */
    public static String writeEnd(Type type) {
        return Signatures.isPrimitive(type) ? "" : ";";
    }

    /**
     * Checks if the given type is primitive.
     *
     * @param type The type to check.
     * @return True if the type is primitive, false otherwise.
     */
    public static boolean isPrimitive(Type type) {
        int s = type.getSort();
        return s != Type.ARRAY && s != Type.OBJECT && s != Type.METHOD;
    }

    /**
     * Parses a {@link ClassSignature} object from the given class name and signature. This method only works if the
     * signature is nonnull. It is suggested to use the {@link ClassSignatureBuilder} to construct a class signature object,
     * as it protects against null signatures.
     *
     * @param name The name.
     * @param signature The signature.
     * @return The parsed class signature.
     */
    public static ClassSignature parseClass(String name, String signature) {
        Type type = Type.getObjectType(name);
        ClassSignatureVisitor visitor = new ClassSignatureVisitor();
        SignatureReader reader = new SignatureReader(signature);
        reader.accept(visitor);
        visitor.visitEnd();

        ClassSignature sigObj = visitor.getSignature();
        sigObj.setType(type);
        return sigObj;
    }

    /**
     * Parses a {@link MethodSignature} object from the given signature. This method only works if the signature is
     * nonnull. It is suggested to use the {@link MethodSignatureBuilder} to construct a method signature object, as it
     * protects against null signatures.
     *
     * @param signature The signature.
     * @return The parsed method signature.
     */
    public static MethodSignature parseMethod(String signature) {
        MethodSignatureVisitor visitor = new MethodSignatureVisitor();
        SignatureReader reader = new SignatureReader(signature);
        reader.accept(visitor);
        visitor.visitEnd();

        return visitor.getSignature();
    }

    /**
     * Parses a {@link TypeInformal} object from the given signature. This method only works if this signature is
     * nonnull. It is suggested to use the {@link TypeSignatureBuilder} to construct a type object, as it protects
     * against null signatures.
     *
     * @param signature The signature.
     * @return The parsed type.
     */
    public static TypeInformal parseType(String signature) {
        TypeSignatureVisitor visitor = new TypeSignatureVisitor();
        SignatureReader reader = new SignatureReader(signature);
        reader.accept(visitor);
        visitor.visitFinish();

        return visitor.getSignature();
    }

    /**
     * @return A new class signature builder.
     */
    public static ClassSignatureBuilder classSignature() {
        return new ClassSignatureBuilder();
    }

    /**
     * @return A new method signature builder.
     */
    public static MethodSignatureBuilder methodSignature() {
        return new MethodSignatureBuilder();
    }

    /**
     * @return A new type signature builder.
     */
    public static TypeSignatureBuilder typeSignature() {
        return new TypeSignatureBuilder();
    }

}
