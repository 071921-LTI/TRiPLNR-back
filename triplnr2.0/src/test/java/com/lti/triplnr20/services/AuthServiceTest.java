package com.lti.triplnr20.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.lti.triplnr20.daos.UserRepository;
import com.lti.triplnr20.models.User;

@SpringBootTest(classes=AuthServiceImpl.class)
public class AuthServiceTest {

	@Autowired
	private AuthService as;
	
	@MockBean
	private UserRepository mockUr;
	
	@MockBean
	private UserService mockUs;
	
	static User mockUser;
	
	@BeforeAll
	public static void setup() {
		User u1 = new User(1, "user", "pass", "first", "last", "address", null);
		mockUser = u1;
	}
	
	@Test
	public void createAuthToken() {
		assertEquals("1:user", as.createAuthToken(mockUser));
	}
	
	
}
