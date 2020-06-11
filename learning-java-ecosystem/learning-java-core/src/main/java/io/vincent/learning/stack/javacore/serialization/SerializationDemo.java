package io.vincent.learning.stack.javacore.serialization;

import static io.vincent.learning.stack.javacore.serialization.SerializationUtil.deserialize;
import static io.vincent.learning.stack.javacore.serialization.SerializationUtil.serialize;

/**
 * Java code for serialization and deserialization of a Java object.
 *
 * @author Vincent
 * @since 1.0, 2019/3/26
 */
@SuppressWarnings("All")
public class SerializationDemo {

    public static void main(String[] args) {
        Demo demo = new Demo(1, "Serialization Demo");
        String fileName = "demo.ser";
        serialize(demo, fileName);

        Demo deserialize = deserialize(fileName);
        System.out.println("a = " + deserialize.a);
        System.out.println("b = " + deserialize.b);

        // 带有 static 变量，和 transient 变量的类，序列化
        Emp emp = new Emp("Serialization Demo", 10,1, 100);
        String fileName1 = "emp.ser";
        serialize(emp, fileName1);

        Emp deserialize1 = deserialize(fileName1);
        System.out.println("t = " + deserialize1.t);
        System.out.println("s = " + deserialize1.s);
        System.out.println("pName = " + deserialize1.name);
        System.out.println("pAge = " + deserialize1.age);

        // 带有其他引用类型变量
        Emp empWithDemo = new Emp("Serialization Demo", 10,1, 100);
        empWithDemo.demo = demo;
        String fileNameEmp = "emp2.ser";
        serialize(empWithDemo, fileNameEmp);

        Emp deserialize2 = deserialize(fileNameEmp);
        System.out.println("t = " + deserialize2.t);
        System.out.println("s = " + deserialize2.s);
        System.out.println("pName = " + deserialize2.name);
        System.out.println("pAge = " + deserialize2.age);
        System.out.println("demo.a = " + deserialize2.demo.a);
        System.out.println("demo.b = " + deserialize2.demo.b);

        // 子类实现了 Serializable，父类没有实现Serializable，
        // 当序列化子类时，父类不会被序列化，且，父类中必须有空参构造函数
        Child child = new Child(50, "parentName", 100, 20, "childName");
        String sfn = "child.ser";
        serialize(child, sfn);
        Child child1 = deserialize(sfn);
        System.out.println("t = " + child1.t); // 0
        System.out.println("s = " + child1.s); // 0
        System.out.println("name = " + child1.name); // childName
        System.out.println("age = " + child1.age); // 20
        System.out.println("pName = " + child1.pName); // null
        System.out.println("pAge = " + child1.pAge); // 0

    }

}
