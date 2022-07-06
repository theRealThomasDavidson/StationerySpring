package com.cognixia.jump.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.config.JwtRequestFilter;
import com.cognixia.jump.model.JwtRequest;
import com.cognixia.jump.model.JwtResponse;
import com.cognixia.jump.model.User;
import com.cognixia.jump.repository.UserRepository;
import com.cognixia.jump.config.JwtTokenUtil;


@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	UserRepository repo;
	
	@Autowired 
	PasswordEncoder encoder;
	
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
	
	
}
