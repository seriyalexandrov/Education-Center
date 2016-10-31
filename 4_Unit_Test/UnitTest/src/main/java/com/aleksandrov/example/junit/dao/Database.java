package com.aleksandrov.example.junit.dao;

import java.math.BigInteger;

public class Database implements DataAccessInterface {

    @Override
    public void startTransaction() {
        throw new AssertionError("DB is not connected");
    }

    @Override
    public void finishTransaction() {
        throw new AssertionError("DB is not connected");
    }

    @Override
    public String getObjectById(BigInteger id) {
        throw new AssertionError("DB is not connected");
    }

    @Override
    public void storeObject(Object o) {
        throw new AssertionError("DB is not connected");
    }
}
