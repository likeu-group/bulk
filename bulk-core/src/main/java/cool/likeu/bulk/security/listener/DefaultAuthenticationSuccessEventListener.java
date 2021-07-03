package cool.likeu.bulk.security.listener;

import cool.likeu.bulk.repo.repository.UserRepository;
import cool.likeu.bulk.security.BulkUserDetailsImpl;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * <p>登录成功后{@code SpringSecurity}触发的登录成功事件，此处逻辑在登录成功后将
 * {@code Authentication}保存的用户数据存入到cache中</p>
 *
 * @see UserRepository
 * @since 1.0
 * @author XiaoYu
 */
@Component
public class DefaultAuthenticationSuccessEventListener implements ApplicationListener<AuthenticationSuccessEvent> {

	private final UserRepository userRepository;

	public DefaultAuthenticationSuccessEventListener(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public void onApplicationEvent(AuthenticationSuccessEvent authenticationSuccessEvent) {
		final Authentication authentication = authenticationSuccessEvent.getAuthentication();
		final BulkUserDetailsImpl bulkUserDetails = (BulkUserDetailsImpl) authentication.getPrincipal();

		userRepository.cacheToken(bulkUserDetails);
	}

}
