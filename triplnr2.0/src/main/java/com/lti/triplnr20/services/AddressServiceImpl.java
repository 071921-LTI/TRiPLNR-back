package com.lti.triplnr20.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class AddressServiceImpl implements AddressService{
	
	private String geocodingURL = "https://maps.googleapis.com/maps/api/geocode/json";

	@Override
	public boolean isValidAddress(String address) {
		RestTemplate rt = new RestTemplate();
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(geocodingURL)
				.queryParam("address", address)
				.queryParam("key", System.getenv("MAPS_API_KEY"));
		
		String uri = builder.build(false).toUriString();
		
		
		ResponseEntity<String> response = rt.getForEntity(uri, String.class);
		System.out.println(response);
		
		
		return false;
	}

}
