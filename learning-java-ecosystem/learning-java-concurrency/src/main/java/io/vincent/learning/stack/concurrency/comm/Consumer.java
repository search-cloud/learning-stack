package io.vincent.learning.stack.concurrency.comm;

class Consumer implements Runnable {
    private Chat m;
    private String[] s2 = {"Hi", "I am good, what about you?", "Great!"};

    public Consumer(Chat m2) {
        this.m = m2;
        new Thread(this, "answer").start();
    }

    public void run() {
        for (String aS2 : s2) {
            m.answer(aS2);
        }
    }
}