package com.lti.triplnr20.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lti.triplnr20.models.User;
import com.lti.triplnr20.services.UserService;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {
	
	UserService us;
	
	@Autowired
	public UserController(UserService us) {
		super();
		this.us = us;
	}
	
	@PostMapping("/update")
	public ResponseEntity<String> update(@RequestBody User user){
		boolean bool = us.updateUser(user);
		if (bool) {
			return new ResponseEntity<>("Successfull update", HttpStatus.OK);
		}else {
			return new ResponseEntity<>("Unsuccessfull update", HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value="/update/{id}")
	public ResponseEntity<User> getById(@PathVariable("id") int id){
		return new ResponseEntity<User>(us.getUserById(id), HttpStatus.OK);
	}
	
}
