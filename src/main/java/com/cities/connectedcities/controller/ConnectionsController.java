package com.cities.connectedcities.controller;

import com.cities.connectedcities.manager.IConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import static com.cities.connectedcities.utils.Constants.*;

/**
 * SpringApplication along with a simple REST controller
 * and a custom exception handler
 */
@SpringBootApplication
@RestController
public class ConnectionsController {

    @Autowired
    IConnectionManager manager;

    public static void main(String[] args) {
        SpringApplication.run(ConnectionsController.class, args);
    }

    @GetMapping(value = "/connected", produces = "text/plain")
    public String isConnected(
            @RequestParam(name = "origin", required = false) String inputOrigin,
            @RequestParam(name = "destination", required = false)  String inputDestination) {


        if(StringUtils.isEmpty(inputOrigin) || StringUtils.isEmpty(inputDestination)) {
            return RESPONSE_NO;
        }

        return manager.checkCommuteExist(inputOrigin, inputDestination);
    }

}
