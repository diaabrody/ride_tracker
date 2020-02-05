package com.pluralsight.service;

import java.util.List;

import com.pluralsight.model.Ride;

public interface RideService {

	List<Ride> getRides();
	Ride createRide(Ride ride);
	Ride getRide(Integer number);
	Ride updateRide(Ride ride);
	void batch();
	void delete(Integer id);

}