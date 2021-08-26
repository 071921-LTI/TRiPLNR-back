package com.lti.triplnr20.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lti.triplnr20.daos.UserRepository;
import com.lti.triplnr20.models.User;

@Service
public class UserServiceImpl implements UserService{
	
	private UserRepository ur;
	
	
	@Autowired
	public UserServiceImpl(UserRepository ur) {
		super();
		this.ur = ur;
	}



	@Override
	public User createUser(User user) {
		//if user.address is valid
		ur.save(user);
		return user;
	}

}
