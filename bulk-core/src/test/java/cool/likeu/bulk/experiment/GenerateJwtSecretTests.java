package cool.likeu.bulk.experiment;

import org.junit.jupiter.api.Test;

import org.springframework.security.core.token.Sha512DigestUtils;

class GenerateJwtSecretTests {

	@Test
	void testGenerateSecret() {
		String sha512Secret = Sha512DigestUtils.shaHex("bulk-xsdq-corporation:author@xiaoyu");
		System.out.println(sha512Secret);
	}

}
