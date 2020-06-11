package io.vincent.learning.stack.javacore.serialization;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Vincent on 2020/5/26.
 *
 * @author Vincent
 * @since 1.0, 2020/5/26
 */
public class SerialExampleTest {

    @Test
    public void serialization() {
        Emp object = new Emp("ab", 20, 2, 1000);
        String filename = "shubham.txt";

        // Serialization
        SerialExample.serialization(object, filename);
    }

    @Test
    public void deserialization() {
        Emp object = new Emp("ab", 20, 2, 1000);
        String filename = "shubham.txt";

        // Serialization
        SerialExample.serialization(object, filename);

        object = null;

        // Deserialization
        SerialExample.deserialization(filename);
    }

}
