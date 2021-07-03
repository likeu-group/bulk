package cool.likeu.bulk.exception.runtime;

/**
 * <p>json序列化异常</p>
 *
 * @since 1.0
 * @author XiaoYu
 */
public class BulkSerializationException extends BulkRuntimeException {

	private static final long serialVersionUID = -5221835794033311637L;

	// TODO
	private static final int ERROR_CODE = 1000;

	private static final String DEFAULT_MSG = "Bulk serialize failed. ";

	private static final String MSG_FOR_SPECIFIED_CLASS = "Bulk serialize for class [%s] failed. ";

	private Class<?> serializedClass;

	public BulkSerializationException() {
		super(ERROR_CODE);
	}

	public BulkSerializationException(Class<?> serializedClass) {
		super(ERROR_CODE, String.format(MSG_FOR_SPECIFIED_CLASS, serializedClass.getName()));
		this.serializedClass = serializedClass;
	}

	public BulkSerializationException(Throwable throwable) {
		super(ERROR_CODE, DEFAULT_MSG, throwable);
	}

	public BulkSerializationException(Class<?> serializedClass, Throwable throwable) {
		super(ERROR_CODE, String.format(MSG_FOR_SPECIFIED_CLASS, serializedClass.getName()), throwable);
		this.serializedClass = serializedClass;
	}

	public Class<?> getSerializedClass() {
		return serializedClass;
	}

}
