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
package io.github.honeyroasted.pecans.signature.builder;

import io.github.honeyroasted.pecans.signature.Signatures;
import io.github.honeyroasted.pecans.signature.ClassSignature;
import io.github.honeyroasted.pecans.signature.type.TypeFill;
import io.github.honeyroasted.pecans.signature.type.TypeParameterized;
import org.objectweb.asm.Type;

/**
 * A utility class to build a class signature. It attempts to parse a signature first, and if that fails it will build
 * a signature with no generic information from the class's name, the name of its superclass, and the names of its
 * interfaces. To successfully build a class signature, every method in this class must be called. The
 * {@link ClassSignatureBuilder#submitSignature(String, String)} method must be called first, followed by all the remaining
 * methods in any order.
 */
public class ClassSignatureBuilder {
    private ClassSignature signature;
    private boolean listen;

    /**
     * Creates a new class signature builder.
     */
    public ClassSignatureBuilder() {
        this.signature = new ClassSignature(new TypeParameterized(Type.getType(Object.class)), new TypeFill(Type.getType(Object.class)));
        this.listen = true;
    }

    /**
     * Submits a class name and signature for parsing by this signature builder. The signature may be null, and if it is,
     * the builder will use the information provided by other methods to construct the signature.
     *
     * @param name The class name.
     * @param signature The class signature (may be null).
     * @return This, for method chaining.
     */
    public ClassSignatureBuilder submitSignature(String name, String signature) {
        if(signature != null) {
            this.signature = Signatures.parseClass(name, signature);
            this.listen = false;
        } else {
            this.signature.setType(Type.getObjectType(name));
        }

        return this;
    }

    /**
     * Submits the name of the superclass to this signature builder. If the signature was previously submitted, and was
     * nonnull, this method will be ignored. Otherwise, the given superclass will be used to build the class signature.
     *
     * @param superclass The name of the superclass.
     * @return This, for method chaining.
     */
    public ClassSignatureBuilder submitSuper(String superclass) {
        if(this.listen && superclass != null) {
            this.signature.setSuperclass(new TypeFill(Type.getObjectType(superclass)));
        }
        return this;
    }

    /**
     * Submits the names of the interfaces to this signature builder. If the signature was previously submitted, and was
     * nonnull, this method will be ignored. Otherwise, the given interfaces will be used to to build the class signature.
     *
     * @param interfaces The names of the interfaces.
     * @return This, for method chaining.
     */
    public ClassSignatureBuilder submitInterfaces(String... interfaces) {
        if(this.listen && interfaces != null) {
            for(String i : interfaces) {
                this.signature.addInterface(new TypeFill(Type.getObjectType(i)));
            }
        }

        return this;
    }

    /**
     * @return The built class signature.
     */
    public ClassSignature get() {
        return this.signature;
    }

}
