package com.lti.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.lti.models.User;
import com.lti.services.AuthService;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {
	
	AuthService as;
	private static final Gson gson = new Gson();
	
	/*
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody User user){
		System.out.println("in post");
		String token = as.login(user);
		if (token != null) {
			return new ResponseEntity<>(token, HttpStatus.OK);
		}
		
		return new ResponseEntity<>("wrong", HttpStatus.BAD_REQUEST);
	}
	*/
	
	@PostMapping("/login")
	public ResponseEntity<String> login(){
		String token = as.login(new User("username", "password"));
		if (token != null) {
			return new ResponseEntity<>(gson.toJson(token), HttpStatus.OK);
		}
		
		return new ResponseEntity<>(gson.toJson("wrong"), HttpStatus.OK);
	}


	@Autowired
	public AuthController(AuthService as) {
		this.as = as;
	}
	
	@GetMapping("/test")
	public ResponseEntity<String> test(){
		return new ResponseEntity<>("it works", HttpStatus.OK);
	}
	
}
