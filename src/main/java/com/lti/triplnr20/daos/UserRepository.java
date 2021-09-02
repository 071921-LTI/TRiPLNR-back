package com.lti.triplnr20.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lti.triplnr20.models.User;


//This is the user repository that is managed by Spring
@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	
	User findUserByUsername(String username);

}
