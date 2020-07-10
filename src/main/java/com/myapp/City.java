package com.myapp;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class City {
    private String name;

    private Set<City> nearby = new HashSet<>();

    private City(String name) {
        Objects.requireNonNull(name);
        this.name = name.trim().toUpperCase();
    }

    private City() {
    }

    public static City build(String name) {
        return new City(name);
    }

    @Override
    public String toString() {

        return "City{" +"name='" + name + "'" +", nearby='" + getNeighbours() +"'}";
    }

    public String getNeighbours() {
        return nearby.stream().map(City::getName).collect(Collectors.joining(","));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public City addNearby(City city) {
        nearby.add(city);
        return this;
    }

    public Set<City> getNearby() {
        return nearby;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof City)) return false;
        City city = (City) o;
        return Objects.equals(name, city.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

}
