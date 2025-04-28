package io.vincent.learning.stack.jvm.asm;

import javassist.*;

public class JavassistDemo {

    public static void main(String[] args) throws Exception {
        TestService proxy = createProxy();
        proxy.sayHello("zhangsan"); // hello:zhangsan
    }

    /**
     * 生成一个TestService的实现类
     */
    public static TestService createProxy() {
        // javassist 底层是ASM，ASM底层是编辑JVM指令码
        ClassPool classPool = new ClassPool();
        // 添加classLoader
        classPool.appendSystemPath();
        // 1.创建一个类
        CtClass class1 = classPool.makeClass("TestServiceImpl");
        try {
            class1.addInterface(classPool.get(TestService.class.getName()));
        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        }
        // 2.创建一个方法
        CtMethod satHelloMethod = null;
        try {
            satHelloMethod = CtNewMethod.make(CtClass.voidType, // void返回值
                    "sayHello", // 方法名
                    new CtClass[]{classPool.get(String.class.getName())}, // 方法参数
                    new CtClass[0], // 异常类型
                    "{System.out.println(\"hello:\"+$1);}", // 方法体内容，$1表示第一个参数
                    class1 // 指定类
                    );
        } catch (CannotCompileException | NotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            class1.addMethod(satHelloMethod);
        } catch (CannotCompileException e) {
            throw new RuntimeException(e);
        }
        // 3.实例化这个对象
        Class aClass;
        try {
            aClass = classPool.toClass(class1);
        } catch (CannotCompileException e) {
            throw new RuntimeException(e);
        }
        // 强制转换
        try {
            return (TestService) aClass.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public interface TestService {
        void sayHello(String name);
    }
}

