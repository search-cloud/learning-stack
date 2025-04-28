package io.vincent.learning.stack.jvm.asm;

public class InvocationHandlerImpl implements InvocationHandler {

    @Override
    public Object invoke(String methodName, Object[] args) {
        System.out.println("hello");
        return null;
    }
}