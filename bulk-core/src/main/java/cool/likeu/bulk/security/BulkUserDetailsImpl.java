package cool.likeu.bulk.security;

import java.time.Duration;
import java.util.Collection;
import java.util.stream.Collectors;

import cool.likeu.bulk.repo.po.UserPO;
import lombok.Data;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * <p>自定义{@link UserDetails}的实现类，承载了UserPO对象，用于获取登录用户的
 * 基础数据</p>
 *
 * @see org.springframework.security.core.userdetails.UserDetails
 * @since 1.0
 * @author XiaoYu
 */
@Data
public class BulkUserDetailsImpl implements UserDetails {

	private static final long serialVersionUID = 1275416081094667470L;

	private UserPO userPO;

	private boolean accountNonExpired = true;

	private boolean accountNonLocked = true;

	private boolean accountEnabled = true;

	private boolean credentialsNonExpired = true;

	/**
	 * Authentication超时时常（与缓存挂钩）
	 */
	private Duration expireTime;

	public BulkUserDetailsImpl(UserPO userPO) {
		this.userPO = userPO;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return userPO.getRoles()
				.stream()
				.map(role -> new SimpleGrantedAuthority(role.getRoleName()))
				.collect(Collectors.toList());
	}

	@Override
	public String getPassword() {
		return userPO.getPassword();
	}

	@Override
	public String getUsername() {
		return userPO.getUsername();
	}

	/**
	 * <p>用户禁止访问状态</p>
	 *
	 * @see UserPO.Status#FORBIDDEN
	 * @return true:未禁止， false：禁止
	 */
	@Override
	public boolean isAccountNonExpired() {
		return this.accountNonExpired;
	}

	/**
	 * <p>用户锁定状态</p>
	 *
	 * @see UserPO.Status#LOCKED
	 * @return true:未锁定， false：锁定
	 */
	@Override
	public boolean isAccountNonLocked() {
		return this.accountNonLocked;
	}

	/**
	 * <p>暂时未用到</p>
	 *
	 * @return true:未过期， false：过期
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		return this.credentialsNonExpired;
	}

	/**
	 * <p>用户激活状态</p>
	 *
	 * @see UserPO.Status#INACTIVATION
	 * @return true:未激活， false：激活
	 */
	@Override
	public boolean isEnabled() {
		return this.accountEnabled;
	}
}
