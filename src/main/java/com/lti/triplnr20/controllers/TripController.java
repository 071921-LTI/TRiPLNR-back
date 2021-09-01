package com.lti.triplnr20.controllers;


import java.util.List;

import javax.sound.sampled.TargetDataLine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.GetMapping;
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

import com.lti.triplnr20.services.AuthService;
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
	public ResponseEntity<Trip> createTrip(@RequestBody Trip trip, @RequestHeader("Authorization") String token ){
		System.out.println("in post create trip");
		System.out.println(trip);
		String[] authToken = token.split(":");
		int userId = Integer.parseInt(authToken[0]);
		
		System.out.println("user id from authtoken is: "+userId);
		
		User u = us.getUserById(userId);
		System.out.println("user: "+ u.getUsername());
		
		System.out.println("Passengers: "+trip.getPassengers());
		
		trip.setManager(u);
		trip.setOrigin(u.getAddress());
		
		Trip newTrip = ts.createTrip(trip);
		if(newTrip != null) {
			return new ResponseEntity<>(newTrip, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>( HttpStatus.OK);

		}
		
	}
	
	@Autowired
	public TripController(TripService ts, UserService us, UserRepository ur, TripRepository tr) {
		this.ts = ts;
		this.us = us;
		this.ur = ur;
		this.tr = tr;
	}

}
