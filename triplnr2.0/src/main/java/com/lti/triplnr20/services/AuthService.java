package com.lti.triplnr20.services;

import com.lti.triplnr20.models.User;

public interface AuthService {
	
	String login(User user);
	String register(User user);

}
