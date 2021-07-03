package cool.likeu.bulk.exception.runtime;

import cool.likeu.bulk.dto.BulkResponse;

/**
 * FIXME 行为异常
 *
 * @author XiaoYu
 */
public class BulkAccountException extends BulkRuntimeException {

	private static final long serialVersionUID = -6757212069180592274L;

	public BulkAccountException(BulkResponse.HttpStatus httpStatus) {
		super(httpStatus);
	}

	public BulkAccountException(BulkResponse.HttpStatus httpStatus, String message) {
		super(httpStatus.getCode(), message);
	}

	public BulkAccountException(BulkResponse.HttpStatus httpStatus, Throwable cause) {
		super(httpStatus, cause);
	}

	public BulkAccountException(int errCode) {
		super(errCode);
	}

	public BulkAccountException(int errCode, String message) {
		super(errCode, message);
	}

	public BulkAccountException(int errCode, Throwable cause) {
		super(errCode, cause);
	}

	public BulkAccountException(int errCode, String message, Throwable cause) {
		super(errCode, message, cause);
	}
}
