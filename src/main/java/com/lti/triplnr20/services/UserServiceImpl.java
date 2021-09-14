package com.lti.triplnr20.services;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
	private S3Service s3;

	@Autowired
	public UserServiceImpl(UserRepository ur, AddressService as) {
		super();
		this.ur = ur;
		this.as = as;
	}

	@Override
	public User getUserBySub(String sub) {
		return ur.findUserBySub(sub);
	}

	
	//Users can only be create if valid address is given and will throw an AuthenticationException if not valid, saves valid users into the database 
	@Override
	@Transactional
	public User createUser(User user, MultipartFile file) throws IOException {
		if (ur.findUserBySub(user.getSub()) == null) {
			String profilePic = s3.upload(file);
			System.out.println(file);
			user.setProfilePic(profilePic);

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
			User u = ur.findUserBySub(user.getSub());
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
	public List<Trip> getTripsByUser(String sub) {
		//List<Trip> trips = new ArrayList<>();
		//trips.addAll(ur.getById(userId).getTrips());
		return ur.findUserBySub(sub).getTrips();
		
	}

	//Gets the list of friends for a give user by its username will return null if none
	@Override
	public List<User> getFriends(String sub) {
		return ur.findUserBySub(sub).getFriends();
	}

	@Override
	public List<User> getProfiles(String sub) {
		List<User> profiles = ur.findAll();
		User user = ur.findUserBySub(sub);
		List<User> friends = user.getFriends();
		
		profiles.removeAll(friends);
		profiles.remove(user);
		return profiles;
	}
	

}
