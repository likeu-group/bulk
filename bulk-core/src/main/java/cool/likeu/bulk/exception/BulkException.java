package cool.likeu.bulk.exception;

import cool.likeu.bulk.dto.BulkResponse;
import cool.likeu.bulk.misc.BulkConstant;

import org.springframework.util.StringUtils;

/**
 * <p>Bulk通用异常定义</p>
 *
 * @see cool.likeu.bulk.exception.runtime.BulkRuntimeException
 * @author XiaoYu
 */
public class BulkException extends Exception {

	private static final long serialVersionUID = 2113007248530124837L;

	private int errCode;

	private String errMsg;

	private Throwable causeThrowable;

	public BulkException() {
	}

	public BulkException(final BulkResponse.HttpStatus httpStatus) {
		super(httpStatus.getMessage());
		this.errCode = httpStatus.getCode();
		this.errMsg = httpStatus.getMessage();
	}

	public BulkException(final BulkResponse.HttpStatus httpStatus, final Throwable causeThrowable) {
		super(httpStatus.getMessage());
		this.errCode = httpStatus.getCode();
		this.errMsg = httpStatus.getMessage();
		this.setCauseThrowable(causeThrowable);
	}

	public BulkException(final int errCode, final String errMsg) {
		super(errMsg);
		this.errCode = errCode;
		this.errMsg = errMsg;
	}

	public BulkException(final int errCode, final Throwable causeThrowable) {
		super(causeThrowable);
		this.errCode = errCode;
		this.setCauseThrowable(causeThrowable);
	}

	public BulkException(final int errCode, final String errMsg, final Throwable causeThrowable) {
		super(errMsg, causeThrowable);
		this.errCode = errCode;
		this.errMsg = errMsg;
		this.setCauseThrowable(causeThrowable);
	}

	public void setErrCode(int errCode) {
		this.errCode = errCode;
	}

	public int getErrCode() {
		return errCode;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public String getErrMsg() {
		if (!StringUtils.isEmpty(this.errMsg)) {
			return this.errMsg;
		}
		if (this.causeThrowable != null) {
			return this.causeThrowable.getMessage();
		}
		return BulkConstant.EMPTY_STRING;
	}

	public void setCauseThrowable(Throwable causeThrowable) {
		this.causeThrowable = this.getCauseThrowable(causeThrowable);
	}

	private Throwable getCauseThrowable(final Throwable t) {
		if (t.getCause() == null) {
			return t;
		}
		return this.getCauseThrowable(t.getCause());
	}
}
