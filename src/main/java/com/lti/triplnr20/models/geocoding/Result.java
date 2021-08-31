package com.lti.triplnr20.models.geocoding;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class Result {
	
    private String formatted_address;

}
