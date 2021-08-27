package com.lti.triplnr20.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.lti.triplnr20.models.User;
import com.lti.triplnr20.services.AuthService;


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
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("Authorization", token);
			
			return ResponseEntity.status(HttpStatus.OK)
					.headers(responseHeaders)
					.body(gson.toJson("success"));
		}
		
		return new ResponseEntity<>(gson.toJson("wrong"), HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody User user){
		String token = as.register(user);
		if (token != null) {
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("Authorization", token);
			
			return ResponseEntity.status(HttpStatus.CREATED)
					.headers(responseHeaders)
					.body(gson.toJson("success"));
		}else {
			return new ResponseEntity<>(gson.toJson("wrong"), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/test")
	public ResponseEntity<String> test(){
		return new ResponseEntity<>(gson.toJson("test works"), HttpStatus.OK);
	}


	@Autowired
	public AuthController(AuthService as) {
		this.as = as;
	}

}
