package com.lti.triplnr20.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.lti.triplnr20.daos.PassengerRequestRepository;
import com.lti.triplnr20.daos.TripRepository;
import com.lti.triplnr20.daos.UserRepository;
import com.lti.triplnr20.models.PassengerRequest;
import com.lti.triplnr20.models.Trip;
import com.lti.triplnr20.models.User;

@SpringBootTest(classes= TripServiceImpl.class)
public class TripServiceTest {
	
	@Autowired
	private TripService ts;
	
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
	
	
	static User mockUser1;
	static User mockUser2;
	static PassengerRequest mockRequest;
	static Trip mockTrip;
	
	static List<User> mockUsers;
	
	@BeforeAll
	public static void setup() {
		User u1 = new User(1, "user", "pass", "first", "last", "address", new ArrayList<Trip>(), null);
		User u2 = new User(2, "user2", "pass","first", "last", "address", new ArrayList<Trip>(), null);
		
		List<User> p1 = new ArrayList<User>();
		p1.add(u2);
		
		Timestamp t1 = new Timestamp (0);
		Timestamp t2 = new Timestamp (1);
		
		Trip trip = new Trip(1, "destination", "origin", "tName", u1, null, p1, t1, t2);
		
		PassengerRequest r1 = new PassengerRequest(1, u1, u2, trip);
		
		mockUsers = new ArrayList<>();
		mockUsers.add(u1);
		mockUsers.add(u2);
		mockUser1 = u1;
		mockUser2 = u2;
		mockRequest = r1;
		mockTrip = trip;
		
	}
	
	@Test
	public void createTripValid() {
		when(mockUr.getById(2)).thenReturn(mockUser2);
		when(mockAs.isValidAddress(mockTrip.getDestination())).thenReturn(mockTrip.getDestination());
		when(mockTr.save(mockTrip)).thenReturn(mockTrip);
		when(mockUr.save(mockTrip.getManager())).thenReturn(mockTrip.getManager());
		
		assertEquals(mockTrip, ts.createTrip(mockTrip));
		verify(mockTr,times(1)).save(mockTrip);
		verify(mockUr,times(1)).save(mockTrip.getManager());
	}
	@Test
	public void createTripInvalidDestination() {
		when(mockUr.getById(2)).thenReturn(mockUser2);
		when(mockAs.isValidAddress(mockTrip.getDestination())).thenReturn(null);
		
		assertEquals(null, ts.createTrip(mockTrip));
	}
	@Test
	public void createTripInvalidUser() {
		when(mockUr.getById(2)).thenReturn(null);

		assertEquals(null, ts.createTrip(mockTrip));
	}
	
	@Test
	public void updateTripValid() {
		when(mockUr.getById(2)).thenReturn(mockUser2);
		when(mockAs.isValidAddress(mockTrip.getDestination())).thenReturn(mockTrip.getDestination());
		when(mockAs.isValidAddress(mockTrip.getOrigin())).thenReturn(mockTrip.getOrigin());
		when(mockTr.getById(mockTrip.getTripId())).thenReturn(mockTrip);
		when(mockTr.save(mockTrip)).thenReturn(mockTrip);
		
		assertEquals(mockTrip, ts.updateTrip(mockTrip));
		verify(mockTr,times(1)).save(mockTrip);
	}
	
	@Test
	public void acceptRequestValid() {
		when(mockUr.getById(2)).thenReturn(mockUser2);
		ts.acceptRequest(mockRequest);
		verify(mockPr,times(1)).delete(mockRequest);
	}
	@Test
	public void acceptRequestInvalid() {
		when(mockUr.getById(2)).thenReturn(mockUser2);
		doNothing().when(mockPr).delete(mockRequest);
		ts.acceptRequest(mockRequest);
		verify(mockPr,times(1)).delete(mockRequest);
	}
	
	@Test
	public void denyRequestValid() {
		ts.denyRequest(mockRequest);
		verify(mockPr,times(1)).delete(mockRequest);
	}
	@Test
	public void denyRequestInvalid() {
		doNothing().when(mockPr).delete(mockRequest);
		ts.denyRequest(mockRequest);
		verify(mockPr,times(1)).delete(mockRequest);
	}
	
	@Test
	public void makeRequestValid() {
		when(mockPr.save(mockRequest)).thenReturn(mockRequest);
		assertEquals(mockRequest, ts.makeRequest(mockRequest));
		verify(mockPr).save(Mockito.any(PassengerRequest.class));
	}
	@Test
	public void makeRequestInvalid() {
		when(mockPr.save(mockRequest)).thenReturn(null);
		assertEquals(null, ts.makeRequest(mockRequest));
		verify(mockPr).save(Mockito.any(PassengerRequest.class));
	}
	
	@Test
	public void getTripByIdValid() {
		when(mockTr.getById(mockTrip.getTripId())).thenReturn(mockTrip);
		assertEquals(mockTrip, ts.getTripById(mockTrip.getTripId()));
	}
	@Test
	public void getTripByIdInvalid() {
		when(mockTr.getById(mockTrip.getTripId())).thenReturn(null);
		assertEquals(null, ts.getTripById(mockTrip.getTripId()));
	}
}