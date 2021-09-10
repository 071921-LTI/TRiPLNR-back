package com.lti.triplnr20.services;

import java.util.List;

import com.lti.triplnr20.models.Trip;
import com.lti.triplnr20.models.User;

public interface UserService {
	
	boolean checkIfExistingUser(String sub);

	User createUser(User user);

	String updateUser(User user);

	User getUserById(int id);
	
	List<Trip> getTripsByUser(int userId);

	List<User> getFriends(String username);
	
	List<User> getProfiles(String username);
}
