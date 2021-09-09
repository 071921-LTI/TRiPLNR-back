package com.lti.triplnr20.ControllerTest;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lti.triplnr20.controllers.WeatherController;
import com.lti.triplnr20.services.WeatherService;

@WebMvcTest(WeatherController.class)
public class WeatherControllerTest {
	
	@MockBean
	private WeatherService ws;
	
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void getCurrentWeather() throws Exception {
		String res = "2021-09-08 71.1 Rain, Partially cloudy";
		when(ws.getCurrentWeather("Unvasville,CT")).thenReturn(res);
		
		mockMvc.perform(get("/weather/Uncasville,CT")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
}
