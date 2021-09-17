package com.lti.triplnr20.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lti.triplnr20.daos.PassengerRequestRepository;
import com.lti.triplnr20.daos.TripRepository;
import com.lti.triplnr20.daos.UserRepository;
import com.lti.triplnr20.models.PassengerRequest;
import com.lti.triplnr20.models.Trip;
import com.lti.triplnr20.models.User;

@Service
public class TripServiceImpl implements TripService {
	
	private TripRepository tr;
	private PassengerRequestRepository pr;
	private UserRepository ur;
	
	
	private AddressService as;
	private UserService us;
	
	@Autowired
	public TripServiceImpl(TripRepository tr, PassengerRequestRepository pr, UserService us, AddressService as, UserRepository ur) {
		super();
		this.tr = tr;
		this.us = us;
		this.as = as;
		this.ur = ur;
		this.pr = pr;
	}
	
	@Override
	@Transactional
	public Trip createTrip(Trip trip) {
		String destination = null;
		User u = trip.getManager();
		
		//list of current user trips
		List<Trip> temptrip = u.getTrips();
		
		//Save list of seperate passengers on trip
		List<User> tempusers = trip.getPassengers();
		trip.setPassengers(new ArrayList <User>());
		
		
		List<String> stops = trip.getStops();
		List<String> valStops = new ArrayList<String>();
		//checks to make sure address is formated in a way google maps api will accept
		destination = as.isValidAddress(trip.getDestination());
		if(destination != null) {
			//sets destination in object
			trip.setDestination(destination);
			
			if(stops != null)
			{
				for(String stop : stops) {
					String tempArr = null;
					tempArr = as.isValidAddress(stop);
					if(tempArr != null) {
						valStops.add(tempArr);
					}
				}
			}
			
			if(valStops.size() > 0 || valStops != null)
				trip.setStops(valStops);
			//saves trip
			tr.save(trip);
			//adds new trip object to existing trips list
			temptrip.add(trip);
			//sets new list to current user
			u.setTrips(temptrip);
			//updates user with new trips list
			ur.save(u);
			
			for (User user : tempusers) {
				
				//Send out passenger requests to all passengers on trip
				PassengerRequest request = new PassengerRequest(0, u, user, trip);
				makeRequest(request);
				
//				user = us.getUserById(user.getUserId());
//				List<Trip> trips = user.getTrips();
//				trips.add(trip);
//				user.setTrips(trips);
//				ur.save(user);
			}
			
			return trip;
		} else {
			return null;
		}
	}
	
	@Override
	public Trip updateTrip(Trip trip) {
		List<User> tempusers = trip.getPassengers();
		List<String> validAddr = new ArrayList<String>();
		trip.setPassengers(new ArrayList <User>());
		
		/*list of current user trips
		checks to make sure address is formated in a way google maps api will accept*/
		trip.setDestination(as.isValidAddress(trip.getDestination()));
		trip.setOrigin(as.isValidAddress(trip.getOrigin()));
		List<String> stops = trip.getStops();
		if(stops != null) {
			for(String stop : stops)
			{
				String tempVal = as.isValidAddress(stop);
				if(tempVal != null)
					validAddr.add(tempVal);
			}
			
		}
		if(validAddr.size() > 0 || validAddr != null)
			trip.setStops(validAddr);
		
		if(trip.getDestination() != null && trip.getOrigin() != null) {
			//removes trip to be updated from list
			Trip tempTrip = tr.getById(trip.getTripId());
			for (User user : tempTrip.getPassengers()) {
				if (!tempusers.contains(ur.getById(user.getUserId()))) {
					List<Trip> tempTrips = user.getTrips();
					tempTrips.remove(tempTrip);
					user.setTrips(tempTrips);
					ur.save(user);
				}
			}
			
			/*Go through passenger list of new the trip, and for every new passenger create a
			   new passenger request for them if one doesn't currently exists for this trip for them*/
			for (User user : tempusers) {
				user = us.getUserById(user.getUserId());
				if (!tempTrip.getPassengers().contains(ur.getById(user.getUserId())) && !getByToAndFromAndTrip(user, trip.getManager(), trip)) {
					PassengerRequest request = new PassengerRequest(0, trip.getManager(), user, trip);
					makeRequest(request);
//					List<Trip> tempTrips = user.getTrips();
//					if (tempTrips == null) {
//						tempTrips = new ArrayList<>();
//						tempTrips.add(trip);
//					}else {
//						tempTrips.add(trip);
//					}
//					user.setTrips(tempTrips);
//					ur.save(user);
				}
			}
			
			//Removes passengers if they are not on the updated trip passenger list
			List<User> keepUsers = new ArrayList <User>();
			for (User user : tempusers) {
				int flag = 0;
				for (User user2 : tempTrip.getPassengers()) {
					if (user.getUserId() == user2.getUserId()) {
						keepUsers.add(user);
						flag = 1;
					}
				}
				if (flag == 0) {
					//Checks if the user has a role, if they do remove them from that as well
					if(trip.getSnacks() != null && user.getUserId() == trip.getSnacks().getUserId()) {
						trip.setSnacks(null);
					}
					
					if(trip.getNavigator() != null && user.getUserId() == trip.getNavigator().getUserId()) {
						trip.setNavigator(null);
					}
					
					if(trip.getMusic() != null && user.getUserId() == trip.getMusic().getUserId()) {
						trip.setMusic(null);
					} 
				}
			}
			
			trip.setPassengers(keepUsers);
			tr.save(trip);

			return trip;
		} else {
			return null;
		}
	}

	
	//Accepts a trip request by adding requested as passenger to trip and the trip to their list
	@Override
	public void acceptRequest(PassengerRequest request) {
		User to = ur.getById(request.getTo().getUserId());
		Trip trip = request.getTrip();
		
		List<Trip> tempTrips = to.getTrips();
		if (tempTrips == null) {
			tempTrips = new ArrayList<>();
			tempTrips.add(trip);
		}else {
			tempTrips.add(trip);	
		}
		to.setTrips(tempTrips);
		
		List<User> tempPass = trip.getPassengers();
		if (tempPass == null) {
			tempPass = new ArrayList<>();
			tempPass.add(to);
		}else {
			tempPass.add(to);	
		}
		trip.setPassengers(tempPass);

		ur.save(to);
		tr.save(trip);
		pr.delete(request);
	}

	@Override
	public void denyRequest(PassengerRequest request) {
		pr.delete(request);
	}
	
	@Override
	public PassengerRequest makeRequest(PassengerRequest request) {

		
		return pr.save(request);

	}
	
	//gets trip by trip id
	@Override
	public Trip getTripById(int tripId) {
		return tr.getById(tripId);
	}
	
	//gets trip by trip id
	@Override
	public List<PassengerRequest> getRequestByTo(User to) {
		return pr.findByTo(to);
	}
	
	//gets passenger request by trip id
	@Override
	public boolean getByToAndFromAndTrip(User to, User from, Trip trip) {
		return pr.existsByToAndFromAndTrip(to, from, trip);
	}
	
}


