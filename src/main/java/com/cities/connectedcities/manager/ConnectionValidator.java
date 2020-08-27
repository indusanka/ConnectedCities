package com.cities.connectedcities.manager;

import com.cities.connectedcities.model.City;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ConnectionValidator {

    private static final Log LOG = LogFactory.getLog(ConnectionValidator.class);

    /**
     * Uses a simple DFS algorithm to determine if destination city is reachable from origin.
     */
    public boolean validateCommute(City origin, City destination) {

        LOG.info(String.format("OriginCity: %s , DestinationCity: %s", origin.getName(), destination.getName()));

        if (origin.equals(destination))
            return true;

        if (origin.getAdjCities().contains(destination))
            return true;

        Stack<City> neighborList = new Stack<>();
        neighborList.add(origin);
        Set<City> visited = new HashSet<>();
        while (!neighborList.isEmpty()) {
            City city = neighborList.pop();
            if (city.equals(destination))
                return true;
            if (!visited.contains(city)) {
                visited.add(city);
                neighborList.addAll(city.getAdjCities());
                LOG.info(String.format("Current city: %s, NeighborsList: %s", city, city.toString()));
            }
        }
        return false;
    }
}
