package com.lti.triplnr20.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lti.triplnr20.daos.TripRepository;
import com.lti.triplnr20.daos.UserRepository;
import com.lti.triplnr20.models.Trip;
import com.lti.triplnr20.models.User;

@Service
public class TripServiceImpl implements TripService {
	
	private TripRepository tr;
	private UserRepository ur;
	private AddressService as;
	private UserService us;
	
	@Autowired
	public TripServiceImpl(TripRepository tr, UserService us, AddressService as) {
		super();
		this.tr = tr;
		this.us = us;
		this.as = as;
	}
	
	@Override
	@Transactional
	public Trip createTrip(Trip trip) {
		System.out.println("in create trip service");
		String destination = null;
		User u = trip.getManager();
		List<Trip> temptrip = u.getTrips();
		
		destination = as.isValidAddress(trip.getDestination());
		if(destination != null) {
			System.out.println("checking dest != null");
			trip.setDestination(destination);
			tr.save(trip);
			temptrip.add(trip);
			u.setTrips(temptrip);
			us.updateUser(u);
			return trip;
		} else {
			return null;
		}
	}

}
