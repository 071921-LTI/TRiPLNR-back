package com.lti.triplnr20.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.lti.triplnr20.daos.UserRepository;
import com.lti.triplnr20.exceptions.AuthenticationException;
import com.lti.triplnr20.exceptions.InvalidAddressException;
import com.lti.triplnr20.exceptions.UserAlreadyExistsException;
import com.lti.triplnr20.models.User;

@SpringBootTest(classes=UserServiceImpl.class)
class UserServiceTest {
	
	@Autowired
	private UserService us;
	
	@MockBean
	private UserRepository mockUr;
	
	@MockBean
	private AddressService mockAs;
	
	static User mockUser;
	
	static List<User> mockUsers;
	
	@BeforeAll
	static void setup() {
		User u1 = new User(1, "user", "first", "last", "", "", "address", null, null);
		User u2 = new User(2, "user2", "first", "last", "", "", "address", null, null);
		mockUsers = new ArrayList<>();
		mockUsers.add(u1);
		mockUsers.add(u2);
		mockUser = u1;
		
	}
	
	@Test
	void createUserValid() throws IOException {
		when(mockUr.findUserBySub("user")).thenReturn(null);
		when(mockAs.isValidAddress("address")).thenReturn("address");
		assertEquals(mockUser, us.createUser(mockUser));
	}
	
	@Test
	void createUserNotValid() {
		when(mockUr.findUserBySub("user")).thenReturn(mockUser);
		assertThrows(AuthenticationException.class, () -> us.createUser(mockUser));
	}
	
	@Test
	void updateUserValid() {
		when(mockUr.findUserBySub("user")).thenReturn(null);
		when(mockAs.isValidAddress("address")).thenReturn("address");
		assertEquals("Successful",us.updateUser(mockUser));
	}
	
	@Test
	void updateUserNotValid() {
		when(mockAs.isValidAddress("address")).thenReturn("address");
		when(mockUr.findUserBySub("user")).thenReturn(new User(2, "", "first", "last", "", "", "address", null, null));
		assertThrows(UserAlreadyExistsException.class, () -> us.updateUser(mockUser));
	}
	
	@Test
	void updateUserAddressNotValid() {
		when(mockUr.findUserBySub("user")).thenReturn(null);
		when(mockAs.isValidAddress("address")).thenReturn(null);
		assertThrows(InvalidAddressException.class, () -> us.updateUser(mockUser));
	}
	
	@Test
	void getUserById() {
		when(mockUr.getById(1)).thenReturn(mockUser);
		assertEquals(mockUser, us.getUserById(1));
	}
	
	@Test
	void getTrips() {
		when(mockUr.findUserBySub("user")).thenReturn(mockUser);
		assertNull(us.getTripsByUser("user"));
	}
	
	
	@Test
	void getFriends() {
		when(mockUr.findUserBySub("user")).thenReturn(mockUser);
		assertNull(us.getFriends("user"));
	}
	
	@Test
	void getProfiles() {
		when(mockUr.findAll()).thenReturn(mockUsers);
		when(mockUr.findUserBySub("user")).thenReturn(mockUser);
		List<User> friends = new ArrayList<>();
		friends.add(new User(2, "user2", "first", "last", "", "", "address", null, null));
		mockUser.setFriends(friends);
		assertEquals( new ArrayList<User>(), us.getProfiles("user"));
	}

	@Test
	void getUserBySubExists() {
		when(mockUr.findUserBySub("user")).thenReturn(mockUser);
		assertEquals(mockUser, us.getUserBySub("user"));
	}

	@Test
	void userNotExists() {
		when(mockUr.findUserBySub("user")).thenReturn(null);
		assertEquals(null, us.getUserBySub("user"));
	}

}
