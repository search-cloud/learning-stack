package io.vincent.learning.stack.javacore.random;

import java.util.Random;

public class RandomTest {
    public static void main(String[] args) {
        int n = 10; // 设置上限值为10
        Random random = new Random();
        int randomNumber = random.nextInt(n);
        System.out.println("随机整数：" + randomNumber);
    }
}
