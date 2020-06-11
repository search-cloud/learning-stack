package io.vincent.learning.stack.javacore.serialization;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Vincent
 * @since 1.0, 2019/3/26
 */
public class SerializationUtil {

	private static final Logger logger = LoggerFactory.getLogger(SerializationUtil.class);

	/**
	 * 简单的序列化方法
	 *
	 * @param object   需要序列化的对象
	 * @param fileName 序列化保存的文件名称
	 * @param <T>      序列化的对象的类型
	 */
	public static <T> void serialize(T object, String fileName) {
		try (FileOutputStream fos = new FileOutputStream(fileName); ObjectOutputStream oos = new ObjectOutputStream(fos)) {
			oos.writeObject(object);
			oos.flush();
			logger.info("Object {} has been serialized", object.toString());
		} catch (IOException e) {
			logger.error("serialize error: ", e);
		}
	}

	/**
	 * 简单的反序列化方法
	 *
	 * @param fileName 序列化后保存的文件
	 * @param <T>      反序列化的对象类型
	 * @return 反序列化后的对象
	 */
	@SuppressWarnings("unchecked")
	public static <T> T deserialize(String fileName) {

		T object = null;

		try (FileInputStream fis = new FileInputStream(fileName); ObjectInputStream ois = new ObjectInputStream(fis)) {
			object = (T) ois.readObject();
			logger.info("Object {} has been deserialized", object.toString());
		} catch (IOException | ClassNotFoundException e) {
			logger.error("deserialize error: ", e);
		}

		return object;

	}
}
