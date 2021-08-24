package com.lti.services;

import org.springframework.stereotype.Service;

import com.lti.models.User;

@Service
public class AuthServiceImpl implements AuthService{

	private String createAuthToken(User user) {
		String token = null;
		token = user.getUserId()+":"+user.getUsername();
		return token;
	}
	
	@Override
	public String login(User user) {
		//testing
		String token = null;
		if (user.getUsername().equals("hello")) {
			if (user.getPassword().equals("world")) {
				token = createAuthToken(user);
			}
		}
		
		
		return token;
	}

}
