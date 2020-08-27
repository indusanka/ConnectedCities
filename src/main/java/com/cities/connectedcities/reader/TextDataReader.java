package com.cities.connectedcities.reader;

import com.cities.connectedcities.exceptions.ReaderException;
import com.cities.connectedcities.model.City;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;

/**
 * Build a city map from the input text file.
 * Throws ReaderException if data file is not readable or invalid
 */
@Component
public class TextDataReader implements IDataReader {

    private static final Log LOG = LogFactory.getLog(TextDataReader.class);

    @Value("${data.file:classpath:city.txt}")
    private String CITIES;

    @Autowired
    private ResourceLoader resourceLoader;

    private Map<String, City> cityMap = new HashMap<>();

    @PostConstruct
    public Map<String, City> readCityConnections() throws ReaderException {

        try {
            Resource resource = resourceLoader.getResource(CITIES);
            InputStream is;
            if (!resource.exists()) {
                is = new FileInputStream(new File(CITIES));
            } else {
                is = resource.getInputStream();
            }

            Scanner scanner = new Scanner(is);

            while (scanner.hasNext()) {

                String line = scanner.nextLine();
                if (StringUtils.isEmpty(line))
                    continue;

                String[] split = line.split(",");
                String Akey = split[0].trim().toUpperCase();
                String Bkey = split[1].trim().toUpperCase();

                if (!Akey.equals(Bkey)) {
                    City A = cityMap.getOrDefault(Akey, City.builder().name(Akey).build());
                    City B = cityMap.getOrDefault(Bkey, City.builder().name(Bkey).build());

                    A.addAdjacentCity(B);
                    B.addAdjacentCity(A);

                    cityMap.put(A.getName(), A);
                    cityMap.put(B.getName(), B);
                }
            }
        } catch (IOException e) {
            LOG.error("Error loading the cities data." + e.getMessage());
            throw new ReaderException(e.getMessage(), e);
        }
        return cityMap;
    }
}
