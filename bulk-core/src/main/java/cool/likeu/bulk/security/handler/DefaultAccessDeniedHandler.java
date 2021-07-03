package cool.likeu.bulk.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cool.likeu.bulk.dto.BulkResponse;
import cool.likeu.bulk.misc.BulkConstant;
import cool.likeu.bulk.utils.JacksonUtils;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

/**
 * <p>请求访问被拒处理器：403</p>
 *
 * @author XiaoYu
 */
@Component
public class DefaultAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException,
			ServletException {
		response.setContentType(BulkConstant.MIME_TYPE_APPLICATION_JSON);
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);

		final BulkResponse<String> failureResult = BulkResponse.failure(403, "您无法访问该资源!");
		final String failureResultStr = JacksonUtils.toJson(failureResult);

		response.getWriter().print(failureResultStr);
	}
}
