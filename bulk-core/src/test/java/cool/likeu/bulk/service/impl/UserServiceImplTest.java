package cool.likeu.bulk.service.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cool.likeu.bulk.repo.po.UserPO;
import cool.likeu.bulk.service.UserService;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {

	private final static String EMAIL_REGEX = "[a-zA-Z0-9_]+@[a-zA-Z0-9_]+(\\.[a-zA-Z0-9]+)+";

	@Autowired
	private UserService userService;

	@Test
	void lookupUserByUsernameTest() {
		for (int i = 0; i < 5; i++) {
			UserPO userPO = userService.lookupUserByUsername("hello_world");
			assertNotNull(userPO);
		}
	}

	@Test
	void determineEmailFormatTest() {
		final String email1 = "xiaoyu@julegame.com";
		final String email2 = "x_xiaoY_u@like.cool";
		final String email3 = "993610942@qq.com";
		Pattern emailPattern = Pattern.compile(EMAIL_REGEX);
		Matcher email1Matcher = emailPattern.matcher(email1);
		Matcher email2Matcher = emailPattern.matcher(email2);
		Matcher email3Matcher = emailPattern.matcher(email3);
		assertTrue(email1Matcher.matches());
		assertTrue(email2Matcher.matches());
		assertTrue(email3Matcher.matches());
	}

	@Test
	void lookupUserByUserIdTest() {
		UserPO userPO = userService.lookupUserByUserId(1L);
		assertNotNull(userPO);
	}
}
