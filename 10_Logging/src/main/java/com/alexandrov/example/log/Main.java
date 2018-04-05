package com.alexandrov.example.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        log.error("Error");
        log.info("Info");
        log.debug("Debug");
        log.trace("Trace");


        if (log.isDebugEnabled()) {
            log.debug("debug enabled", longCalculation());
        }

        if (log.isTraceEnabled()) {
            log.trace("trace enabled", longCalculation());
        }

        POJO pojo = new POJO();
        pojo.name = "a";
        pojo.surname = "b";
        pojo.age = 17;

        log.debug("Log pojo", pojo);
    }

    private static String longCalculation() {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "log";
    }
}
