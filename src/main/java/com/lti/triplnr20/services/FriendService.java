package com.lti.triplnr20.services;

import java.util.List;

import com.lti.triplnr20.models.FriendRequest;
import com.lti.triplnr20.models.User;

public interface FriendService {

	void acceptRequest(FriendRequest request);
	void denyRequest(FriendRequest request);
	List<FriendRequest> getRequestsByUser(User user);
	FriendRequest makeRequest(FriendRequest request);
	
}
