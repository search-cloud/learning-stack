package io.vincent.learning.stack.concurrency.comm;

public class TestThread {
    public static void main(String[] args) {
        Chat m = new Chat();
        new Producer(m);
        new Consumer(m);
    }
}