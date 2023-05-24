package io.vincent.learning.stack.jvm.instrument;


import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * SnailClassFileTransformer.
 *
 * @author Vincent.Lu.
 * @since 2023/4/24
 */
public class SnailClassFileTransformer implements ClassFileTransformer {

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        if (!"io/vincent/learning/stack/jvm/instrument/TraceTest".equals(className)) {
            return classfileBuffer;
        }
        ClassReader classReader = new ClassReader(classfileBuffer);
        ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_FRAMES);
        SnailClassVisitor classVisitor = new SnailClassVisitor(Opcodes.ASM7, classWriter);
        classReader.accept(classVisitor, ClassReader.SKIP_FRAMES | ClassReader.SKIP_DEBUG);
        return classWriter.toByteArray();
    }
}
