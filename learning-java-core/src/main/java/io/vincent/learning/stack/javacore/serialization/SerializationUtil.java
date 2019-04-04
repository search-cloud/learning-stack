package io.vincent.learning.stack.javacore.serialization;

import java.io.*;

/**
 * @author Vincent
 * @since 1.0, 2019/3/26
 */
public class SerializationUtil {

    /**
     * 简单的序列化方法
     *
     * @param object   需要序列化的对象
     * @param fileName 序列化保存的文件名称
     * @param <T>      序列化的对象的类型
     */
    static <T> void serialize(T object, String fileName) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(fileName);
            oos = new ObjectOutputStream(fos);

            oos.writeObject(object);
            oos.flush();

            System.out.println("Object has been serialized");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 简单的反序列化方法
     *
     * @param fileName 序列化后保存的文件
     * @param <T>      反序列化的对象类型
     * @return 反序列化后的对象
     */
    @SuppressWarnings("Unchecked")
    static <T> T deserialize(String fileName) {

        T object = null;

        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(fileName);
            ois = new ObjectInputStream(fis);

            object = (T) ois.readObject();

            System.out.println("Object has been deserialized ");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return object;

    }
}
