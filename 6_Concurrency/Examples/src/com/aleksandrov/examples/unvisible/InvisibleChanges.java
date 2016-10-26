package com.aleksandrov.examples.unvisible;

public class InvisibleChanges {

    private volatile static boolean ready;
    private volatile static int number;

    private static class ReaderThread implements Runnable {

        @Override
        public void run() {
            while (!ready)
                Thread.yield();
            System.out.println(number);
        }
    }

    public static void main(String[] args) {
        new Thread(new ReaderThread()).start();
        ready = true;
        number = 42;
    }

}
