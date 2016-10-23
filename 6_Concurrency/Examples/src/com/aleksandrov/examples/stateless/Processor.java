package com.aleksandrov.examples.stateless;

import java.math.BigInteger;

public class Processor {

    public void service(Request req, Response resp) {

        BigInteger i = extractFromRequest(req);
        BigInteger result = process(i);
        putIntoResponse(resp, result);

    }

    private BigInteger process(BigInteger i) {

        try {
            //very long processing
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return i.add(BigInteger.ONE);
    }

    private void putIntoResponse(Response response, BigInteger result) {
        response.responceData.put("resultKey", result);
    }

    private BigInteger extractFromRequest(Request req) {
        return req.requestData.get("dataKey");
    }

}
