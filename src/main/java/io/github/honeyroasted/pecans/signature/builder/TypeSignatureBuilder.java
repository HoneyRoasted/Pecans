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
import io.github.honeyroasted.pecans.signature.type.TypeFill;
import io.github.honeyroasted.pecans.signature.type.TypeInformal;
import org.objectweb.asm.Type;

/**
 * A utility class to build a type signature. It attempts to parse a signature first, and if that fails it will build
 * a signature with no generic information from the type's descriptor or name. To successfully build a type signature,
 * the {@link TypeSignatureBuilder#submitSignature(String)} must be called, followed by either the
 * {@link TypeSignatureBuilder#submitDesc(String)} or {@link TypeSignatureBuilder#submitInternal(String)} method.
 */
public class TypeSignatureBuilder {
    private TypeInformal type;
    private boolean listen;

    /**
     * Creates a new type signature builder.
     */
    public TypeSignatureBuilder() {
        this.type = new TypeFill(Type.getType(Object.class));
        this.listen = true;
    }

    /**
     * Submits a type signature for parsing by this signature builder. The signature may be null, and if it is, the
     * builder will use the information provided by the other methods to construct the signature.
     *
     * @param signature The type signature.
     * @return This, for method chaining.
     */
    public TypeSignatureBuilder submitSignature(String signature) {
        if(signature != null) {
            this.type = Signatures.parseType(signature);
            this.listen = false;
        }

        return this;
    }

    /**
     * Submits the type descriptor to this signature builder. If the signature was previously submitted and was nonnull,
     * this method will be ignored. Otherwise, the given descriptor will be used to build the type signature.
     *
     * @param desc The type descriptor.
     * @return This, for method chaining.
     */
    public TypeSignatureBuilder submitDesc(String desc) {
        if(this.listen && desc != null) {
            this.type = new TypeFill(Type.getType(desc));
        }

        return this;
    }

    /**
     * Submits the internal name to this signature builder. If the signature was previously submitted and was nonnull,
     * this method will be ignored. Otherwise, the given name will be used the build the type signature.
     *
     * @param internalName The internal name.
     * @return This, for method chaining.
     */
    public TypeSignatureBuilder submitInternal(String internalName) {
        if(this.listen && internalName != null) {
            this.type = new TypeFill(Type.getObjectType(internalName));
        }

        return this;
    }

    /**
     * @return The built type signature.
     */
    public TypeInformal get() {
        return this.type;
    }

}
