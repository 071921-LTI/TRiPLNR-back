package com.lti.triplnr20.services;

import java.util.ArrayList;
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
	public TripServiceImpl(TripRepository tr, UserService us, AddressService as, UserRepository ur) {
		super();
		this.tr = tr;
		this.us = us;
		this.as = as;
		this.ur = ur;
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
		System.out.println(trip.getDestination());
		//list of current user trips
		//checks to make sure address is formated in a way google maps api will accept
		trip.setDestination(as.isValidAddress(trip.getDestination()));
		trip.setOrigin(as.isValidAddress(trip.getOrigin()));
		if(trip.getDestination() != null && trip.getOrigin() != null) {
			//removes trip to be updated from list
			Trip tempTrip = tr.getById(trip.getTripId());
			for (User user : tempTrip.getPassengers()) {
				if (!trip.getPassengers().contains(user)) {
					List<Trip> tempTrips = user.getTrips();
					tempTrips.remove(tempTrip);
					user.setTrips(tempTrips);
					ur.save(user);
				}
				
			}
			for (User user : trip.getPassengers()) {
				user = us.getUserById(user.getUserId());
				if (!tempTrip.getPassengers().contains(user)) {
					List<Trip> tempTrips = user.getTrips();
					if (tempTrips == null) {
						tempTrips = new ArrayList<>();
						tempTrips.add(trip);
					}else {
						tempTrips.add(trip);
					}
					user.setTrips(tempTrips);
					ur.save(user);
				}
			}
			
			tr.save(trip);

			return trip;
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


