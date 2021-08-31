package com.lti.triplnr20.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lti.triplnr20.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	
	User findUserByUsername(String username);

}
