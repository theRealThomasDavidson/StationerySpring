package com.cognixia.jump;

import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.cognixia.jump.repository.UserRepository;

@SpringBootTest
class StationerySpringApplicationTests {
	@MockBean
	private UserRepository repo;
	@Test
	void contextLoads() {
		verifyNoMoreInteractions( repo );
	}

}
