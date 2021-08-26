package com.lti.triplnr20.services;

import com.lti.triplnr20.models.User;

public interface UserService {
	
	User createUser(User user);
	boolean updateUser(User user);
	User getUserById(int id);

}
