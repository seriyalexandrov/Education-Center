package com.alexandrov.example.log;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MainLambdas {

    private static final Logger log = LogManager.getLogger(MainLambdas.class);

    public static void main(String[] args) {
        LongCalculation longCalculation = new LongCalculation();

        if (log.isDebugEnabled()) {
            log.debug("debug enabled", longCalculation.calculate());
        }

        log.trace("", ()->longCalculation.calculate());
    }
}
