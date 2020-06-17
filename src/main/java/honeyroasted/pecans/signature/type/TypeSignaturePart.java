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
package honeyroasted.pecans.signature.type;

import honeyroasted.pecans.signature.Signatures;
import org.objectweb.asm.Type;

/**
 * Represents an element of a signature or an element of an element of a signature. All TypeSignatureParts are mutable
 * so that they can be built up through ASM's visitor pattern, but you are strongly advised not to mutate them yourself
 * after they have first been initialized.
 */
public interface TypeSignaturePart {

    /**
     * @return The formatted signature part, as it would appear in bytecode.
     */
    String write();

    /**
     * @return The formatted signature part, as a descriptor.
     */
    String writeDesc();

    default String writeInternalName() {
        return Type.getType(writeDesc()).getInternalName();
    }

    default String writeExternalName() {
        return writeInternalName().replace('/', '.');
    }

    /**
     * @return True if this is a primitive type, false otherwise.
     */
    default boolean isPrimitive() {
        return this instanceof TypeFill && Signatures.isPrimitive(((TypeFill) this).getType());
    }

    /**
     * @return True if this is a long or double, false otherwise.
     */
    default boolean isTwoWords() {
        return this.isPrimitive() && ((TypeFill) this).getType().getSize() == 2;
    }

    /**
     * @return True if this is an array type, false otherwise.
     */
    default boolean isArray() {
        return this instanceof TypeArray;
    }

    /**
     * @return True if this is a filled type, false otherwise.
     */
    default boolean isFill() {
        return this instanceof TypeFill;
    }

    /**
     * @return True if this is a inner type, false otherwise.
     */
    default boolean isInner() {
        return this instanceof TypeInner;
    }

    /**
     * @return True if this is a parameterized type, false otherwise.
     */
    default boolean isParameterized() {
        return this instanceof TypeParameterized;
    }

    /**
     * @return True if this is a formal type variable, false otherwise.
     */
    default boolean isVar() {
        return this instanceof TypeVar;
    }

    /**
     * @return True if this is a reference to a formal type variable, false otherwise.
     */
    default boolean isVarRef() {
        return this instanceof TypeVarRef;
    }

    /**
     * @return True if this is a wildcard type, false otherwise.
     */
    default boolean isWild() {
        return this instanceof TypeWild;
    }

}
