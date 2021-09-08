package com.lti.triplnr20.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.lti.triplnr20.models.geocoding.GeocodingJSON;

@Service
public class AddressServiceImpl implements AddressService{
	
	//Sets the Google API URL to be used for API requests
	private String geocodingURL = "https://maps.googleapis.com/maps/api/geocode/json";

	//Validates addresses by using the Google Geocode API to check if an address exists
	//Makes an API call to the Google Geocode API and if valid returns a formated address
	@Override
	public String isValidAddress(String address) {
		RestTemplate rt = new RestTemplate();
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(geocodingURL)
				.queryParam("address", address)
				.queryParam("key", System.getenv("MAPS_API_KEY"));
		
		String uri = builder.build(false).toUriString();
		
		
		GeocodingJSON response = rt.getForObject(uri, GeocodingJSON.class);
		
		if (response.getStatus().equals("OK")) {
			return response.getResults().get(0).getFormatted_address();
		}
		
		return null;
	}

}
