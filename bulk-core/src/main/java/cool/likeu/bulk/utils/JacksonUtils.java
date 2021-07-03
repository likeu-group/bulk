package cool.likeu.bulk.utils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import cool.likeu.bulk.exception.runtime.BulkDeserializationException;
import cool.likeu.bulk.exception.runtime.BulkSerializationException;

/**
 * <p>jackson序列化工具</p>
 *
 * @since 1.0
 * @author XiaoYu
 */
public class JacksonUtils {

	static ObjectMapper SERDE_MAPPER = new ObjectMapper();

	static {
		SERDE_MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		SERDE_MAPPER.setSerializationInclusion(JsonInclude.Include.ALWAYS);
	}

	/**
	 * Object to json string.
	 *
	 * @param obj obj
	 * @return json string
	 * @throws BulkSerializationException if transfer failed
	 */
	public static String toJson(Object obj) {
		try {
			return SERDE_MAPPER.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			throw new BulkSerializationException(obj.getClass(), e);
		}
	}

	/**
	 * Object to json string byte array.
	 *
	 * @param obj obj
	 * @return json string byte array
	 * @throws BulkDeserializationException if transfer failed
	 */
	public static byte[] toJsonBytes(Object obj) {
		try {
			return ByteUtils.toBytes(SERDE_MAPPER.writeValueAsString(obj));
		} catch (JsonProcessingException e) {
			throw new BulkDeserializationException(obj.getClass(), e);
		}
	}

	/**
	 * Json string deserialize to Object.
	 *
	 * @param json json string
	 * @param cls  class of object
	 * @param <T>  General type
	 * @return object
	 * @throws BulkDeserializationException if deserialize failed
	 */
	public static <T> T toObj(byte[] json, Class<T> cls) {
		try {
			return toObj(StringUtils.newStringForUtf8(json), cls);
		} catch (Exception e) {
			throw new BulkDeserializationException(cls, e);
		}
	}

	/**
	 * Json string deserialize to Object.
	 *
	 * @param json json string
	 * @param cls  {@link Type} of object
	 * @param <T>  General type
	 * @return object
	 * @throws BulkDeserializationException if deserialize failed
	 */
	public static <T> T toObj(byte[] json, Type cls) {
		try {
			return toObj(StringUtils.newStringForUtf8(json), cls);
		} catch (Exception e) {
			throw new BulkDeserializationException(e);
		}
	}

	/**
	 * Json string deserialize to Object.
	 *
	 * @param inputStream json string input stream
	 * @param cls         class of object
	 * @param <T>         General type
	 * @return object
	 * @throws BulkDeserializationException if deserialize failed
	 */
	public static <T> T toObj(InputStream inputStream, Class<T> cls) {
		try {
			return SERDE_MAPPER.readValue(inputStream, cls);
		} catch (IOException e) {
			throw new BulkDeserializationException(e);
		}
	}

	/**
	 * Json string deserialize to Object.
	 *
	 * @param json          json string byte array
	 * @param typeReference {@link TypeReference} of object
	 * @param <T>           General type
	 * @return object
	 * @throws BulkDeserializationException if deserialize failed
	 */
	public static <T> T toObj(byte[] json, TypeReference<T> typeReference) {
		try {
			return toObj(StringUtils.newStringForUtf8(json), typeReference);
		} catch (Exception e) {
			throw new BulkDeserializationException(e);
		}
	}

	/**
	 * Json string deserialize to Object.
	 *
	 * @param json json string
	 * @param cls  class of object
	 * @param <T>  General type
	 * @return object
	 * @throws BulkDeserializationException if deserialize failed
	 */
	public static <T> T toObj(String json, Class<T> cls) {
		try {
			return SERDE_MAPPER.readValue(json, cls);
		} catch (IOException e) {
			throw new BulkDeserializationException(cls, e);
		}
	}

	/**
	 * Json string deserialize to Object.
	 *
	 * @param json json string
	 * @param type {@link Type} of object
	 * @param <T>  General type
	 * @return object
	 * @throws BulkDeserializationException if deserialize failed
	 */
	public static <T> T toObj(String json, Type type) {
		try {
			return SERDE_MAPPER.readValue(json, SERDE_MAPPER.constructType(type));
		} catch (IOException e) {
			throw new BulkDeserializationException(e);
		}
	}

	/**
	 * Json string deserialize to Object.
	 *
	 * @param json          json string
	 * @param typeReference {@link TypeReference} of object
	 * @param <T>           General type
	 * @return object
	 * @throws BulkDeserializationException if deserialize failed
	 */
	public static <T> T toObj(String json, TypeReference<T> typeReference) {
		try {
			return SERDE_MAPPER.readValue(json, typeReference);
		} catch (IOException e) {
			throw new BulkDeserializationException(typeReference.getClass(), e);
		}
	}

	/**
	 * Json string deserialize to Object.
	 *
	 * @param inputStream json string input stream
	 * @param type        {@link Type} of object
	 * @param <T>         General type
	 * @return object
	 * @throws BulkDeserializationException if deserialize failed
	 */
	public static <T> T toObj(InputStream inputStream, Type type) {
		try {
			return SERDE_MAPPER.readValue(inputStream, SERDE_MAPPER.constructType(type));
		} catch (IOException e) {
			throw new BulkDeserializationException(type, e);
		}
	}

	/**
	 * Json string deserialize to Jackson {@link JsonNode}.
	 *
	 * @param json json string
	 * @return {@link JsonNode}
	 * @throws BulkDeserializationException if deserialize failed
	 */
	public static JsonNode toObj(String json) {
		try {
			return SERDE_MAPPER.readTree(json);
		} catch (IOException e) {
			throw new BulkDeserializationException(e);
		}
	}

	/**
	 * Register sub type for child class.
	 *
	 * @param clz  child class
	 * @param type type name of child class
	 */
	public static void registerSubtype(Class<?> clz, String type) {
		SERDE_MAPPER.registerSubtypes(new NamedType(clz, type));
	}

	/**
	 * Create a new empty Jackson {@link ObjectNode}.
	 *
	 * @return {@link ObjectNode}
	 */
	public static ObjectNode createEmptyJsonNode() {
		return new ObjectNode(SERDE_MAPPER.getNodeFactory());
	}

	/**
	 * Create a new empty Jackson {@link ArrayNode}.
	 *
	 * @return {@link ArrayNode}
	 */
	public static ArrayNode createEmptyArrayNode() {
		return new ArrayNode(SERDE_MAPPER.getNodeFactory());
	}

	/**
	 * Parse object to Jackson {@link JsonNode}.
	 *
	 * @param obj object
	 * @return {@link JsonNode}
	 */
	public static JsonNode transferToJsonNode(Object obj) {
		return SERDE_MAPPER.valueToTree(obj);
	}

	/**
	 * construct java type -> Jackson Java Type.
	 *
	 * @param type java type
	 * @return JavaType {@link JavaType}
	 */
	public static JavaType constructJavaType(Type type) {
		return SERDE_MAPPER.constructType(type);
	}

}
