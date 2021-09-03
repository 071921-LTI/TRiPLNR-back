package com.lti.triplnr20.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.TSFBuilder;
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
		String destination = null;
		User u = trip.getManager();
		//list of current user trips
		List<Trip> temptrip = u.getTrips();
		//checks to make sure address is formated in a way google maps api will accept
		destination = as.isValidAddress(trip.getDestination());
		if(destination != null) {
			//sets destination in object
			trip.setDestination(destination);
			//saves trip
			tr.save(trip);
			//adds new trip object to existing trips list
			temptrip.add(trip);
			//sets new list to current user
			u.setTrips(temptrip);
			//updates user with new trips list
			us.updateUser(u);
			return trip;
		} else {
			return null;
		}
	}
	
	@Override
	public Trip updateTrip(Trip trip) {
		String destination = null;
		String origin = null;
		Trip existingTrip = tr.getById(trip.getTripId());
		User u = trip.getManager();
		List<Trip> temptrip = u.getTrips();
		//list of current user trips
		//checks to make sure address is formated in a way google maps api will accept
		destination = as.isValidAddress(trip.getDestination());
		origin = as.isValidAddress(trip.getOrigin());
		if(destination != null && origin != null) {
			//removes trip to be updated from list
			temptrip.remove(existingTrip);
			us.updateUser(u);
			
			//sets destination in object
			existingTrip.setDestination(destination);
			existingTrip.setOrigin(origin);
			//adds updated trip to trip list
			temptrip.add(existingTrip);
			//saves trip
			tr.save(existingTrip);
			//updates user with new list
			us.updateUser(u);

			return existingTrip;
		} else {
			return null;
		}
	}


	
	
	
	//gets grip by trip id
	@Override
	public Trip getTripById(int tripId) {
		return tr.getById(tripId);
	}
	
}


