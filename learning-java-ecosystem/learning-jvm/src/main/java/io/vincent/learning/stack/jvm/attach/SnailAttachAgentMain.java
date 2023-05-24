package io.vincent.learning.stack.jvm.attach;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

/**
 * SnailAttachAgentMain.
 *
 * @author Vincent.Lu.
 * @since 2023/4/24
 */
public class SnailAttachAgentMain {
    public static void agentmain(final String agentArgs, final Instrumentation inst) throws ClassNotFoundException, UnmodifiableClassException {
        System.out.println("agent main called");
        inst.addTransformer(new SnailClassFileTransformer(), true);
        Class[] allLoadedClasses = inst.getAllLoadedClasses();
        for (Class allLoadedClass : allLoadedClasses) {
            if ("io.vincent.learning.stack.jvm.attach.TraceEmptyTest".equals(allLoadedClass.getName())) {
                System.out.println("Reloading: " + allLoadedClass.getName());
                inst.retransformClasses(allLoadedClass);
                break;
            }
        }
    }
}
