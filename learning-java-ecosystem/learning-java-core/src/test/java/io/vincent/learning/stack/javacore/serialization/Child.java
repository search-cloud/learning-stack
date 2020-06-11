package io.vincent.learning.stack.javacore.serialization;

import java.io.Serializable;

/**
 * Created by Vincent on 2019/4/4.
 *
 * @author Vincent
 * @since 1.0, 2019/4/4
 */
public class Child extends Parent implements Serializable {

    int age;
    String name;

    public Child(int t, String parentName, int parentAge, int age, String name) {
        super(t, parentName, parentAge);
        this.age = age;
        this.name = name;
    }
}
