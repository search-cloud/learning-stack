package io.vincent.learning.stack.javacore.serialization;

import java.io.*;

/**
 * Java code for serialization and deserialization of a Java object.
 *
 */
public class SerialExample {

    public static void main(String[] args) {
        Emp object = new Emp("ab", 20, 2, 1000);
        String filename = "shubham.txt";

        // Serialization
        serialization(object, filename);

        object = null;

        // Deserialization
        deserialization(filename);
    }

    private static void serialization(Emp object, String filename) {
        try {

            // Saving of object in a file
            FileOutputStream file = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(file);

            // Method for serialization of object
            out.writeObject(object);

            out.close();
            file.close();

            System.out.println("Object has been serialized\nData before Deserialization.");
            printData(object);

            // value of static variable changed
            object.s = 2000;
        } catch (IOException ex) {
            System.out.println("IOException is caught");
        }
    }

    private static void deserialization(String filename) {
        Emp object;
        try {

            // Reading the object from a file
            FileInputStream file = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(file);

            // Method for deserialization of object
            object = (Emp) in.readObject();

            in.close();
            file.close();
            System.out.println("Object has been deserialized\nData after Deserialization.");
            printData(object);

            // System.out.println("z = " + object1.z);
        } catch (IOException ex) {
            System.out.println("IOException is caught");
        } catch (ClassNotFoundException ex) {
            System.out.println("ClassNotFoundException is caught");
        }
    }

    public static void printData(Emp object1) {

        System.out.println("name = " + object1.name);
        System.out.println("age = " + object1.age);
        System.out.println("t = " + object1.t);
        System.out.println("s = " + object1.s);
    }
}
