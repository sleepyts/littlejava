package com.Threads;

public class ThreadPrint extends Thread{
    public volatile static Object lock = new Object();
    public static int currentPrint=0;
    private static final int count=10;
    private final String message;
    private final int id;

    public ThreadPrint(int id,  String message) {
        this.id = id;
        this.message = message;
    }

    @Override
    public void run() {
        for (int i = 0; i < count; i++) {
            synchronized (lock){
                while(currentPrint%3!= id){
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                System.out.println(Thread.currentThread().getName() + " " + message);
                currentPrint++;
                lock.notifyAll();
            }
        }
    }
}
