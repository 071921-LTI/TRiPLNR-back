package com.lti.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	Gson gson = new Gson();
	
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody User user){
		String token = as.login(user);
		if (token != null) {
			return new ResponseEntity<>(gson.toJson(token), HttpStatus.OK);
		}
		
		return new ResponseEntity<>(gson.toJson("wrong"), HttpStatus.BAD_REQUEST);
	}


	@Autowired
	public AuthController(AuthService as) {
		this.as = as;
	}
	
}
