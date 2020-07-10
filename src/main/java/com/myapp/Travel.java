package com.myapp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

public class Travel {

    private static final Log log = LogFactory.getLog(Travel.class);

    private Travel() { }

    public static String journey(City origin, City dest) {

    		log.info("Origin City: " + origin.getName() + ", Destination City: " + dest.getName());

        if (origin.equals(dest)) return "yes";

        if (origin.getNearby().contains(dest)) return "yes";

        Set<City> visitedCities = new HashSet<>(Collections.singleton(origin));

        Deque<City> neighbourCityList = new ArrayDeque<>(origin.getNearby());

        while (!neighbourCityList.isEmpty()) {

            City city = neighbourCityList.getLast();

            if (city.equals(dest)) return "yes";

            if (!visitedCities.contains(city)) {
            		visitedCities.add(city);

            		neighbourCityList.addAll(city.getNearby());
            		neighbourCityList.removeAll(visitedCities);

                log.info("Visiting: "+ city.getName() + " , neighbours: " + (city.getNeighbours()) + ", neighbourCityList: " + neighbourCityList.toString());
            } else {
            		neighbourCityList.removeAll(Collections.singleton(city));
            }
        }

        return "no";
    }
}

