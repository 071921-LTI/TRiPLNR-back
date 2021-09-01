package com.lti.triplnr20.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lti.triplnr20.daos.FriendRequestRepository;
import com.lti.triplnr20.daos.UserRepository;
import com.lti.triplnr20.models.FriendRequest;
import com.lti.triplnr20.models.User;

@Service
public class FriendServiceImpl implements FriendService{

	private FriendRequestRepository frr;
	private UserRepository ur;
	
	
	@Autowired
	public FriendServiceImpl(FriendRequestRepository frr, UserRepository ur) {
		super();
		this.frr = frr;
		this.ur = ur;
	}

	@Override
	public void acceptRequest(FriendRequest request) {
		User from = ur.getById(request.getFrom().getUserId());
		User to = ur.getById(request.getTo().getUserId());
		
		List<User> fromFriends = from.getFriends();
		List<User> toFriends = to.getFriends();
		
		fromFriends.add(to);
		toFriends.add(from);
		
		from.setFriends(fromFriends);
		to.setFriends(toFriends);
		
		ur.save(from);
		ur.save(to);
		
		frr.delete(request);
		
	}

	@Override
	public void denyRequest(FriendRequest request) {
		
		frr.delete(request);
		
	}

	@Override
	public List<FriendRequest> getRequestsByUser(User user) {
		return frr.findByTo(user);
	}

	@Override
	public FriendRequest makeRequest(FriendRequest request) {
		return frr.save(request);
	}
	
	

}
