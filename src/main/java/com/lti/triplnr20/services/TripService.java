package com.lti.triplnr20.services;

import com.lti.triplnr20.models.Trip;

public interface TripService {
	
	Trip createTrip(Trip trip);
	Trip getTripById(int tripId);

}
