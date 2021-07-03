package cool.likeu.bulk.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

/**
 * <p></p>
 *
 * @since 1.0
 * @author XiaoYU
 * @param <T>
 */
public class BulkResponse<T> extends AbstractBulkDTO<T> implements Serializable {

	private static final long serialVersionUID = 3988316106344964950L;

	private final Integer status;

	public BulkResponse(Integer status, T data) {
		super(data, null);
		this.status = status;
	}

	public BulkResponse(Integer status, String message) {
		super(null, message);
		this.status = status;
	}

	public Integer getStatus() {
		return status;
	}

	public static <T> BulkResponse<T> success(Integer status, T data) {
		return new BulkResponse<>(status, data);
	}

	public static <T> BulkResponse<T> success(org.springframework.http.HttpStatus status, T data) {
		return success(status.value(), data);
	}

	public static <T> BulkResponse<T> success(T data) {
		return success(org.springframework.http.HttpStatus.OK, data);
	}

	public static <T> BulkResponse<T> failure(Integer status, String message) {
		return new BulkResponse<>(status, message);
	}

	public static <T> BulkResponse<T> failure(HttpStatus status, String message) {
		return failure(status.getCode(), message);
	}

	public static <T> BulkResponse<T> failure(String message) {
		return failure(HttpStatus.BAD_REQUEST, message);
	}

	public static <T> BulkResponse<T> failure(HttpStatus status) {
		return failure(status.getCode(), status.getMessage());
	}

	/**
	 * <p>BulkResponse HttpStatus</p>
	 *
	 * TODO 完善错误定义
	 *
	 * @author XiaoYu
	 */
	@Getter
	public enum HttpStatus {
		OK(1000, "success"),
		INTERNAL_ERROR(1001, "Internal server error"),
		UNAUTHORIZED(1002, "Unauthorized"),
		FORBIDDEN(1003, "Forbidden"),
		BAD_REQUEST(1004, "Bad Request");

		private final static Map<Integer, HttpStatus> HTTP_STATUS_CACHE;

		static {
			HTTP_STATUS_CACHE = new HashMap<>(values().length);
			for (HttpStatus httpStatus : values()) {
				HTTP_STATUS_CACHE.put(httpStatus.getCode(), httpStatus);
			}
		}

		private final int code;

		private final String message;

		HttpStatus(int code, String message) {
			this.code = code;
			this.message = message;
		}

		public static HttpStatus of(int status) {
			return HTTP_STATUS_CACHE.get(status);
		}
	}
}
