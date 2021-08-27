//package com.lti.triplnr20.controllers;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestHeader;
//
//import com.google.gson.Gson;
//import com.lti.triplnr20.daos.UserRepository;
//import com.lti.triplnr20.models.Trip;
//import com.lti.triplnr20.models.User;
//import com.lti.triplnr20.services.AuthService;
//import com.lti.triplnr20.services.TripService;
//import com.lti.triplnr20.services.UserService;
//
//
//@RestController
//@RequestMapping("/trip")
//@CrossOrigin("*")
//public class TripController {
//	
//	
//	TripService ts;
//	
//	//UserService us;
//	UserRepository ur;
//	Gson gson = new Gson();
//	@Autowired
//	public TripController(TripService ts) {
//		super();
//		this.ts = ts;
//	}
//	
//	@PostMapping("/create")
//	public ResponseEntity<String> createTrip(@RequestBody Trip trip, @RequestHeader("Authorization") String token ){
//		String[] authToken = token.split(":");
//		int userId = Integer.valueOf(authToken[0]);
//		User u = ur.getById(userId);
//		trip.setManager(u);
//		trip.setOrigin(u.getAddress());
//		if(ts.createTrip(trip) != null) {
//			return new ResponseEntity<>(gson.toJson(trip), HttpStatus.CREATED);
//		} else {
//			return new ResponseEntity<>(gson.toJson("wrong"), HttpStatus.BAD_REQUEST);
//		}
//		
//	}
//	
//
//
//}
