package cool.likeu.bulk.service;

import cool.likeu.bulk.repo.po.UserPO;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class RoleSecurityServiceTests {

	@Autowired
	private UserService userService;

	@Test
	void testLookupUserHasRole() {
		final String username = "hello_world";
		UserPO userPO = userService.lookupUserByUsername(username);
		assertNotNull(userPO, "User is null.");
		System.out.println(userPO);
	}

}
