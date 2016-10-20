package com.aleksandrov.example.junit;

import com.aleksandrov.example.junit.dao.DataAccessInterface;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;

import java.math.BigInteger;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class FunctionalityTest {

    DataAccessInterface accessInterface;
    CoreFunctionality functionality;

    @Before
    public void setUp() throws NoSuchFieldException, IllegalAccessException {
        accessInterface = FunctionalityTestUtils.createDataAccessMock();
        functionality = new CoreFunctionality();

        //Set via reflection or make field package-private
        FunctionalityTestUtils.setFunctionalityDataInterface(functionality, accessInterface);
    }

    @Test
    public void testGetTenObjects() {

        List<String> actualResult = functionality.getTenObjects();
        List<String> expectedResult = FunctionalityTestUtils.expectedList();

        FunctionalityTestUtils.assertListsEquals(expectedResult, actualResult);
    }

    @Test
    public void testStoreObject() {
        functionality.updateObject(BigInteger.TEN);
        verify(accessInterface, times(1)).startTransaction();
        verify(accessInterface, times(1)).finishTransaction();
        verify(accessInterface, times(1)).storeObject(Matchers.any(String.class));
    }
}
