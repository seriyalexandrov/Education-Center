package com.aleksandrov.example.junit;

import com.aleksandrov.example.junit.dao.DataAccessInterface;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;

import java.math.BigInteger;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class DataBaseInteractionTest {

    DataAccessInterface accessInterface;
    DataBaseInteraction interaction;

    @Before
    public void setUp() throws NoSuchFieldException, IllegalAccessException {
        accessInterface = TestUtils.createDataAccessMock();
        interaction = new DataBaseInteraction();

        //Set via reflection or make field package-private
        TestUtils.setAccessDataInterface(interaction, accessInterface);
    }

    @Test
    public void testGetTenObjects() {

        List<String> actualResult = interaction.getTenObjects();
        List<String> expectedResult = TestUtils.expectedList();

        TestUtils.assertListsEquals(expectedResult, actualResult);
    }

    @Test
    public void testStoreObject() {
        interaction.updateObject(BigInteger.TEN);
        verify(accessInterface, times(1)).startTransaction();
        verify(accessInterface, times(1)).finishTransaction();
        verify(accessInterface, times(1)).storeObject(Matchers.any(String.class));
    }
}
