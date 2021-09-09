package com.lti.triplnr20.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.lti.triplnr20.daos.PassengerRequestRepository;
import com.lti.triplnr20.daos.TripRepository;
import com.lti.triplnr20.daos.UserRepository;
import com.lti.triplnr20.models.PassengerRequest;
import com.lti.triplnr20.models.Trip;
import com.lti.triplnr20.models.User;

@SpringBootTest(classes=TripServiceImpl.class)
public class TripServiceTest {
	
	@Autowired
	private TripService us;
	
	@MockBean
	private TripRepository mockTr;
	@MockBean
	private PassengerRequestRepository mockPr;
	@MockBean
	private UserRepository mockUr;
	
	@MockBean
	private AddressService mockAs;
	@MockBean
	private UserService mockUs;
	
	static User mockUser;
	static PassengerRequest mockRequest;
	
	static List<User> mockUsers;
	
	@BeforeAll
	public static void setup() {
		User u1 = new User(1, "user", "pass", "first", "last", "address", null, null);
		User u2 = new User(2, "user2", "pass","first", "last", "address", null, null);
		
		List<User> p1 = new ArrayList<User>();
		p1.add(u2);
		
		Timestamp t1 = new Timestamp (0);
		Timestamp t2 = new Timestamp (1);
		
		Trip trip = new Trip(1, "destination", "origin", "tName", u1, p1, t1, t2);
					
		
		
		PassengerRequest  r1 = new PassengerRequest(1, "user1", "user2", p1, );
		mockUsers = new ArrayList<>();
		mockUsers.add(u1);
		mockUsers.add(u2);
		mockUser = u1;
		
	}
	
	@Test
	public void createUserValid() {
		when(mockUr.findUserByUsername("user")).thenReturn(null);
		when(mockAs.isValidAddress("address")).thenReturn("address");
		assertEquals(mockUser, us.createUser(mockUser));
	}