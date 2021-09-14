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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
class TripServiceTest {
	
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
	static User mockUser4;
	static PassengerRequest mockRequest;
	static PassengerRequest mockRequestNull;
	static Trip mockTrip1;
	static Trip mockTrip2;
	static Trip mockTrip4;
	
	static List<User> mockUsers;
	static List<PassengerRequest> mockPassengerRequests;
	
	@BeforeEach
	void setup() {
		User u1 = new User(1, "user", "first", "last", "address", new ArrayList<Trip>(), null);
		User u2 = new User(2, "user2","first", "last", "address", new ArrayList<Trip>(), null);
		User u3 = new User(3, "user3","first", "last", "address", new ArrayList<Trip>(), null);
		User u4 = new User(4, "user4", "first", "last", "address", null, null);
		
		Timestamp t1 = new Timestamp (0);
		Timestamp t2 = new Timestamp (1);
		
		List<String> sarray = new ArrayList<String>();
		sarray.add("stop1");
		sarray.add("stop2");
		sarray.add("stop3");
		
		List<User> p1 = new ArrayList<User>();
		p1.add(u1);
		Trip trip4 = new Trip(4, "destination", "origin", "tName", u1, sarray, p1, t1, t2, "spotify", u1, u2, u3);
		p1.add(u2);
		
		Trip trip1 = new Trip(1, "destination", "origin", "tName", u1, sarray, p1, t1, t2, "spotify", u1, u2, u3);
		p1.add(u3);
		Trip trip2 = new Trip(2, "destination", "origin", "tName", u1, sarray, p1, t1, t2, "spotify", u1, u2, u3);
		Trip trip3 = new Trip(3, "destination", "origin", "tName", u1, sarray, null, t1, t2, "spotify", u1, u2, u3);
		
		List<Trip> trips = new ArrayList<Trip>();	
		trips.add(trip1);
		u1.setTrips(trips);
		u2.setTrips(trips);
		
		PassengerRequest r1 = new PassengerRequest(1, u1, u2, trip1);
		PassengerRequest r2 = new PassengerRequest(1, u2, u4, trip3);
		
		mockUsers = new ArrayList<>();
		mockUsers.add(u1);
		mockUsers.add(u2);
		
		mockUser1 = u1;
		mockUser2 = u2;
		mockUser4 = u4;
		mockRequest = r1;
		mockRequestNull = r2;
		mockTrip1 = trip1;
		mockTrip2 = trip2;
		mockTrip4 = trip4;
		
	}
	
	@Test
	void createTripValid() {
		when(mockUr.getById(2)).thenReturn(mockUser2);
		when(mockAs.isValidAddress(mockTrip1.getDestination())).thenReturn(mockTrip1.getDestination());
		when(mockTr.save(mockTrip1)).thenReturn(mockTrip1);
		when(mockUr.save(mockTrip1.getManager())).thenReturn(mockTrip1.getManager());
		
		assertEquals(mockTrip1, ts.createTrip(mockTrip1));
		verify(mockTr,times(1)).save(mockTrip1);
		verify(mockUr,times(1)).save(mockTrip1.getManager());
	}
	@Test
	void createTripInvalidLocations() {
		when(mockUr.getById(2)).thenReturn(mockUser2);
		when(mockAs.isValidAddress(mockTrip1.getDestination())).thenReturn(null);
		when(mockAs.isValidAddress(mockTrip1.getOrigin())).thenReturn(null);
		
		assertEquals(null, ts.createTrip(mockTrip1));
	}
	@Test
	void createTripInvalidUser() {
		when(mockUr.getById(2)).thenReturn(null);

		assertEquals(null, ts.createTrip(mockTrip1));
	}
	
