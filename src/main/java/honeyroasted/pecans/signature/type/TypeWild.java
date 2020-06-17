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

import org.objectweb.asm.Type;

import java.util.Objects;

/**
 * Represents a wildcard type with an upper and lower bound. If no lower bound exists, it will be null, and if no upper
 * bound exists, it will be {@link Object}.
 */
public class TypeWild implements TypeSignaturePart, TypeInformal {
    private TypeInformal upper;
    private TypeInformal lower;

    /**
     * Creates a new wild type with the given upper bound and lower bound.
     *
     * @param upper The upper bound.
     * @param lower The lower bound.
     */
    public TypeWild(TypeInformal upper, TypeInformal lower) {
        this.upper = upper;
        this.lower = lower;
    }

    /**
     * @return The upper bound of this wildcard type (may be {@link Object}).
     */
    public TypeInformal getUpper() {
        return this.upper;
    }

    /**
     * Sets the upper bound of this wildcard type.
     *
     * @param upper The new upper bound.
     */
    public void setUpper(TypeInformal upper) {
        this.upper = upper;
    }

    /**
     * @return The lower bound of this wildcard type (may be null).
     */
    public TypeInformal getLower() {
        return this.lower;
    }

    /**
     * Sets the lower bound of this wildcard type.
     *
     * @param lower The new lower bound.
     */
    public void setLower(TypeInformal lower) {
        this.lower = lower;
    }

    @Override
    public String write() {
        if(upper != null) {
            return "+" + this.upper.write();
        } else if (this.lower != null) {
            return "-" + this.lower.write();
        } else {
            return "*";
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TypeWild typeWild = (TypeWild) o;
        return Objects.equals(upper, typeWild.upper) &&
                Objects.equals(lower, typeWild.lower);
    }

    @Override
    public int hashCode() {
        return Objects.hash(upper, lower);
    }

    @Override
    public String writeDesc() {
        return Type.getType(Object.class).getDescriptor();
    }
}
