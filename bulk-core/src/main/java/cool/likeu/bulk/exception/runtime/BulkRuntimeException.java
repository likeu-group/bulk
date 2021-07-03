package cool.likeu.bulk.exception.runtime;

import cool.likeu.bulk.dto.BulkResponse;

/**
 * <p>bulk运行时异常定义</p>
 *
 * @see cool.likeu.bulk.exception.BulkException
 * @author XiaoYu
 */
public class BulkRuntimeException extends RuntimeException {

	private static final long serialVersionUID = -3166209235440377811L;

	private int errCode;

	public BulkRuntimeException(final BulkResponse.HttpStatus httpStatus) {
		super(httpStatus.getMessage());
		this.errCode = httpStatus.getCode();
	}

	public BulkRuntimeException(final BulkResponse.HttpStatus httpStatus, final String message) {
		super(message);
		this.errCode = httpStatus.getCode();
	}

	public BulkRuntimeException(final BulkResponse.HttpStatus httpStatus, Throwable cause) {
		super(httpStatus.getMessage(), cause);
		this.errCode = httpStatus.getCode();
	}

	public BulkRuntimeException(final int errCode) {
		super();
		this.errCode = errCode;
	}

	public BulkRuntimeException(final int errCode, final String message) {
		super(message);
		this.errCode = errCode;
	}

	public BulkRuntimeException(int errCode, Throwable cause) {
		super(cause);
		this.errCode = errCode;
	}

	public BulkRuntimeException(int errCode, String message, Throwable cause) {
		super(message, cause);
		this.errCode = errCode;
	}

	public void setErrCode(int errCode) {
		this.errCode = errCode;
	}

	public int getErrCode() {
		return errCode;
	}
}
