package com.aleksandrov.example.junit;

import com.aleksandrov.example.junit.dao.DataAccessInterface;

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestUtils {

    static DataAccessInterface createDataAccessMock() {
        DataAccessInterface accessInterface = mock(DataAccessInterface.class);

        when(accessInterface.getObjectById(any(BigInteger.class))).thenReturn("A");
        doNothing().when(accessInterface).startTransaction();
        doNothing().when(accessInterface).finishTransaction();
        doNothing().when(accessInterface).storeObject(any(String.class));

        return accessInterface;
    }

    static void setAccessDataInterface(DataBaseInteraction f, DataAccessInterface i)
            throws NoSuchFieldException, IllegalAccessException {
        Field dataAccessField = f.getClass().getDeclaredField("accessInterface");
        dataAccessField.setAccessible(true);
        dataAccessField.set(f, i);
    }

    static List<String> expectedList() {
        List<String> result = new ArrayList<>();
        for(int counter = 0; counter < 10; counter++) {
            result.add("A");
        }
        return result;
    }

    static void assertListsEquals(List<String> expected, List<String> actual) {

        assertEquals(expected.size(), actual.size());

        int cycleBound = expected.size();
        for (int counter = 0; counter < cycleBound; counter++) {
            assertEquals(expected.get(counter), actual.get(counter));
        }
    }
}
