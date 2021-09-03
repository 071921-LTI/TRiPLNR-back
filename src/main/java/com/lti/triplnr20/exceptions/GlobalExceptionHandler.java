package com.lti.triplnr20.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
//This is the global exception handler that handles all exceptions and http responses for each exception 
public class GlobalExceptionHandler {
	
	@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="User Cannot Be Authenticated.")
	@ExceptionHandler(AuthenticationException.class)
	public void handleAuthenticationException() {
	}
	
	@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="User is not Authorized.")
	@ExceptionHandler(AuthorizationException.class)
	public void handleAuthorizationException() {
	}
	
	@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="User Already Exists.")
	@ExceptionHandler(UserAlreadyExistsException.class)
	public void handleUserAlreadyExistsException() {
	}
	
	@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Invalid Username.")
	@ExceptionHandler(InvalidUsernameException.class)
	public void handleInvalidUsernameException() {
	}
	
	@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="User Does Not Exist.")
	@ExceptionHandler(UserDoesNotExistException.class)
	public void handleUserDoesNotExistException() {
	}
	
	@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Address is invalid.")
	@ExceptionHandler(InvalidAddressException.class)
	public void handleInvalidAddressExistException() {
		
	}
	
	
	
	
}