package com.lti.triplnr20.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lti.triplnr20.models.weather.WeatherJSON;
import com.lti.triplnr20.services.WeatherService;
import com.lti.triplnr20.services.WeatherServiceImpl;

@RestController
@RequestMapping("/weather")
@CrossOrigin(exposedHeaders = "Authorization")
public class WeatherController {
	
	WeatherService ws = new WeatherServiceImpl();
	@Autowired
	public WeatherController(WeatherService ws) {
		this.ws = ws;
	}
	
	@GetMapping("/{address}")
	public ResponseEntity<?> getCurrentWeather(@PathVariable("address") String address, @RequestHeader("Authorization") String token){
		return new ResponseEntity<>(ws.getCurrentWeather(address), HttpStatus.OK);
	}
	
	@GetMapping("/{address}/{day}")
	public ResponseEntity<?> getDestinationWeather(@PathVariable("address") String address,@PathVariable("day") String day, @RequestHeader("Authorization") String token){
		return new ResponseEntity<>(ws.getDestinationWeather(address, day), HttpStatus.OK);
	}
	

	
}
