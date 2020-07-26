package honeyroasted.pecans.util;

public class ByteArrayClassLoader extends ClassLoader {

    public ByteArrayClassLoader(ClassLoader parent) {
        super(parent);
    }

    public ByteArrayClassLoader() {

    }

    public Class<?> defineClass(String name, byte[] b) {
        return super.defineClass(name, b, 0, b.length);
    }

}
