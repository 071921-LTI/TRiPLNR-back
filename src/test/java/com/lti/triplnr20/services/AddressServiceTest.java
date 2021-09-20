package com.lti.triplnr20.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes= AddressServiceImpl.class)
class AddressServiceTest {
	
	@Autowired
	private AddressService as;
	
	//Mock variables and objects
	static String sentMockAddress;
	static String recievedMockAddress;
	
	static String invalidMockAddress;
	
	@BeforeEach
	void setup() {
		sentMockAddress = "385 Farmington Ave, Hartford, CT 06105";
		recievedMockAddress = "385 Farmington Ave, Hartford, CT 06105, USA";
		invalidMockAddress = " ";
	}
	
	@Test
	void isValidAddressValid() {
		assertEquals(recievedMockAddress, as.isValidAddress(sentMockAddress));
	}
	@Test
	void isValidAddressInvalid() {
		assertEquals(null, as.isValidAddress(invalidMockAddress));
	}


}
