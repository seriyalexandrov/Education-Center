package com.aleksandrov.example.junit;

import java.math.BigInteger;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        CoreFunctionality functionality = new CoreFunctionality();

        List<String> result = functionality.getTenObjects();
        System.out.println(result);

        functionality.updateObject(BigInteger.TEN);

    }
}
