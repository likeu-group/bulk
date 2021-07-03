package cool.likeu.bulk.dto;

import java.io.Serializable;

public abstract class AbstractBulkDTO<T> implements Serializable {

	private static final long serialVersionUID = -4188658081705779718L;

	protected T data;

	protected String message;

	public AbstractBulkDTO(T data, String message) {
		this.data = data;
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public String getMessage() {
		return message;
	}
}
