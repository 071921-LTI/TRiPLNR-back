package com.lti.triplnr20.daos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lti.triplnr20.models.FriendRequest;
import com.lti.triplnr20.models.PassengerRequest;
import com.lti.triplnr20.models.User;

//This is the friend request repository that is managed by Spring
public interface PassengerRequestRepository extends JpaRepository<PassengerRequest, Integer>{
	List<FriendRequest> findByTo(User user);
}
