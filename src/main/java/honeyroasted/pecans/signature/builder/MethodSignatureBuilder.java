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
package honeyroasted.pecans.signature.builder;

import honeyroasted.pecans.signature.Signatures;
import honeyroasted.pecans.signature.MethodSignature;
import honeyroasted.pecans.signature.type.TypeFill;
import org.objectweb.asm.Type;

/**
 * A utility class to build a method signature. It attempts to parse a signature first, and if that fails it will build
 * a signature with no generic information from the method's descriptor. To successfully build a method signature, every
 * method in this class must be called. The {@link MethodSignatureBuilder#submitDesc(String)} must be called, followed by
 * all the remaining methods in any order.
 */
public class MethodSignatureBuilder {
    private MethodSignature signature;
    private boolean listen;

    /**
     * Creates a new method signature builder.
     */
    public MethodSignatureBuilder() {
        this.signature = new MethodSignature(new TypeFill(Type.getType(void.class)));
        this.listen = true;
    }

    /**
     * Submits a method signature for parsing by this signature builder. The signature may be null, and if it is,
     * the builder will use the information provided by the other methods to construct the signature.
     *
     * @param signature The method signature.
     * @return This, for method chaining.
     */
    public MethodSignatureBuilder submitSignature(String signature) {
        if (signature != null) {
            this.signature = Signatures.parseMethod(signature);
            this.listen = false;
        }
        return this;
    }

    /**
     * Submits the method descriptor to this signature builder. If the signature was previously submitted and was nonnull,
     * this method will be ignored. Otherwise, the given descriptor will be used to build the method signature.
     *
     * @param desc The method descriptor.
     * @return This, for method chaining.
     */
    public MethodSignatureBuilder submitDesc(String desc) {
        if (this.listen && desc != null) {
            Type type = Type.getMethodType(desc);
            this.signature.setReturn(new TypeFill(type.getReturnType()));

            for (Type param : type.getArgumentTypes()) {
                this.signature.addParameter(new TypeFill(param));
            }
        }

        return this;
    }

    /**
     * Submits the names of the exceptions associated with the method to this signature builder. If the signature was previously
     * submitted and was nonnull, this method will be ignored. Otherwise, the given exceptions will be used to build
     * the method signature.
     *
     * @param exceptions The exceptions associated with the method.
     * @return This, for method chaining.
     */
    public MethodSignatureBuilder submitExceptions(String... exceptions) {
        if (this.listen && exceptions != null) {
            for (String exe : exceptions) {
                this.signature.addException(new TypeFill(Type.getObjectType(exe)));
            }
        }

        return this;
    }

    /**
     * @return The build method signature.
     */
    public MethodSignature get() {
        return this.signature;
    }

}
