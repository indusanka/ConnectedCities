package com.cities.connectedcities.manager;

import com.cities.connectedcities.exceptions.ReaderException;
import com.cities.connectedcities.model.City;
import com.cities.connectedcities.reader.IDataReader;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.util.Map;
import static com.cities.connectedcities.utils.Constants.*;

/**
 * Determines if two cities are connected. Manager depends on reader to load data and a validator to determine
 * the connection between two cities.
 */
@Component
public class ConnectionManager implements IConnectionManager {

    private static final Log LOG = LogFactory.getLog(ConnectionManager.class);
    private IDataReader reader;
    private Map<String, City> cityMap;
    private ConnectionValidator validator;

    @Autowired
    public ConnectionManager(IDataReader reader, ConnectionValidator validator) {
        this.reader = reader;
        this.validator = validator;
    }

    ConnectionManager(Map<String, City> cityMap, ConnectionValidator validator) {
        this.cityMap = cityMap;
        this.validator = validator;
    }

    @PostConstruct
    private void loadData() throws ReaderException {
        cityMap = reader.readCityConnections();
    }

    private City getCity(String name) {
        City city =  null;
        if (cityMap.containsKey(name))
          city = cityMap.get(name);
        return city;
    }

    public String checkCommuteExist(String inputOrigin, String inputDestination) {
        City originCity = getCity(inputOrigin.toUpperCase());
        City destCity = getCity(inputDestination.toUpperCase());

        if (originCity == null || destCity == null) {
            LOG.info("Bad request");
            return RESPONSE_NO;
        }

        if (validator.validateCommute(originCity, destCity))
            return RESPONSE_YES;
        else
            return RESPONSE_NO;
    }
}