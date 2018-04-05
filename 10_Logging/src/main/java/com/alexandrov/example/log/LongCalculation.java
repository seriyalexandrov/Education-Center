package com.alexandrov.example.log;

public class LongCalculation {

    String calculate() {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "log";
    }
}
