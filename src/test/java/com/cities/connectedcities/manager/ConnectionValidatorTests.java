package com.cities.connectedcities.manager;

import com.cities.connectedcities.model.City;
import java.util.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ConnectionValidatorTests {
    private ConnectionValidator validator;

    @Before
    public void init() {
        validator = new ConnectionValidator();
    }

    @Test
    public void test_ValidateCommute_WithSameOriginDestination() {
        City cityA = City.builder().name("Seattle").build();
        City cityB = City.builder().name("Seattle").build();
        Assert.assertTrue(validator.validateCommute(cityA, cityB) == true);
    }

    @Test
    public void test_ValidateCommute_WithDestinationInAdjacentCities() {
        Set<City> adjCityList = new HashSet<>();
        City cityB = City.builder().name("Redmond").build();
        adjCityList.add(cityB);
        City cityA = City.builder().name("Seattle").adjCities(adjCityList).build();
        Assert.assertTrue(validator.validateCommute(cityA, cityB) == true);
    }

    @Test
    public void test_ValidateCommute_WithDestinationInNestedAdjacentCities() {
        Set<City> adjCityList = new HashSet<>();
        City cityC = City.builder().name("Kirkland").build();
        adjCityList.add(cityC);

        City cityB = City.builder().name("Redmond").adjCities(adjCityList).build();
        adjCityList = new HashSet<>();
        adjCityList.add(cityB);

        City cityA = City.builder().name("Seattle").adjCities(adjCityList).build();
        Assert.assertTrue(validator.validateCommute(cityA, cityC) == true);
    }

    @Test
    public void test_ValidateCommute_WithDestinationNotInAdjacentCities() {
        Set<City> adjCityList = new HashSet<>();
        City cityB = City.builder().name("Redmond").build();
        adjCityList.add(cityB);
        City cityA = City.builder().name("Seattle").adjCities(adjCityList).build();
        City cityC = City.builder().name("Kirkland").build();
        Assert.assertTrue(validator.validateCommute(cityA, cityC) == false);
    }

    @Test
    public void test_ValidateCommute_WithDestinationNotInNestedAdjacentCities() {
        Set<City> adjCityList = new HashSet<>();
        City cityE = City.builder().name("Dallas").build();
        adjCityList.add(cityE);

        City cityD = City.builder().adjCities(adjCityList).name("Austin").build();

        City cityC = City.builder().name("Kirkland").build();
        adjCityList = new HashSet<>();
        adjCityList.add(cityC);

        City cityB = City.builder().name("Redmond").adjCities(adjCityList).build();
        adjCityList = new HashSet<>();
        adjCityList.add(cityB);

        City cityA = City.builder().name("Seattle").adjCities(adjCityList).build();
        Assert.assertTrue(validator.validateCommute(cityA, cityE) == false);
    }
}
