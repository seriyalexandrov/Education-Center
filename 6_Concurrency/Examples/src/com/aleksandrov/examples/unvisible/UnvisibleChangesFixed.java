package com.aleksandrov.examples.unvisible;

public class UnvisibleChangesFixed {

    private static boolean ready;
    private volatile static int number;

    private static class ReaderThread implements Runnable {

        @Override
        public void run() {
            boolean localReady;

            while (true) {

                synchronized (UnvisibleChangesFixed.class) {
                    localReady = ready;
                }

                if (localReady) break;

                Thread.yield();
            }
            System.out.println(number);
        }
    }

    public static void main(String[] args) {

        new Thread(new ReaderThread()).start();

        synchronized (UnvisibleChangesFixed.class) {
            number = 42;
            ready = true;
        }
    }
}
