package com.cities.connectedcities.manager;

import com.cities.connectedcities.exceptions.ReaderException;
import com.cities.connectedcities.model.City;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static com.cities.connectedcities.utils.Constants.*;

public class ConnectionManagerTests {
    private ConnectionValidator validator;
    private Map<String, City> cityMap;
    private ConnectionManager manager;
    private City cityA;
    private City cityB;
    private City cityC;
    private City cityD;
    private City cityE;

    @Before
    public void init() throws ReaderException {
        validator = new ConnectionValidator();
        cityMap = new HashMap<>();

        cityA = City.builder().name("Seattle").build();
        cityB = City.builder().name("Redmond").adjCities(Collections.singleton(cityA)).build();
        cityC = City.builder().name("Kirkland").adjCities(Collections.singleton(cityB)).build();

        cityD = City.builder().name("Austin").build();
        cityE = City.builder().name("Dallas").adjCities(Collections.singleton(cityD)).build();

        cityMap.put(cityA.getName(), cityA);
        cityMap.put(cityB.getName(), cityB);
        cityMap.put(cityC.getName(), cityC);
        manager = new ConnectionManager(cityMap, validator);

    }

    @Test
    public void test_checkCommuteExist_WithUnknownCity() {
        City cityE = City.builder().name("Dallas").build();
        Assert.assertTrue(manager.checkCommuteExist(cityE.getName(), cityC.getName()).equals(RESPONSE_NO));
    }

    @Test
    public void test_checkCommuteExist_WithConnectedCities() {
        String inputCityA = "Redmond";
        String inputCityB = "Seattle";
        Assert.assertTrue(manager.checkCommuteExist(inputCityA, inputCityB).equals(RESPONSE_YES));
    }

    @Test
    public void test_checkCommuteExist_WithUnConnectedCities() {
        String inputCityA = "Seattle";
        String inputCityB = "Austin";
        Assert.assertTrue(manager.checkCommuteExist(inputCityA, inputCityB).equals(RESPONSE_NO));
    }
}
