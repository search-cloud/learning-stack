package io.vincent.learning.stack.jvm.instrument;


import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.commons.AdviceAdapter;

/**
 * SnailMethodVisitor.
 *
 * @author Vincent.Lu.
 * @since 2023/4/24
 */
public class SnailMethodVisitor extends AdviceAdapter {

    protected MethodVisitor mv;
    protected String name;

    /**
     * Constructs a new {@link AdviceAdapter}.
     *
     * @param api the ASM API version implemented by this visitor. Must be one of {@link
     *     org.objectweb.asm.Opcodes#ASM4}, {@link org.objectweb.asm.Opcodes#ASM5},
     *     {@link org.objectweb.asm.Opcodes#ASM6} or {@link org.objectweb.asm.Opcodes#ASM7}.
     * @param methodVisitor the method visitor to which this adapter delegates calls.
     * @param access the method's access flags (see {@link jdk.internal.org.objectweb.asm.Opcodes}).
     * @param name the method's name.
     * @param descriptor the method's descriptor (see {@link Type Type}).
     */
    protected SnailMethodVisitor(int api, MethodVisitor methodVisitor, int access, String name, String descriptor) {
        super(api, methodVisitor, access, name, descriptor);
        this.mv = methodVisitor;
        this.name = name;
    }

    @Override
    protected void onMethodEnter() {
        // 在方法开始的地方插入：<<< enter [something]
        mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mv.visitLdcInsn("<<< enter " + name);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
        super.onMethodEnter();
    }

    @Override
    protected void onMethodExit(int opcode) {
        super.onMethodExit(opcode);
        // 在方法结束的地方插入：<<< exist [something]
        mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mv.visitLdcInsn(">>> exit " + name);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
    }
}