	@Test
	void updateTripValid() {
		when(mockUr.getById(2)).thenReturn(mockUser2);
		when(mockAs.isValidAddress(mockTrip1.getDestination())).thenReturn(mockTrip1.getDestination());
		when(mockAs.isValidAddress(mockTrip1.getOrigin())).thenReturn(mockTrip1.getOrigin());
		when(mockTr.getById(mockTrip1.getTripId())).thenReturn(mockTrip1);
		when(mockTr.save(mockTrip1)).thenReturn(mockTrip1);
		when(mockUr.save(mockUser1)).thenReturn(mockUser1);
		when(mockUr.save(mockUser2)).thenReturn(mockUser2);
		
		assertEquals(mockTrip1, ts.updateTrip(mockTrip1));
		verify(mockTr,times(1)).save(mockTrip1);
	}
	@Test
	void updateTripValidDeleteAllPassengers() {
		when(mockUr.getById(mockTrip4.getManager().getUserId())).thenReturn(mockUser4);
		when(mockUr.getById(mockTrip4.getManager().getUserId())).thenReturn(mockUser1);
		when(mockAs.isValidAddress(mockTrip4.getDestination())).thenReturn(mockTrip4.getDestination());
		when(mockAs.isValidAddress(mockTrip4.getOrigin())).thenReturn(mockTrip4.getOrigin());
		when(mockTr.getById(mockTrip4.getTripId())).thenReturn(mockTrip1);
		when(mockTr.save(mockTrip1)).thenReturn(mockTrip1);
		when(mockUr.save(mockUser1)).thenReturn(mockUser1);
		when(mockTr.save(mockTrip4)).thenReturn(mockTrip4);
		when(mockUr.save(mockUser4)).thenReturn(mockUser4);
		
		assertEquals(mockTrip4, ts.updateTrip(mockTrip4));
	}
	@Test
	void updateTripInvalid() {
		when(mockAs.isValidAddress(mockTrip1.getDestination())).thenReturn(null);
		when(mockAs.isValidAddress(mockTrip1.getOrigin())).thenReturn(null);
		
		assertEquals(null, ts.updateTrip(mockTrip1));
	}
	
	@Test
	void acceptRequestValid() {
		when(mockUr.getById(2)).thenReturn(mockUser2);
		ts.acceptRequest(mockRequest);
		verify(mockPr,times(1)).delete(mockRequest);
	}
	@Test
	void acceptRequestValidNullPassTrip() {
		when(mockUr.getById(4)).thenReturn(mockUser4);
		ts.acceptRequest(mockRequestNull);
		verify(mockPr,times(1)).delete(mockRequestNull);
	}
	@Test
	void acceptRequestInvalid() {
		when(mockUr.getById(2)).thenReturn(mockUser2);
		doNothing().when(mockPr).delete(mockRequest);
		ts.acceptRequest(mockRequest);
		verify(mockPr,times(1)).delete(mockRequest);
	}

	
	@Test
	void denyRequestValid() {
		ts.denyRequest(mockRequest);
		verify(mockPr,times(1)).delete(mockRequest);
	}
	@Test
	void denyRequestInvalid() {
		doNothing().when(mockPr).delete(mockRequest);
		ts.denyRequest(mockRequest);
		verify(mockPr,times(1)).delete(mockRequest);
	}
	
	@Test
	void makeRequestValid() {
		when(mockPr.save(mockRequest)).thenReturn(mockRequest);
		assertEquals(mockRequest, ts.makeRequest(mockRequest));
		verify(mockPr).save(Mockito.any(PassengerRequest.class));
	}
	@Test
	void makeRequestInvalid() {
		when(mockPr.save(mockRequest)).thenReturn(null);
		assertEquals(null, ts.makeRequest(mockRequest));
		verify(mockPr).save(Mockito.any(PassengerRequest.class));
	}
	
	@Test
	void getTripByIdValid() {
		when(mockTr.getById(mockTrip1.getTripId())).thenReturn(mockTrip1);
		assertEquals(mockTrip1, ts.getTripById(mockTrip1.getTripId()));
	}
	@Test
	void getTripByIdInvalid() {
		when(mockTr.getById(mockTrip1.getTripId())).thenReturn(null);
		assertEquals(null, ts.getTripById(mockTrip1.getTripId()));
	}
	
	@Test
	void getByToAndFromAndTripValid() {
		when(mockPr.existsByToAndFromAndTrip(mockUser1, mockUser2, mockTrip1)).thenReturn(true);
		assertEquals(true, ts.getByToAndFromAndTrip(mockUser1, mockUser2, mockTrip1));
	}
	@Test
	void getRequestByToValid() {
		when(mockPr.findByTo(mockUser1)).thenReturn(mockPassengerRequests);
		assertEquals(mockPassengerRequests, ts.getRequestByTo(mockUser1));
	}
	
	@Test
	void getRequestByToInvalid() {
		when(mockPr.findByTo(mockUser1)).thenReturn(null);
		assertEquals(null, ts.getRequestByTo(mockUser1));
	}
}