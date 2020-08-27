package com.cities.connectedcities.model;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;

public class CityTest {
    @Test
    public void test_addAdjacentCity() {
        City city = City.builder().name("Seattle").build();
        city.addAdjacentCity(City.builder().adjCities(new HashSet<City>()).name("Redmond").build());
        Assert.assertTrue(city.getAdjCities().size() == 1);
    }

    @Test
    public void test_addAdjacentCity_WithCaseSensitive() {
        City city = City.builder().name("Seattle").build();
        city.addAdjacentCity(City.builder().name("Redmond").build());
        city.addAdjacentCity(City.builder().name("redmond").build());
        city.addAdjacentCity(City.builder().name(" redmond").build());
        city.addAdjacentCity(City.builder().name(" redmond ").build());
        Assert.assertTrue(city.getAdjCities().size() == 1);
    }
}
