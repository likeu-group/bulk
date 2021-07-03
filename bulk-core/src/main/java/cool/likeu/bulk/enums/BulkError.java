package cool.likeu.bulk.enums;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

/**
 * <p>自定义Response的返回code</p>
 *
 * TODO deprecated this 完善错误定义，应该放在BulkResponse中
 *
 * @author XiaoYu
 */
@Getter
public enum BulkError {
	OK(1000, 200, "success"),
	INTERNAL_ERROR(1001, 500, "Internal server error");

	private final static Map<Integer, BulkError> HTTP_ERROR_MAP;

	static {
		HTTP_ERROR_MAP = new HashMap<>(values().length);
		for (BulkError httpError : values()) {
			HTTP_ERROR_MAP.put(httpError.getCode(), httpError);
		}
	}

	private final int code;
	private final int httpCode;
	private final String message;

	BulkError(int code, int httpCode, String message) {
		this.code = code;
		this.httpCode = httpCode;
		this.message = message;
	}
}
