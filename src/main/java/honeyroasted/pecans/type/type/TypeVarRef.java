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
package honeyroasted.pecans.type.type;

import org.objectweb.asm.Type;

import java.util.Objects;

/**
 * Represents a reference to a formal type parameter in either a method signature or class signature.
 */
public class TypeVarRef implements TypeSignaturePart, TypeInformal {
    private String name;

    /**
     * Creates a new reference to a formal type parameter with the given name.
     *
     * @param name The name.
     */
    public TypeVarRef(String name) {
        this.name = name;
    }

    /**
     * @return The name of this type parameter reference.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the name of this type parameter reference/
     *
     * @param name The new name.
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String write() {
        return "T" + this.name + ";";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TypeVarRef ref = (TypeVarRef) o;
        return Objects.equals(name, ref.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String writeDesc() {
        return Type.getType(Object.class).getDescriptor();
    }
}
