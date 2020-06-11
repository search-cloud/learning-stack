package io.vincent.learning.stack.concurrency.comm;

class Producer implements Runnable {
    Chat m;
    String[] s1 = {"Hi", "How are you ?", "I am also doing fine!"};

    public Producer(Chat m1) {
        this.m = m1;
        new Thread(this, "question").start();
    }

    public void run() {
        for (int i = 0; i < s1.length; i++) {
            m.question(s1[i]);
        }
    }
}
