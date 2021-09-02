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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.lti.triplnr20.daos.TripRepository;
import com.lti.triplnr20.daos.UserRepository;
import com.lti.triplnr20.models.Trip;
import com.lti.triplnr20.models.User;
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
	

	@GetMapping("/dashboard")
	public ResponseEntity<List<Trip>> getTripsByUser(@RequestHeader("Authorization") String token){
		String[] authToken = token.split(":");
		int userId = Integer.valueOf(authToken[0]);
		return new ResponseEntity<>(us.getTripsByUser(userId), HttpStatus.OK);
	}
	
	
	@PostMapping("/create")
	public ResponseEntity<Trip> createTrip(@RequestBody Trip trip, @RequestHeader("Authorization") String token, @RequestHeader("StartTime") String startTimeString ){
		System.out.println("in post create trip");
		
		
		String[] authToken = token.split(":");
		int userId = Integer.parseInt(authToken[0]);
		User u = us.getUserById(userId);
		
		System.out.println("starting time string header value:" +startTimeString);
		
		if(startTimeString == "0000-00-00 00:00:00"){
			String canFollowDirections = "0000-00-00 00:00:00";
			Timestamp startTimeStamp = Timestamp.valueOf(canFollowDirections);
			trip.setStartTime(startTimeStamp);
		} else {
			Timestamp startTimeStamp = Timestamp.valueOf(startTimeString);
			trip.setStartTime(startTimeStamp);
			
		}
		
		
		trip.setManager(u);
		trip.setOrigin(u.getAddress());
		
		Trip newTrip = ts.createTrip(trip);
		if(newTrip != null) {
			return new ResponseEntity<>(newTrip, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>( HttpStatus.OK);

		}
		
	}
	
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
