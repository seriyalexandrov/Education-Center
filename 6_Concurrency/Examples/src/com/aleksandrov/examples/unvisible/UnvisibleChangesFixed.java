package com.aleksandrov.examples.unvisible;

public class UnvisibleChangesFixed {

    private static volatile boolean ready;
    private static volatile int number;

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
        number = 42;
        ready = true;
    }
}
