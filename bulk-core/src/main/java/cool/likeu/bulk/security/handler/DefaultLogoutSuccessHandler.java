package cool.likeu.bulk.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cool.likeu.bulk.dto.BulkResponse;
import cool.likeu.bulk.repo.repository.UserRepository;
import cool.likeu.bulk.security.BulkUserDetailsImpl;
import cool.likeu.bulk.security.token.JwtTokenManager;
import cool.likeu.bulk.utils.JacksonUtils;
import cool.likeu.bulk.utils.StringUtils;

import static cool.likeu.bulk.misc.BulkConstant.MIME_TYPE_APPLICATION_JSON;
import static cool.likeu.bulk.misc.BulkConstant.SECURITY_ACCESS_TOKEN_HTTP_HEADER;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

/**
 * <p>登出账号处理器</p>
 *
 * @since 1.0
 * @author XiaoYu
 */
@Component
public class DefaultLogoutSuccessHandler implements LogoutSuccessHandler {

	private final JwtTokenManager jwtTokenManager;

	private final UserRepository userRepository;

	public DefaultLogoutSuccessHandler(JwtTokenManager jwtTokenManager, UserRepository userRepository) {
		this.jwtTokenManager = jwtTokenManager;
		this.userRepository = userRepository;
	}

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		if (authentication == null) {
			final String authToken = request.getHeader(SECURITY_ACCESS_TOKEN_HTTP_HEADER);
			if (!StringUtils.isEmpty(authToken)) {
				final String username = jwtTokenManager.getUsernameFromToken(authToken);
				userRepository.removeUserInCache(username);
			}
		} else {
			final BulkUserDetailsImpl bulkUserDetails = (BulkUserDetailsImpl) authentication.getPrincipal();
			userRepository.removeUserInCache(bulkUserDetails.getUsername());
		}
		final BulkResponse<String> logoutSuccess = BulkResponse.success("logout success!");
		response.setContentType(MIME_TYPE_APPLICATION_JSON);
		response.getWriter().print(JacksonUtils.toJson(logoutSuccess));
	}
}
