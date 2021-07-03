package cool.likeu.bulk.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cool.likeu.bulk.dto.BulkResponse;
import cool.likeu.bulk.misc.BulkConstant;
import cool.likeu.bulk.utils.JacksonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import static cool.likeu.bulk.dto.BulkResponse.HttpStatus.FORBIDDEN;
import static cool.likeu.bulk.dto.BulkResponse.HttpStatus.INTERNAL_ERROR;
import static cool.likeu.bulk.dto.BulkResponse.HttpStatus.OK;
import static cool.likeu.bulk.dto.BulkResponse.HttpStatus.UNAUTHORIZED;

/**
 * <p>Authentication Failed的切入点</p>
 *
 * @since 1.0
 * @author XiaoYu
 */
@Component
public class DefaultAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private final static Logger LOGGER = LoggerFactory.getLogger(DefaultAuthenticationEntryPoint.class);

	/**
	 * <p>Commences an authentication scheme.</p>
	 *
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param authException AuthenticationException
	 * @throws IOException IOException
	 * @throws ServletException ServletException
	 */
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		response.setContentType(BulkConstant.MIME_TYPE_APPLICATION_JSON);

		doResponse(response, authException);
	}

	private void doResponse(HttpServletResponse response, AuthenticationException authException)
			throws IOException, ServletException {

		BulkResponse<String> failureCauseDTO;
		if (authException instanceof BadCredentialsException) {
			response.setStatus(HttpServletResponse.SC_OK);
			failureCauseDTO = BulkResponse.failure(OK, "用户名或密码不正确!");
		}
		else if (authException instanceof LockedException) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			failureCauseDTO = BulkResponse.failure(FORBIDDEN, "账户已被锁定!");
		}
		else if (authException instanceof AccountExpiredException) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			failureCauseDTO = BulkResponse.failure(FORBIDDEN, "已禁止该账户访问!");
		}
		else if (authException instanceof DisabledException) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			failureCauseDTO = BulkResponse.failure(FORBIDDEN, "用户尚未激活!");
		}
		else {
			Throwable causeThrowable = authException.getCause();
			if (causeThrowable instanceof AuthenticationException) {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				failureCauseDTO = BulkResponse.failure(UNAUTHORIZED, "您还未授权!");
			} else {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				failureCauseDTO = BulkResponse.failure(INTERNAL_ERROR);
				LOGGER.error("Exception occurred when authentication account, cause throwable class name:[{}]",
						causeThrowable.getClass().getName(), authException);
			}
		}

		response.getWriter().print(JacksonUtils.toJson(failureCauseDTO));
	}

}
