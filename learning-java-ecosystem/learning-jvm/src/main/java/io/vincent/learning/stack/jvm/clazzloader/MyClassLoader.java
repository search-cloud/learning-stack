package io.vincent.learning.stack.jvm.clazzloader;

import javassist.*;

import java.io.IOException;

/**
 * MyClassLoader.
 *
 * @author Vincent.Lu.
 * @since 2023/4/23
 */
public class MyClassLoader {

    private static byte[] generateClass() throws CannotCompileException, IOException {
        ClassPool classPool = ClassPool.getDefault();

        CtClass ctClass = classPool.getOrNull("greetings.Go");

        if (ctClass != null) {
            ctClass.defrost();
        }

        ctClass = classPool.makeClass("greetings.Go");

        CtMethod method = new CtMethod(CtClass.voidType, "greetings", new CtClass[]{}, ctClass);
        method.setModifiers(Modifier.PUBLIC);
        method.setBody("{ System.out.println(\"Hello World!\"); }");

        return ctClass.toBytecode();
    }

    static class BinLoader extends ClassLoader {
        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            if (name.equals("greetings.Go")) {
                try {
                    byte[] classBytes = generateClass();
                    return defineClass(name, classBytes, 0, classBytes.length);
                } catch (CannotCompileException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return super.findClass(name);
        }
    }

}
