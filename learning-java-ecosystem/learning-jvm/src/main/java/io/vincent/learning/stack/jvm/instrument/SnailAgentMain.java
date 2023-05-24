package io.vincent.learning.stack.jvm.instrument;

import java.lang.instrument.Instrumentation;

/**
 * SnailAgentMain.
 *
 * @author Vincent.Lu.
 * @since 2023/4/24
 */
public class SnailAgentMain {
    public static void premain(final String agentArgs, final Instrumentation inst) {
        inst.addTransformer(new SnailClassFileTransformer(), true);
    }
}
