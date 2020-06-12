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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a formal type parameter in a method signature or a class signature.
 */
public class TypeVar implements TypeSignaturePart {
    private String name;
    private TypeInformal classBound;
    private List<TypeInformal> interBound;

    /**
     * Creates a new formal type parameter with the given name.
     *
     * @param name The name of this formal type parameter.
     */
    public TypeVar(String name) {
        this.name = name;
        this.classBound = new TypeFill(Type.getType(Object.class));
        this.interBound = new ArrayList<>();
    }

    /**
     * @return The name of this formal type parameter.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the name of this formal type parameter.
     *
     * @param name The new name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The class bound of this formal type parameter (may be {@link Object}).
     */
    public TypeInformal getClassBound() {
        return this.classBound;
    }

    /**
     * Sets the class bound of this formal type parameter.
     *
     * @param classBound The new class bound.
     */
    public void setClassBound(TypeInformal classBound) {
        this.classBound = classBound;
    }

    /**
     * @return The interface bounds of this formal type parameter (may be empty).
     */
    public List<TypeInformal> getInterBound() {
        return this.interBound;
    }

    /**
     * Adds an interface bound to this formal type parameter.
     *
     * @param bound The bound to add.
     */
    public void addInterBound(TypeInformal bound) {
        this.interBound.add(bound);
    }

    @Override
    public String write() {
        StringBuilder k = new StringBuilder().append(this.name)
                .append(":")
                .append(this.classBound.write());
        if(!this.interBound.isEmpty()) {
            this.interBound.forEach(f -> k.append(":").append(f.write()));
        }
        return k.toString();
    }

    @Override
    public String writeDesc() {
        return Type.getType(Object.class).getDescriptor();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TypeVar typeVar = (TypeVar) o;
        return Objects.equals(name, typeVar.name) &&
                Objects.equals(classBound, typeVar.classBound) &&
                Objects.equals(interBound, typeVar.interBound);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, classBound, interBound);
    }

}
