package io.vincent.learning.stack.javacore.serialization;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Java code for serialization and deserialization of a Java object.
 *
 * @author Vincent
 * @since 1.0, 2019/3/26
 */
@SuppressWarnings("All")
public class SerializationUtilTest {

    private static final Logger logger = LoggerFactory.getLogger(SerializationUtilTest.class);

    private Demo demo;

    @BeforeEach
    public void before() {
        demo = new Demo(1, "Serialization Demo");
    }

    @Test
    public void main() {
        String fileName = "demo.ser";
        SerializationUtil.serialize(demo, fileName);

        Demo deserialize = SerializationUtil.deserialize(fileName);
        logger.info("a = " + deserialize.a);
        logger.info("b = " + deserialize.b);
    }

    @Test
    public void testSerizlize2() {
        // 子类实现了 Serializable，父类没有实现Serializable，
        // 当序列化子类时，父类不会被序列化，且，父类中必须有空参构造函数
        Child child = new Child(50, "parentName", 100, 20, "childName");
        String sfn = "child.ser";
        SerializationUtil.serialize(child, sfn);
        Child child1 = SerializationUtil.deserialize(sfn);
        logger.info("t = " + child1.t); // 0
        logger.info("s = " + child1.s); // 0
        logger.info("name = " + child1.name); // childName
        logger.info("age = " + child1.age); // 20
        logger.info("pName = " + child1.pName); // null
        logger.info("pAge = " + child1.pAge); // 0
    }

    @Test
    public void testSerialize1() {
        // 带有其他引用类型变量
        Emp empWithDemo = new Emp("Serialization Demo", 10, 1, 100);
        empWithDemo.demo = demo;
        String fileNameEmp = "emp2.ser";
        SerializationUtil.serialize(empWithDemo, fileNameEmp);

        Emp deserialize2 = SerializationUtil.deserialize(fileNameEmp);
        logger.info("t = " + deserialize2.t);
        logger.info("s = " + deserialize2.s);
        logger.info("pName = " + deserialize2.name);
        logger.info("pAge = " + deserialize2.age);
        logger.info("demo.a = " + deserialize2.demo.a);
        logger.info("demo.b = " + deserialize2.demo.b);
    }

    @Test
    public void testSerialize() {
        // 带有 static 变量，和 transient 变量的类，序列化
        Emp emp = new Emp("Serialization Demo", 10, 1, 100);
        String fileName1 = "emp.ser";
        SerializationUtil.serialize(emp, fileName1);

        Emp deserialize1 = SerializationUtil.deserialize(fileName1);
        logger.info("t = " + deserialize1.t);
        logger.info("s = " + deserialize1.s);
        logger.info("pName = " + deserialize1.name);
        logger.info("pAge = " + deserialize1.age);
    }

}
