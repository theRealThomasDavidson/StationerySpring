package com.cognixia.jump.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.RequestHeader;

import com.cognixia.jump.model.Order;
import com.cognixia.jump.model.User;
import com.cognixia.jump.repository.UserRepository;

@WebMvcTest(UserController.class)
public class UserControllerTests {
private static final String STARTING_URI = "http://localhost:8080/user";
	
	// mocking the request/response
	@Autowired
	private MockMvc mvc;
	
	// mock the method calls for the service
	// we decide what data gets returned from the service methods instead
	@MockBean
	private UserRepository repo;

	// when the controller tries to autowire the service,
	// mock the creation of the service
	@InjectMocks
	private UserController controller;
	
	@Test
	void testGetAllUsers() throws Exception {
		
		// uri for the request
		String uri = STARTING_URI + "/";
		
		// the data that will be returned by the service when
		// getAllCars() method is called
		List<User> allUsers = new ArrayList<User>();
		allUsers.add(new User(1, "colin", "e@ma.il", "howdy", null, true, new ArrayList<Order>()));
		allUsers.add(new User(1, "daryl", "e@ma.il", "pass", null, true, new ArrayList<Order>()));
		
		Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqYXZhaW51c2UiLCJleHAiOjE2NTcxOTU1NjAsImlhdCI6MTY1NzE3NzU2MH0.sUG345efTwaFLtnVmN-c4LkOVAjda-A5sDf1r81JiLwlnBJlEOY6pIchm6hj6-wH25tfAPF4WLncdRS-qh8SCw");
        
		
		mvc.perform( get(uri)   // perform get request
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization","Bearer eyJhbGciO"
						+ "iJIUzUxMiJ9.eyJzdWIiOiJqYXZhaW51c2UiLCJleHAiOjE2NTcxOTU1NjAsIml"
						+ "hdCI6MTY1NzE3NzU2MH0.sUG345efTwaFLtnVmN-c4LkOVAjda-A5sD"
						+ "f1r81JiLwlnBJlEOY6pIchm6hj6-wH25tfAPF4WLncdRS-qh8SCw"))
	 
        		.andDo( print() ) // print request sent/response given
				.andExpect( status().isOk() ) // expect a 200 status code
				.andExpect( content().contentType( MediaType.APPLICATION_JSON_VALUE ) ) // checks content type is json
				.andExpect( jsonPath( "$.length()" ).value( allUsers.size() ) ) // length of the list matches one above
				.andExpect( jsonPath("$[0].id").value(allUsers.get(0).getId()) ) // check each column value for the cars in list
				.andExpect( jsonPath("$[0].username").value(allUsers.get(0).getUsername()) )
				.andExpect( jsonPath("$[0].password").value(allUsers.get(0).getPassword()) )
				.andExpect( jsonPath("$[1].id").value(allUsers.get(1).getId()) )
				.andExpect( jsonPath("$[1].username").value(allUsers.get(1).getUsername()) )
				.andExpect( jsonPath("$[1].password").value(allUsers.get(1).getPassword()) );
		
		// verify how many times a method was called (how many times methods from service 
		// were used)
		verify( repo, times(1) ).findAll(); // getAllCars() from service called once
		verifyNoMoreInteractions( repo ); // after checking above, service is no longer called
	}
	
}
