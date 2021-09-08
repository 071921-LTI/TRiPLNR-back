package com.lti.triplnr20.services;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.*;
import org.assertj.core.api.Assertions;
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
		assertNotNull(ws.getCurrentWeather("Uncasville,CT,US"));
	}
	
	@Test
	public void getCurrentWeatherFail() {
		assertNotNull(ws.getCurrentWeather("Uncasville,CT,US"));
	}
	
	@Test
	public void  getDestiationWeatherPass(){
		String address = "waller,tx";
		String futureDay = "1";
		assertNotNull(ws.getDestinationWeather(address, futureDay));
	}
	
	@Test
	public void  getDestiationWeatherFail(){
		String address = "";
		String futureDay = "1";
		assertEquals(ws.getDestinationWeather(address, futureDay), HttpStatus.NOT_FOUND);
		
	}
	
	
	
}
