package com.lti.triplnr20.services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lti.triplnr20.daos.UserRepository;
import com.lti.triplnr20.exceptions.AuthenticationException;
import com.lti.triplnr20.exceptions.InvalidAddressException;
import com.lti.triplnr20.exceptions.UserAlreadyExistsException;
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
	public boolean checkIfExistingUser(String sub) {
		if (ur.findUserBySub(sub) != null) {
			return true;
		}

		return false;
	}

	
	//Users can only be create if valid address is given and will throw an AuthenticationException if not valid, saves valid users into the database 
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

	//Update user function that allows for update of user info, will throw exception to be handled by ExceptionHandler if update unsuccessful
	@Override
	@Transactional
	public String updateUser(User user) {
		String address = null;
		address = as.isValidAddress(user.getAddress());
		if (address != null) {
			user.setAddress(address);
			User u = ur.findUserByUsername(user.getUsername());
			if (u != null && u.getUserId() != user.getUserId()){
				throw new UserAlreadyExistsException();
			}else {
				ur.save(user);
				return "Successful";
			}
		
		}else {
			throw new InvalidAddressException();
		}
	}

	//Gets the user with the given user id will return null if not found
	@Override
	@Transactional
	public User getUserById(int id) {
		return ur.getById(id);
	}

	//Gets the list of trips by given user id will return null if not found
	public List<Trip> getTripsByUser(int userId) {
		//List<Trip> trips = new ArrayList<>();
		//trips.addAll(ur.getById(userId).getTrips());
		return ur.getById(userId).getTrips();
		
	}

	//Gets the list of friends for a give user by its username will return null if none
	@Override
	public List<User> getFriends(String username) {
		return ur.findUserByUsername(username).getFriends();
	}

	@Override
	public List<User> getProfiles(String username) {
		List<User> profiles = ur.findAll();
		User user = ur.findUserByUsername(username);
		List<User> friends = user.getFriends();
		
		profiles.removeAll(friends);
		profiles.remove(user);
		return profiles;
	}
	

}
