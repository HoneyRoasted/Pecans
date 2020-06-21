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

import honeyroasted.pecans.type.type.TypeFill;
import honeyroasted.pecans.type.type.TypeParameterized;
import org.objectweb.asm.Type;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a class signature. Specifically a class signature represents:
 * <ul>
 *     <li>The formal type parameters of a class (the generics)</li>
 *     <li>The type of the superclass a class extends</li>
 *     <li>The types of the interfaces a class implements</li>
 * </ul>
 * This class also represents the type of the class itself. Thus it represents a combination of a class's signature
 * and its internal name.
 */
public class ClassSignature {
    private TypeParameterized type;
    private TypeFill superclass;
    private List<TypeFill> interfaces;

    /**
     * Creates a new class signature with the given type and superclass.
     *
     * @param type The type.
     * @param superclass The super class.
     */
    public ClassSignature(TypeParameterized type, TypeFill superclass) {
        this.type = type;
        this.superclass = superclass;
        this.interfaces = new ArrayList<>();
    }

    public static ClassSignature of(TypeParameterized type) {
        return new ClassSignature(type, new TypeFill(Type.getType(Object.class)));
    }

    public static ClassSignature of(TypeParameterized type, TypeFill superclass) {
        return new ClassSignature(type, superclass);
    }

    /**
     * @return The formatted signature, as it would appear in bytecode.
     */
    public String write() {
        StringBuilder k = new StringBuilder();
        k.append(this.type.writeParams()).append(this.superclass.write());
        this.interfaces.forEach(f -> k.append(f.write()));
        return k.toString();
    }

    public String writeDesc() {
        return this.type.writeDesc();
    }

    public String writeInternalName() {
        return this.type.writeInternalName();
    }

    /**
     * @return The type of this class signature.
     */
    public TypeParameterized getType() {
        return this.type;
    }

    /**
     * Sets the base type of this class signature. This method is functionally equivalent to {@code signature.getType().setType(type)}.
     *
     * @param type The new base type.
     */
    public void setType(Type type) {
        this.type.setType(type);
    }

    /**
     * Sets the type of this class signature.
     *
     * @param type The new type.
     */
    public void setType(TypeParameterized type) {
        this.type = type;
    }

    /**
     * @return The superclass of this class signature.
     */
    public TypeFill getSuperclass() {
        return this.superclass;
    }

    /**
     * Sets the superclass of this class signature.
     *
     * @param superclass The new superclass.
     */
    public ClassSignature setSuperclass(TypeFill superclass) {
        this.superclass = superclass;
        return this;
    }

    /**
     * @return The interfaces of this class signature.
     */
    public List<TypeFill> getInterfaces() {
        return this.interfaces;
    }

    /**
     * Adds an interface to this class signature.
     *
     * @param inter The interface to add to this class signature.
     */
    public ClassSignature addInterface(TypeFill inter) {
        this.interfaces.add(inter);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassSignature signature = (ClassSignature) o;
        return Objects.equals(type, signature.type) &&
                Objects.equals(superclass, signature.superclass) &&
                Objects.equals(interfaces, signature.interfaces);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, superclass, interfaces);
    }

}
