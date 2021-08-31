package com.lti.triplnr20.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.lti.triplnr20.daos.UserRepository;
import com.lti.triplnr20.models.User;

@SpringBootTest(classes=UserServiceImpl.class)
public class UserServiceTest {
	
	@Autowired
	private UserService us;
	
	@MockBean
	private static UserRepository mockUr;
	
	@MockBean
	private static AddressService mockAs;
	
	static User mockUser;
	
	
	
	

}
