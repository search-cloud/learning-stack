package io.vincent.learning.stack.jvm.asm;

import java.lang.reflect.Proxy;

/**
 * JDK动态代理实例
 */
public class JDKDynamicProxyDemo {

    public static void main(String[] args) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        // 真实的对象
        DefaultEchoService realObj = new DefaultEchoService();

        // 代理的对象
        Object proxy = Proxy.newProxyInstance(classLoader, new Class[]{EchoService.class}, (proxy1, method, args1) -> {
            System.out.println("动态前置");
            Object obj = null;
            if (EchoService.class.isAssignableFrom(method.getDeclaringClass())) {
                // 执行真实方法
                obj = method.invoke(realObj, args1);
            }
            System.out.println("动态后置");
            return obj;
        });
        EchoService echoService = (EchoService) proxy;
        System.out.println(echoService.echo("Hello,World"));
    }
}