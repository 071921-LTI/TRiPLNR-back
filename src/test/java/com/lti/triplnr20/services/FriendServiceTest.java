package com.lti.triplnr20.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.lti.triplnr20.daos.FriendRequestRepository;
import com.lti.triplnr20.daos.UserRepository;
import com.lti.triplnr20.models.FriendRequest;
import com.lti.triplnr20.models.User;

@SpringBootTest(classes=FriendServiceImpl.class)
class FriendServiceTest {
	
	@Autowired
	private FriendService fs;
	
	@MockBean
	private UserRepository mockUr;
	
	@MockBean
	private FriendRequestRepository mockFrr;
	
	static FriendRequest mockRequest;
	
	static User mockUser1;
	static User mockUser2;
	
	@BeforeAll
	static void setup() {
		User u1 = new User(1, "user", "first", "last", "address", "pic", "bio", null, new ArrayList<User>());
		User u2 = new User(2, "user2", "first", "last", "address", "pic", "bio", null, new ArrayList<User>());
		
		mockUser1 = u1;
		mockUser2 = u2;
		
		FriendRequest request = new FriendRequest(1, u1, u2);
		
		mockRequest = request;
		
	}
	
	@Test
	void makeRequestValid() {
		when(mockFrr.existsByFromAndTo(mockUser1, mockUser2)).thenReturn(false);
		when(mockFrr.save(mockRequest)).thenReturn(mockRequest);
		assertEquals(mockRequest, fs.makeRequest(mockRequest));
	}
	
	@Test
	void makeRequestNotValid() {
		when(mockFrr.existsByFromAndTo(mockUser1, mockUser2)).thenReturn(true);
		assertNull(fs.makeRequest(mockRequest));
	}
	
	@Test
	void getRequests() {
		List<FriendRequest> requests = new ArrayList<>();
		requests.add(mockRequest);
		when(mockFrr.findByTo(mockUser2)).thenReturn(requests);
		assertEquals(requests, fs.getRequestsByUser(mockUser2));
	}
	
	@Test
	void acceptRequest() {
		when(mockUr.getById(1)).thenReturn(mockUser1);
		when(mockUr.getById(2)).thenReturn(mockUser2);
		fs.acceptRequest(mockRequest);
		verify(mockFrr, times(1)).delete(mockRequest);
	}
	
	@Test
	void denyRequest() {
		fs.denyRequest(mockRequest);
		verify(mockFrr, times(1)).delete(mockRequest);
	}

}
