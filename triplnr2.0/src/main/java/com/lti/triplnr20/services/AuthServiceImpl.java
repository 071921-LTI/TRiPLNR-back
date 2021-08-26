package com.lti.triplnr20.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lti.triplnr20.daos.UserRepository;
import com.lti.triplnr20.models.User;

@Service
public class AuthServiceImpl implements AuthService {

	private UserRepository ur;
	private UserService us;

	@Autowired
	public AuthServiceImpl(UserRepository ur, UserService us) {
		super();
		this.us = us;
		this.ur = ur;
	}

	private String createAuthToken(User user) {
		String token = null;
		token = user.getUserId() + ":" + user.getUsername();
		return token;
	}

	@Override
	public String login(User user) {
		String token = null;
		if (ur.findUserByUsername(user.getUsername()) != null) {
			user.setUserId(ur.findUserByUsername(user.getUsername()).getUserId());
			token = createAuthToken(user);
		}

		return token;
	}

	@Override
	public String register(User user) {
		String token = null;
		if (user != null) {
			if (ur.findUserByUsername(user.getUsername()) != null) {
				return null;
			} else {
				token = createAuthToken(us.createUser(user));
			}
		}
		return token;
	}

}
