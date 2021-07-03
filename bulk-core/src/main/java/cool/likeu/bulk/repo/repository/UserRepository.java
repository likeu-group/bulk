package cool.likeu.bulk.repo.repository;

import cool.likeu.bulk.repo.po.UserPO;
import cool.likeu.bulk.security.BulkUserDetailsImpl;

/**
 * TODO 面对调用来说 UserRepository应该在UserService中，对调用来说是无感知的
 * 使用Spring-Cache注解
 */
public interface UserRepository {

	void cacheToken(BulkUserDetailsImpl bulkUserDetails);

	void cacheUser(UserPO userPO);

	UserPO lookupUserByCache(String username);

	boolean hasUserExistsInCache(String username);

	boolean removeUserInCache(String username);
}
