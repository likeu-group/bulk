package cool.likeu.bulk.exception;

import cool.likeu.bulk.dto.BulkResponse;
import cool.likeu.bulk.exception.runtime.BulkRuntimeException;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * <p>全局异常拦截处理器</p>
 *
 * @author XiaoYu
 */
@RestControllerAdvice
public class BulkExceptionAdvice {

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler({BulkRuntimeException.class})
	public BulkResponse<String> process(BulkRuntimeException runtimeException) {
		// TODO HttpEntity
		return BulkResponse.failure(runtimeException.getErrCode(), runtimeException.getMessage());
	}
}
