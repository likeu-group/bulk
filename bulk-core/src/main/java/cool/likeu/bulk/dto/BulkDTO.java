package cool.likeu.bulk.dto;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import org.springframework.http.HttpStatus;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class BulkDTO<T> extends AbstractBulkDTO<T> implements Serializable {

	private Integer status;

	public BulkDTO(Integer status, T data) {
		super(data, null);
		this.status = status;
	}

	public BulkDTO(Integer status, String message) {
		super(null, message);
		this.status = status;
	}

	public static <T> BulkDTO<T> success(Integer status, T data) {
		return new BulkDTO<>(status, data);
	}

	public static <T> BulkDTO<T> success(HttpStatus status, T data) {
		return success(status.value(), data);
	}

	public static <T> BulkDTO<T> success(T data) {
		return success(HttpStatus.OK, data);
	}

	public static <T> BulkDTO<T> failure(Integer status, String message) {
		return new BulkDTO<>(status, message);
	}

	public static <T> BulkDTO<T> failure(HttpStatus status, String message) {
		return failure(status.value(), message);
	}

	public static <T> BulkDTO<T> failure(String message) {
		return failure(HttpStatus.BAD_REQUEST, message);
	}
}
