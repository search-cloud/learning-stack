package io.vincent.learning.stack.jvm.asm;

import javassist.*;

import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class Javassist3Demo {

    public static void main(String[] args) throws Exception {

        TestService proxy = createProxy(TestService.class, (methodName, args1) -> {
            // 根据方法判断逻辑
            if(methodName.equals("sayHello3")) {
                System.out.println("hello" + args1[0]);
                return "aa";
            } else {
                System.out.println("hello2" + args1[0]);
                return "aa2";
            }
        });
        proxy.sayHello("zhangsan"); // hellozhangsan
        proxy.sayHello2("zz", 1);
        System.out.println(proxy.sayHello3("qq"));
    }

    static int count = 0;
    public static <T> T createProxy(Class<T> classInterface, InvocationHandler handler) throws Exception {
        ClassPool classPool = new ClassPool();
        classPool.appendSystemPath();
        // 1.创建一个类
        CtClass impl = classPool.makeClass("$Proxy" + count ++);
        impl.addInterface(classPool.get(classInterface.getName()));

        // 2.impl类中 添加属性handler
        CtField fie = CtField.make("public io.vincent.learning.stack.jvm.asm.InvocationHandler handler=null;", impl);
        impl.addField(fie);

        // 有返回值类型和无返回值类型的源码
        String src = "return ($r)this.handler.invoke(\"%s\", $args);"; // $args获取所有参数
        String voidSrc = "this.handler.invoke(\"%s\",$args);";

        for (Method method : classInterface.getMethods()) {
            CtClass returnType = classPool.get(method.getReturnType().getName());
            String name = method.getName();
            CtClass[] parameters = toCtClass(classPool, method.getParameterTypes());
            CtClass[] errors = toCtClass(classPool, method.getExceptionTypes());

            // 2.创建一个方法
            CtMethod newMethod = CtNewMethod.make(returnType, // 返回值
                    name, // 方法名
                    parameters, // 方法参数
                    errors, // 异常类型
                    method.getReturnType().equals(Void.class) ? String.format(voidSrc, method.getName()) : String.format(src, method.getName()), // 方法体内容
                    impl // 指定类
            );
            impl.addMethod(newMethod);

        }

        // 生成字节码（辅助学习用）
        byte[] bytes = impl.toBytecode();
        Files.write(Paths.get(System.getProperty("user.dir") + "/build/" + impl.getName() + ".class"), bytes);

        // 3.实例化这个对象
        var aClass = classPool.toClass(impl);
        // noinspection unchecked
        T t = (T) aClass.newInstance();
        aClass.getField("handler").set(t, handler); // 初始化赋值
        // 强制转换
        return t;
    }


    private static CtClass[] toCtClass(ClassPool pool, Class[] classes) {
        return Arrays.stream(classes).map(c -> {
            try {
                return pool.get(c.getName());
            } catch (NotFoundException e) {
                throw new RuntimeException(e);
            }
        }).toList().toArray(new CtClass[0]);
    }


}

