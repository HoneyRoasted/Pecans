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
 * Represents some an inner class, with some outer class.
 */
public class TypeInner extends TypeFill implements TypeInformal {
    private TypeFill outer;
    private String desc;

    /**
     * Creates a new inner type with the given type and the give outer type.
     *
     * @param type The inner type.
     * @param outer The outer type.
     */
    public TypeInner(Type type, TypeFill outer) {
        super(type);
        this.outer = outer;
        this.desc = outer.getType().getInternalName() + "$" + type.getInternalName();
    }

    public TypeInner setDesc(String desc) {
        this.desc = desc;
        return this;
    }

    /**
     * @return The outer type of this inner type.
     */
    public TypeFill getOuter() {
        return this.outer;
    }

    /**
     * Sets the outer type of this inner type.
     *
     * @param outer The new outer type.
     */
    public void setOuter(TypeFill outer) {
        this.outer = outer;
    }

    @Override
    public String write() {
        String outer = this.outer.write();
        if (outer.endsWith(";")) {
            outer = outer.substring(0, outer.length() - 1);
        }
        return outer + "." + this.getType().getInternalName() + ";";
    }

    @Override
    public String writeDesc() {
        return this.desc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TypeInner typeInner = (TypeInner) o;
        return Objects.equals(outer, typeInner.outer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(outer);
    }

}
