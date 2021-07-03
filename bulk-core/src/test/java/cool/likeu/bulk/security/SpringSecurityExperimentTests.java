package cool.likeu.bulk.security;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

class SpringSecurityExperimentTests {

	@Test
	void testPasswordEncoder() {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode("123456");
		// $2a$10$JgNebXUr.HUDS1h7CnD.i.iA.WamtSusEgbV6Zcrgk2jzgr2W9du2
		System.out.println(encodedPassword);

		// DelegatePasswordEncoder用法
		final String idForEncode = "bcrypt";
		Map<String, PasswordEncoder> encoders = new HashMap<>();
		encoders.put(idForEncode, new BCryptPasswordEncoder());
		encoders.put("pbkdf2", new Pbkdf2PasswordEncoder());
		encoders.put("scrypt", new SCryptPasswordEncoder());

		PasswordEncoder delegatePasswordEncoder =
				new DelegatingPasswordEncoder(idForEncode, encoders);

		// {bcrypt}$2a$10$aXQE3cTYy9TOjZSJCEDDheu94KciUuYhuP4zdbm8txaliFfhg5jGG
		System.out.println(delegatePasswordEncoder.encode("123456"));
	}

}
