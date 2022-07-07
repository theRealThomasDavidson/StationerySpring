package com.cognixia.jump.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.cognixia.jump.repository.UserRepository;

@Service
@Component
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepository repo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<com.cognixia.jump.model.User> userFound = repo.findByUsername(username);
		if (userFound.isEmpty()) {throw new UsernameNotFoundException(username);}
		
		return new JwtUserDetails(userFound.get());
	}
}
