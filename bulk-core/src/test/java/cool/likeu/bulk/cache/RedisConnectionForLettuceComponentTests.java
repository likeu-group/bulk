package cool.likeu.bulk.cache;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 *
 * @author XiaoYu
 * @since 2021/12/8 20:19
 * @version 1.0.0
 */
class RedisConnectionForLettuceComponentTests {

	private final static String REDIS_CONN_URL = "redis://123456@192.168.1.29/0";

	private RedisClient redisClient;
	private StatefulRedisConnection<String, String> statefulRedisConnection;

	@BeforeEach
	void initialize() {
		this.redisClient = RedisClient.create(REDIS_CONN_URL);
		assertNotNull(this.redisClient, "You should initialize the lettuce redis client.");
		this.statefulRedisConnection = redisClient.connect();
	}

	@Test
	void testSyncSaveValue() {
		testStatefulRedisConnectionIsReady();
		String result = this.statefulRedisConnection.sync().set("foo", "bar");
		assertEquals(result, "OK");
	}

	@Test
	void testLookupValue() {
		testStatefulRedisConnectionIsReady();
		String value = this.statefulRedisConnection.sync().get("foo");
		assertNotNull(value);
	}

	private void testStatefulRedisConnectionIsReady() {
		assertNotNull(statefulRedisConnection, "You should initialize the redis client and connected.");
	}

	@AfterEach
	void destroy() {
		if (statefulRedisConnection != null && statefulRedisConnection.isOpen()) {
			statefulRedisConnection.close();
		}
		if (redisClient != null) {
			redisClient.shutdown();
		}
	}
}
