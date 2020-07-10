package com.myapp;

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
import java.util.Map;
import java.util.Scanner;


@Component
public class RoadMap {

    private final Log log = LogFactory.getLog(getClass());

    private Map<String, City> cityMap = new HashMap<>();

    @Value("${data.file:classpath:cities.txt}")
    private String cities;

    @Autowired
    private ResourceLoader resourceLoader;


    public Map<String, City> getCityMap() {
        return cityMap;
    }

    @PostConstruct
    private void read() throws IOException {

        Resource resource = resourceLoader.getResource(cities);

        InputStream is;

        if (!resource.exists()) {
            is = new FileInputStream(new File(cities));
        } else {
            is = resource.getInputStream();
        }

        Scanner scanner = new Scanner(is);

        while (scanner.hasNext()) {

            String inputData = scanner.nextLine();
            if (StringUtils.isEmpty(inputData)) 
            		continue;

            log.info("inputData: "+inputData);

            String[] split = inputData.split(",");
            String city1 = split[0].trim().toUpperCase();
            String city2 = split[1].trim().toUpperCase();

            if (!city1.equals(city2)) {
                City c1 = cityMap.getOrDefault(city1, City.build(city1));
                City c2 = cityMap.getOrDefault(city2, City.build(city2));

                c1.addNearby(c2);
                c2.addNearby(c1);

                cityMap.put(c1.getName(), c1);
                cityMap.put(c2.getName(), c2);
            }
        }

        log.info("Map: " + cityMap);
    }

    public City getCity(String name) {
        return cityMap.get(name);
    }

}
