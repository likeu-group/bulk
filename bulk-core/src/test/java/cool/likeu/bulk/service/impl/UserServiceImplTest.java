package cool.likeu.bulk.service.impl;

import cool.likeu.bulk.repo.po.UserPO;
import cool.likeu.bulk.service.UserService;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {

	@Autowired
	private UserService userService;

	@Test
	void lookupUserByUsernameTest() {
		for (int i = 0; i < 5; i++) {
			UserPO userPO = userService.lookupUserByUsername("hello_world");
			assertNotNull(userPO);
		}
	}
}
