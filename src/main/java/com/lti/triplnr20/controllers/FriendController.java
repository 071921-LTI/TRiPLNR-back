package com.lti.triplnr20.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lti.triplnr20.models.FriendRequest;
import com.lti.triplnr20.services.AuthServiceImpl;
import com.lti.triplnr20.services.FriendService;
import com.lti.triplnr20.services.UserService;

@RestController
@RequestMapping("/friends")
@CrossOrigin(exposedHeaders="Authorization")
public class FriendController {
	
	private FriendService fs;
	private UserService us;

	@Autowired
	public FriendController(FriendService fs, UserService us) {
		super();
		this.fs = fs;
		this.us = us;
	}
	
	@GetMapping("/myrequests")
	public ResponseEntity<List<FriendRequest>> getRequests(@RequestHeader("Authorization") String token){
		return new ResponseEntity<>(fs.getRequestsByUser(us.getUserById(AuthServiceImpl.getIdFromToken(token))), HttpStatus.OK);
	}
	
	@PostMapping("/newrequest")
	public ResponseEntity<FriendRequest> newRequest(@RequestHeader("Authorization") String token, FriendRequest request){
		return new ResponseEntity<>(fs.makeRequest(request), HttpStatus.CREATED);
	}
	
	@PutMapping("/accept")
	public ResponseEntity<Void> acceptRequest(@RequestHeader("Authorization") String token, FriendRequest request){
		fs.acceptRequest(request);
		return new ResponseEntity<>( HttpStatus.OK);
	}
	
	@PutMapping("/deny")
	public ResponseEntity<Void> denyRequest(@RequestHeader("Authorization") String token, FriendRequest request){
		fs.denyRequest(request);
		return new ResponseEntity<>( HttpStatus.OK);
	}
	
	
	
	
	
	

}
