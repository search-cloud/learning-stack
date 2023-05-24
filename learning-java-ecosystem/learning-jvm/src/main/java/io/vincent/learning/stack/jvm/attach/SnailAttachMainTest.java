package io.vincent.learning.stack.jvm.attach;

import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;

import java.io.IOException;

/**
 * SnailAttachMainTest.
 *
 * @author Vincent.Lu.
 * @since 2023/4/24
 */
public class SnailAttachMainTest {
    public static void main(String[] args) throws IOException, AttachNotSupportedException {
        VirtualMachine vm = VirtualMachine.attach(args[0]);
        try {
            vm.loadAgent("/Users/Vincent/Workstation/Learning/workspace/lxx/learning-stack/learning-java-ecosystem/learning-jvm/agent/attach/SnailAttachAgent.jar");
        } catch (AgentLoadException e) {
            e.printStackTrace();
        } catch (AgentInitializationException e) {
            e.printStackTrace();
        } finally {
            vm.detach();
        }
    }
}
