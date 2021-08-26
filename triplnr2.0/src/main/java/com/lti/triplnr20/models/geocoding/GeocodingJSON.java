package com.lti.triplnr20.models.geocoding;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class GeocodingJSON {
	
	private List<Result> results;
	private String status;

}
