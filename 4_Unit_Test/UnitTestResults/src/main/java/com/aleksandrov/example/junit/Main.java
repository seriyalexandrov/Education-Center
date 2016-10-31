package com.aleksandrov.example.junit;

import java.math.BigInteger;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        DataBaseInteraction functionality = new DataBaseInteraction();

        List<String> result = functionality.getTenObjects();
        System.out.println(result);

        functionality.updateObject(BigInteger.TEN);

    }
}
