package com.lti.triplnr20.services;

import java.util.List;

import com.lti.triplnr20.models.Trip;
import com.lti.triplnr20.models.User;

public interface UserService {
	
	User createUser(User user);

	String updateUser(User user);

	User getUserById(int id);
	
	List<Trip> getTripsByUser(int userId);

}
