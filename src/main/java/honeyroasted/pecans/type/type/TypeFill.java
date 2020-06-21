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

import honeyroasted.pecans.type.Signatures;
import org.objectweb.asm.Type;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a type with zero or more type arguments, as in, it is a type with formal type parameters that have been
 * <i>fill</i>ed.
 */
public class TypeFill implements TypeInformal {
    private Type type;
    private List<TypeInformal> fill;

    /**
     * Creates a new filled type with the given {@link Type}.
     *
     * @param type The type.
     */
    public TypeFill(Type type) {
        this.type = type;
        this.fill = new ArrayList<>();
    }

    /**
     * @return The base type of this filled type.
     */
    public Type getType() {
        return this.type;
    }

    /**
     * Sets the base type of this filled type.
     *
     * @param type The new base type.
     */
    public void setType(Type type) {
        this.type = type;
    }

    /**
     * @return The filled type parameters of this filled type.
     */
    public List<TypeInformal> getFill() {
        return this.fill;
    }

    /**
     * Adds a filled type parameter to this filled type.
     *
     * @param part The filled type parameter to add.
     */
    public TypeFill addPart(TypeInformal part) {
        this.fill.add(part);
        return this;
    }

    /**
     * Converts this filled type to an inner type with the given type, and this type as the outer type.
     *
     * @param inner The inner type.
     * @return A new inner type.
     */
    public TypeInner asInner(Type inner) {
        return new TypeInner(inner, this);
    }

    @Override
    public String write() {
        StringBuilder k = new StringBuilder().append(Signatures.writeStart(this.type));

        if(!this.fill.isEmpty()) {
            k.append("<");
            this.fill.forEach(f -> k.append(f.write()));
            k.append(">");
        }
        return k.append(Signatures.writeEnd(this.type)).toString();
    }

    @Override
    public String writeDesc() {
        return type.getDescriptor();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TypeFill typeFill = (TypeFill) o;
        return Objects.equals(type, typeFill.type) &&
                Objects.equals(fill, typeFill.fill);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, fill);
    }

}
