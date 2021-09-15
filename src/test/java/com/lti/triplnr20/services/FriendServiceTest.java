package com.lti.triplnr20.services;

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
public class FriendServiceTest {
	
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
		User u1 = new User(1, "user", "first", "last", "address", null, null);
		User u2 = new User(2, "user2", "first", "last", "address", null, null);
		
		mockUser1 = u1;
		mockUser2 = u2;
		
		FriendRequest request = new FriendRequest(1, u1, u2);
		
		mockRequest = request;
		
	}
	
	@Test
	public void makeRequestValid() {
		
	}

}
