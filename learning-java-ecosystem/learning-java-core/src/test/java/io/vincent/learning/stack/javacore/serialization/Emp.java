package io.vincent.learning.stack.javacore.serialization;

import java.io.Serializable;

/**
 * 实现 Serializable 的对象，可序列化
 */
class Emp implements Serializable {
    // transient 成员变量不序列化
    transient int t;
    // static 类变量不序列化
    static int s;
    // 普通成员变量序列化
    String name;
    int age;
    Demo demo;

    // Default constructor
    public Emp(String name, int age, int t, int s) {
        this.name = name;
        this.age = age;
        this.t = t;
        this.s = s;
    }

}
