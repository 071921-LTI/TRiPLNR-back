package com.lti.triplnr20.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.google.gson.Gson;

import com.lti.triplnr20.models.User;
import com.lti.triplnr20.services.UserService;

@RestController
@RequestMapping("/user")

@CrossOrigin(origins = "*")
public class UserController {

	UserService us;
	Gson gson = new Gson();

	@Autowired
	public UserController(UserService us) {
		super();
		this.us = us;
	}


	@PutMapping("/update/{id}")
	public ResponseEntity<String> update(@RequestBody User user, @PathVariable("id") int id){
		User u = us.getUserById(id);
		System.out.println(u);
		user.setUserId(u.getUserId());

		String res = us.updateUser(user);
		System.out.println(user);
		if (res.equals("Successful")) {
			return new ResponseEntity<>(gson.toJson("Successful"), HttpStatus.OK);
		}else {
			return new ResponseEntity<>(gson.toJson(res), HttpStatus.BAD_REQUEST);
		}
	}
	@GetMapping(value="/{id}")
	public ResponseEntity<String> getById(@PathVariable("id") int id){
		return new ResponseEntity<>(gson.toJson(us.getUserById(id)), HttpStatus.OK);

	}
	
}
