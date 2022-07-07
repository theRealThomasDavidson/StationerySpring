package com.cognixia.jump.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// will make sure 404 is the status code instead of 500
//@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public ResourceNotFoundException(String resource) {
		super(resource + " was not found");
	}

}
