package com.aleksandrov.example.junit.dao;

import java.math.BigInteger;

public interface DataAccessInterface {

    void startTransaction();

    void finishTransaction();

    String getObjectById(BigInteger id);

    void storeObject(Object o);

}
