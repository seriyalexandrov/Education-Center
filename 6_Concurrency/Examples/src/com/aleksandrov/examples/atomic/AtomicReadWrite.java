package com.aleksandrov.examples.atomic;

public class AtomicReadWrite {

    private volatile static long value;

    private static class ReaderThread implements Runnable {

        @Override
        public void run() {
            assert value == 0 || value == -1;
        }
    }

    private static class WriterThread implements Runnable {

        @Override
        public void run() {
            value = -1;
        }
    }

    public static void main(String[] args) {

        new Thread(new WriterThread()).start();
        new Thread(new ReaderThread()).start();
    }

}
