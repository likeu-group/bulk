package cool.likeu.bulk.repo.repository.impl;

import java.util.Objects;

import cool.likeu.bulk.dto.BulkResponse;
import cool.likeu.bulk.exception.runtime.BulkRuntimeException;
import cool.likeu.bulk.repo.po.UserPO;
import cool.likeu.bulk.repo.repository.UserRepository;
import cool.likeu.bulk.security.BulkUserDetailsImpl;

import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import static cool.likeu.bulk.misc.BulkConstant.USER_CACHE_PREFIX;

@Component("userRepositoryImpl")
public class UserRepositoryImpl implements UserRepository {

	private final RedisTemplate<Object, Object> redisClient;

	public UserRepositoryImpl(RedisTemplate<Object, Object> redisClient) {
		this.redisClient = redisClient;
	}

	@Override
	public void cacheToken(BulkUserDetailsImpl bulkUserDetails) {
		final UserPO userPO = bulkUserDetails.getUserPO();
		if (userPO == null) {
			throw new BulkRuntimeException(BulkResponse.HttpStatus.INTERNAL_ERROR, "User information cannot be null.");
		}

		final UserPO cacheUserPO = new UserPO();
		BeanUtils.copyProperties(userPO, cacheUserPO);

		/*
		 FIX [BUG:#1] 将缓存中的密码置空后，再次登录则会出现BadCredentialsException

		 cacheUserPO.setPassword(null);
		 */

		redisClient.opsForValue().set(USER_CACHE_PREFIX + userPO.getUsername(),
				userPO, bulkUserDetails.getExpireTime());
	}

	@Override
	public void cacheUser(UserPO userPO) {
		redisClient.opsForValue().set(USER_CACHE_PREFIX + userPO.getUsername(), userPO);
	}

	@Override
	public UserPO lookupUserByCache(String username) {
		Object cacheObject = redisClient.opsForValue().get(USER_CACHE_PREFIX + username);
		if (Objects.nonNull(cacheObject)) {
			return (UserPO) cacheObject;
		}
		return null;
	}

	@Override
	public boolean hasUserExistsInCache(String username) {
		Boolean hasResult = redisClient.hasKey(USER_CACHE_PREFIX + username);
		return Objects.isNull(hasResult) ? Boolean.FALSE : hasResult;
	}

	@Override
	public boolean removeUserInCache(String username) {
		Boolean removeResult = redisClient.delete(USER_CACHE_PREFIX + username);
		return Objects.isNull(removeResult) ? Boolean.FALSE : removeResult;
	}
}
