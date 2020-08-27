package com.cities.connectedcities.model;

import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Simple POJO City class.
 */
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class City {

    @EqualsAndHashCode.Include
    private String name;

    @Builder.Default
    private Set<City> adjCities = new HashSet<>();

    private City(String name, Set<City> adjCities) {
        Objects.requireNonNull(name);
        this.name = name.trim().toUpperCase();
        this.adjCities = adjCities;
    }

    private City() {
    }

    @Override
    public String toString() {

        String connectedCities = adjCities
                                    .stream()
                                    .map(City::getName)
                                    .collect(Collectors.joining(","));
        return String.format("name: %s, connectedCities: %s", name, connectedCities);
    }

    public void addAdjacentCity(City city) {
        adjCities.add(city);
    }
}
