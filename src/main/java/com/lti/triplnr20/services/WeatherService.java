package com.lti.triplnr20.services;

public interface WeatherService {
	
	 String getCurrentWeather(String address);
	 
	 String getDestinationWeather(String address, String day);
}
