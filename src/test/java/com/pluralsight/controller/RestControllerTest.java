package com.pluralsight.controller;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.pluralsight.model.Ride;

import org.junit.Test;

public class RestControllerTest {

	@Test(timeout=3000)
	public void testGetRides() {
		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<List<Ride>> ridesResponse = restTemplate.exchange(
				"http://localhost:8080/ride_tracker/rides", HttpMethod.GET,
				null, new ParameterizedTypeReference<List<Ride>>() {
				});
		List<Ride> rides = ridesResponse.getBody();

		for (Ride ride : rides) {
			System.out.println("Ride name: " + ride.getName());
		}
	}
	
	@Test(timeout=5000)
	public void testcreateRides() {
		RestTemplate restTemplate = new RestTemplate();
		Ride ride = new Ride();
		ride.setName("stranger 2 ");
		ride.setDuration(200);
		String url = "http://localhost:8080/ride_tracker/ride";
		ride =restTemplate.postForObject(url, ride , Ride.class);
		System.out.println("ride is "+ ride);
	}
	@Test(timeout=3000)
	public void testGetRide() {
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8080/ride_tracker/ride/1";
		Ride ride=restTemplate.getForObject(url, Ride.class);
		System.out.println(ride.getName());
	}
	
	@Test(timeout=3000)
	public void testupdateRide() {
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8080/ride_tracker/ride/1";
		Ride ride = restTemplate.getForObject(url, Ride.class);
		ride.setDuration(ride.getDuration() + 60 +60);
		url="http://localhost:8080/ride_tracker/ride";
		restTemplate.put(url, ride);
	}
	
	@Test(timeout=3000)
	public void testBatch() {
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8080/ride_tracker/batch";
		restTemplate.getForObject(url, Ride.class);
	}
	
	@Test(timeout=3000)
	public void testDelete() {
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8080/ride_tracker/ride/3";
		restTemplate.delete(url);
	}
	
	@Test(timeout=5000)
	public void testError() {
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8080/ride_tracker/test";
		restTemplate.getForObject(url, Ride.class);
	}
	
	
	
	
}
