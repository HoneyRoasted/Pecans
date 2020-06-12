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
package io.github.honeyroasted.pecans.signature.type;

import org.objectweb.asm.Type;

import java.util.Objects;

/**
 * Represents an array type.
 */
public class TypeArray implements TypeInformal, TypeSignaturePart {
    private TypeInformal type;
    private String desc;

    /**
     * Creates an array type with the given element, with the given dimensions.
     *
     * @param element The element of the array.
     * @param dimensions The dimensions of the array.
     * @return An n-dimensional array type (or a 1d array type if dimensions is less than or equal to 1).
     */
    public static TypeArray create(TypeInformal element, int dimensions) {
        TypeArray array = new TypeArray(element);
        for (int i = 0; i < dimensions - 1; i++) {
            array = new TypeArray(array);
        }
        return array;
    }

    /**
     * Creates a new TypeArray with the given element.
     *
     * @param type The element of this array.
     */
    public TypeArray(TypeInformal type) {
        this.type = type;
        this.desc = "[" + type.writeDesc();
    }

    /**
     * @return The direct element of this array (it may be another TypeArray).
     */
    public TypeInformal getType() {
        return this.type;
    }

    /**
     * Sets the direct element of this array.
     *
     * @param type The new element
     */
    public void setType(TypeInformal type) {
        this.type = type;
    }

    /**
     * @return The depth, or number of dimensions, of this array.
     */
    public int depth() {
        if(this.type instanceof TypeArray) {
            return 1 + ((TypeArray) this.type).depth();
        } else {
            return 1;
        }
    }

    /**
     * @return The root element of this array (it will not be a TypeArray).
     */
    public TypeInformal element() {
        if(this.type instanceof TypeArray) {
            return ((TypeArray) this.type).element();
        } else {
            return this.type;
        }
    }

    @Override
    public String write() {
        return "[" + this.type.write();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TypeArray array = (TypeArray) o;
        return Objects.equals(type, array.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }

    @Override
    public String writeDesc() {
        return this.desc;
    }
}
