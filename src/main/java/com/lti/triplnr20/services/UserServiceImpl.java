package com.lti.triplnr20.services;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lti.triplnr20.daos.UserRepository;
import com.lti.triplnr20.exceptions.AuthenticationException;

import com.lti.triplnr20.models.Trip;

import com.lti.triplnr20.models.User;

@Service
public class UserServiceImpl implements UserService {

	private UserRepository ur;
	private AddressService as;

	@Autowired
	public UserServiceImpl(UserRepository ur, AddressService as) {
		super();
		this.ur = ur;
		this.as = as;
	}

	@Override
	@Transactional
	public User createUser(User user) {
		if (ur.findUserByUsername(user.getUsername()) == null) {
			String address = null;
			address = as.isValidAddress(user.getAddress());
			if (address != null) {
				user.setAddress(address);
				ur.save(user);
				return user;
			} else {
				throw new AuthenticationException();
			}
		}else {
			throw new AuthenticationException();
		}
	}


	@Override
	@Transactional
	public String updateUser(User user) {
		try {
			String address = null;
			address = as.isValidAddress(user.getAddress());
			System.out.println(address);
			System.out.println(user.getAddress());
			if (address != null) {
				user.setAddress(address);
				ur.save(user);
				return "Successful";
			}else {
				return "Invalid Address";
			}
		}catch (IllegalArgumentException e) {
			return "Username in use";
		}
	}

	@Override
	@Transactional
	public User getUserById(int id) {
		return ur.getById(id);
	}

	
	public List<Trip> getTripsByUser(int userId) {
		List<Trip> trips = new ArrayList<>();
		trips.addAll(ur.getById(userId).getTrips());
		return trips;
		
	}

}
