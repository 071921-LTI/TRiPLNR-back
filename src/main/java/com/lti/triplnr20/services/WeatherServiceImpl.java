package com.lti.triplnr20.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.lti.triplnr20.models.weather.WeatherJSON;
@Service
public class WeatherServiceImpl implements WeatherService {

	@Override
	public String getCurrentWeather(String address) {
		String url = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/"+address+"?unitGroup=us";
		RestTemplate rt = new RestTemplate();
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
				.queryParam("key", System.getenv("WEATHER_API_KEY"));
		String uri = builder.build(false).toUriString();
		System.out.println(uri);
		WeatherJSON r = rt.getForObject(uri, WeatherJSON.class);
		String res = r.getDays().get(0).datetime + " "
		+ String.valueOf(r.getDays().get(0).temp)+ " "
		+r.getDays().get(0).conditions;
		return res;
		
	}

	@Override
	public String getDestinationWeather(String address, String day) {
		String url = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/"+address+"?unitGroup=us";
		RestTemplate rt = new RestTemplate();
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
				.queryParam("key", System.getenv("WEATHER_API_KEY"));
		String uri = builder.build(false).toUriString();
		System.out.println(uri);
		WeatherJSON r = rt.getForObject(uri, WeatherJSON.class);
		String res = r.getDays().get(Integer.parseInt(day)).datetime + " " 
		+String.valueOf(r.getDays().get(Integer.parseInt(day)).temp) +" "
				+r.getDays().get(Integer.parseInt(day)).conditions;
		return res;
	}
	

}
