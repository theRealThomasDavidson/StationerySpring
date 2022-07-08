package com.cognixia.jump.controller;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;

import com.cognixia.jump.config.JwtAuthenticationEntryPoint;
import com.cognixia.jump.config.JwtRequestFilter;
import com.cognixia.jump.config.JwtTokenUtil;
import com.cognixia.jump.model.User;
import com.cognixia.jump.repository.UserRepository;
import com.cognixia.jump.service.JwtUserDetails;
import com.cognixia.jump.service.JwtUserDetailsService;
import org.springframework.context.annotation.FilterType;


@WebMvcTest(value=UserController.class, includeFilters = {
		@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtTokenUtil.class) })
public class UserControllerTests {
private static final String STARTING_URI = "http://localhost:8080/";
	
	@MockBean
	private AuthenticationController controller;
	
	@MockBean
	private UserController userController;
	
	@MockBean
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private JwtUserDetailsService myUserDetailsService;
	
	@MockBean
	private UserRepository repo;

	@Autowired
	private JwtTokenUtil jwtUtil;
	
	@Autowired
	private JwtRequestFilter filter;
	
	@Test
	void testGetAllUsers() throws Exception {
		
		// uri for the request
		String uri = STARTING_URI + "user/all";
		
		User admin = new User(null, "admin", null, "pass", null, true, null);
		admin.setRole(User.Role.ROLE_ADMIN);
		UserDetails usrDeets = new JwtUserDetails(admin);

		String token = jwtUtil.generateToken(usrDeets);
		
		
		List<User> users = new ArrayList<User>();
		User alice = new User(1, "alice", null, "pass1", null, true, null);
		User bob = new User(2, "bob", null, "pass1", null, true, null);
		alice.setRole(User.Role.ROLE_USER);
		bob.setRole(User.Role.ROLE_USER);
		//ResponseEntity<?>
		users.add(alice);
		users.add(bob);
		when(repo.findAll()).thenReturn(users);
		when(myUserDetailsService.loadUserByUsername("admin")).thenReturn(usrDeets);
		mvc.perform( get(uri)   // perform get request
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization","Bearer " + token))
	 
        		.andDo( print() ) // print request sent/response given
				.andExpect( status().isOk() ); // expect a 200 status code
				
		// verify how many times a method was called (how many times methods from service 
		// were used)
		//verify( repo, times(1) ).findAll(); // getAllCars() from service called once
		verifyNoMoreInteractions( repo ); // after checking above, service is no longer called
	}
	
}
