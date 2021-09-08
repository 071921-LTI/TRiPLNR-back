package com.lti.triplnr20.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
@SpringBootTest(classes=WeatherServiceImpl.class)
public class WeatherServiceTest {
	@Autowired
	WeatherService ws = new WeatherServiceImpl();
	static String weather;
	
	@BeforeAll
	public static void setUp(){
		
	}
	
	@Test
	public void getCurrentWeather() {
		String address = "Niantic,CT,US";
		assertEquals(address, ws.getCurrentWeather(address));
	}
	
	@Test
	public void getCurrentWeatherFail() {
		String address = "Niantic,CT,US";
		assertEquals(address, ws.getCurrentWeather("Uncasville,CT,US"));
	}
}
