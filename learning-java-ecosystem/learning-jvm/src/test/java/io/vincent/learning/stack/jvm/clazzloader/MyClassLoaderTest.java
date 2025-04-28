package io.vincent.learning.stack.jvm.clazzloader;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

/**
 * MyClassLoaderTest.
 *
 * @author Vincent.Lu.
 * @since 2023/4/23
 */
public class MyClassLoaderTest {

    @Test
    public void testGenClass() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        ClassLoader binLoader = new MyClassLoader.BinLoader();

        Class<?> aClass = binLoader.loadClass("greetings.Go");

        Object o = aClass.getConstructor().newInstance();
        o.getClass().getMethod("greetings").invoke(o);
    }
}