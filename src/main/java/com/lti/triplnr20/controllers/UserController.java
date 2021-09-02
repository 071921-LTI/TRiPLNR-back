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
//@CrossOrigin(exposedHeaders = "Authorization")
public class UserController {

	UserService us;
	Gson gson = new Gson();

	@Autowired
	public UserController(UserService us) {
		super();
		this.us = us;
	}


	@PutMapping("/update")
	public ResponseEntity<String> update(@RequestBody User user, @RequestHeader("Authorization") String token ){
		String[] authToken = token.split(":");
	
		int id = Integer.parseInt(authToken[0]);
		User u = us.getUserById(id);
		user.setUserId(u.getUserId());

		us.updateUser(user);
		return new ResponseEntity<>(gson.toJson("Update Successful"), HttpStatus.OK);
	}
	@GetMapping(value="/{id}")
	public ResponseEntity<User> getById(@PathVariable("id") int id){
		return new ResponseEntity<>(us.getUserById(id), HttpStatus.OK);

	}
	
	@GetMapping(value="/user")
	public ResponseEntity<User> getByUser(@RequestHeader("Authorization") String token ){
		String[] authToken = token.split(":");
		int id = Integer.parseInt(authToken[0]);
		return new ResponseEntity<>(us.getUserById(id), HttpStatus.OK);

	}
	
	@GetMapping("/myfriends")
	public ResponseEntity<List<User>> getFriends(@RequestHeader("Authorization") String token){
		return new ResponseEntity<>(us.getFriends(AuthServiceImpl.getUserFromToken(token)), HttpStatus.OK);
	}
	
	@GetMapping("/profiles")
	public ResponseEntity<List<User>> getProfiles(@RequestHeader("Authorization") String token){
		return new ResponseEntity<>(us.getProfiles(AuthServiceImpl.getUserFromToken(token)), HttpStatus.OK);
		
	}
	
}