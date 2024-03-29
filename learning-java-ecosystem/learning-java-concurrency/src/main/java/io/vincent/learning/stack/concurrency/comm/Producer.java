package io.vincent.learning.stack.concurrency.comm;

class Producer implements Runnable {
    Chat m;
    String[] s1 = {"Hi", "How are you ?", "I am also doing fine!"};

    public Producer(Chat m1) {
        this.m = m1;
        new Thread(this, "question").start();
    }

    public void run() {
        for (String s : s1) {
            m.question(s);
        }
    }
}
