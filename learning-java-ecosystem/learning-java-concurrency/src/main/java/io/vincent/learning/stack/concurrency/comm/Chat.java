package io.vincent.learning.stack.concurrency.comm;

class Chat {
    private boolean flag = false;

    public synchronized void question(String msg) {
        if (flag) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Q: " + msg);
        flag = true;
        notify();
    }

    public synchronized void answer(String msg) {
        if (!flag) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("A: " + msg);
        flag = false;
        notify();
    }
}