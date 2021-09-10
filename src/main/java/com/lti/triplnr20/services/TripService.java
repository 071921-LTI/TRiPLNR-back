package com.lti.triplnr20.services;

import java.util.List;

import com.lti.triplnr20.models.PassengerRequest;
import com.lti.triplnr20.models.Trip;
import com.lti.triplnr20.models.User;

public interface TripService {
	
	Trip createTrip(Trip trip);
	Trip getTripById(int tripId);
	Trip updateTrip(Trip trip);
	void denyRequest(PassengerRequest request);
	void acceptRequest(PassengerRequest request);
	PassengerRequest makeRequest(PassengerRequest request);
	List<PassengerRequest> getRequestByTo(User to);
	boolean getByToAndFromAndTrip(User to, User from, Trip trip);
}
