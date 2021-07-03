package cool.likeu.bulk.experiment;

import java.util.concurrent.TimeUnit;

import cool.likeu.bulk.repo.po.UserPO;
import cool.likeu.bulk.repo.repository.UserRepository;
import cool.likeu.bulk.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class RedisCacheTests {

	@Autowired
	private RedisTemplate<Object, Object> redisClient;

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	private UserPO globalUserPO;

	@BeforeEach
	void testInit() {
		globalUserPO = userService.lookupUserByUserId(1L);
	}

	@Test
	void testWrite() {
		redisClient.opsForValue()
				.set("bulk_user:test:" + globalUserPO.getUsername(), globalUserPO);
	}

	@Test
	void testSearch() {
		UserPO cacheObject = (UserPO) redisClient.opsForValue().get(globalUserPO.getUsername());
		assertNotNull(cacheObject);
		System.out.println(cacheObject);
	}

	@Test
	void testDelete() {
		Boolean deleteResult = redisClient.delete(globalUserPO.getUsername());
		assertEquals(deleteResult, Boolean.TRUE);
	}

	@Test
	void testExpire() {
		redisClient.opsForValue().set(globalUserPO.getUsername(), globalUserPO, 2, TimeUnit.MINUTES);
	}

	@Test
	void testLookupUserByCache() {
		final String username = "2f56f1985eaa46b68968db2175e4fb82";

		UserPO userPO = userRepository.lookupUserByCache(username);
		assertNotNull(userPO);
	}

	@Test
	void testSpringCacheComponent() {
		for (int i = 0; i < 3; i++) {
			UserPO user = userService.lookupUserByUserId(1L);
			assertNotNull(user);
		}
	}

	@Cacheable(cacheNames = "bulk_user_cache", key = "#roleId")
	public UserPO findUser(Long roleId) {
		return userService.lookupUserByUserId(roleId);
	}
}
