package cool.likeu.bulk.security;

import java.time.Duration;

import cool.likeu.bulk.dto.BulkResponse;
import cool.likeu.bulk.exception.runtime.BulkAccountException;
import cool.likeu.bulk.repo.po.UserPO;
import cool.likeu.bulk.service.UserService;

import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import static cool.likeu.bulk.security.token.JwtTokenManager.EXPIRATION_TIME;

/**
 * <p>UserDetailsServiceImpl实现了{@link UserDetailsService}接口中的
 * {@link UserDetailsService#loadUserByUsername(String)}方法，方法执
 * 行成功后返回{@link UserDetails}对象，为构建{@link org.springframework.security.core.Authentication}
 * 对象提供必需的信息。</p>
 * <p>{@code UserDetails}(当前使用{@link User})对象中，包含了：</p>
 * <ul>
 *     <li>username - 用户名</li>
 *     <li>password - 密码凭证</li>
 *     <li>{@link GrantedAuthority} - 例如：角色、作用域。如果提供正确的用户名和密码,
 *     并且账户已启用，则应授予caller对应的角色。不能为null</li>
 *     <li>accountNonExpired - 如果账号未过期则设置为true</li>
 *     <li>accountNonLocked - 如果账号未锁定，则设置为true</li>
 *     <li>credentialsNonExpired - 如果凭证未过期，则设置为true</li>
 *     <li>enabled - 如果账户已启用，则设置为true</li>
 * </ul>
 *
 * @see UserService
 * @author XiaoYu
 */
@Primary
@Service("bulkUserDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserService userService;

	public UserDetailsServiceImpl(UserService userService) {
		this.userService = userService;
	}

	/**
	 * <p>
	 *     查询user信息，并将username、password、Role集合传入到{@link User}中,
	 *     用于Authentication
	 * </p>
	 * @param username 用户名
	 * @return UserDetails
	 * @throws UsernameNotFoundException UsernameNotFoundException
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserPO userPO = userService.lookupUserByUsername(username);
		if (ObjectUtils.isEmpty(userPO)) {
			throw new UsernameNotFoundException("The user(" + username + ") could not be found. ");
		}
		return createUserDetails(userPO);
	}

	/**
	 * <p>创建UserDetails</p>
	 *
	 * @param userPO UserPO
	 * @return {@link BulkUserDetailsImpl}
	 */
	private UserDetails createUserDetails(UserPO userPO) {
		final BulkUserDetailsImpl bulkUserDetails = new BulkUserDetailsImpl(userPO);
		// 设置缓存信息的有效时长，和jwt颁发时长一致
		bulkUserDetails.setExpireTime(Duration.ofMillis(EXPIRATION_TIME));
		switch (userPO.convertUserStatus()) {
			case NORMAL:
				// NOOP
				break;
			case LOCKED:
				bulkUserDetails.setAccountNonLocked(false);
				break;
			case FORBIDDEN:
				bulkUserDetails.setAccountNonExpired(false);
				break;
			case INACTIVATION:
				bulkUserDetails.setAccountEnabled(false);
				break;
			default:
				throw new BulkAccountException(BulkResponse.HttpStatus.INTERNAL_ERROR,
						"The defined user status does not exist, please check, status code: " + userPO.getStatus());
		}
		return bulkUserDetails;
	}
}
