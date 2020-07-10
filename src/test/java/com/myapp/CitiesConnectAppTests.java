package com.myapp;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
public class CitiesConnectAppTests {

    @Autowired
    RoadMap roadMap;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void fileLoad() {
        Assert.assertFalse("File load failed", roadMap.getCityMap().isEmpty());
    }

    @Test
    public void sameCity() {
        City city = City.build("Boston");
    }

    @Test
    public void neighbours() {
        City cityA = roadMap.getCity("Boston");
        City cityB = roadMap.getCity("Newark");

        Assert.assertNotNull("Invalid test data. City not found: Boston", cityA);
        Assert.assertNotNull("Invalid test data. City not found: Newark", cityB);
    }

    @Test
    public void distantConnected() {
        City cityA = roadMap.getCity("Philadelphia");
        City cityB = roadMap.getCity("Boston");

        Assert.assertNotNull("Invalid test data. City not found: Philadelphia", cityA);
        Assert.assertNotNull("Invalid test data. City not found: Boston", cityB);
    }

    @Test
    public void restConnectedIT() {

        Map<String, String> params = new HashMap<>();
        params.put("origin", "Boston");
        params.put("destination", "Newark");

        String body = restTemplate.getForObject("/connected?origin={origin}&destination={destination}", String.class, params);
        Assert.assertEquals("true", body);
    }

    @Test
    public void restNotConnectedIT() {

        Map<String, String> params = new HashMap<>();
        params.put("origin", "Boston");
        params.put("destination", "Albany");

        String body = restTemplate.getForObject("/connected?origin={origin}&destination={destination}", String.class, params);
        Assert.assertEquals("false", body);
    }

    @Test
    public void badRequestIT() {
        String body = restTemplate.getForObject("/connected", String.class);
        ResponseEntity<String> response = restTemplate.exchange("/connected?origin=none&destination=none", HttpMethod.GET, HttpEntity.EMPTY, String.class);
        Assert.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

}
