package com.lti.triplnr20.services;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public class WeatherServiceImpl implements WeatherService {

	@Override
	public String getCurrentWeather(String address) {
		// TODO Auto-generated method stub
//		String url = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/"+address+"?unitGroup=us";
//		RestTemplate rt = new RestTemplate();
//		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
//				.queryParam("key", System.getenv("WEATHER_API_KEY"));
//		String uri = builder.build(false).toUriString();
//		System.out.println(uri);
//		return url;
		return null;
	}

	@Override
	public String getDestinationWeather(String address, String day) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
