package io.vincent.learning.stack.javacore.serialization;

import java.io.*;

/**
 * Java code for serialization and deserialization of a Java object.
 *
 * @author Vincent
 * @since 1.0, 2019/3/26
 */
public class SerializationDemo {

    public static void main(String[] args) {
        Demo demo = new Demo(1, "Serialization Demo");
        String fileName = "demo.ser";
        serialize(demo, fileName);

        Demo deserialize = deserialize(fileName);
        System.out.println("a = " + deserialize.a);
        System.out.println("b = " + deserialize.b);

        Emp emp = new Emp("Serialization Demo", 10,1, 100);
        String fileName1 = "emp.ser";
        serialize(emp, fileName1);

        Emp deserialize1 = deserialize(fileName1);
        System.out.println("t = " + deserialize1.t);
        System.out.println("s = " + deserialize1.s);
        System.out.println("name = " + deserialize1.name);
        System.out.println("age = " + deserialize1.age);

    }

    private static <T> void serialize(T object, String fileName) {
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

    private static <T> T deserialize(String fileName) {

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

class Demo implements java.io.Serializable {
    int a;
    String b;

    // Default constructor
    Demo(int a, String b) {
        this.a = a;
        this.b = b;
    }

}

class Emp implements Serializable {
    transient int t;
    static int s;
    String name;
    int age;

    // Default constructor
    public Emp(String name, int age, int t, int s) {
        this.name = name;
        this.age = age;
        this.t = t;
        this.s = s;
    }

}
