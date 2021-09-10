package com.lti.triplnr20.controllers;


import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.lti.triplnr20.daos.TripRepository;
import com.lti.triplnr20.daos.UserRepository;
import com.lti.triplnr20.models.FriendRequest;
import com.lti.triplnr20.models.PassengerRequest;
import com.lti.triplnr20.models.Trip;
import com.lti.triplnr20.models.User;
import com.lti.triplnr20.services.AuthServiceImpl;
import com.lti.triplnr20.services.TripService;
import com.lti.triplnr20.services.UserService;


@RestController
@RequestMapping("/trip")
@CrossOrigin(exposedHeaders = "Authorization")

public class TripController {
	
	
	TripService ts;
	TripRepository tr;

	UserService us;
	UserRepository ur;
	Gson gson = new Gson();
	
	String timeFormat = "0000-00-00 00:00:00";
	
	@GetMapping("/dashboard")
	public ResponseEntity<List<Trip>> getTripsByUser(@RequestHeader("Authorization") String token){
		return new ResponseEntity<>(us.getTripsByUser(token), HttpStatus.OK);
	}
	
	
	@PostMapping("/create")
	//create trip receives trip object, authorization header and starttime header
	public ResponseEntity<Trip> createTrip(@RequestBody Trip trip, @RequestHeader("Authorization") String token, @RequestHeader("StartTime") String startTimeString, @RequestHeader("EndTime") String endTimeString ){
		
		
		//gets current user object
		User u = us.getUserBySub(token);
		
		//checks startTimeString header
		if(startTimeString.equals(timeFormat)){
			String cantFollowDirections = timeFormat;
			Timestamp startTimeStamp = Timestamp.valueOf(cantFollowDirections);
			trip.setStartTime(startTimeStamp);
		} else {
			//if startTimeString is in proper format, creates Timestamp datatype using string data
			Timestamp startTimeStamp = Timestamp.valueOf(startTimeString);
			//sets new startTimeStamp to startTime of trip
			trip.setStartTime(startTimeStamp);	
		}
		
		if(endTimeString.equals(timeFormat)){
			String cantFollowDirections2 = timeFormat;
			Timestamp endTimeStamp = Timestamp.valueOf(cantFollowDirections2);
			trip.setEndTime(endTimeStamp);
		} else {
			//if startTimeString is in proper format, creates Timestamp datatype using string data
			Timestamp endTimeStamp = Timestamp.valueOf(endTimeString);
			//sets new startTimeStamp to startTime of trip
			trip.setEndTime(endTimeStamp);	
		}
		
		
		//sets current user to manager of trip
		trip.setManager(u);
		//gets address of current user and set to trip origin location
		trip.setOrigin(u.getAddress());
		//calls tripServicese method create trip and passes through new trip object
		Trip newTrip = ts.createTrip(trip);
		//checks to see if a new trip has been created and saved and returns proper response 
		if(newTrip != null) {
			return new ResponseEntity<>(newTrip, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>( HttpStatus.BAD_REQUEST);

		}
		
	}
	
	@PutMapping("/update")
	public ResponseEntity<Trip> updateTrip(@RequestBody Trip trip, @RequestHeader("Authorization") String token, @RequestHeader("StartTime") String startTimeString, @RequestHeader("EndTime") String endTimeString ){
		
		//gets current user object
		User u = us.getUserBySub(token);
		
		//checks startTimeString header
		if(startTimeString.equals(timeFormat)){
			String cantFollowDirections = timeFormat;
			Timestamp startTimeStamp = Timestamp.valueOf(cantFollowDirections);
			trip.setStartTime(startTimeStamp);
		} else {
			//if startTimeString is in proper format, creates Timestamp datatype using string data
			Timestamp startTimeStamp = Timestamp.valueOf(startTimeString);
			//sets new startTimeStamp to startTime of trip
			trip.setStartTime(startTimeStamp);	
		}
		
		if(endTimeString.equals(timeFormat)){
			String cantFollowDirections2 = timeFormat;
			Timestamp endTimeStamp = Timestamp.valueOf(cantFollowDirections2);
			trip.setEndTime(endTimeStamp);
		} else {
			//if startTimeString is in proper format, creates Timestamp datatype using string data
			Timestamp endTimeStamp = Timestamp.valueOf(endTimeString);
			//sets new startTimeStamp to startTime of trip
			trip.setEndTime(endTimeStamp);	
		}
		
		//gets address of current user and set to trip origin location
		trip.setManager(u);
		//calls tripServicese method create trip and passes through new trip object
		Trip newTrip = ts.updateTrip(trip);
		//checks to see if a new trip has been created and saved and returns proper response 
		if(newTrip != null) {
			return new ResponseEntity<>(newTrip, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>( HttpStatus.OK);
			}
}
	
	//Requests for all friend requests for the current user logged in 
	@GetMapping("/myrequests")
	public ResponseEntity<List<PassengerRequest>> getRequests(@RequestHeader("Authorization") String token){
		return new ResponseEntity<>(ts.getRequestByTo(us.getUserBySub(token)), HttpStatus.OK);
	}
	
	@PutMapping("/accept")
	public ResponseEntity<Trip> acceptPassengerRequest(@RequestBody PassengerRequest passengerRequest) {
		ts.acceptRequest(passengerRequest);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PutMapping("/deny")
	public ResponseEntity<Trip> denyPassengerRequest(@RequestBody PassengerRequest passengerRequest) {
		ts.denyRequest(passengerRequest);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	//get method to return trip by provided trip id
	@GetMapping("/{id}")
	public ResponseEntity<Trip> getTrip(@PathVariable("id") int tripId, @RequestHeader("Authorization") String token){
		return new ResponseEntity<>(ts.getTripById(tripId), HttpStatus.OK);
	}
	
	@Autowired
	public TripController(TripService ts, UserService us, UserRepository ur, TripRepository tr) {
		this.ts = ts;
		this.us = us;
		this.ur = ur;
		this.tr = tr;
	}

}
