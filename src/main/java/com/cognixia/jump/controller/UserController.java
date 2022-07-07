package com.cognixia.jump.controller;


import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.model.User;
import com.cognixia.jump.repository.UserRepository;

import io.swagger.v3.oas.annotations.Operation;



@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	UserRepository repo;
	
	@Autowired 
	PasswordEncoder encoder;
	@Operation(summary = "Get all the users", 
			   description = "Gets all the users from the animal table in the spring_project database."
			)
	@GetMapping("/")
	public ResponseEntity<?> index(){
		return ResponseEntity.status(200)
				.body(repo.findAll());
	}
	
	@PostMapping("/")
	public ResponseEntity<?> CreateUser(@Valid @RequestBody User user){
		user.setId(null);
		user.setPassword(encoder.encode(user.getPassword()));
		User created = repo.save(user);
		return ResponseEntity.status(201).body(created);
	}
	@GetMapping("/me")
	public ResponseEntity<?> myUser(Authentication authentication, Principal principal){

		return ResponseEntity.status(200)
				.body(repo.findByUsername(authentication.getName()));
	}
	
}
