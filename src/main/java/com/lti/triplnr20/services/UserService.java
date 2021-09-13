package com.lti.triplnr20.services;

import java.util.List;

import com.lti.triplnr20.models.Trip;
import com.lti.triplnr20.models.User;

public interface UserService {
	
	User getUserBySub(String sub);

	User createUser(User user);

	String updateUser(User user);

	User getUserById(int id);
	
	List<Trip> getTripsByUser(String sub);

	List<User> getFriends(String sub);
	
	List<User> getProfiles(String sub);
}
