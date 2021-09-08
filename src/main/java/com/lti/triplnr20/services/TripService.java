package com.lti.triplnr20.services;

import com.lti.triplnr20.models.PassengerRequest;
import com.lti.triplnr20.models.Trip;

public interface TripService {
	
	Trip createTrip(Trip trip);
	Trip getTripById(int tripId);
	Trip updateTrip(Trip trip);
	void denyRequest(PassengerRequest request);
	void acceptRequest(PassengerRequest request);
	PassengerRequest makeRequest(PassengerRequest request);
}
