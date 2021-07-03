package cool.likeu.bulk.exception.runtime;

import java.lang.reflect.Type;

public class BulkDeserializationException extends BulkRuntimeException {

	private static final long serialVersionUID = -4309448946791968534L;

	// TODO
	private static final int ERROR_CODE = 1001;

	private static final String DEFAULT_MSG = "Bulk deserialize failed. ";

	private static final String MSG_FOR_SPECIFIED_CLASS = "Bulk deserialize for class [%s] failed. ";

	private Class<?> targetClass;

	public BulkDeserializationException() {
		super(ERROR_CODE);
	}

	public BulkDeserializationException(Class<?> targetClass) {
		super(ERROR_CODE, String.format(MSG_FOR_SPECIFIED_CLASS, targetClass.getName()));
		this.targetClass = targetClass;
	}

	public BulkDeserializationException(Type targetType) {
		super(ERROR_CODE, String.format(MSG_FOR_SPECIFIED_CLASS, targetType.toString()));
	}

	public BulkDeserializationException(Throwable throwable) {
		super(ERROR_CODE, DEFAULT_MSG, throwable);
	}

	public BulkDeserializationException(Class<?> targetClass, Throwable throwable) {
		super(ERROR_CODE, String.format(MSG_FOR_SPECIFIED_CLASS, targetClass.getName()), throwable);
		this.targetClass = targetClass;
	}

	public BulkDeserializationException(Type targetType, Throwable throwable) {
		super(ERROR_CODE, String.format(MSG_FOR_SPECIFIED_CLASS, targetType.toString()), throwable);
	}

	public Class<?> getTargetClass() {
		return targetClass;
	}

}
