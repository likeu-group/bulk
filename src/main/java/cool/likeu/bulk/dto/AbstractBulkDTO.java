package cool.likeu.bulk.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public abstract class AbstractBulkDTO<T> implements Serializable {

	protected T data;

	protected String message;

	public AbstractBulkDTO(T data, String message) {
		this.data = data;
		this.message = message;
	}

}
