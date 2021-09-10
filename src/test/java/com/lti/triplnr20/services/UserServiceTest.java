package com.lti.triplnr20.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

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
public class UserServiceTest {
	
	@Autowired
	private UserService us;
	
	@MockBean
	private UserRepository mockUr;
	
	@MockBean
	private AddressService mockAs;
	
	static User mockUser;
	
	static List<User> mockUsers;
	
	@BeforeAll
	public static void setup() {
		User u1 = new User(1, "user", "pass", "", "first", "last", "address", null, null);
		User u2 = new User(2, "user2", "pass","", "first", "last", "address", null, null);
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
	
	@Test
	public void createUserNotValid() {
		when(mockUr.findUserByUsername("user")).thenReturn(mockUser);
		assertThrows(AuthenticationException.class, () -> us.createUser(mockUser));
	}
	
	@Test
	public void updateUserValid() {
		when(mockUr.findUserByUsername("user")).thenReturn(null);
		when(mockAs.isValidAddress("address")).thenReturn("address");
		assertEquals("Successful",us.updateUser(mockUser));
	}
	
	@Test
	public void updateUserNotValid() {
		when(mockAs.isValidAddress("address")).thenReturn("address");
		when(mockUr.findUserByUsername("user")).thenReturn(new User(2, "user2", "pass", "", "first", "last", "address", null, null));
		assertThrows(UserAlreadyExistsException.class, () -> us.updateUser(mockUser));
	}
	
	@Test
	public void updateUserAddressNotValid() {
		when(mockUr.findUserByUsername("user")).thenReturn(null);
		when(mockAs.isValidAddress("address")).thenReturn(null);
		assertThrows(InvalidAddressException.class, () -> us.updateUser(mockUser));
	}
	
	@Test
	public void getUserById() {
		when(mockUr.getById(1)).thenReturn(mockUser);
		assertEquals(mockUser, us.getUserById(1));
	}
	
	@Test
	public void getTrips() {
		when(mockUr.getById(1)).thenReturn(mockUser);
		assertNull(us.getTripsByUser(1));
	}
	
	
	@Test
	public void getFriends() {
		when(mockUr.findUserByUsername("user")).thenReturn(mockUser);
		assertNull(us.getFriends("user"));
	}
	
	@Test
	public void getProfiles() {
		when(mockUr.findAll()).thenReturn(mockUsers);
		when(mockUr.findUserByUsername("user")).thenReturn(mockUser);
		List<User> friends = new ArrayList<>();
		friends.add(new User(2, "user2", "pass", "", "first", "last", "address", null, null));
		mockUser.setFriends(friends);
		assertEquals( new ArrayList<User>(), us.getProfiles("user"));
	}

	@Test
	public void userExists() {
		when(mockUr.findUserBySub("")).thenReturn(mockUser);
		assertEquals(true, us.checkIfExistingUser(""));
	}

	@Test
	public void userNotExists() {
		when(mockUr.findUserBySub("")).thenReturn(null);
		assertEquals(false, us.checkIfExistingUser(""));
	}

}
