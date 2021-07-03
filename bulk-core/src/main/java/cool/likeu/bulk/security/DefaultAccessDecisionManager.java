package cool.likeu.bulk.security;

import java.util.Collection;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

/**
 * <p>自定义权限决策器</p>
 * <p>Makes a final access control (<strong>authorization</strong>) decision.</p>
 *
 * @author XiaoYu
 */
@Component
public class DefaultAccessDecisionManager implements AccessDecisionManager {

	@Override
	public void decide(Authentication authentication, Object object,
			Collection<ConfigAttribute> configAttributes) throws AccessDeniedException,
			InsufficientAuthenticationException {
		if (authentication == null) {
			throw new AccessDeniedException("The current account is unauthenticated. ");
		}
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		for (ConfigAttribute configAttribute : configAttributes) {
			if (this.supports(configAttribute)) {
				for (GrantedAuthority authority : authorities) {
					if (comparison(configAttribute, authority)) {
						return;
					}
				}
			}
		}
		throw new AccessDeniedException("The current account does not have this permission. ");
	}

	@Override
	public boolean supports(ConfigAttribute attribute) {
		return true;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}

	private boolean comparison(ConfigAttribute attribute, GrantedAuthority authority) {
		return attribute.getAttribute().equals(authority.getAuthority());
	}
}
