package com.myapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import java.util.Objects;

@SpringBootApplication
@RestController
public class CitiesConnectApp {

    @Autowired
    RoadMap roadMap;

    public static void main(String[] args) {
        SpringApplication.run(CitiesConnectApp.class, args);
    }

    
    @GetMapping(value = "/connected", produces = "text/plain")
    public String isConnected(
            @RequestParam String origin,
            @RequestParam String destination) {

        City originCity = roadMap.getCity(origin.toUpperCase());
        City destCity = roadMap.getCity(destination.toUpperCase());

        Objects.requireNonNull(originCity);
        Objects.requireNonNull(destCity);

        return String.valueOf(Travel.journey(originCity, destCity));
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String cityError() {
        return "Origin city or Destination city does not exist or invalid";
    }

}
