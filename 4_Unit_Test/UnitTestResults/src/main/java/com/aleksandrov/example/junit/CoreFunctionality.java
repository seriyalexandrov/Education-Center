package com.aleksandrov.example.junit;

import com.aleksandrov.example.junit.dao.DataAccessInterface;
import com.aleksandrov.example.junit.dao.Database;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class CoreFunctionality {

    private final DataAccessInterface accessInterface = new Database();

    public List<String> getTenObjects() {

        List<String> result = new ArrayList<>();

        for(long objectId = 0; objectId < 10; objectId++) {
            BigInteger bigObjectId = BigInteger.valueOf(objectId);
            result.add(accessInterface.getObjectById(bigObjectId));
        }

        return result;
    }

    public void updateObject(BigInteger id) {

        accessInterface.startTransaction();

        String o = accessInterface.getObjectById(id);
        updateObjectData(o);
        accessInterface.storeObject(o);

        accessInterface.finishTransaction();
    }

    private void updateObjectData(String o) {
        //update String fields
    }
}
