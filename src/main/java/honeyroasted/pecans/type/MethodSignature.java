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

import honeyroasted.pecans.type.type.TypeInformal;
import honeyroasted.pecans.type.type.TypeVar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a method signature. Specifically, a method signature represents:
 * <ul>
 * <li>The return type of a method</li>
 * <li>The formal type parameters of a method (the generics)</li>
 * <li>The types of the method's parameters</li>
 * <li>The types of the exceptions the method declares as thrown</li>
 * </ul>
 */
public class MethodSignature {
    private List<TypeVar> generics;
    private List<TypeInformal> paras;
    private TypeInformal ret;
    private List<TypeInformal> exceptions;

    /**
     * Creates a new method signature with the given return type.
     *
     * @param ret The return type.
     */
    public MethodSignature(TypeInformal ret) {
        this.ret = ret;
        this.generics = new ArrayList<>();
        this.paras = new ArrayList<>();
        this.exceptions = new ArrayList<>();
    }

    public static MethodSignature of(TypeInformal ret) {
        return new MethodSignature(ret);
    }

    /**
     * @return The formatted signature, as it would appear in bytecode.
     */
    public String write() {
        StringBuilder builder = new StringBuilder();
        if (!this.generics.isEmpty()) {
            builder.append("<");
            this.generics.forEach(t -> builder.append(t.write()));
            builder.append(">");
        }
        builder.append("(");
        this.paras.forEach(t -> builder.append(t.write()));
        builder.append(")");
        builder.append(this.ret.write());
        this.exceptions.forEach(t -> builder.append("^").append(t.write()));
        return builder.toString();
    }

    public String writeDesc() {
        StringBuilder builder = new StringBuilder();
        builder.append("(");
        this.paras.forEach(t -> builder.append(t.writeDesc()));
        builder.append(")");
        builder.append(this.ret.writeDesc());
        return builder.toString();
    }

    /**
     * @return The return type of this method signature.
     */
    public TypeInformal getReturn() {
        return this.ret;
    }

    /**
     * Sets the return type of this method signature.
     *
     * @param ret The new return type.
     */
    public void setReturn(TypeInformal ret) {
        this.ret = ret;
    }

    /**
     * @return The formal type parameters associated with this method signature.
     */
    public List<TypeVar> getGenerics() {
        return this.generics;
    }

    /**
     * @return The types of the parameters of this method signature.
     */
    public List<TypeInformal> getParameters() {
        return this.paras;
    }

    /**
     * @return The types of the exceptions associated with this method signature.
     */
    public List<TypeInformal> getExceptions() {
        return this.exceptions;
    }

    /**
     * Adds a formal type parameter to this method signature.
     *
     * @param gen The formal type parameter to add to this method signature.
     */
    public void addGeneric(TypeVar gen) {
        this.generics.add(gen);
    }


    /**
     * Adds a parameter to this method signature.
     *
     * @param para The parameter to add.
     */
    public void addParameter(TypeInformal para) {
        this.paras.add(para);
    }

    /**
     * Adds an exception to this method signature.
     *
     * @param except The exception to add.
     */
    public void addException(TypeInformal except) {
        this.exceptions.add(except);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MethodSignature signature = (MethodSignature) o;
        return Objects.equals(generics, signature.generics) &&
                Objects.equals(paras, signature.paras) &&
                Objects.equals(ret, signature.ret) &&
                Objects.equals(exceptions, signature.exceptions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(generics, paras, ret, exceptions);
    }

}
