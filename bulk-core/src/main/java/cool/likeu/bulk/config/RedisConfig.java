package cool.likeu.bulk.config;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import cool.likeu.bulk.exception.runtime.BulkRuntimeException;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleCacheErrorHandler;
import org.springframework.cache.interceptor.SimpleCacheResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * <p>SpringBoot Data Redis配置</p>
 *
 * @see StringRedisSerializer
 * @see Jackson2JsonRedisSerializer
 * @since 1.0
 * @author XiaoYu
 */
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

	private final RedisConnectionFactory connectionFactory;

	public RedisConfig(RedisConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}

	@Bean
	public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
		final RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConnectionFactory);

		final StringRedisSerializer keySerializer = new StringRedisSerializer();
		final Jackson2JsonRedisSerializer<Object> valueSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
		final ObjectMapper jsonSerializer = new ObjectMapper();

		jsonSerializer.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		jsonSerializer.activateDefaultTyping(LaissezFaireSubTypeValidator.instance,
				ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
		jsonSerializer.disable(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES);
		valueSerializer.setObjectMapper(jsonSerializer);

		redisTemplate.setKeySerializer(keySerializer);
		redisTemplate.setValueSerializer(valueSerializer);

		redisTemplate.setHashKeySerializer(keySerializer);
		redisTemplate.setHashValueSerializer(valueSerializer);
		redisTemplate.afterPropertiesSet();

		return redisTemplate;
	}

	@Bean
	@Override
	public KeyGenerator keyGenerator() {
		return (o, method, objects) -> {
			StringBuilder builder = new StringBuilder();
			builder.append(o.getClass().getName()).append(".");
			builder.append(method.getName()).append(".");
			for (Object obj : objects) {
				builder.append(obj.toString());
			}
			return builder.toString();
		};
	}

	@Bean
	@Override
	public CacheResolver cacheResolver() {
		CacheManager defaultCacheManager = this.cacheManager();
		if (Objects.isNull(defaultCacheManager)) {
			throw new BulkRuntimeException(500, "[CacheManager] RedisConfig#cacheManager builded is null.");
		}
		return new SimpleCacheResolver(defaultCacheManager);
	}

	@Bean
	@Override
	public CacheErrorHandler errorHandler() {
		return new SimpleCacheErrorHandler();
	}

	@Bean
	@Override
	public CacheManager cacheManager() {
		RedisCacheConfiguration cacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
				.disableCachingNullValues()
				.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(
						new GenericJackson2JsonRedisSerializer()));
		return RedisCacheManager.builder(connectionFactory).cacheDefaults(cacheConfiguration).build();
	}
}
