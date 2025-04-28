package io.vincent.learning.stack.design.patterns.filterchain.v4;

import java.io.*;

public class DeepCopyUtils {
    public static <T extends Serializable> T deepCopy(T object) {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(object);
            oos.flush();
            return deserialize(bos.toByteArray());
        } catch (IOException e) {
            throw new ChainException("Deep copy failed", e);
        }
    }

    
    @SuppressWarnings("unchecked")
    private static <T> T deserialize(byte[] bytes) {
        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes))) {
            return (T) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new ChainException("Deserialization failed", e);
        }
    }
}