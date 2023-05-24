package io.vincent.learning.stack.jvm.instrument;


import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * SnailClassVisitor.
 *
 * @author Vincent.Lu.
 * @since 2023/4/24
 */
public class SnailClassVisitor extends ClassVisitor {

    protected SnailClassVisitor(int api) {
        super(api);
    }

    protected SnailClassVisitor(int api, ClassVisitor classVisitor) {
        super(api, classVisitor);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        MethodVisitor methodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions);
        if (name.equals("<init>")) {
            return methodVisitor;
        }

        return new SnailMethodVisitor(Opcodes.ASM5, methodVisitor, access, name, descriptor);
    }
}
