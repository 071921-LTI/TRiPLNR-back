package com.lti.triplnr20.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.lti.triplnr20.daos.TripRepository;
import com.lti.triplnr20.daos.UserRepository;
import com.lti.triplnr20.models.Trip;

public class TripServiceImpl implements TripService {
	
	private TripRepository tr;
	private UserRepository ur;
	private AddressService as;
	
	@Autowired
	public TripServiceImpl(TripRepository tr, UserRepository ur, AddressService as) {
		super();
		this.tr = tr;
		this.ur = ur;
		this.as = as;
	}
	
	@Override
	@Transactional
	public Trip createTrip(Trip trip) {
		String destination = null;
		destination = as.isValidAddress(trip.getDestination());
		if(destination != null) {
			trip.setDestination(destination);
			tr.save(trip);
			return trip;
		} else {
			return null;
		}
	}

}