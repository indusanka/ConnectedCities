package com.cities.connectedcities.reader;

import com.cities.connectedcities.exceptions.ReaderException;
import com.cities.connectedcities.model.City;
import java.util.Map;

public interface IDataReader<T extends ReaderException> {
    Map<String, City> readCityConnections() throws T;
}
