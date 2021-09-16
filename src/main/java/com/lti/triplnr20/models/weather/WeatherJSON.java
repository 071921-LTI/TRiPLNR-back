package com.lti.triplnr20.models.weather;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class WeatherJSON {
	private String address;
	private List<Day> days;
	private Day currentConditions;
	private List<Object> alerts;
	
	

}
