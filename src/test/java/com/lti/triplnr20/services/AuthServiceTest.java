package com.lti.triplnr20.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.lti.triplnr20.daos.UserRepository;
import com.lti.triplnr20.exceptions.AuthenticationException;
import com.lti.triplnr20.models.User;

@SpringBootTest(classes=AuthServiceImpl.class)
public class AuthServiceTest {

	@Autowired
	private AuthService as;
	
	@MockBean
	private UserRepository mockUr;
	
	@MockBean
	private UserService mockUs;
	
	@MockBean
	private AddressService mockAs;
	
	static User mockUser;
	
	static String mockToken;
	
	@BeforeAll
	public static void setup() {
		User u1 = new User(1, "user", "pass", "first", "last", "address", null, null);
		mockUser = u1;
		mockToken  = "1:user";
	}
	
	@Test
	public void createAuthToken() {
		assertEquals("1:user", as.createAuthToken(mockUser));
	}
	
	@Test
	public void loginValid() {
		when(mockUr.findUserByUsername("user")).thenReturn(mockUser);
		
		assertEquals("1:user", as.login(mockUser));
	}
	
	@Test
	public void loginInvalid() {
		when(mockUr.findUserByUsername("user")).thenReturn(null);
		
		assertThrows(AuthenticationException.class, () -> as.login(mockUser));
	}
	
	@Test
	public void registerValid() {
		when(mockUr.findUserByUsername("user")).thenReturn(null);
		when(mockAs.isValidAddress(mockUser.getAddress())).thenReturn(mockUser.getAddress());
		when(mockUs.createUser(mockUser)).thenReturn(mockUser);
		
		assertEquals(mockToken, as.register(mockUser));
	}
	
	
	@Test
	public void registerInvalid() {
		when(mockUs.createUser(mockUser)).thenThrow(AuthenticationException.class);
		
		assertThrows(AuthenticationException.class, () -> as.register(mockUser));
	}
}
