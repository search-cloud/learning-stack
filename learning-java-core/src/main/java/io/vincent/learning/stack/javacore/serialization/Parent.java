package io.vincent.learning.stack.javacore.serialization;

/**
 * Created by Vincent on 2019/4/4.
 *
 * @author Vincent
 * @since 1.0, 2019/4/4
 */
public class Parent {
    // transient 成员变量不序列化
    transient int t;
    // static 类变量不序列化
    static int s;
    // 普通成员变量序列化
    String pName;
    int pAge;

    public Parent() {
    }

    public Parent(int t, String pName, int pAge) {
        this.t = t;
        this.pName = pName;
        this.pAge = pAge;
    }
}
