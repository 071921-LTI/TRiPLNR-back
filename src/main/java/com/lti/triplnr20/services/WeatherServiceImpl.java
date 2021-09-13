package com.lti.triplnr20.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.List;
import com.lti.triplnr20.models.weather.Day;
import com.lti.triplnr20.models.weather.WeatherJSON;

@Service
public class WeatherServiceImpl implements WeatherService {

	@Override
	public Day getCurrentWeather(String address) {
		String url = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/"+address+"?unitGroup=us";
		RestTemplate rt = new RestTemplate();
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
				.queryParam("key", System.getenv("WEATHER_API_KEY"));
		String uri = builder.build(false).toUriString();
		System.out.println(uri);
		WeatherJSON r = rt.getForObject(uri, WeatherJSON.class);
		
		return r.getCurrentConditions();	
	}

	@Override
	public Day getDestinationWeather(String address, int day) {
		String url = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/"+address+"?unitGroup=us";
		RestTemplate rt = new RestTemplate();
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
				.queryParam("key", System.getenv("WEATHER_API_KEY"));
		String uri = builder.build(false).toUriString();
		System.out.println(uri);
		WeatherJSON r = rt.getForObject(uri, WeatherJSON.class);
		return r.getDays().get(day);
	}
	

}
