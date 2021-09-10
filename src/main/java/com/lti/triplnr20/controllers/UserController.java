package com.lti.triplnr20.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.lti.triplnr20.models.User;
import com.lti.triplnr20.services.AuthServiceImpl;
import com.lti.triplnr20.services.UserService;

@RestController
@RequestMapping("/users")

@CrossOrigin(origins = "*")

public class UserController {

	UserService us;
	Gson gson = new Gson();

	@Autowired
	public UserController(UserService us) {
		super();
		this.us = us;
	}

	
	/* 
	* Update will receive user in request body and try to update changes to the database will throw 
	* InvalidAddressException and UserAlreadyExistsException which is handled by the Exception Handler if thrown
	*/
	@PutMapping("/update")
	public ResponseEntity<String> update(@RequestBody User user, @RequestHeader("Authorization") String token ){
		String[] authToken = token.split(":");
	
		int id = Integer.parseInt(authToken[0]);
		User u = us.getUserById(id);
		user.setUserId(u.getUserId());
		us.updateUser(user);
		return new ResponseEntity<>(gson.toJson("Update Successful"), HttpStatus.OK);
	}
	
	//Receives a user id as path parameter and gets the current user with matched id in the database 
	@GetMapping(value="/{id}")
	public ResponseEntity<User> getById(@PathVariable("id") int id){
		return new ResponseEntity<>(us.getUserById(id), HttpStatus.OK);

	}

	// Recieves the sub in header to find logged in user
	@GetMapping(value="/sub")
	public ResponseEntity<User> getBySub(@RequestHeader("Authorization") String sub) {
		return new ResponseEntity<>(us.getUserBySub(sub), HttpStatus.OK);
	}
	
	//Requests for the authorization token in the request header and will send corresponding user information back 
	@GetMapping(value="/user")
	public ResponseEntity<User> getByUser(@RequestHeader("Authorization") String token ){
		return new ResponseEntity<>(us.getUserBySub(token), HttpStatus.OK);

	}
	
	///Requests for the authorization token in the request header and will send corresponding user friend list back 
	@GetMapping("/myfriends")
	public ResponseEntity<List<User>> getFriends(@RequestHeader("Authorization") String token){
		return new ResponseEntity<>(us.getFriends(token), HttpStatus.OK);
	}
	
	@GetMapping("/profiles")
	public ResponseEntity<List<User>> getProfiles(@RequestHeader("Authorization") String token){
		return new ResponseEntity<>(us.getProfiles(token), HttpStatus.OK);
		
	}
	
}
