package com.pluralsight.service;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pluralsight.model.Ride;
import com.pluralsight.repository.RideRepository;

@Service("rideService")
public class RideServiceImpl implements RideService {

	@Autowired
	private RideRepository rideRepository;
	
	@Override
	public List<Ride> getRides() {
		return rideRepository.getRides();
	}
	
	@Override
	public Ride createRide(Ride ride) {
		return rideRepository.createRide(ride);
	}

	@Override
	public Ride getRide(Integer number) {
		Ride ride=rideRepository.getRide(number);
		System.out.println(ride);
		return ride;
	}

	@Override
	public Ride updateRide(Ride ride) {
		return rideRepository.updateRide(ride);
	}

	@Override
	public void batch() {
		List<Object[]> pairs = new ArrayList<>();
		List<Ride> rides=rideRepository.getRides();
		for(Ride ride : rides) {
			Object[] tmp = {new Date() , ride.getId()};
			pairs.add(tmp);
		}
		rideRepository.updateRides(pairs);
	}

}
